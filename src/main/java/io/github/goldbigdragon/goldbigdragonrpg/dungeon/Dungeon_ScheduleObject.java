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

package io.github.goldbigdragon.goldbigdragonrpg.dungeon;

import java.util.ArrayList;
import java.util.List;

import io.github.goldbigdragon.goldbigdragonrpg.effect.PacketUtil;
import io.github.goldbigdragon.goldbigdragonrpg.effect.SoundUtil;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_ServerOption;
import io.github.goldbigdragon.goldbigdragonrpg.structure.Struct_PostBox;
import io.github.goldbigdragon.goldbigdragonrpg.util.Util_Player;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.goldbigdragon.goldbigdragonrpg.servertick.ServerTick_Main;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;

public class Dungeon_ScheduleObject {
    private long UTC = 0;
    private int count = 0;
    private Player leader;
    private String Type = "D_RC";//Dungeon_RoomCreate
    private List<Character> Grid = new ArrayList<Character>();
    private List<Integer> GridLoc = new ArrayList<Integer>();
    private List<Character> KeyGrid = new ArrayList<Character>();
    private List<Integer> KeyGridLoc = new ArrayList<Integer>();
    private List<String> DungeonMaker = new ArrayList<String>();
    private byte size = 5;
    private String DungeonType = "Stone";
    private String DungeonName = null;
    private long StartX = 0;
    private int StartY = 0;
    private long StartZ = 0;
    private boolean BossRoomAdded = false;
    private long SpawnX = 0;
    private long SpawnZ = 0;
    private ItemStack item;

    public void CancelDungeonCreate() {
        count = -99;
        ServerTick_Main.DungeonSchedule.remove(this);
        if (leader.isOnline()) {
            if (leader.getInventory().firstEmpty() == -1) {
                new SoundUtil().SP(leader, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
                leader.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[SYSTEM] : 던전 생성 도중 퇴장하여, 제물로 넣은 아이템이 복구되었습니다!");
                new Struct_PostBox().SendPost_Server(leader.getUniqueId().toString(), "[시스템]", "[던전 생성 취소]", "던전 생성 도중 퇴장하여, 제물로 넣은 아이템이 복구되었습니다.", item);
            } else
                leader.getInventory().addItem(item);
        } else
            new Struct_PostBox().SendPost_Server(leader.getUniqueId().toString(), "[시스템]", "[던전 생성 취소]", "던전 생성 도중 퇴장하여, 제물로 넣은 아이템이 복구되었습니다.", item);

        for (short count = 0; count < getDungeonMakerSize(); count++) {
            if (Bukkit.getServer().getPlayer(DungeonMaker.get(count)) != null) {
                Player player = Bukkit.getServer().getPlayer(DungeonMaker.get(count));
                if (player != leader) {
                    if (player.isOnline()) {
                        if (player.getLocation().getWorld().getName().compareTo("Dungeon") == 0) {
                            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[SYSTEM] : 던전 생성자가 던전을 떠나, 던전 입장이 취소되었습니다!");
                            YamlController YC = new YamlController(Main_Main.plugin);
                            String DungeonName = Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_Enter();
                            long UTC = Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_UTC();
                            YamlManager PlayerConfig = YC.getNewConfig("Dungeon/Dungeon/" + DungeonName + "/Entered/" + UTC + ".yml");
                            if (PlayerConfig.contains("EnteredAlter")) {
                                DungeonName = PlayerConfig.getString("EnteredAlter");
                                PlayerConfig = YC.getNewConfig("Dungeon/AltarList.yml");
                                if (PlayerConfig.contains(DungeonName)) {
                                    Location loc = new Location(Bukkit.getServer().getWorld(PlayerConfig.getString(DungeonName + ".World")), PlayerConfig.getLong(DungeonName + ".X"), PlayerConfig.getLong(DungeonName + ".Y") + 1, PlayerConfig.getLong(DungeonName + ".Z"));
                                    player.teleport(loc);
                                    return;
                                }
                            }
                            new Util_Player().teleportToCurrentArea(player, true);
                            return;
                        }
                    }
                }
            }
        }
    }

    public void SendCreatingRate(int index) {
        if (leader == null) {
            CancelDungeonCreate();
            return;
        }
        if (leader.isOnline()) {
            if (leader.getLocation().getWorld().getName().compareTo("Dungeon") != 0) {
                CancelDungeonCreate();
                return;
            }
        } else {
            CancelDungeonCreate();
            return;
        }
        short total = (short) (Grid.size() + KeyGrid.size() - 1);
        for (short count = 0; count < getDungeonMakerSize(); count++) {
            if (Bukkit.getServer().getPlayer(DungeonMaker.get(count)) != null) {
                Player p = Bukkit.getServer().getPlayer(DungeonMaker.get(count));
                if (p.isOnline()) {
                    if (p.getLocation().getWorld().getName().compareTo("Dungeon") == 0) {
                        String Title = "\'" + ChatColor.BLUE + "[던전 생성]\'";
                        String SubTitle = "\'" + ChatColor.DARK_BLUE + "[ " + ChatColor.BLUE + index + ChatColor.DARK_BLUE + " / " + ChatColor.BLUE + total + ChatColor.DARK_BLUE + " ]\'";
                        new PacketUtil().sendTitleSubTitle(Bukkit.getServer().getPlayer(DungeonMaker.get(count)), Title, SubTitle, (byte) 0, (byte) 0, (byte) 1);
                    }
                }
            }
        }
        if (index >= total)
            for (int count = 0; count < getDungeonMakerSize(); count++) {
                if (Bukkit.getServer().getPlayer(DungeonMaker.get(count)) != null) {
                    Player p = Bukkit.getServer().getPlayer(DungeonMaker.get(count));
                    if (p.isOnline()) {
                        if (p.getLocation().getWorld().getName().compareTo("Dungeon") == 0) {
                            String Title = "\'" + ChatColor.WHITE + DungeonName + "\'";
                            new PacketUtil().sendTitle(p, Title, (byte) 2, (byte) 3, (byte) 2);
                        }
                    }
                }
            }

    }

    public Dungeon_ScheduleObject(String type) {
        this.UTC = ServerTick_Main.nowUTC;
        this.Type = type;
    }

    public boolean containsKeyImage(int loc) {
        if (KeyGridLoc.contains(loc))
            return true;
        else
            return false;
    }

    public char getUnderKeyImage(int loc) {
        for (int count = 0; count < GridLoc.size(); count++) {
            if (GridLoc.get(count) == loc)
                return Grid.get(count);
        }
        return '─';
    }

    public void addGridLoc(int loc) {
        GridLoc.add(GridLoc.size(), loc);
    }

    public void addKeyGridLoc(int loc) {
        KeyGridLoc.add(KeyGridLoc.size(), loc);
    }

    public void addGrid(char grid) {
        Grid.add(Grid.size(), grid);
    }

    public void addKeyGrid(char grid) {
        KeyGrid.add(KeyGrid.size(), grid);
    }

    public int getGridSize() {
        return Grid.size();
    }

    public int getKeyGridSize() {
        return KeyGrid.size();
    }

    public byte getSize() {
        return size;
    }

    public void setSize(byte size) {
        this.size = size;
    }

    public String getDungeonType() {
        return DungeonType;
    }

    public void setDungeonType(String dungeonType) {
        DungeonType = dungeonType;
    }

    public String getDungeonName() {
        return DungeonName;
    }

    public void setDungeonName(String dungeonName) {
        DungeonName = dungeonName;
    }

    public long getStartX() {
        return StartX;
    }

    public void setStartX(long startX) {
        StartX = startX;
    }

    public int getStartY() {
        return StartY;
    }

    public void setStartY(int startY) {
        StartY = startY;
    }

    public long getStartZ() {
        return StartZ;
    }

    public void setStartZ(long startZ) {
        StartZ = startZ;
    }

    public char getDirection(int index, char Direction) {
        int loc = GridLoc.get(index);
        switch (Direction) {
            case 'N':
                if (!GridLoc.contains(loc - size))
                    return (char) -1;
                loc = loc - size;
                break;
            case 'E':
                if (loc % size == size - 1)
                    return (char) -1;
                if (!GridLoc.contains(loc + 1))
                    return (char) -1;
                loc = loc + 1;
                break;
            case 'S':
                if (!GridLoc.contains(loc + size))
                    return (char) -1;
                loc = loc + size;
                break;
            case 'W':
                if (loc % size == 0)
                    return (char) -1;
                if (!GridLoc.contains(loc - 1))
                    return (char) -1;
                loc = loc - 1;
                break;
        }
        for (int count = 0; count < Grid.size(); count++) {
            if (GridLoc.get(count) == loc)
                return Grid.get(count);
        }
        return (char) -1;
    }

    public char getGrid(int index) {
        return Grid.get(index);
    }

    public void setGrid(List<Character> grid) {
        Grid = grid;
    }

    public int getGridLoc(int index) {
        return GridLoc.get(index);
    }

    public void setGridLoc(List<Integer> gridLoc) {
        GridLoc = gridLoc;
    }

    public char getKeyGrid(int index) {
        return KeyGrid.get(index);
    }

    public void setKeyGrid(List<Character> keyGrid) {
        KeyGrid = keyGrid;
    }

    public int getKeyGridLoc(int index) {
        return KeyGridLoc.get(index);
    }

    public void setKeyGridLoc(List<Integer> keyGridLoc) {
        KeyGridLoc = keyGridLoc;
    }

    public int getDungeonMakerSize() {
        return DungeonMaker.size();
    }

    public String getDungeonMaker(int index) {
        return DungeonMaker.get(index);
    }

    public void addDungeonMaker(String player) {
        DungeonMaker.add(DungeonMaker.size(), player);
    }

    public void setDungeonMaker(List<String> player) {
        DungeonMaker = player;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public void copyThisScheduleObject(long WillAddTick) {
        ServerTick_Main.DungeonSchedule.put(WillAddTick, this);
    }

    public long getUTC() {
        return UTC;
    }

    public void setUTC(long uTC) {
        UTC = uTC;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isBossRoomAdded() {
        return BossRoomAdded;
    }

    public void setBossRoomAdded(boolean bossRoomAdded) {
        BossRoomAdded = bossRoomAdded;
    }

    public long getSpawnX() {
        return SpawnX;
    }

    public void setSpawnX(long spawnX) {
        SpawnX = spawnX;
    }

    public long getSpawnZ() {
        return SpawnZ;
    }

    public void setSpawnZ(long spawnZ) {
        SpawnZ = spawnZ;
    }

    public void setLeader(Player p) {
        leader = p;
    }

    public Player getLeader() {
        return leader;
    }

    public void setItem(ItemStack usedItem) {
        item = usedItem;
    }

    public ItemStack getItem() {
        return item;
    }
}
