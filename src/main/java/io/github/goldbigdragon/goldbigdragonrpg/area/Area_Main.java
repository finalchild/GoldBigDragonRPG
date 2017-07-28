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

package io.github.goldbigdragon.goldbigdragonrpg.area;

import java.util.ArrayList;
import java.util.List;

import io.github.goldbigdragon.goldbigdragonrpg.effect.PacketUtil;
import io.github.goldbigdragon.goldbigdragonrpg.effect.SoundUtil;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_ServerOption;
import io.github.goldbigdragon.goldbigdragonrpg.servertick.ServerTick_Main;
import io.github.goldbigdragon.goldbigdragonrpg.servertick.ServerTick_Object;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Area_Main {
    public void addAreaList() {
        Main_ServerOption.AreaList.clear();
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager AreaList = YC.getNewConfig("Area/AreaList.yml");

        Object[] arealist = AreaList.getConfigurationSection("").getKeys(false).toArray();

        for (short count = 0; count < arealist.length; count++) {
            Area_Object AO = new Area_Object();
            AO.AreaName = arealist[count].toString();
            AO.minX = AreaList.getInt(arealist[count].toString() + ".X.Min");
            AO.maxX = AreaList.getInt(arealist[count].toString() + ".X.Max");
            AO.minY = AreaList.getInt(arealist[count].toString() + ".Y.Min");
            AO.maxY = AreaList.getInt(arealist[count].toString() + ".Y.Max");
            AO.minZ = AreaList.getInt(arealist[count].toString() + ".Z.Min");
            AO.maxZ = AreaList.getInt(arealist[count].toString() + ".Z.Max");
            if (Main_ServerOption.AreaList.containsKey(AreaList.getString(arealist[count].toString() + ".World"))) {
                List<Area_Object> areaList = Main_ServerOption.AreaList.get(AreaList.getString(arealist[count].toString() + ".World"));
                areaList.add(AO);
                Main_ServerOption.AreaList.remove(AreaList.getString(arealist[count].toString() + ".World"));
                Main_ServerOption.AreaList.put(AreaList.getString(arealist[count].toString() + ".World"), areaList);
            } else {
                ArrayList<Area_Object> areaList = new ArrayList<>();
                areaList.add(AO);
                Main_ServerOption.AreaList.put(AreaList.getString(arealist[count].toString() + ".World"), areaList);
            }
        }
    }

    public void CreateNewArea(Player player, Location loc1, Location loc2, String name) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager AreaList = YC.getNewConfig("Area/AreaList.yml");

        if (AreaList.contains(name)) {
            SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
            player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 이름은 이미 등록되어 있습니다!");
            return;
        }

        if (!loc1.getWorld().equals(loc2.getWorld())) {
            SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
            player.sendMessage(ChatColor.RED + "[SYSTEM] : 서로 다른 월드간은 영역 지정을 할 수 없습니다!");
            return;
        }
        loc1.add(1, 0, 0);
        loc2.add(0, 0, 1);
        AreaList.set(name + ".World", loc1.getWorld().getName());
        if (loc1.getX() > loc2.getX()) {
            AreaList.set(name + ".X.Min", loc2.getX());
            AreaList.set(name + ".X.Max", loc1.getX());
            AreaList.set(name + ".SpawnLocation.X", loc2.getX());
        } else {
            AreaList.set(name + ".X.Min", loc1.getX());
            AreaList.set(name + ".X.Max", loc2.getX());
            AreaList.set(name + ".SpawnLocation.X", loc1.getX());
        }
        if (loc1.getY() > loc2.getY()) {
            AreaList.set(name + ".Y.Min", loc2.getY());
            AreaList.set(name + ".Y.Max", loc1.getY());
            AreaList.set(name + ".SpawnLocation.Y", loc2.getY());
        } else {
            AreaList.set(name + ".Y.Min", loc1.getY());
            AreaList.set(name + ".Y.Max", loc2.getY());
            AreaList.set(name + ".SpawnLocation.Y", loc1.getY());
        }
        if (loc1.getZ() > loc2.getZ()) {
            AreaList.set(name + ".Z.Min", loc2.getZ());
            AreaList.set(name + ".Z.Max", loc1.getZ());
            AreaList.set(name + ".SpawnLocation.Z", loc2.getZ());
        } else {
            AreaList.set(name + ".Z.Min", loc1.getZ());
            AreaList.set(name + ".Z.Max", loc2.getZ());
            AreaList.set(name + ".SpawnLocation.Z", loc1.getZ());
        }
        AreaList.set(name + ".SpawnLocation.Pitch", 0);
        AreaList.set(name + ".SpawnLocation.Yaw", 0);
        AreaList.set(name + ".Name", name);
        AreaList.set(name + ".Description", name + "에 오신 것을 환영합니다.");
        AreaList.set(name + ".PVP", false);
        AreaList.set(name + ".BlockPlace", false);
        AreaList.set(name + ".BlockBreak", false);
        AreaList.set(name + ".BlockUse", false);
        AreaList.set(name + ".SpawnPoint", true);
        AreaList.set(name + ".MobSpawn", false);
        AreaList.set(name + ".Alert", true);
        AreaList.set(name + ".Music", false);
        AreaList.set(name + ".BGM", 0);
        AreaList.set(name + ".Priority", 5);
        AreaList.createSection(name + ".Monster");
        AreaList.createSection(name + ".MonsterSpawnRule");
        AreaList.createSection(name + ".Fishing.54");
        AreaList.createSection(name + ".Fishing.30");
        AreaList.createSection(name + ".Fishing.10");
        AreaList.createSection(name + ".Fishing.5");
        AreaList.createSection(name + ".Fishing.1");
        AreaList.createSection(name + ".Mining");
        AreaList.set(name + ".Restrict.MinNowLevel", 0);
        AreaList.set(name + ".Restrict.MaxNowLevel", 0);
        AreaList.set(name + ".Restrict.MinRealLevel", 0);
        AreaList.set(name + ".Restrict.MaxRealLevel", 0);
        AreaList.saveConfig();

        SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_CHICKEN_EGG, 2.0F, 1.7F);
        player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 지정 구역 등록 성공!");
        Area_Gui AGUI = new Area_Gui();
        AGUI.AreaSettingGUI(player, name);

        Area_Object AO = new Area_Object();
        AO.AreaName = name;
        AO.minX = AreaList.getInt(name + ".X.Min");
        AO.maxX = AreaList.getInt(name + ".X.Max");
        AO.minY = AreaList.getInt(name + ".Y.Min");
        AO.maxY = AreaList.getInt(name + ".Y.Max");
        AO.minZ = AreaList.getInt(name + ".Z.Min");
        AO.maxZ = AreaList.getInt(name + ".Z.Max");
        if (Main_ServerOption.AreaList.containsKey(AreaList.getString(name + ".World"))) {
            List<Area_Object> areaList = Main_ServerOption.AreaList.get(AreaList.getString(name + ".World"));
            areaList.add(AO);
            Main_ServerOption.AreaList.remove(AreaList.getString(name + ".World"));
            Main_ServerOption.AreaList.put(AreaList.getString(name + ".World"), areaList);
        } else {
            ArrayList<Area_Object> areaList = new ArrayList<>();
            areaList.add(AO);
            Main_ServerOption.AreaList.put(AreaList.getString(name + ".World"), areaList);
        }
    }

    public void RemoveArea(Player player, String name) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager AreaList = YC.getNewConfig("Area/AreaList.yml");

        if (Main_ServerOption.AreaList.get(AreaList.getString(name + ".World")).size() == 1)
            Main_ServerOption.AreaList.remove(AreaList.getString(name + ".World"));
        else {
            List<Area_Object> areaList = Main_ServerOption.AreaList.get(AreaList.getString(name + ".World"));
            for (short count = 0; count < areaList.size(); count++) {
                if (areaList.get(count).toString().compareTo(name) == 0) {
                    areaList.remove(count);
                    break;
                }
            }
            Main_ServerOption.AreaList.put(AreaList.getString(name + ".World"), areaList);
        }

        if (AreaList.contains(name)) {
            AreaList.removeKey(name + ".World");
            AreaList.removeKey(name + ".X.Min");
            AreaList.removeKey(name + ".X.Max");
            AreaList.removeKey(name + ".Y.Min");
            AreaList.removeKey(name + ".Y.Max");
            AreaList.removeKey(name + ".Z.Min");
            AreaList.removeKey(name + ".Z.Max");
            AreaList.removeKey(name + ".Name");
            AreaList.removeKey(name + ".SpawnLocation.X");
            AreaList.removeKey(name + ".SpawnLocation.Y");
            AreaList.removeKey(name + ".SpawnLocation.Z");
            AreaList.removeKey(name + ".SpawnLocation.Pitch");
            AreaList.removeKey(name + ".SpawnLocation.Yaw");
            AreaList.removeKey(name + ".Description");
            AreaList.removeKey(name + ".PVP");
            AreaList.removeKey(name + ".BlockBreak");
            AreaList.removeKey(name + ".BlockPlace");
            AreaList.removeKey(name + ".SpawnPoint");
            AreaList.removeKey(name + ".MobSpawn");
            AreaList.removeKey(name + ".Alert");
            AreaList.removeKey(name + ".Restrict.MinNowLevel");
            AreaList.removeKey(name + ".Restrict.MaxNowLevel");
            AreaList.removeKey(name + ".Restrict.MinRealLevel");
            AreaList.removeKey(name + ".Restrict.MaxRealLevel");
            AreaList.removeKey(name);
            AreaList.saveConfig();
            SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_CHICKEN_EGG, 2.0F, 1.7F);
            player.sendMessage(ChatColor.RED + "[SYSTEM] : 지정 구역 삭제 성공!");
        } else {
            SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
            player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 이름의 구역은 존재하지 않습니다!");
        }
    }

    public void OptionSetting(Player player, String AreaName, char type, String string) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager AreaList = YC.getNewConfig("Area/AreaList.yml");

        if (AreaList.contains(AreaName)) {
            switch (type) {
                case 0: {
                    AreaList.set(AreaName + ".Name", string);
                    player.sendMessage(ChatColor.GREEN + "[SYSTEM] : " + ChatColor.YELLOW + AreaName + ChatColor.GREEN + " 영역의 이름이 " + ChatColor.YELLOW + string + ChatColor.GREEN + " 으로 변경 되었습니다!");
                }
                break;
                case 1: {
                    AreaList.set(AreaName + ".Description", string);
                    player.sendMessage(ChatColor.GREEN + "[SYSTEM] : " + ChatColor.YELLOW + AreaName + ChatColor.GREEN + " 영역의 설명이 " + ChatColor.YELLOW + string + ChatColor.GREEN + " 으로 변경 되었습니다!");
                }
                break;
            }
            AreaList.saveConfig();
        } else {
            SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
            player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 이름의 구역은 존재하지 않습니다!");
        }
    }

    public String[] SearchAreaName(Location loc) {
        if (Main_ServerOption.AreaList.containsKey(loc.getWorld().getName())) {
            List<String> AreaName = new ArrayList<>();
            List<Area_Object> AreaList = Main_ServerOption.AreaList.get(loc.getWorld().getName());
            for (short count = 0; count < AreaList.size(); count++) {
                if (AreaList.get(count).minX <= loc.getX() && AreaList.get(count).maxX >= loc.getX())
                    if (AreaList.get(count).minY <= loc.getY() && AreaList.get(count).maxY >= loc.getY())
                        if (AreaList.get(count).minZ <= loc.getZ() && AreaList.get(count).maxZ >= loc.getZ())
                            AreaName.add(AreaList.get(count).AreaName);
            }

            if (AreaName.isEmpty())
                return null;
            else {
                YamlController YC = new YamlController(Main_Main.plugin);
                YamlManager AreaYM = YC.getNewConfig("Area/AreaList.yml");

                int TopPriority = -1000;

                String[] ret = new String[2];
                for (short count = 0; count < AreaName.size(); count++) {
                    if (!AreaYM.contains(AreaName.get(count) + ".Priority")) {
                        AreaYM.set(AreaName.get(count) + ".Priority", 5);
                        AreaYM.saveConfig();
                    }
                    int AreaPriority = AreaYM.getInt(AreaName.get(count) + ".Priority");
                    if (AreaPriority >= TopPriority) {
                        ret[0] = AreaName.get(count);
                        TopPriority = AreaPriority;
                    }
                }
                ret[1] = AreaYM.getString(ret[0] + ".Name");
                return ret;
            }
        }
        return null;
    }

    public String[] getAreaName(Player player) {
        return SearchAreaName(player.getLocation());
    }

    public String[] getAreaName(Entity entity) {
        return SearchAreaName(entity.getLocation());
    }

    public String[] getAreaName(Block block) {
        return SearchAreaName(block.getLocation());
    }

    public boolean getAreaOption(String AreaName, char type) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager AreaList = YC.getNewConfig("Area/AreaList.yml");

        switch (type) {
            case 0:
                return AreaList.getBoolean(AreaName + ".PVP");
            case 1:
                return AreaList.getBoolean(AreaName + ".BlockBreak");
            case 2:
                return AreaList.getBoolean(AreaName + ".SpawnPoint");
            case 3:
                return AreaList.getBoolean(AreaName + ".MobSpawn");
            case 4:
                return AreaList.getBoolean(AreaName + ".Alert");
            case 5:
                return AreaList.getBoolean(AreaName + ".BlockPlace");
            case 6:
                return AreaList.getBoolean(AreaName + ".Music");
            case 7:
                return AreaList.getBoolean(AreaName + ".BlockUse");
            case 8: {
                if (AreaList.contains(AreaName + ".MonsterSpawnRule")) {
                    if (AreaList.getConfigurationSection(AreaName + ".MonsterSpawnRule").getKeys(false).size() <= 0)
                        return false;
                    else
                        return true;
                } else
                    return false;
            }
        }
        return false;
    }

    public void sendAreaTitle(Player player, String AreaName) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager AreaList = YC.getNewConfig("Area/AreaList.yml");
        if (getAreaOption(AreaName, (char) 4)) {
            String Title = AreaList.getString(AreaName + ".Name").replace("%player%", player.getName());
            String Description = AreaList.getString(AreaName + ".Description").replace("%player%", player.getName());
            new PacketUtil().sendTitleSubTitle(player, "\'" + Title + "\'", "\'" + Description + "\'", (byte) 1, (byte) 10, (byte) 1);
        }
    }

    public void AreaMonsterSpawnAdd(String AreaName, String Count) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager AreaConfig = YC.getNewConfig("Area/AreaList.yml");
        if (Long.parseLong(Count) != -1) {
            if (AreaConfig.contains(AreaName + ".MonsterSpawnRule")) {
                if (AreaConfig.getConfigurationSection(AreaName + ".MonsterSpawnRule").getKeys(false).size() != 0) {
                    if (AreaConfig.getString(AreaName + ".MonsterSpawnRule." + Count + ".loc.world") != null) {
                        if (!ServerTick_Main.MobSpawningAreaList.contains(AreaName))
                            ServerTick_Main.MobSpawningAreaList.add(AreaName);

                        Long UTC = ServerTick_Main.nowUTC + 5;
                        for (; ; ) {
                            if (ServerTick_Main.Schedule.containsKey(UTC))
                                UTC = UTC + 1;
                            else
                                break;
                        }
                        ServerTick_Object OBJECT = new ServerTick_Object(UTC, "A_MS");
                        OBJECT.setString((byte) 0, AreaName);
                        OBJECT.setString((byte) 1, AreaConfig.getString(AreaName + ".MonsterSpawnRule." + Count + ".loc.world"));
                        if (AreaConfig.contains(AreaName + ".MonsterSpawnRule." + Count + ".Monster"))
                            OBJECT.setString((byte) 2, AreaConfig.getString(AreaName + ".MonsterSpawnRule." + Count + ".Monster"));
                        OBJECT.setString((byte) 3, Count);
                        OBJECT.setInt((byte) 0, AreaConfig.getInt(AreaName + ".MonsterSpawnRule." + Count + ".loc.x"));
                        OBJECT.setInt((byte) 1, AreaConfig.getInt(AreaName + ".MonsterSpawnRule." + Count + ".loc.y"));
                        OBJECT.setInt((byte) 2, AreaConfig.getInt(AreaName + ".MonsterSpawnRule." + Count + ".loc.z"));
                        OBJECT.setInt((byte) 3, AreaConfig.getInt(AreaName + ".MonsterSpawnRule." + Count + ".range"));
                        OBJECT.setInt((byte) 4, AreaConfig.getInt(AreaName + ".MonsterSpawnRule." + Count + ".count"));
                        OBJECT.setInt((byte) 5, AreaConfig.getInt(AreaName + ".MonsterSpawnRule." + Count + ".max"));
                        OBJECT.setMaxCount(AreaConfig.getInt(AreaName + ".MonsterSpawnRule." + Count + ".timer"));
                        ServerTick_Main.Schedule.put(UTC, OBJECT);
                    }
                }
            }
        } else {
            if (AreaConfig.contains(AreaName + ".MonsterSpawnRule")) {
                if (AreaConfig.getConfigurationSection(AreaName + ".MonsterSpawnRule").getKeys(false).size() != 0) {
                    if (!ServerTick_Main.MobSpawningAreaList.contains(AreaName)) {
                        ServerTick_Main.MobSpawningAreaList.add(AreaName);
                        Object[] RuleName = AreaConfig.getConfigurationSection(AreaName + ".MonsterSpawnRule").getKeys(false).toArray();
                        for (short count = 0; count < RuleName.length; count++) {
                            String ruleNumber = RuleName[count].toString();
                            if (AreaConfig.getString(AreaName + ".MonsterSpawnRule." + ruleNumber + ".loc.world") != null) {
                                Long UTC = ServerTick_Main.nowUTC + 5;
                                for (; ; ) {
                                    if (ServerTick_Main.Schedule.containsKey(UTC))
                                        UTC = UTC + 1;
                                    else
                                        break;
                                }
                                ServerTick_Object OBJECT = new ServerTick_Object(UTC, "A_MS");
                                OBJECT.setString((byte) 0, AreaName);
                                OBJECT.setString((byte) 1, AreaConfig.getString(AreaName + ".MonsterSpawnRule." + ruleNumber + ".loc.world"));
                                if (AreaConfig.contains(AreaName + ".MonsterSpawnRule." + ruleNumber + ".Monster"))
                                    OBJECT.setString((byte) 2, AreaConfig.getString(AreaName + ".MonsterSpawnRule." + ruleNumber + ".Monster"));
                                OBJECT.setString((byte) 3, ruleNumber);
                                OBJECT.setInt((byte) 0, AreaConfig.getInt(AreaName + ".MonsterSpawnRule." + ruleNumber + ".loc.x"));
                                OBJECT.setInt((byte) 1, AreaConfig.getInt(AreaName + ".MonsterSpawnRule." + ruleNumber + ".loc.y"));
                                OBJECT.setInt((byte) 2, AreaConfig.getInt(AreaName + ".MonsterSpawnRule." + ruleNumber + ".loc.z"));
                                OBJECT.setInt((byte) 3, AreaConfig.getInt(AreaName + ".MonsterSpawnRule." + ruleNumber + ".range"));
                                OBJECT.setInt((byte) 4, AreaConfig.getInt(AreaName + ".MonsterSpawnRule." + ruleNumber + ".count"));
                                OBJECT.setInt((byte) 5, AreaConfig.getInt(AreaName + ".MonsterSpawnRule." + ruleNumber + ".max"));
                                OBJECT.setMaxCount(AreaConfig.getInt(AreaName + ".MonsterSpawnRule." + ruleNumber + ".timer"));
                                ServerTick_Main.Schedule.put(UTC, OBJECT);
                            }
                        }
                    }
                }
            }
        }
    }
}
