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

package io.github.goldbigdragon.goldbigdragonrpg.main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import io.github.goldbigdragon.goldbigdragonrpg.admin.NewBie_Config;
import io.github.goldbigdragon.goldbigdragonrpg.area.Area_Main;
import io.github.goldbigdragon.goldbigdragonrpg.party.Party_Object;
import io.github.goldbigdragon.goldbigdragonrpg.servertick.ServerTick_Main;
import io.github.goldbigdragon.goldbigdragonrpg.servertick.ServerTick_ScheduleManager;
import io.github.goldbigdragon.goldbigdragonrpg.skill.Skill_Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.goldbigdragon.goldbigdragonrpg.area.Area_Object;
import io.github.goldbigdragon.goldbigdragonrpg.corpse.Corpse_Main;
import io.github.goldbigdragon.goldbigdragonrpg.monster.Monster_Object;
import io.github.goldbigdragon.goldbigdragonrpg.party.Party_DataManager;
import io.github.goldbigdragon.goldbigdragonrpg.user.User_Object;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;
import net.milkbowl.vault.economy.Economy;

public class Main_ServerOption {
    public static ArrayList<String> DungeonTheme = new ArrayList<>();

    public static Economy economy = null;

    public static String STR = "체력";
    public static String DEX = "솜씨";
    public static String INT = "지력";
    public static String WILL = "의지";
    public static String LUK = "행운";
    public static String Money = ChatColor.YELLOW + "" + ChatColor.BOLD + "Gold";
    public static String Damage = "대미지";
    public static String MagicDamage = "마법 대미지";

    public static byte Event_SkillPoint = 1;
    public static byte Event_StatPoint = 1;
    public static byte Event_Exp = 1;
    public static byte Event_DropChance = 1;
    public static byte Event_Proficiency = 1;

    public static byte LevelUpPerSkillPoint = 1;
    public static byte LevelUpPerStatPoint = 10;


    public static int MaxLevel = 100;
    public static int MaxSTR = 1500;
    public static int MaxDEX = 1500;
    public static int MaxINT = 1500;
    public static int MaxWILL = 1500;
    public static int MaxLUK = 1500;
    public static int EXPShareDistance = 50;
    public static long MaxDropMoney = 100000;

    public static String STR_Lore = "%enter%" + ChatColor.GRAY + " " + Main_ServerOption.STR + "은 플레이어의%enter%" + ChatColor.GRAY + " 물리적 공격력을%enter%" + ChatColor.GRAY + " 상승시켜 줍니다.%enter%";
    public static String DEX_Lore = "%enter%" + ChatColor.GRAY + " " + Main_ServerOption.DEX + "는 플레이어의%enter%" + ChatColor.GRAY + " 원거리 공격력을%enter%" + ChatColor.GRAY + " 상승시켜 줍니다.%enter%";
    public static String INT_Lore = "%enter%" + ChatColor.GRAY + " " + Main_ServerOption.INT + "은 플레이어가%enter%" + ChatColor.GRAY + " 사용하는 스킬 중%enter%" + ChatColor.GRAY + " " + Main_ServerOption.INT + " 영향을 받는%enter%" + ChatColor.GRAY + " 스킬 공격력을%enter%" + ChatColor.GRAY + " 상승시켜 줍니다.%enter%";
    public static String WILL_Lore = "%enter%" + ChatColor.GRAY + " " + Main_ServerOption.WILL + "는 플레이어의%enter%" + ChatColor.GRAY + " 크리티컬 및 스킬 중%enter%" + ChatColor.GRAY + " " + Main_ServerOption.WILL + " 영향을 받는%enter%" + ChatColor.GRAY + " 스킬 공격력을%enter%" + ChatColor.GRAY + " 상승시켜 줍니다.%enter%";
    public static String LUK_Lore = "%enter%" + ChatColor.GRAY + " " + Main_ServerOption.LUK + "은 플레이어에게%enter%" + ChatColor.GRAY + " 뜻하지 않은 일이 일어날%enter%" + ChatColor.GRAY + " 확률을 증가시킵니다.%enter%";

    public static boolean MoneySystem = false;
    public static short Money1ID = 348;
    public static byte Money1DATA = 0;
    public static short Money2ID = 371;
    public static byte Money2DATA = 0;
    public static short Money3ID = 147;
    public static byte Money3DATA = 0;
    public static short Money4ID = 266;
    public static byte Money4DATA = 0;
    public static short Money5ID = 41;
    public static byte Money5DATA = 0;
    public static short Money6ID = 41;
    public static byte Money6DATA = 0;

    public static String serverUpdate = "2017-07-08-12:35";
    public static String serverVersion = "Advanced";
    private static String updateCheckURL = "https://goldbigdragon.github.io/1_10.html";
    public static String currentServerUpdate = "2017-07-08-12:35";
    public static String currentServerVersion = "Advanced";

    public static java.util.Map<Long, Party_Object> Party = new LinkedHashMap<>();
    public static java.util.Map<Player, Long> PartyJoiner = new LinkedHashMap<>();

    public static Map<Player, Location> catchedLocation1 = new HashMap<>();
    public static Map<Player, Location> catchedLocation2 = new HashMap<>();

    public static Map<Player, String> PlayerUseSpell = new HashMap<>();
    public static Map<Player, ItemStack> PlayerlastItem = new HashMap<>();

    public static Map<String, List<Area_Object>> AreaList = new HashMap<>();
    public static Map<Player, String> PlayerCurrentArea = new HashMap<>();
    public static Map<String, User_Object> PlayerList = new HashMap<>();
    public static Map<String, Monster_Object> MonsterList = new HashMap<>();
    public static Map<String, String> MonsterNameMatching = new HashMap<>();

    public static boolean MagicSpellsCatched = false;
    public static boolean MagicSpellsEnable = false;
    public static boolean CitizensCatched = false;

    public static boolean Mapping = false;
    public static boolean AntiExplode = true;
    public static boolean PVP = true;

    public static boolean dualWeapon = true;

    public static ItemStack DeathRescue = null;
    public static ItemStack DeathRevive = null;


    public void Initialize() {
        new Area_Main().addAreaList();
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "[OK]" + ChatColor.DARK_GRAY + " 영역 정보 로드");
        Object[] players = Bukkit.getOnlinePlayers().toArray();
        for (short count = 0; count < players.length; count++) {
            Player p = ((Player) players[count]);
            new User_Object(p);
            if (new Area_Main().SearchAreaName(p.getLocation()) != null)
                PlayerCurrentArea.put(p, new Area_Main().SearchAreaName(p.getLocation())[0].toString());
            if (PlayerList.get(p.getUniqueId().toString()).isDeath())
                new Corpse_Main().CreateCorpse(p);
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "[OK]" + ChatColor.DARK_GRAY + " 플레이어 정보 로드");
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager Monster = YC.getNewConfig("Monster/MonsterList.yml");
        Object[] KeyList = Monster.getConfigurationSection("").getKeys(false).toArray();
        for (int count = 0; count < KeyList.length; count++) {
            String RealName = (String) KeyList[count];
            Monster_Object OM = new Monster_Object(RealName, Monster.getString(RealName + ".Name"), Monster.getLong(RealName + ".EXP"), Monster.getInt(RealName + ".HP"), Monster.getInt(RealName + ".MIN_Money"), Monster.getInt(RealName + ".MAX_Money"), Monster.getInt(RealName + ".STR"), Monster.getInt(RealName + ".DEX"), Monster.getInt(RealName + ".INT"), Monster.getInt(RealName + ".WILL"), Monster.getInt(RealName + ".LUK"), Monster.getInt(RealName + ".DEF"), Monster.getInt(RealName + ".Protect"), Monster.getInt(RealName + ".Magic_DEF"), Monster.getInt(RealName + ".Magic_Protect"));
            MonsterList.put(RealName, OM);
            MonsterNameMatching.put(Monster.getString(RealName + ".Name"), RealName);
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "[OK]" + ChatColor.DARK_GRAY + " 몬스터 정보 로드");

        File MusicFolder = new File(Main_Main.plugin.getDataFolder().getAbsolutePath() + "/NoteBlockSound/");
        if (!MusicFolder.exists())
            MusicFolder.mkdirs();
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "[OK]" + ChatColor.DARK_GRAY + " NBS 파일 로드");
        new Main_Config().CheckConfig(YC);
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "[OK]" + ChatColor.DARK_GRAY + " 콘피그 정보 로드");
        if (!YC.isExit("Skill/SkillList.yml"))
            new Skill_Config().CreateNewSkillList();
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "[OK]" + ChatColor.DARK_GRAY + " 스킬 정보 로드");
        if (!YC.isExit("Skill/JobList.yml"))
            new Skill_Config().CreateNewJobList();
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "[OK]" + ChatColor.DARK_GRAY + " 직업 정보 로드");
        if (!YC.isExit("ETC/NewBie.yml"))
            new NewBie_Config().CreateNewConfig();
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "[OK]" + ChatColor.DARK_GRAY + " 초보자 정보 로드");

        new Party_DataManager().loadParty();
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "[OK]" + ChatColor.DARK_GRAY + " 파티 정보 로드");

        YamlManager WorldConfig = YC.getNewConfig("WorldList.yml");
        Object[] worldname = WorldConfig.getKeys().toArray();
        for (short count = 0; count < WorldConfig.getKeys().size(); count++)
            if (Bukkit.getWorld(worldname[count].toString()) == null)
                WorldCreator.name(worldname[count].toString()).createWorld();
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "[OK]" + ChatColor.DARK_GRAY + " 월드 정보 로드");

        new ServerTick_Main(Main_Main.plugin);
        new ServerTick_ScheduleManager().loadCategoriFile();
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "[OK]" + ChatColor.DARK_GRAY + " 틱 정보 로드");

        if (!Bukkit.getServer().getOnlineMode())
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "비 정품 서버에서는 일부 기능이 정상적인 실행이 되지 않을수도 있습니다.");

        if (Bukkit.getWorld("Dungeon") == null) {
            WorldCreator.name("Dungeon").type(WorldType.FLAT).generateStructures(false).createWorld();
            Block block = null;
            for (byte count = 0; count < 21; count++) {
                for (byte count2 = 0; count2 < 21; count2++) {
                    byte id = 1;
                    if (count == 1 || count2 == 1 || count == 7 || count2 == 7 || count == 14 || count2 == 14 || count == 20 || count2 == 20)
                        id = 89;
                    block = new Location(Bukkit.getWorld("Dungeon"), -100 + count, 30, -100 + count2).getBlock();
                    block.setTypeIdAndData(id, (byte) 0, true);
                    block = new Location(Bukkit.getWorld("Dungeon"), -100 + count, 42, -100 + count2).getBlock();
                    block.setTypeIdAndData(id, (byte) 0, true);
                }
            }
            for (byte count = 0; count < 21; count++) {
                for (byte count2 = 0; count2 < 12; count2++) {
                    byte id = 1;
                    if (count2 == 1 || count2 == 11)
                        id = 89;
                    block = new Location(Bukkit.getWorld("Dungeon"), -100 + count, 30 + count2, -100).getBlock();
                    block.setTypeIdAndData(id, (byte) 0, true);
                    block = new Location(Bukkit.getWorld("Dungeon"), -100, 30 + count2, -100 + count).getBlock();
                    block.setTypeIdAndData(id, (byte) 0, true);
                    block = new Location(Bukkit.getWorld("Dungeon"), -100 + count, 30 + count2, -79).getBlock();
                    block.setTypeIdAndData(id, (byte) 0, true);
                    block = new Location(Bukkit.getWorld("Dungeon"), -79, 30 + count2, -100 + count).getBlock();
                    block.setTypeIdAndData(id, (byte) 0, true);
                }
            }
            block = new Location(Bukkit.getWorld("Dungeon"), -100 + 10, 31, -100 + 11).getBlock();
            block.setTypeIdAndData(138, (byte) 0, true);
            block = new Location(Bukkit.getWorld("Dungeon"), -100 + 11, 31, -100 + 11).getBlock();
            block.setTypeIdAndData(138, (byte) 0, true);
            block = new Location(Bukkit.getWorld("Dungeon"), -100 + 10, 31, -100 + 10).getBlock();
            block.setTypeIdAndData(138, (byte) 0, true);
            block = new Location(Bukkit.getWorld("Dungeon"), -100 + 11, 31, -100 + 10).getBlock();
            block.setTypeIdAndData(138, (byte) 0, true);


            YamlManager DungeonData = YC.getNewConfig("Dungeon/DungeonData.yml");
            DungeonData.set("StartPoint.X", 1000);
            DungeonData.set("StartPoint.Y", 30);
            DungeonData.set("StartPoint.Z", 1000);
            DungeonData.saveConfig();
        } else {
            Iterator<Entity> entityList = Bukkit.getWorld("Dungeon").getEntities().iterator();
            boolean isPlayerExist = false;
            while (entityList.hasNext()) {
                Entity entity = (entityList.next());
                if (entity.getType() == EntityType.PLAYER) {
                    Player p = (Player) entity;
                    if (p.isOnline()) {
                        isPlayerExist = true;
                        break;
                    }
                }
            }
            if (!isPlayerExist) {
                while (entityList.hasNext())
                    entityList.next().remove();
                YamlManager DungeonData = YC.getNewConfig("Dungeon/DungeonData.yml");
                if (DungeonData.getLong("StartPoint.X") >= 50000) {
                    DungeonData.set("StartPoint.X", 1000);
                    DungeonData.set("StartPoint.Y", 30);
                    DungeonData.set("StartPoint.Z", 1000);
                    DungeonData.saveConfig();
                }
            }
        }
        File directory = new File(Main_Main.plugin.getDataFolder() + "\\Dungeon\\Schematic");
        if (!directory.exists())
            directory.mkdir();
        File[] fileList = directory.listFiles();
        try {
            for (int count = 0; count < fileList.length; count++)
                if (!fileList[count].isFile()) {
                    File InnerDirectory = new File(Main_Main.plugin.getDataFolder() + "\\Dungeon\\Schematic\\" + fileList[count].getName());
                    File[] schematicList = InnerDirectory.listFiles();
                    if (schematicList.length != 25) {
                        ArrayList<String> DungeonFile = new ArrayList<>();
                        for (byte count2 = 0; count2 < schematicList.length; count2++)
                            DungeonFile.add(schematicList[count2].getName());
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Fail] " + fileList[count].getName() + " 던전 테마를 사용하려면 아래 구성물들이 더 필요합니다!");
                        if (!DungeonFile.contains("Boss.schematic"))
                            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "　 Boss.schematic");
                        for (byte count2 = 0; count2 < 4; count2++)
                            if (!DungeonFile.contains("Closed_Door" + count2 + ".schematic"))
                                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "　 Closed_Door" + count2 + ".schematic");
                        if (!DungeonFile.contains("CrossRoad.schematic"))
                            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "　 CrossRoad.schematic");
                        for (byte count2 = 0; count2 < 4; count2++)
                            if (!DungeonFile.contains("Door" + count2 + ".schematic"))
                                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "　 Door" + count2 + ".schematic");
                        for (byte count2 = 0; count2 < 4; count2++)
                            if (!DungeonFile.contains("LRoad" + count2 + ".schematic"))
                                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "　 LRoad" + count2 + ".schematic");
                        for (byte count2 = 0; count2 < 2; count2++)
                            if (!DungeonFile.contains("Road" + count2 + ".schematic"))
                                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "　 Road" + count2 + ".schematic");
                        if (!DungeonFile.contains("Room.schematic"))
                            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "　 Room.schematic");
                        for (byte count2 = 0; count2 < 4; count2++)
                            if (!DungeonFile.contains("TRoad" + count2 + ".schematic"))
                                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "　 TRoad" + count2 + ".schematic");
                        for (byte count2 = 0; count2 < 4; count2++)
                            if (!DungeonFile.contains("Wall" + count2 + ".schematic"))
                                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "　 Wall" + count2 + ".schematic");
                    } else {
                        DungeonTheme.add(fileList[count].getName());
                        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "[던전 테마 추가] " + fileList[count].getName());
                    }
                }
        } catch (Exception e) {
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "[OK]" + ChatColor.DARK_GRAY + " 던전 정보 로드");

        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "──────────────────────");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "I changed My Symbol!");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Like this Oriental Dragon...");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Because some peoples claimed");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "my original Dragon symbol...");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "(They said my original symbol");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "looks like the 'Nazi''s Hakenkreuz)");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "　");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　　◆");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　◆　◆");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　◆　　　◆");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　　　◆");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　　◆　　　◆");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　◆　　　◆　◆");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　◆　　　◆　　　◆");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　◆　　　◆　　　　　◆");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　◆　◆　　　　　　　◆");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　◆　　　　　　　　　◆");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "　GoldBigDragon Advanced");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "http://cafe.naver.com/goldbigdragon");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "　　　　");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "──────────────────────");

        VersionCheck();
    }


    public void VersionCheck() {
        BufferedInputStream in = null;
        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(updateCheckURL);
            URLConnection urlConnection = url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream());
            byte[] bufRead = new byte[10];
            int lenRead = 0;

            while ((lenRead = in.read(bufRead)) > 0)
                sb.append(new String(bufRead, 0, lenRead));
            String[] Parsed = sb.toString().split("<br>");

            String Version = Parsed[1].split(": ")[1];
            String Update = Parsed[2].split(": ")[1];

            Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "최신 버전 : " + Version);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "현재 버전 : " + serverVersion);
            Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "최신 패치 : " + Update);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "현재 패치 : " + serverUpdate);

            currentServerUpdate = Update;
            currentServerVersion = Version;
            if (serverVersion.compareTo(Version) == 0 && serverUpdate.compareTo(Update) == 0)
                Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[ ! ] 현재 GoldBigDragonRPG는 최신 버전입니다!");
            else {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ ! ] GoldBigDragonRPG가 최신 버전이 아닙니다! 아래 주소에서 다운로드 받으세요!");
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ ! ] http://cafe.naver.com/goldbigdragon/57885");
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ ! ] 패치가 필요한 이유 : " + Parsed[3].split(": ")[1]);
            }

        } catch (IOException ioe) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[버전 체크 오류] 인터넷 연결을 확인 해 주세요!");
        }
    }

    public void MagicSpellCatch() {
        if (!MagicSpellsCatched) {
            MagicSpellsCatched = true;
            if (!Bukkit.getPluginManager().isPluginEnabled("MagicSpells")) {
                ErrorMessage();
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "MagicSpells 플러그인을 찾을 수 없습니다!");
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "MagicSpells 다운로드 URL");
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "http://nisovin.com/magicspells/Start");
            } else {
                MagicSpellsEnable = true;
                new io.github.goldbigdragon.goldbigdragonrpg.dependency.SpellMain(Main_Main.plugin);
            }
        }
    }

    public void CitizensCatch() {
        if (!CitizensCatched) {
            CitizensCatched = true;
            if (!Bukkit.getPluginManager().isPluginEnabled("Citizens")) {
                ErrorMessage();
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Citizens 플러그인을 찾을 수 없습니다!");
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Citizens 다운로드 URL");
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "http://www.curse.com/bukkit-plugins/minecraft/citizens#t1:other-downloads");
            } else
                new io.github.goldbigdragon.goldbigdragonrpg.dependency.CitizensMain(Main_Main.plugin);
        }
    }

    public void ErrorMessage() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　［경　고］");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　　　■");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　　■■■");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　　■■■");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　■■■■■");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　　■■　■■");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　■■■　■■■");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　　■■■　■■■");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　■■■■■■■■■");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "　■■■■　■■■■");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "■■■■■■■■■■■");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[플레이에 지장은 없습니다]");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "");
    }
}
