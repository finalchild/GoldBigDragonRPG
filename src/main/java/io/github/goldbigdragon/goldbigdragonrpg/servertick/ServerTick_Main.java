/*
 * Copyright 2015, 2016, 2017 GoldBigDragon
 * Copyright 2017 Final Child
 *
 * This file is part of GoldBigDragonRPG.
 *
 * GoldBigDragonRPG is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 2 of the License.
 *
 * GoldBigDragonRPG is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GoldBigDragonRPG.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.goldbigdragon.goldbigdragonrpg.servertick;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import io.github.goldbigdragon.goldbigdragonrpg.area.Area_Main;
import io.github.goldbigdragon.goldbigdragonrpg.area.Area_ServerTask;
import io.github.goldbigdragon.goldbigdragonrpg.corpse.Corpse_Main;
import io.github.goldbigdragon.goldbigdragonrpg.dungeon.Dungeon_ScheduleObject;
import io.github.goldbigdragon.goldbigdragonrpg.dungeon.Dungeon_ServerTask;
import io.github.goldbigdragon.goldbigdragonrpg.effect.Effect_Packet;
import io.github.goldbigdragon.goldbigdragonrpg.effect.Effect_Sound;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_ServerOption;
import io.github.goldbigdragon.goldbigdragonrpg.quest.Quest_GUI;
import io.github.goldbigdragon.goldbigdragonrpg.user.User_Object;
import io.github.goldbigdragon.goldbigdragonrpg.util.ETC;
import io.github.goldbigdragon.goldbigdragonrpg.util.Util_Number;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

class Object_RankingSet {
    String stringValue;
    long longValue;

    public Object_RankingSet(String stringValue, long longValue) {
        this.stringValue = stringValue;
        this.longValue = longValue;
    }

    public Long getLongValue() {
        return longValue;
    }
}

class Descending_longValue implements Comparator<Object_RankingSet> {
    @Override
    public int compare(Object_RankingSet o1, Object_RankingSet o2) {
        return o2.getLongValue().compareTo(o1.getLongValue());
    }
}

public class ServerTick_Main {
    public static ArrayList<String> MobSpawningAreaList = new ArrayList<String>();
    public static ArrayList<String> NaviUsingList = new ArrayList<String>();
    public static HashMap<String, String> PlayerTaskList = new HashMap<String, String>();
    public static String ServerTask = "null";
    public static HashMap<Long, ServerTick_Object> Schedule = new HashMap<Long, ServerTick_Object>();
    public static HashMap<Long, Dungeon_ScheduleObject> DungeonSchedule = new HashMap<Long, Dungeon_ScheduleObject>();
    public static long nowUTC = 0;
    int BroadCastMessageTime = 0;
    int BroadCastMessageCool = 0;
    public static short SafeLine = 100;
    public static byte oneSec = 0;
    //초당 최대 실행 스케줄 갯수 설정
    //(값이 클수록 초당 많은 작업을 하지만, 그만큼 서버에 부담이 된다.)

    public ServerTick_Main() {
    }

    public ServerTick_Main(JavaPlugin plugin) {
        nowUTC = new ETC().getNowUTC();
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                nowUTC += 50;
                CheckShcedule();
            }
        }, 0, 1);

        Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
            Iterator<Player> PlayerList;
            Corpse_Main CCM = new Corpse_Main();
            io.github.goldbigdragon.goldbigdragonrpg.dependency.NoteBlockAPIMain NBAPI = new io.github.goldbigdragon.goldbigdragonrpg.dependency.NoteBlockAPIMain();
            YamlController YC = new YamlController(Main_Main.plugin);
            Area_Main A = new Area_Main();
            Quest_GUI QGUI = new Quest_GUI();
            String Area;
            String QuestName;
            User_Object uo;

            @Override
            public void run() {
                BroadCastMessage();

                PlayerList = (Iterator<Player>) Bukkit.getOnlinePlayers().iterator();
                Player player;
                YamlManager AreaList = YC.getNewConfig("config.yml");
                boolean isMabinogi = AreaList.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System");
                while (PlayerList.hasNext()) {
                    player = PlayerList.next();
                    uo = Main_ServerOption.PlayerList.get(player.getUniqueId().toString());
                    CCM.asyncDeathCapture(player);
                    if (player.getLocation().getWorld().getName().compareTo("Dungeon") != 0) {
                        if (uo.getDungeon_Enter() != null) {
                            NBAPI.Stop(player);
                            uo.setDungeon_Enter(null);
                            uo.setDungeon_UTC(-1);
                        }
                        if (A.getAreaName(player) != null) {
                            if (uo.getETC_CurrentArea() == null)
                                uo.setETC_CurrentArea("null");
                            Area = A.getAreaName(player)[0];
                            if (uo.getETC_CurrentArea().compareTo(Area) != 0) {
                                AreaList = YC.getNewConfig("Area/AreaList.yml");
                                //레벨 제한 확인
                                boolean restrict = false;
                                if (AreaList.getInt(Area + ".Restrict.MinNowLevel") != 0 && (AreaList.getInt(Area + ".Restrict.MinNowLevel") > uo.getStat_Level() || AreaList.getInt(Area + ".Restrict.MaxNowLevel") < uo.getStat_Level()))
                                    restrict = true;
                                if (isMabinogi && (AreaList.getInt(Area + ".Restrict.MinRealLevel") != 0 && (AreaList.getInt(Area + ".Restrict.MinRealLevel") > uo.getStat_RealLevel() || AreaList.getInt(Area + ".Restrict.MaxRealLevel") < uo.getStat_RealLevel())))
                                    restrict = true;
                                if (restrict) {
                                    Location playerLoc = player.getLocation();
                                    int calc1 = AreaList.getInt(Area + ".X.Max") - playerLoc.getBlockX();
                                    int calc2 = AreaList.getInt(Area + ".X.Min") - playerLoc.getBlockX();
                                    int staticX = AreaList.getInt(Area + ".X.Min");
                                    int staticZ = AreaList.getInt(Area + ".Z.Min");

                                    int xF = 0;
                                    int zF = 0;

                                    if (calc1 < 0)
                                        calc1 *= -1;
                                    if (calc2 < 0)
                                        calc2 *= -1;
                                    if (calc1 < calc2) {
                                        staticX = AreaList.getInt(Area + ".X.Max") + 1;
                                        xF = calc1;
                                    } else {
                                        staticX = AreaList.getInt(Area + ".X.Min") - 1;
                                        xF = calc2;
                                    }

                                    calc1 = AreaList.getInt(Area + ".Z.Max") - playerLoc.getBlockZ();
                                    calc2 = AreaList.getInt(Area + ".Z.Min") - playerLoc.getBlockZ();
                                    calc1 = AreaList.getInt(Area + ".Z.Max") - playerLoc.getBlockZ();
                                    calc2 = AreaList.getInt(Area + ".Z.Min") - playerLoc.getBlockZ();
                                    if (calc1 < 0)
                                        calc1 *= -1;
                                    if (calc2 < 0)
                                        calc2 *= -1;
                                    if (calc1 < calc2) {
                                        staticZ = AreaList.getInt(Area + ".Z.Max") + 1;
                                        zF = calc1;
                                    } else {
                                        staticZ = AreaList.getInt(Area + ".Z.Min") - 1;
                                        zF = calc2;
                                    }

                                    new Effect_Sound().SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
                                    new Effect_Packet().sendActionBar(player, "§c§l레벨이 맞지 않아 입장할 수 없습니다!");
                                    if (xF < zF)
                                        player.teleport(new Location(player.getWorld(), staticX, playerLoc.getY() + 0.2, playerLoc.getZ(), playerLoc.getYaw(), playerLoc.getPitch()));
                                    else
                                        player.teleport(new Location(player.getWorld(), playerLoc.getX(), playerLoc.getY() + 0.2, staticZ, playerLoc.getYaw(), playerLoc.getPitch()));
                                } else {
                                    uo.setETC_CurrentArea(Area);
                                    Main_ServerOption.PlayerCurrentArea.put(player, Area);
                                    A.AreaMonsterSpawnAdd(Area, "-1");
                                    NBAPI.Stop(player);
                                    uo.setETC_CurrentArea(Area);
                                    if (AreaList.getBoolean(Area + ".SpawnPoint"))
                                        uo.setETC_LastVisited(Area);
                                    if (uo.isBgmOn()) {
                                        if (AreaList.getBoolean(Area + ".Music"))
                                            NBAPI.Play(player, AreaList.getInt(Area + ".BGM"));
                                    }
                                    if (AreaList.getBoolean(Area + ".Alert")) {
                                        YamlManager QuestList = YC.getNewConfig("Quest/QuestList.yml");
                                        YamlManager PlayerQuestList = YC.getNewConfig("Quest/PlayerData/" + player.getUniqueId() + ".yml");

                                        Object[] b = PlayerQuestList.getConfigurationSection("Started").getKeys(false).toArray();
                                        for (short count = 0; count < b.length; count++) {
                                            QuestName = (String) b[count];
                                            if (PlayerQuestList.getString("Started." + QuestName + ".Type").compareTo("Visit") == 0) {
                                                if (PlayerQuestList.getString("Started." + QuestName + ".AreaName").compareTo(Area) == 0) {
                                                    PlayerQuestList.set("Started." + QuestName + ".Type", QuestList.getString(QuestName + ".FlowChart." + (PlayerQuestList.getInt("Started." + QuestName + ".Flow") + 1) + ".Type"));
                                                    PlayerQuestList.set("Started." + QuestName + ".Flow", PlayerQuestList.getInt("Started." + QuestName + ".Flow") + 1);
                                                    PlayerQuestList.removeKey("Started." + QuestName + ".AreaName");
                                                    PlayerQuestList.saveConfig();
                                                    QGUI.QuestRouter(player, QuestName);
                                                    break;
                                                }
                                            }
                                        }
                                        A.sendAreaTitle(player, Area);
                                    }
                                }
                            }
                        } else {
                            Main_ServerOption.PlayerCurrentArea.put(player, "null");
                            uo.setETC_CurrentArea("null");
                            NBAPI.Stop(player);
                        }
                    }
                }


            }
        }, 0, 10);

        Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
            File directory = new File(Main_Main.plugin.getDataFolder() + "\\Stats");

            @Override
            public void run() {
                if (!directory.exists())
                    directory.mkdir();
                File[] fileList = directory.listFiles();
                YamlController YC = new YamlController(Main_Main.plugin);
                YamlManager YAML = null;
                ArrayList<Object_RankingSet> MoneyRankingSet = new ArrayList<Object_RankingSet>();
                Object_RankingSet ORS = null;
                try {
                    for (int count = 0; count < fileList.length; count++)
                        if (fileList[count].isFile()) {
                            YAML = YC.getNewConfig("Stats/" + fileList[count].getName());
                            ORS = new Object_RankingSet(YAML.getString("Player.Name"), YAML.getLong("Stat.Money"));
                            MoneyRankingSet.add(ORS);
                        }
                } catch (Exception e) {
                }
                Collections.sort(MoneyRankingSet, new Descending_longValue());
                YAML = YC.getNewConfig("Ranking/money.yml");
                YAML.removeKey("Rank");
                YAML.removeKey("NameSet");
                for (int count = 0; count < MoneyRankingSet.size(); count++) {
                    YAML.set("Rank." + count + ".Name", MoneyRankingSet.get(count).stringValue);
                    YAML.set("Rank." + count + ".Money", MoneyRankingSet.get(count).longValue);
                    YAML.set("NameSet." + MoneyRankingSet.get(count).stringValue + ".Rank", count);
                    YAML.set("NameSet." + MoneyRankingSet.get(count).stringValue + ".Money", MoneyRankingSet.get(count).longValue);
                }
                YAML.saveConfig();
            }
        }, 0, 1200);//1분마다
    }

    public void BroadCastMessage() {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager Config = YC.getNewConfig("config.yml");
        BroadCastMessageTime = Config.getInt("Server.BroadCastSecond");
        if (BroadCastMessageTime != 0)
            if (BroadCastMessageCool >= BroadCastMessageTime) {
                BroadCastMessageCool = 0;
                YamlManager BroadCast = YC.getNewConfig("BroadCast.yml");
                if (BroadCast.contains("0"))
                    if (BroadCast.getConfigurationSection("").getKeys(false).toArray().length != 0)
                        Bukkit.broadcastMessage(BroadCast.getString(BroadCast.getConfigurationSection("").getKeys(false).toArray()[new Util_Number().RandomNum(0, BroadCast.getConfigurationSection("").getKeys(false).toArray().length - 1)].toString()));
            } else
                BroadCastMessageCool++;
    }

    public void CheckShcedule() {
        Object[] ScheduleList = Schedule.keySet().toArray();
        Object[] DungeonScheduleList = DungeonSchedule.keySet().toArray();
        long scheduleUTC = 0;
        short SafeLineCounter = SafeLine;
        for (short count = 0; count < ScheduleList.length; count++) {
            if (SafeLineCounter <= 0) break;
            scheduleUTC = Long.parseLong(ScheduleList[count].toString());
            if (scheduleUTC <= nowUTC) {
                ExcuteSchedule(scheduleUTC);
                SafeLineCounter--;
                Schedule.remove(Long.parseLong(ScheduleList[count].toString()));
            }
        }
        SafeLineCounter = SafeLine;
        for (short count = 0; count < DungeonScheduleList.length; count++) {
            if (SafeLineCounter <= 0) break;
            scheduleUTC = Long.parseLong(DungeonScheduleList[count].toString());
            if (scheduleUTC <= nowUTC) {
                DungeonExcuteSchedule(scheduleUTC);
                SafeLineCounter--;
                DungeonSchedule.remove(Long.parseLong(DungeonScheduleList[count].toString()));
            }
        }
    }

    public void ExcuteSchedule(long UTC) {
        String Type = Schedule.get(UTC).getType();
        switch (Type) {
            case "A_MS"://Area_MonsterSpawn
                new Area_ServerTask().AreaMobSpawn(UTC);
                return;
            case "A_RB"://Area_RegenBlock
                new Area_ServerTask().AreaRegenBlock(UTC);
                return;
            case "NV"://Navigation
                new ServerTask_Navigation().Navigation(UTC);
                return;
            case "P_EC"://Player_Exchange
                new ServerTask_Player().ExChangeTimer(UTC);
                return;
            case "G_SM"://Gamble_SlotMachine
                new ServerTask_Player().Gamble_SlotMachine_Rolling(UTC);
                return;
            case "C_S"://Create_Structure
                new ServerTask_Server().CreateStructureMain(UTC);
                return;
            case "Sound":
                new ServerTask_Effect().PlaySoundEffect(Schedule.get(UTC));
                return;
            case "P_UTS":
                new ServerTask_Player().UseTeleportScroll(UTC);
                return;
            default:
        }
    }

    public void DungeonExcuteSchedule(long UTC) {
        String Type = DungeonSchedule.get(UTC).getType();
        switch (Type) {
            case "D_RC"://Dungeon_RoomCreate
                new Dungeon_ServerTask().CreateRoom(DungeonSchedule.get(UTC));
                return;
            case "D_KRC"://Dungeon_KeyRoomCreate
                new Dungeon_ServerTask().CreateKeyRoom(DungeonSchedule.get(UTC));
                return;
            default:
        }
    }
}
