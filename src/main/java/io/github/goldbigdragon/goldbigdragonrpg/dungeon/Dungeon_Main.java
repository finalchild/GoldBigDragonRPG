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
import java.util.Arrays;

import io.github.goldbigdragon.goldbigdragonrpg.effect.PacketUtil;
import io.github.goldbigdragon.goldbigdragonrpg.effect.ParticleUtil;
import io.github.goldbigdragon.goldbigdragonrpg.effect.SoundUtil;
import io.github.goldbigdragon.goldbigdragonrpg.listener.Main_ItemDrop;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import io.github.goldbigdragon.goldbigdragonrpg.monster.Monster_Spawn;
import io.github.goldbigdragon.goldbigdragonrpg.servertick.ServerTick_Main;
import io.github.goldbigdragon.goldbigdragonrpg.servertick.ServerTick_Object;
import io.github.goldbigdragon.goldbigdragonrpg.util.Util_Number;
import io.github.goldbigdragon.goldbigdragonrpg.util.Util_Player;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.goldbigdragon.goldbigdragonrpg.main.Main_ServerOption;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;

public class Dungeon_Main {

    public void IronDoorOpening(Location loc) {
        ServerTick_Object STSO = new ServerTick_Object(0, "Sound");
        STSO.setType("Sound");
        STSO.setString((byte) 1, loc.getWorld().getName());
        STSO.setInt((byte) 0, (int) loc.getX());
        STSO.setInt((byte) 1, (int) loc.getY());
        STSO.setInt((byte) 2, (int) loc.getZ());
        STSO.setString((byte) 0, "0000001");//소리 구성
        STSO.setInt((byte) 3, 20);//소리 크기
        STSO.setInt((byte) 4, 5);//소리 속도

        STSO.setInt((byte) 5, 1);//틱 설정
        STSO.setMaxCount(STSO.getString((byte) 0).length());
        STSO.setTick(ServerTick_Main.nowUTC);
        ServerTick_Main.Schedule.put(ServerTick_Main.nowUTC, STSO);
    }

    public void DungeonClear(Player player, Location BossLoc) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/" + Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_Enter() + "/Option.yml");

        int Reward_M = DungeonConfig.getInt("Reward.Money");
        int Reward_E = DungeonConfig.getInt("Reward.EXP");

        ItemStack item = new ItemStack(292);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ChatColor.GREEN + "" + ChatColor.BLACK + "" + ChatColor.GREEN + "" + ChatColor.WHITE + "" + ChatColor.BOLD + "[보상 상자 열쇠]");
        im.setLore(Arrays.asList("", ChatColor.WHITE + "보상 상자를 열 수 있는", ChatColor.WHITE + "낡은 열쇠이다."));
        im.addEnchant(Enchantment.DURABILITY, 6000, true);
        item.setItemMeta(im);

        if (Main_ServerOption.PartyJoiner.containsKey(player)) {
            Player[] partyMember = Main_ServerOption.Party.get(Main_ServerOption.PartyJoiner.get(player)).getMember();
            for (short count = 0; count < partyMember.length; count++) {
                Long target = Main_ServerOption.PlayerList.get(partyMember[count].getUniqueId().toString()).getDungeon_UTC();
                if (target.equals(Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_UTC())) {
                    {
                        SoundUtil.playSound(partyMember[count], Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.8F);
                        new Util_Player().giveItemDrop(partyMember[count], item, partyMember[count].getLocation());
                        new Util_Player().DungeonClear(partyMember[count], Reward_M, Reward_E);
                    }
                }
            }
        } else {
            SoundUtil.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.8F);
            new Util_Player().giveItemDrop(player, item, player.getLocation());
            new Util_Player().DungeonClear(player, Reward_M, Reward_E);
        }

        BossLoc.add(3, -1, -30);
        IronDoorOpening(BossLoc);
        Block block = null;
        for (int count = 0; count < 7; count++) {
            for (int count2 = 0; count2 < 5; count2++) {
                block = BossLoc.add(-1, 0, 0).getBlock();
                block.setType(Material.AIR, true);
            }
            block = BossLoc.add(5, 1, 0).getBlock();
        }
        BossLoc.add(0, -7, -1);
        for (int count = 0; count < 7; count++) {
            for (int count2 = 0; count2 < 5; count2++) {
                block = BossLoc.add(-1, 0, 0).getBlock();
                block.setType(Material.AIR, true);
            }
            block = BossLoc.add(5, 1, 0).getBlock();
        }
    }

    public void BossRoomOpen(Player player, Location BossLoc, String DungeonName) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/" + Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_Enter() + "/Option.yml");
        int SoundTrack = DungeonConfig.getInt("BGM.BOSS");

        io.github.goldbigdragon.goldbigdragonrpg.dependency.NoteBlockAPIMain NBAPIM = new io.github.goldbigdragon.goldbigdragonrpg.dependency.NoteBlockAPIMain();
        if (Main_ServerOption.PartyJoiner.containsKey(player)) {
            Player[] partyMember = Main_ServerOption.Party.get(Main_ServerOption.PartyJoiner.get(player)).getMember();
            for (short count = 0; count < partyMember.length; count++) {
                if (Main_ServerOption.PlayerList.get(partyMember[count].getUniqueId().toString()).isBgmOn()) {
                    Long target = Main_ServerOption.PlayerList.get(partyMember[count].getUniqueId().toString()).getDungeon_UTC();
                    if (target.equals(Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_UTC())) {
                        NBAPIM.Stop(partyMember[count]);
                        NBAPIM.Play(partyMember[count], SoundTrack);
                    }
                }
            }
        } else {
            NBAPIM.Stop(player);
            NBAPIM.Play(player, SoundTrack);
        }

        YamlManager MonsterConfig = YC.getNewConfig("Dungeon/Dungeon/" + DungeonName + "/Monster.yml");
        Object[] MobList = null;
        if (MonsterConfig.getConfigurationSection("Boss").getKeys(false).size() != 0)
            MobList = MonsterConfig.getConfigurationSection("Boss").getKeys(false).toArray();

        int XYZloc[] = new int[3];
        XYZloc[0] = (int) BossLoc.getX();
        XYZloc[1] = (int) BossLoc.getY();
        XYZloc[2] = (int) BossLoc.getZ();
        byte GroupNumber = (byte) new Util_Number().RandomNum(0, 15);
        char Group = '0';
        switch (GroupNumber) {
            case 0:
                Group = '0';
                break;
            case 1:
                Group = '1';
                break;
            case 2:
                Group = '2';
                break;
            case 3:
                Group = '3';
                break;
            case 4:
                Group = '4';
                break;
            case 5:
                Group = '5';
                break;
            case 6:
                Group = '6';
                break;
            case 7:
                Group = '7';
                break;
            case 8:
                Group = '8';
                break;
            case 9:
                Group = '9';
                break;
            case 10:
                Group = 'a';
                break;
            case 11:
                Group = 'b';
                break;
            case 12:
                Group = 'c';
                break;
            case 13:
                Group = 'd';
                break;
            case 14:
                Group = 'e';
                break;
            case 15:
                Group = 'f';
                break;
        }

        if (MobList == null || MobList.length == 0)
            DungeonClear(player, BossLoc);
        else {
            Monster_Spawn MC = new Monster_Spawn();
            for (int count = 0; count < MobList.length; count++) {
                BossLoc.add(0, 0.2, 0);
                SoundUtil.playSound(BossLoc, Sound.ENTITY_WITHER_DEATH, 1.3F, 1.8F);
                MC.SpawnMob(BossLoc, MonsterConfig.getString("Boss." + MobList[count].toString()), (byte) 4, XYZloc, Group, true);
            }
        }
    }

    public void EraseAllDungeonKey(Player player, boolean isDrop) {
        ItemStack item = new ItemStack(292);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ChatColor.GREEN + "" + ChatColor.BLACK + "" + ChatColor.GREEN + "" + ChatColor.WHITE + "" + ChatColor.BOLD + "[던전 룸 열쇠]");
        im.setLore(Arrays.asList("", ChatColor.WHITE + "던전 룸을 열 수 있는", ChatColor.WHITE + "낡은 열쇠이다."));
        im.addEnchant(Enchantment.DURABILITY, 6000, true);
        item.setItemMeta(im);
        if (isDrop)
            new Util_Player().dropItem(player, item, true);
        else
            new Util_Player().deleteItem(player, item, -1);

        item = new ItemStack(292);
        im = item.getItemMeta();
        im.setDisplayName(ChatColor.GREEN + "" + ChatColor.BLACK + "" + ChatColor.GREEN + "" + ChatColor.WHITE + "" + ChatColor.BOLD + "[보상 상자 열쇠]");
        im.setLore(Arrays.asList("", ChatColor.WHITE + "보상 상자를 열 수 있는", ChatColor.WHITE + "낡은 열쇠이다."));
        im.addEnchant(Enchantment.DURABILITY, 6000, true);
        item.setItemMeta(im);
        new Util_Player().dropItem(player, item, true);

        if (isDrop)
            new Util_Player().dropItem(player, item, true);
        else
            new Util_Player().deleteItem(player, item, -1);
    }

    public void MonsterSpawn(Location loc) {
        Block SB = new Location(loc.getWorld(), loc.getX(), loc.getY() + 12, loc.getZ()).getBlock();
        Sign SignBlock = (Sign) SB.getState();
        String DungeonName = SignBlock.getLine(2);

        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager MonsterConfig = YC.getNewConfig("Dungeon/Dungeon/" + DungeonName + "/Monster.yml");
        Object[] MobList = null;
        String ListName = "Middle";
        byte randomLevel = (byte) new Util_Number().RandomNum(0, 3);
        if (randomLevel <= 2) {
            if (MonsterConfig.getConfigurationSection("Middle").getKeys(false).size() == 0)
                if (MonsterConfig.getConfigurationSection("High").getKeys(false).size() == 0)
                    if (MonsterConfig.getConfigurationSection("Normal").getKeys(false).size() == 0)
                        MobList = null;
                    else {
                        ListName = "Normal";
                        MobList = MonsterConfig.getConfigurationSection("Normal").getKeys(false).toArray();
                    }
                else {
                    ListName = "High";
                    MobList = MonsterConfig.getConfigurationSection("High").getKeys(false).toArray();
                }
            else {
                ListName = "Middle";
                MobList = MonsterConfig.getConfigurationSection("Middle").getKeys(false).toArray();
            }
        } else {
            if (MonsterConfig.getConfigurationSection("High").getKeys(false).size() == 0)
                if (MonsterConfig.getConfigurationSection("Middle").getKeys(false).size() == 0)
                    if (MonsterConfig.getConfigurationSection("Normal").getKeys(false).size() == 0)
                        MobList = null;
                    else {
                        ListName = "Normal";
                        MobList = MonsterConfig.getConfigurationSection("Normal").getKeys(false).toArray();
                    }
                else {
                    ListName = "Middle";
                    MobList = MonsterConfig.getConfigurationSection("Middle").getKeys(false).toArray();
                }
            else {
                ListName = "High";
                MobList = MonsterConfig.getConfigurationSection("High").getKeys(false).toArray();
            }
        }
        if (MobList != null) {
            String Mob = MobList[new Util_Number().RandomNum(0, MobList.length - 1)].toString();
            if (Mob != null) {
                randomLevel = (byte) new Util_Number().RandomNum(0, 5);
                switch (randomLevel) {
                    case 0://2회 스폰
                        break;
                    default://1회 스폰
                        break;
                }

                int[] XYZLoc = new int[3];
                XYZLoc[0] = (int) loc.getX();
                XYZLoc[1] = (int) loc.getY();
                XYZLoc[2] = (int) loc.getZ();
                byte GroupNumber = (byte) new Util_Number().RandomNum(0, 15);
                char Group = '0';
                switch (GroupNumber) {
                    case 0:
                        Group = '0';
                        break;
                    case 1:
                        Group = '1';
                        break;
                    case 2:
                        Group = '2';
                        break;
                    case 3:
                        Group = '3';
                        break;
                    case 4:
                        Group = '4';
                        break;
                    case 5:
                        Group = '5';
                        break;
                    case 6:
                        Group = '6';
                        break;
                    case 7:
                        Group = '7';
                        break;
                    case 8:
                        Group = '8';
                        break;
                    case 9:
                        Group = '9';
                        break;
                    case 10:
                        Group = 'a';
                        break;
                    case 11:
                        Group = 'b';
                        break;
                    case 12:
                        Group = 'c';
                        break;
                    case 13:
                        Group = 'd';
                        break;
                    case 14:
                        Group = 'e';
                        break;
                    case 15:
                        Group = 'f';
                        break;
                }
                Monster_Spawn MC = new Monster_Spawn();
                loc.add(0, 1, 0);
                for (int count = 0; count < 7; count++) {
                    SoundUtil.playSound(loc, Sound.ENTITY_WITHER_DEATH, 1.3F, 1.8F);
                    MC.SpawnMob(loc, MonsterConfig.getString(ListName + "." + Mob), (byte) 1, XYZLoc, Group, true);
                    loc.add(0, 0.2, 0);
                }
                SoundUtil.playSound(loc, Sound.ENTITY_WITHER_DEATH, 1.3F, 1.8F);
                MC.SpawnMob(loc, MonsterConfig.getString(ListName + "." + Mob), (byte) 3, XYZLoc, Group, true);
            } else {
                Location blockLoc = new Location(loc.getWorld(), loc.getX(), loc.getY() + 1, loc.getZ());
                ItemStack item = new ItemStack(292);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(ChatColor.GREEN + "" + ChatColor.BLACK + "" + ChatColor.GREEN + "" + ChatColor.WHITE + "" + ChatColor.BOLD + "[던전 룸 열쇠]");
                im.setLore(Arrays.asList("", ChatColor.WHITE + "던전 룸을 열 수 있는", ChatColor.WHITE + "낡은 열쇠이다."));
                im.addEnchant(Enchantment.DURABILITY, 6000, true);
                item.setItemMeta(im);
                new Main_ItemDrop().CustomItemDrop(blockLoc, item);
                new Dungeon_Main().DungeonTrapDoorWorker(loc, false);
            }
        }
    }

    public void DungeonInteract(PlayerInteractEvent event) {
        //양동이 사용 못하게 하기
        if (event.getPlayer().getInventory().getItemInMainHand().getTypeId() >= 325 &&
                event.getPlayer().getInventory().getItemInMainHand().getTypeId() <= 327) {
            event.setCancelled(true);
            SoundUtil.playSound(event.getPlayer(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
            new PacketUtil().sendActionBar(event.getPlayer(), ChatColor.RED + "" + ChatColor.BOLD + "[던전에서는 양동이 사용이 불가능합니다!]");
            return;
        } else if (event.getPlayer().getInventory().getItemInMainHand().getTypeId() == 432) {
            event.setCancelled(true);
            SoundUtil.playSound(event.getPlayer(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
            new PacketUtil().sendActionBar(event.getPlayer(), ChatColor.RED + "" + ChatColor.BOLD + "[던전에서는 후렴과 사용이 불가능합니다!]");
            return;
        }
        Block block = event.getClickedBlock();
        if (block == null)
            return;
        if (block.getType() == Material.AIR)
            return;

        if (block.getType().getId() == 146)//덫 상자
        {
            if (TrapChestOpen(block)) {
                event.setCancelled(true);
                byte howMuch = (byte) new Util_Number().RandomNum(0, 8);
                for (byte count = 0; count < howMuch; count++)
                    new Util_Player().addMoneyAndEXP(event.getPlayer(), new Util_Number().RandomNum(0, 500), 0, block.getLocation(), true, false);
            }
        } else if (block.getType().getId() == 95) //구슬방 구슬
            TrapGlassTouch(block, event.getPlayer());
        else if (block.getType().getId() == 138)//던전 탈출용 신호기
        {
            event.setCancelled(true);
            new Dungeon_Gui().DungeonEXIT(event.getPlayer());
        } else if (block.getType().getId() == 23) //던전 문 열쇠구멍
        {
            event.setCancelled(true);
            Block SB = new Location(block.getWorld(), block.getX(), block.getY() + 10, block.getZ()).getBlock();
            if (SB.getType() != Material.SIGN_POST)
                return;
            if (event.getClickedBlock().getLocation().add(0, 10, 0).getBlock() != null) {
                if (event.getClickedBlock().getLocation().add(0, 10, 0).getBlock().getType() == Material.SIGN_POST) {
                    Sign SignBlock = (Sign) event.getClickedBlock().getLocation().add(0, 10, 0).getBlock().getState();
                    String GridImage = SignBlock.getLine(1);
                    if (GridImage.compareTo("▲") == 0 || GridImage.compareTo("▼") == 0 || GridImage.compareTo("▶") == 0 || GridImage.compareTo("◀") == 0 || GridImage.compareTo("♠") == 0) {
                        ItemStack item = new ItemStack(292);
                        ItemMeta im = item.getItemMeta();
                        im.setDisplayName(ChatColor.GREEN + "" + ChatColor.BLACK + "" + ChatColor.GREEN + "" + ChatColor.WHITE + "" + ChatColor.BOLD + "[던전 룸 열쇠]");
                        im.setLore(Arrays.asList("", ChatColor.WHITE + "던전 룸을 열 수 있는", ChatColor.WHITE + "낡은 열쇠이다."));
                        im.addEnchant(Enchantment.DURABILITY, 6000, true);
                        item.setItemMeta(im);
                        if (new Util_Player().deleteItem(event.getPlayer(), item, 1)) {
                            Location loc = event.getClickedBlock().getLocation();
                            String Title = "\'" + ChatColor.BLUE + "\'";
                            String SubTitle = "\'" + ChatColor.WHITE + "던전 룸 열쇠를 사용하여 문을 열었다.\'";
                            new PacketUtil().sendTitleSubTitle(event.getPlayer(), Title, SubTitle, (byte) 1, (byte) 2, (byte) 1);
                            IronDoorOpening(loc);
                            switch (GridImage) {
                                case "▲":
                                case "▼":
                                    loc.add(-2, -2, 0);
                                    block = loc.getBlock();
                                    block.setType(Material.AIR, true);
                                    for (int count = 0; count < 6; count++) {
                                        for (int count2 = 0; count2 < 5; count2++) {
                                            block.setType(Material.AIR, true);
                                            block = loc.add(1, 0, 0).getBlock();
                                        }
                                        block = loc.add(-5, 1, 0).getBlock();
                                    }
                                    break;
                                case "♠":
                                    loc.add(-2, -2, 0);
                                    block = loc.getBlock();
                                    block.setType(Material.AIR, true);
                                    for (int count = 0; count < 6; count++) {
                                        for (int count2 = 0; count2 < 5; count2++) {
                                            block.setType(Material.AIR, true);
                                            block = loc.add(1, 0, 0).getBlock();
                                        }
                                        block = loc.add(-5, 1, 0).getBlock();
                                    }
                                    loc = event.getClickedBlock().getLocation().add(-2, -2, -1);
                                    block = loc.getBlock();
                                    block.setType(Material.AIR, true);
                                    for (int count = 0; count < 6; count++) {
                                        for (int count2 = 0; count2 < 5; count2++) {
                                            block.setType(Material.AIR, true);
                                            block = loc.add(1, 0, 0).getBlock();
                                        }
                                        block = loc.add(-5, 1, 0).getBlock();
                                    }
                                    loc = event.getClickedBlock().getLocation().add(0, -1, -33);
                                    BossRoomOpen(event.getPlayer(), loc, SignBlock.getLine(2));
                                    break;
                                case "▶":
                                case "◀":
                                    loc.add(0, -2, -2);
                                    block = loc.getBlock();
                                    block.setType(Material.AIR, true);
                                    for (int count = 0; count < 6; count++) {
                                        for (int count2 = 0; count2 < 5; count2++) {
                                            block.setType(Material.AIR, true);
                                            block = loc.add(0, 0, 1).getBlock();
                                        }
                                        block = loc.add(0, 1, -5).getBlock();
                                    }
                                    break;
                            }
                            return;
                        } else {
                            String Title = "\'" + ChatColor.BLUE + "\'";
                            String SubTitle = "\'" + ChatColor.WHITE + "문을 열기 위해서는 열쇠가 필요할 것 같다...\'";
                            new PacketUtil().sendTitleSubTitle(event.getPlayer(), Title, SubTitle, (byte) 1, (byte) 2, (byte) 1);
                        }
                    } else {
                        String Title = "\'" + ChatColor.BLUE + "\'";
                        String SubTitle = "\'" + ChatColor.WHITE + "열쇠로 열 수 있는 문이 아닌 것 같다...\'";
                        new PacketUtil().sendTitleSubTitle(event.getPlayer(), Title, SubTitle, (byte) 1, (byte) 2, (byte) 1);
                        return;
                    }
                }
            }
        } else if (block.getType().getId() == 54) //미믹 방 일반 상자
        {
            SoundUtil.playSound(block.getLocation().add(0, 2, 0), Sound.BLOCK_CHEST_OPEN, 0.5F, 1.8F);
            event.setCancelled(true);
            block.setType(Material.AIR);
            ItemStack item = new ItemStack(292);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(ChatColor.GREEN + "" + ChatColor.BLACK + "" + ChatColor.GREEN + "" + ChatColor.WHITE + "" + ChatColor.BOLD + "[던전 룸 열쇠]");
            im.setLore(Arrays.asList("", ChatColor.WHITE + "던전 룸을 열 수 있는", ChatColor.WHITE + "낡은 열쇠이다."));
            im.addEnchant(Enchantment.DURABILITY, 6000, true);
            item.setItemMeta(im);
            SoundUtil.playSound(block.getLocation().add(0, 2, 0), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.5F, 1.8F);
            new Main_ItemDrop().CustomItemDrop(block.getLocation().add(0.5, 1, 0.5), item);
            byte howMuch = (byte) new Util_Number().RandomNum(0, 4);
            for (byte count = 0; count < howMuch; count++)
                new Util_Player().addMoneyAndEXP(event.getPlayer(), new Util_Number().RandomNum(0, 500), 0, block.getLocation(), true, false);
        } else if (block.getType().getId() == 130) //보상 상자
        {
            Block SB = new Location(block.getWorld(), block.getX(), block.getY() + 12, block.getZ()).getBlock();
            if (SB.getType() != Material.SIGN_POST)
                return;
            event.setCancelled(true);
            Player player = event.getPlayer();
            ItemStack item = new ItemStack(292);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(ChatColor.GREEN + "" + ChatColor.BLACK + "" + ChatColor.GREEN + "" + ChatColor.WHITE + "" + ChatColor.BOLD + "[보상 상자 열쇠]");
            im.setLore(Arrays.asList("", ChatColor.WHITE + "보상 상자를 열 수 있는", ChatColor.WHITE + "낡은 열쇠이다."));
            im.addEnchant(Enchantment.DURABILITY, 6000, true);
            item.setItemMeta(im);
            if (new Util_Player().deleteItem(player, item, 1)) {
                event.getClickedBlock().setType(Material.AIR, true);
                SoundUtil.playSound(event.getClickedBlock().getLocation(), Sound.BLOCK_CHEST_OPEN, 1.0F, 1.0F);
                Sign SignBlock = (Sign) SB.getState();
                String DungeonName = SignBlock.getLine(2);
                YamlController YC = new YamlController(Main_Main.plugin);
                YamlManager RewardConfig = YC.getNewConfig("Dungeon/Dungeon/" + DungeonName + "/Reward.yml");

                boolean treasureGet = false;
                int luck = new Util_Number().RandomNum(0, 7);
                item = RewardConfig.getItemStack("100." + luck);
                if (item != null) {
                    treasureGet = true;
                    new Util_Player().giveItemDrop(player, item, event.getClickedBlock().getLocation());
                }
                luck = new Util_Number().RandomNum(1, 10);
                if (luck != 10) {
                    int count = new Util_Number().RandomNum(0, 7);
                    item = RewardConfig.getItemStack("90." + count);
                    if (item != null) {
                        treasureGet = true;
                        new Util_Player().giveItemDrop(player, item, event.getClickedBlock().getLocation());
                    }
                }
                luck = new Util_Number().RandomNum(1, 10);
                if (luck <= 5) {
                    int count = new Util_Number().RandomNum(0, 7);
                    item = RewardConfig.getItemStack("50." + count);
                    if (item != null) {
                        treasureGet = true;
                        new Util_Player().giveItemDrop(player, item, event.getClickedBlock().getLocation());
                    }
                }
                luck = new Util_Number().RandomNum(1, 10);
                if (luck == 1) {
                    int count = new Util_Number().RandomNum(0, 7);
                    item = RewardConfig.getItemStack("10." + count);
                    if (item != null) {
                        treasureGet = true;
                        new Util_Player().giveItemDrop(player, item, event.getClickedBlock().getLocation());
                    }
                }
                luck = new Util_Number().RandomNum(1, 100);
                if (luck == 1) {
                    int count = new Util_Number().RandomNum(0, 7);
                    item = RewardConfig.getItemStack("1." + count);
                    if (item != null) {
                        treasureGet = true;
                        new Util_Player().giveItemDrop(player, item, event.getClickedBlock().getLocation());
                    }
                }
                luck = new Util_Number().RandomNum(1, 1000);
                if (luck == 1) {
                    int count = new Util_Number().RandomNum(0, 7);
                    item = RewardConfig.getItemStack("0." + count);
                    if (item != null) {
                        treasureGet = true;
                        new Util_Player().giveItemDrop(player, item, event.getClickedBlock().getLocation());
                    }
                }

                if (!treasureGet)
                    new PacketUtil().sendActionBar(player, ChatColor.RED + "" + ChatColor.BOLD + "[꽝! 다음 기회에...]");
            } else {
                SoundUtil.playSound(player, Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1.0F, 0.5F);
                new PacketUtil().sendActionBar(player, ChatColor.WHITE + "" + ChatColor.BOLD + "[상자에 맞는 열쇠가 없습니다!]");
            }
        }
    }

    public boolean TrapChestOpen(Block block) {
        Block SB = new Location(block.getWorld(), block.getX(), block.getY() + 12, block.getZ()).getBlock();
        if (SB.getType() != Material.SIGN_POST)
            return false;
        Monster_Spawn MC = new Monster_Spawn();

        Sign SignBlock = (Sign) SB.getState();
        String GridImage = SignBlock.getLine(1);
        String DungeonName = SignBlock.getLine(2);
        Location loc = new Location(block.getWorld(), block.getX(), block.getY(), block.getZ());

        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager MonsterConfig = YC.getNewConfig("Dungeon/Dungeon/" + DungeonName + "/Monster.yml");
        Object[] MobList = null;
        String ListName = "Normal";
        byte randomLevel = (byte) new Util_Number().RandomNum(0, 3);
        if (!MonsterConfig.contains("Normal"))
            MonsterConfig.createSection("Normal");
        if (!MonsterConfig.contains("Middle"))
            MonsterConfig.createSection("Middle");
        if (!MonsterConfig.contains("High"))
            MonsterConfig.createSection("High");
        if (randomLevel <= 1) {
            if (MonsterConfig.getConfigurationSection("Normal").getKeys(false).size() == 0)
                if (MonsterConfig.getConfigurationSection("Middle").getKeys(false).size() == 0)
                    MobList = null;
                else {
                    ListName = "Middle";
                    MobList = MonsterConfig.getConfigurationSection("Middle").getKeys(false).toArray();
                }
            else {
                ListName = "Normal";
                MobList = MonsterConfig.getConfigurationSection("Normal").getKeys(false).toArray();
            }
        } else if (randomLevel == 2) {
            if (MonsterConfig.getConfigurationSection("Middle").getKeys(false).size() == 0)
                if (MonsterConfig.getConfigurationSection("High").getKeys(false).size() == 0)
                    if (MonsterConfig.getConfigurationSection("Normal").getKeys(false).size() == 0)
                        MobList = null;
                    else
                        MobList = MonsterConfig.getConfigurationSection("Normal").getKeys(false).toArray();
                else {
                    ListName = "High";
                    MobList = MonsterConfig.getConfigurationSection("High").getKeys(false).toArray();
                }
            else {
                ListName = "Middle";
                MobList = MonsterConfig.getConfigurationSection("Middle").getKeys(false).toArray();
            }
        } else {
            if (MonsterConfig.getConfigurationSection("High").getKeys(false).size() == 0)
                if (MonsterConfig.getConfigurationSection("Middle").getKeys(false).size() == 0)
                    if (MonsterConfig.getConfigurationSection("Normal").getKeys(false).size() == 0)
                        MobList = null;
                    else
                        MobList = MonsterConfig.getConfigurationSection("Normal").getKeys(false).toArray();
                else {
                    ListName = "Middle";
                    MobList = MonsterConfig.getConfigurationSection("Middle").getKeys(false).toArray();
                }
            else {
                ListName = "High";
                MobList = MonsterConfig.getConfigurationSection("High").getKeys(false).toArray();
            }
        }
        if (MobList != null && MobList.length != 0) {
            ArrayList<String> Mob = new ArrayList<>();
            for (int count = 0; count < 8; count++)
                Mob.add(MobList[new Util_Number().RandomNum(0, MobList.length - 1)].toString());
            if (MobList.length > 0) {
                if (GridImage.compareTo("◇") == 0) {
                    SoundUtil.playSound(loc, Sound.BLOCK_CHEST_OPEN, 1.3F, 1.8F);
                    MC.SpawnMob(loc, MonsterConfig.getString(ListName + "." + Mob.get(0)), (byte) -1, null, (char) -1, false);
                } else {
                    randomLevel = (byte) new Util_Number().RandomNum(0, 5);
                    switch (randomLevel) {
                        case 0://2회 스폰
                            break;
                        default://1회 스폰
                            break;
                    }

                    DungeonTrapDoorWorker(loc, true);
                    int[] XYZLoc = new int[3];
                    XYZLoc[0] = block.getX();
                    XYZLoc[1] = block.getY();
                    XYZLoc[2] = block.getZ();
                    byte RoomChallenge = (byte) new Util_Number().RandomNum(1, 5);
                    byte GroupNumber = (byte) new Util_Number().RandomNum(0, 15);
                    char Group = '0';
                    switch (GroupNumber) {
                        case 0:
                            Group = '0';
                            break;
                        case 1:
                            Group = '1';
                            break;
                        case 2:
                            Group = '2';
                            break;
                        case 3:
                            Group = '3';
                            break;
                        case 4:
                            Group = '4';
                            break;
                        case 5:
                            Group = '5';
                            break;
                        case 6:
                            Group = '6';
                            break;
                        case 7:
                            Group = '7';
                            break;
                        case 8:
                            Group = '8';
                            break;
                        case 9:
                            Group = '9';
                            break;
                        case 10:
                            Group = 'a';
                            break;
                        case 11:
                            Group = 'b';
                            break;
                        case 12:
                            Group = 'c';
                            break;
                        case 13:
                            Group = 'd';
                            break;
                        case 14:
                            Group = 'e';
                            break;
                        case 15:
                            Group = 'f';
                            break;
                    }
                    loc.add(0, 1, 0);
                    if (RoomChallenge <= 2) {
                        for (int count = 0; count < 8; count++) {
                            SoundUtil.playSound(loc, Sound.ENTITY_WITHER_DEATH, 1.3F, 1.8F);
                            loc.add(0, 0.2, 0);
                            MC.SpawnMob(loc, MonsterConfig.getString(ListName + "." + Mob.get(count)), (byte) 2, XYZLoc, Group, true);
                        }
                    } else {
                        for (int count = 0; count < 7; count++) {
                            loc.add(0, 0.2, 0);
                            SoundUtil.playSound(loc, Sound.ENTITY_WITHER_DEATH, 1.3F, 1.8F);
                            MC.SpawnMob(loc, MonsterConfig.getString(ListName + "." + Mob.get(count)), (byte) 1, XYZLoc, Group, true);
                        }
                        MC.SpawnMob(loc, MonsterConfig.getString(ListName + "." + Mob.get(7)), (byte) 3, XYZLoc, Group, true);
                    }
                    /*
                    일반 방 열면 몹 스폰되고 문 닫히게 하기
            		열쇠 가지고 있는 몬스터에게는 이름 앞에 칼라 코드를 붙여 표시한다.
            		칼라코드 순서 배열은 녹-검-녹 으로 통일한다.
            		녹-검-녹-빨 = 열쇠 가진 녀석
            		녹-검-녹-노 = 일반 녀석
            		녹-검-녹-녹 = 보스를 뜻하는 것으로, 반경 25블록 이내에 녹검녹녹이 없을 경우, 보스방 문이 열린다.
            		보스방 문을 탐지하기 위해서 보상방 철창 중앙의 위치를 던전 콘피그에 저장 시킨다.
            		녹-검-녹-파 = 다음 웨이브 존재
            		이후 오는 & 코드는 각각의 몬스터 그룹을 설정하기 위함으로,
            		방 내에 그룹 코드가 붙은 몬스터가 반경 20 이내에 없을 경우, 다음 웨이브가 나오거나
            		문이 열리게 된다. 몬스터 그룹 코드는 0 ~ f 까지 존재한다.
            		
            		문이 열리기 위해서는 애초에 열었던 상자 위치를 알고 있어야 하는데,
            		X, Y, Z 좌표를 저장 해 두어야 한다.
            		저장하기 좋은 곳은 몬스터가 장착 중인 아이템인데
            		그게 까다롭다.
            		손에 아이템이 없다면 장착 시켜 주고, 있다면 그 아래에 좌표를 저장한 뒤,
            		죽을 때 계산하고 다시 좌표를 없애버리면 되지만, 블레이즈, 골렘, 위더 등의 몬스터들은 아이템 장착란이 없다.
            		
            		
            		EntityDeath 이벤트에서, 엔티티가 죽은 월드가 Dungeon일 경우만
            		위와 같은 연산을 처리하며, 커스텀 몬스터 관련 데이터를 찾기 위해
            		앞의 8캐릭터 바이트 커스텀 네임은 삭제하고 넘기도록 한다.
            		고로 Entity Damage By Entity 이벤트에서도 던전에서 때릴 경우를 잡아 주어야 한다.
            		*/
                }
            } else if (GridImage.compareTo("◇") != 0) {
                Location blockLoc = new Location(block.getWorld(), block.getLocation().getX(), block.getLocation().getY() + 1, block.getLocation().getZ());
                ItemStack item = new ItemStack(292);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(ChatColor.GREEN + "" + ChatColor.BLACK + "" + ChatColor.GREEN + "" + ChatColor.WHITE + "" + ChatColor.BOLD + "[던전 룸 열쇠]");
                im.setLore(Arrays.asList("", ChatColor.WHITE + "던전 룸을 열 수 있는", ChatColor.WHITE + "낡은 열쇠이다."));
                im.addEnchant(Enchantment.DURABILITY, 6000, true);
                item.setItemMeta(im);
                new Main_ItemDrop().CustomItemDrop(blockLoc, item);
            }
        } else if (GridImage.compareTo("◇") != 0) {
            Location blockLoc = new Location(block.getWorld(), block.getLocation().getX(), block.getLocation().getY() + 1, block.getLocation().getZ());
            ItemStack item = new ItemStack(292);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(ChatColor.GREEN + "" + ChatColor.BLACK + "" + ChatColor.GREEN + "" + ChatColor.WHITE + "" + ChatColor.BOLD + "[던전 룸 열쇠]");
            im.setLore(Arrays.asList("", ChatColor.WHITE + "던전 룸을 열 수 있는", ChatColor.WHITE + "낡은 열쇠이다."));
            im.addEnchant(Enchantment.DURABILITY, 6000, true);
            item.setItemMeta(im);
            new Main_ItemDrop().CustomItemDrop(blockLoc, item);
        }
        SoundUtil.playSound(block.getLocation(), org.bukkit.Sound.BLOCK_CHEST_OPEN, 1.0F, 0.5F);
        block.setTypeIdAndData(0, (byte) 0, true);
        return true;
    }

    public void TrapGlassTouch(Block block, Player player) {
        Block SB = new Location(block.getWorld(), block.getX(), block.getY() + 11, block.getZ()).getBlock();
        if (SB.getType() != Material.SIGN_POST)
            return;
        Monster_Spawn MC = new Monster_Spawn();
        ParticleUtil p = new ParticleUtil();
        if (block.getData() == 15 || block.getData() == 14 || block.getData() == 13 ||
                block.getData() == 11 || block.getData() == 8) {
            switch (block.getData()) {
                case 15:
                    block.setTypeIdAndData(95, (byte) 7, true);
                    break;
                case 14:
                    block.setTypeIdAndData(95, (byte) 6, true);
                    break;
                case 13:
                    block.setTypeIdAndData(95, (byte) 5, true);
                    break;
                case 11:
                    block.setTypeIdAndData(95, (byte) 3, true);
                    break;
                case 8:
                    block.setTypeIdAndData(95, (byte) 0, true);
                    break;
            }
            for (int counter = 0; counter < 50; counter++)
                ParticleUtil.PL(block.getLocation(), org.bukkit.Effect.MAGIC_CRIT, 0);
            SoundUtil.playSound(block.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 0.5F);
        } else if (block.getData() == 0 || block.getData() == 3 || block.getData() == 5 || block.getData() == 6 || block.getData() == 7) {
            for (int counter = 0; counter < 31; counter++)
                ParticleUtil.PL(block.getLocation(), org.bukkit.Effect.CRIT, 0);
            SoundUtil.playSound(block.getLocation(), org.bukkit.Sound.ENTITY_GENERIC_HURT, 0.5F, 0.5F);
            return;
        }
        block = new Location(block.getWorld(), block.getX(), block.getY() - 2, block.getZ()).getBlock();
        if (block.getType() == Material.STONE) {
            block = new Location(block.getWorld(), block.getX(), block.getY() + 13, block.getZ()).getBlock();
            Sign SignBlock = (Sign) block.getState();
            String GridImage = SignBlock.getLine(1);
            Location loc = new Location(block.getWorld(), block.getX(), block.getY(), block.getZ());
            int Direction = Integer.parseInt(SignBlock.getLine(3));
            DungeonDoorRemover(player, GridImage.charAt(0), Direction, loc);
        } else {
            block = new Location(block.getWorld(), block.getX(), block.getY() + 13, block.getZ()).getBlock();
            Sign SignBlock = (Sign) block.getState();
            String GridImage = SignBlock.getLine(1);
            String DungeonName = SignBlock.getLine(2);
	        /*
	        Direction : 0 = 북
	        Direction : 1 = 북동
	        Direction : 2 = 동
	        Direction : 3 = 남동
	        Direction : 4 = 남
	        Direction : 5 = 남서
	        Direction : 6 = 서
	        Direction : 7 = 북서
	        Direction : 8 = 중앙
	         */
            Location loc = new Location(block.getWorld(), block.getX(), block.getY(), block.getZ());
            int Direction = Integer.parseInt(SignBlock.getLine(3));
            int NowLevel = Integer.parseInt(SignBlock.getLine(0).split("/")[0]);
            if (NowLevel <= 0)
                NowLevel = 1;
            int MaxLevel = Integer.parseInt(SignBlock.getLine(0).split("/")[1]);
            switch (Direction) {
                case 1:
                    loc.add(-6, -12, 6);
                    break;
                case 3:
                    loc.add(-6, -12, -6);
                    break;
                case 5:
                    loc.add(6, -12, -6);
                    break;
                case 7:
                    loc.add(6, -12, 6);
                    break;
            }
            YamlController YC = new YamlController(Main_Main.plugin);
            YamlManager MonsterConfig = YC.getNewConfig("Dungeon/Dungeon/" + DungeonName + "/Monster.yml");
            Object[] MobList = null;
            String ListName = "Normal";
            if (GridImage.compareTo("◎") == 0) {
                if (MonsterConfig.getConfigurationSection("SubBoss").getKeys(false).size() == 0)
                    if (MonsterConfig.getConfigurationSection("High").getKeys(false).size() == 0)
                        if (MonsterConfig.getConfigurationSection("Middle").getKeys(false).size() == 0)
                            if (MonsterConfig.getConfigurationSection("Normal").getKeys(false).size() == 0)
                                MobList = null;
                            else
                                MobList = MonsterConfig.getConfigurationSection("Normal").getKeys(false).toArray();
                        else {
                            ListName = "Middle";
                            MobList = MonsterConfig.getConfigurationSection("Middle").getKeys(false).toArray();
                        }
                    else {
                        ListName = "High";
                        MobList = MonsterConfig.getConfigurationSection("High").getKeys(false).toArray();
                    }
                else {
                    ListName = "SubBoss";
                    MobList = MonsterConfig.getConfigurationSection("SubBoss").getKeys(false).toArray();
                }
                if (MobList != null) {
                    loc.add(0, 1, 0);
                    for (int count = 0; count < 4; count++) {
                        loc.add(new Util_Number().RandomNum(-2, 2), 0.1 * count, new Util_Number().RandomNum(-2, 2));
                        MC.SpawnMob(loc, MonsterConfig.getString(ListName + "." + MobList[new Util_Number().RandomNum(0, MobList.length - 1)].toString()), (byte) -1, null, (char) -1, false);
                    }
                }
            } else {
                if (MaxLevel < 5)
                    MaxLevel = MaxLevel * 20;
                else if (MaxLevel < 10)
                    MaxLevel = MaxLevel * 10;
                else if (MaxLevel < 20)
                    MaxLevel = MaxLevel * 5;
                else if (MaxLevel >= 100)
                    MaxLevel = 100;
                if (MaxLevel / NowLevel > 20) {
                    if (MonsterConfig.getConfigurationSection("Normal").getKeys(false).size() == 0)
                        if (MonsterConfig.getConfigurationSection("Middle").getKeys(false).size() == 0)
                            MobList = null;
                        else {
                            ListName = "Middle";
                            MobList = MonsterConfig.getConfigurationSection("Middle").getKeys(false).toArray();
                        }
                    else {
                        ListName = "Normal";
                        MobList = MonsterConfig.getConfigurationSection("Normal").getKeys(false).toArray();
                    }
                } else if (MaxLevel / NowLevel > 10) {
                    if (MonsterConfig.getConfigurationSection("Middle").getKeys(false).size() == 0)
                        if (MonsterConfig.getConfigurationSection("High").getKeys(false).size() == 0)
                            if (MonsterConfig.getConfigurationSection("Normal").getKeys(false).size() == 0)
                                MobList = null;
                            else
                                MobList = MonsterConfig.getConfigurationSection("Normal").getKeys(false).toArray();
                        else {
                            ListName = "High";
                            MobList = MonsterConfig.getConfigurationSection("High").getKeys(false).toArray();
                        }
                    else {
                        ListName = "Middle";
                        MobList = MonsterConfig.getConfigurationSection("Middle").getKeys(false).toArray();
                    }
                } else {
                    if (MonsterConfig.getConfigurationSection("High").getKeys(false).size() == 0)
                        if (MonsterConfig.getConfigurationSection("Middle").getKeys(false).size() == 0)
                            if (MonsterConfig.getConfigurationSection("Normal").getKeys(false).size() == 0)
                                MobList = null;
                            else
                                MobList = MonsterConfig.getConfigurationSection("Normal").getKeys(false).toArray();
                        else {
                            ListName = "Middle";
                            MobList = MonsterConfig.getConfigurationSection("Middle").getKeys(false).toArray();
                        }
                    else {
                        ListName = "High";
                        MobList = MonsterConfig.getConfigurationSection("High").getKeys(false).toArray();
                    }
                }
            }

            if (MobList != null) {
                loc.add(0, 1, 0);
                for (int count = 0; count < 4; count++) {
                    loc.add(new Util_Number().RandomNum(-2, 2), 0.1 * count, new Util_Number().RandomNum(-2, 2));
                    SoundUtil.playSound(loc, Sound.ENTITY_WITHER_DEATH, 1.3F, 1.8F);
                    MC.SpawnMob(loc, MonsterConfig.getString(ListName + "." + MobList[new Util_Number().RandomNum(0, MobList.length - 1)].toString()), (byte) -1, null, (char) -1, false);
                }
            }
        }
    }


    private void DungeonDoorRemover(Player player, char GridImage, int Direction, Location loc) {
        Location Original = loc.add(0, -12, 0);
        Original.setX(loc.getX());
        Original.setY(loc.getY());
        Original.setZ(loc.getZ());
        Block block = null;
        switch (GridImage) {
            case '△':
                switch (Direction) {
                    case 1:
                        loc.add(-3, 0, -4).getBlock();
                        break;
                    case 3:
                        loc.add(-3, 0, -16).getBlock();
                        break;
                    case 5:
                        loc.add(9, 0, -16).getBlock();
                        break;
                    case 7:
                        loc.add(9, 0, -4).getBlock();
                        break;
                }
                IronDoorOpening(loc);
                for (int count = 0; count < 5; count++) {
                    for (int count2 = 0; count2 < 5; count2++) {
                        block = loc.add(-1, 0, 0).getBlock();
                        block.setType(Material.AIR, true);
                    }
                    block = loc.add(5, 1, 0).getBlock();
                }
                break;
            case '♥':
                Location BossLoc = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
                Location SignLoc = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
                SignLoc.add(0, 12, 0);
                Sign SignBlock = (Sign) SignLoc.getBlock().getState();
                switch (Direction) {
                    case 1:
                        loc = loc.add(-3, 0, -4);
                        BossLoc = BossLoc.add(-6, 1, -37);
                        break;
                    case 3:
                        loc.add(-3, 0, -16).getBlock();
                        BossLoc = BossLoc.add(-6, 1, -49);
                        break;
                    case 5:
                        loc.add(9, 0, -16).getBlock();
                        BossLoc = BossLoc.add(6, 1, -49);
                        break;
                    case 7:
                        loc.add(9, 0, -4).getBlock();
                        BossLoc = BossLoc.add(6, 1, -37);
                        break;
                }
                IronDoorOpening(loc);
                for (int c = 0; c < 2; c++) {
                    for (int count = 0; count < 5; count++) {
                        for (int count2 = 0; count2 < 5; count2++) {
                            block = loc.add(-1, 0, 0).getBlock();
                            block.setType(Material.AIR, true);
                        }
                        block = loc.add(5, 1, 0).getBlock();
                    }
                    block = loc.add(0, -5, -1).getBlock();
                }
                BossRoomOpen(player, BossLoc, SignBlock.getLine(2));
                break;
            case '▽':
                switch (Direction) {
                    case 1:
                        loc.add(-3, 0, 16).getBlock();
                        break;
                    case 3:
                        loc.add(-3, 0, 4).getBlock();
                        break;
                    case 5:
                        loc.add(9, 0, 4).getBlock();
                        break;
                    case 7:
                        loc.add(9, 0, 16).getBlock();
                        break;
                }
                IronDoorOpening(loc);
                for (int count = 0; count < 5; count++) {
                    for (int count2 = 0; count2 < 5; count2++) {
                        block = loc.add(-1, 0, 0).getBlock();
                        block.setType(Material.AIR, true);
                    }
                    block = loc.add(5, 1, 0).getBlock();
                }
                break;
            case '◁':
                switch (Direction) {
                    case 1:
                        loc.add(-16, 0, 9).getBlock();
                        break;
                    case 3:
                        loc.add(-16, 0, -3).getBlock();
                        break;
                    case 5:
                        loc.add(-4, 0, -3).getBlock();
                        break;
                    case 7:
                        loc.add(-4, 0, 9).getBlock();
                        break;
                }
                IronDoorOpening(loc);
                for (int count = 0; count < 5; count++) {
                    for (int count2 = 0; count2 < 5; count2++) {
                        block = loc.add(0, 0, -1).getBlock();
                        block.setType(Material.AIR, true);
                    }
                    block = loc.add(0, 1, 5).getBlock();
                }
                break;
            case '▷':
                switch (Direction) {
                    case 1:
                        loc.add(4, 0, 9).getBlock();
                        break;
                    case 3:
                        loc.add(4, 0, -3).getBlock();
                        break;
                    case 5:
                        loc.add(16, 0, -3).getBlock();
                        break;
                    case 7:
                        loc.add(16, 0, 9).getBlock();
                        break;
                }
                IronDoorOpening(loc);
                for (int count = 0; count < 5; count++) {
                    for (int count2 = 0; count2 < 5; count2++) {
                        block = loc.add(0, 0, -1).getBlock();
                        block.setType(Material.AIR, true);
                    }
                    block = loc.add(0, 1, 5).getBlock();
                }
                break;
        }
    }

    public void DungeonTrapDoorWorker(Location loc, boolean isCreate) {
        Block block = null;
        Material M = Material.STAINED_GLASS_PANE;
        byte blockData = (byte) 14;

        IronDoorOpening(loc);
        Location loc2;
        byte signX = 0;
        byte signZ = 0;
        byte addingX;
        byte addingZ;
        byte uppingX;
        byte uppingZ;
        for (byte directionLoop = 0; directionLoop < 4; directionLoop++) {
            signX = 0;
            signZ = 0;
            addingX = 0;
            addingZ = 0;
            uppingX = 0;
            uppingZ = 0;
            loc2 = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
            if (directionLoop == 0) {
                loc2.add(-10, 0, 2);
                addingZ = -1;
                uppingZ = 5;
                signX = 0;
                signZ = -2;
            } else if (directionLoop == 1) {
                loc2.add(2, 0, -10);
                addingX = -1;
                uppingX = 5;
                signZ = 0;
                signX = -2;
            } else if (directionLoop == 2) {
                loc2.add(10, 0, 2);
                addingZ = -1;
                uppingZ = 5;
                signX = 0;
                signZ = -2;
            } else {
                loc2.add(2, 0, 10);
                addingX = -1;
                uppingX = 5;
                signZ = 0;
                signX = -2;
            }
            block = loc2.getBlock();
            for (int count = 0; count < 6; count++) {
                for (int count2 = 0; count2 < 5; count2++) {
                    if (isCreate) {
                        if (block.getType() == Material.AIR || block == null) {
                            block.setType(M, true);
                            block.setData(blockData);
                        }
                    } else if (block.getType() == M || block == null)
                        block.setType(Material.AIR, true);
                    block = loc2.add(addingX, 0, addingZ).getBlock();
                }
                block = loc2.add(uppingX, 1, uppingZ).getBlock();
            }
            block = loc2.add(signX, 6, signZ).getBlock();

            block.setType(Material.SIGN_POST);

            if (block.getType() == Material.SIGN_POST) {
                Sign s = (Sign) block.getState();
                s.setLine(0, "" + (int) loc.getX());//들어갈 곳 X
                s.setLine(1, "" + (int) loc.getY() + 1);//들어갈 곳 Y
                s.setLine(2, "" + (int) loc.getZ());//들어갈 곳 Z
                s.setLine(3, "");//빈 공간
                s.update();
            } else {
                block.setType(Material.SIGN_POST);
                if (block.getType() == Material.SIGN_POST) {
                    Sign s = (Sign) block.getState();
                    s.setLine(0, "" + (int) loc.getX());//들어갈 곳 X
                    s.setLine(1, "" + (int) loc.getY() + 1);//들어갈 곳 Y
                    s.setLine(2, "" + (int) loc.getZ());//들어갈 곳 Z
                    s.setLine(3, "");//빈 공간
                    s.update();
                }
            }
        }
    }

}
