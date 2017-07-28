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

package io.github.goldbigdragon.goldbigdragonrpg.user;

import java.util.Arrays;

import io.github.goldbigdragon.goldbigdragonrpg.effect.SoundUtil;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_ServerOption;
import io.github.goldbigdragon.goldbigdragonrpg.servertick.ServerTick_Object;
import io.github.goldbigdragon.goldbigdragonrpg.util.ETC;
import io.github.goldbigdragon.goldbigdragonrpg.util.GuiUtil;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import io.github.goldbigdragon.goldbigdragonrpg.servertick.ServerTick_Main;

public class Equip_Gui extends GuiUtil {
    public void EquipWatchGUI(Player player, Player target) {
        String UniqueCode = "§0§0§0§0§6§r";
        Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0장비 구경");
        if (Main_ServerOption.PlayerList.get(target.getUniqueId().toString()).isOption_EquipLook()) {
            Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160, 7, 1, null, 0, inv);
            Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160, 7, 1, null, 1, inv);
            Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160, 7, 1, null, 2, inv);
            Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160, 7, 1, null, 3, inv);
            Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160, 7, 1, null, 4, inv);
            Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160, 7, 1, null, 9, inv);
            Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160, 7, 1, null, 18, inv);
            Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160, 7, 1, null, 27, inv);
            Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160, 7, 1, null, 36, inv);
            Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160, 7, 1, null, 45, inv);
            Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160, 7, 1, null, 46, inv);
            Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160, 7, 1, null, 47, inv);
            Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160, 7, 1, null, 48, inv);
            Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160, 7, 1, null, 49, inv);
            Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160, 7, 1, null, 40, inv);
            Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160, 7, 1, null, 31, inv);
            Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160, 7, 1, null, 22, inv);
            Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160, 7, 1, null, 13, inv);


            if (target.getInventory().getHelmet() == null)
                Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "미착용", 302, 0, 1, null, 11, inv);
            else
                ItemStackStack(target.getInventory().getHelmet(), 11, inv);
            if (target.getInventory().getChestplate() == null)
                Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "미착용", 303, 0, 01, null, 20, inv);
            else
                ItemStackStack(target.getInventory().getChestplate(), 20, inv);
            if (target.getInventory().getLeggings() == null)
                Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "미착용", 304, 0, 1, null, 29, inv);
            else
                ItemStackStack(target.getInventory().getLeggings(), 29, inv);
            if (target.getInventory().getBoots() == null)
                Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "미착용", 305, 0, 1, null, 38, inv);
            else
                ItemStackStack(target.getInventory().getBoots(), 38, inv);
            if (target.getInventory().getItemInMainHand() == null)
                Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "맨손", 336, 0, 1, null, 28, inv);
            else if (target.getInventory().getItemInMainHand().getType() == Material.AIR)
                Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "맨손", 336, 0, 1, null, 28, inv);
            else
                ItemStackStack(target.getInventory().getItemInMainHand(), 28, inv);
            if (target.getInventory().getItemInOffHand() == null)
                Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "맨손", 336, 0, 1, null, 30, inv);
            else if (target.getInventory().getItemInOffHand().getType() == Material.AIR)
                Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "맨손", 336, 0, 1, null, 30, inv);
            else
                ItemStackStack(target.getInventory().getItemInOffHand(), 30, inv);
        } else {
            Stack(ChatColor.RED + "[    비공개    ]", 160, 14, 1, null, 0, inv);
            Stack(ChatColor.RED + "[    비공개    ]", 160, 14, 1, null, 1, inv);
            Stack(ChatColor.RED + "[    비공개    ]", 160, 14, 1, null, 2, inv);
            Stack(ChatColor.RED + "[    비공개    ]", 160, 14, 1, null, 3, inv);
            Stack(ChatColor.RED + "[    비공개    ]", 160, 14, 1, null, 4, inv);
            Stack(ChatColor.RED + "[    비공개    ]", 160, 14, 1, null, 9, inv);
            Stack(ChatColor.RED + "[    비공개    ]", 160, 14, 1, null, 18, inv);
            Stack(ChatColor.RED + "[    비공개    ]", 160, 14, 1, null, 27, inv);
            Stack(ChatColor.RED + "[    비공개    ]", 160, 14, 1, null, 36, inv);
            Stack(ChatColor.RED + "[    비공개    ]", 160, 14, 1, null, 45, inv);
            Stack(ChatColor.RED + "[    비공개    ]", 160, 14, 1, null, 46, inv);
            Stack(ChatColor.RED + "[    비공개    ]", 160, 14, 1, null, 47, inv);
            Stack(ChatColor.RED + "[    비공개    ]", 160, 14, 1, null, 48, inv);
            Stack(ChatColor.RED + "[    비공개    ]", 160, 14, 1, null, 49, inv);
            Stack(ChatColor.RED + "[    비공개    ]", 160, 14, 1, null, 40, inv);
            Stack(ChatColor.RED + "[    비공개    ]", 160, 14, 1, null, 31, inv);
            Stack(ChatColor.RED + "[    비공개    ]", 160, 14, 1, null, 22, inv);
            Stack(ChatColor.RED + "[    비공개    ]", 160, 14, 1, null, 13, inv);

            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "알수없음", 302, 0, 1, null, 11, inv);
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "알수없음", 303, 0, 1, null, 20, inv);
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "알수없음", 304, 0, 1, null, 29, inv);
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "알수없음", 305, 0, 1, null, 38, inv);
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "알수없음", 336, 0, 1, null, 28, inv);
        }

        Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160, 9, 1, null, 5, inv);
        Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160, 9, 1, null, 6, inv);
        Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160, 9, 1, null, 7, inv);
        Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160, 9, 1, null, 8, inv);
        Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160, 9, 1, null, 14, inv);
        Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160, 9, 1, null, 17, inv);
        Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160, 9, 1, null, 23, inv);
        Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160, 9, 1, null, 26, inv);
        Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160, 9, 1, null, 32, inv);
        Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160, 9, 1, null, 35, inv);
        Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160, 9, 1, null, 41, inv);
        Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160, 9, 1, null, 44, inv);
        Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160, 9, 1, null, 50, inv);
        Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160, 9, 1, null, 51, inv);
        Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160, 9, 1, null, 52, inv);
        Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160, 9, 1, null, 53, inv);

        Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "교환 신청", 388, 0, 1, Arrays.asList(
                ChatColor.GRAY + "교환 신청을 합니다.", ChatColor.GRAY + "보안을 위해 아이템을 나누는", ChatColor.GRAY + "동작은 금지되어 있습니다.", "", ChatColor.YELLOW + "[    대상    ]", ChatColor.WHITE + "" + ChatColor.BOLD + target.getName()), 15, inv);
        Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "친구 추가", 397, 3, 1, Arrays.asList(
                ChatColor.GRAY + "친구 추가 요청을 합니다.", "", ChatColor.YELLOW + "[    대상    ]", ChatColor.WHITE + "" + ChatColor.BOLD + target.getName()), 16, inv);
        player.openInventory(inv);
    }

    public void ExChangeGUI(Player player, Player target, ItemStack[] SideItems, boolean MyReady, boolean TargetReady) {
        String UniqueCode = "§0§0§0§0§7§r";
        Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0교환");

        for (byte count = 0; count < 5; count++)
            Stack(ChatColor.GRAY + "" + ChatColor.BOLD + "[   칸막이   ]", 160, 15, 1, null, 4 + (count * 9), inv);

        Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "[ 교환 취소 ]", 166, 0, 1, null, 49, inv);

        ItemStackStack(getPlayerSkull(ChatColor.GREEN + "" + ChatColor.BOLD + player.getName(), 1, null, player.getName()), 0, inv);
        ItemStackStack(getPlayerSkull(ChatColor.GREEN + "" + ChatColor.BOLD + target.getName(), 1, null, target.getName()), 8, inv);

        for (byte count = 1; count < 4; count++)
            Stack(ChatColor.BLUE + "" + ChatColor.BOLD + "[   자신   ]", 160, 11, 1, null, count, inv);
        for (byte count = 5; count < 8; count++)
            Stack(ChatColor.RED + "" + ChatColor.BOLD + "[   상대   ]", 160, 14, 1, null, count, inv);

        for (byte count = 0; count < 4; count++)
            Stack(ChatColor.BLUE + "" + ChatColor.BOLD + "[   자신   ]", 160, 11, 1, null, 9 + (count * 9), inv);
        for (byte count = 0; count < 4; count++)
            Stack(ChatColor.RED + "" + ChatColor.BOLD + "[   상대   ]", 160, 14, 1, null, 17 + (count * 9), inv);

        for (byte count = 46; count < 49; count++)
            Stack(ChatColor.BLUE + "" + ChatColor.BOLD + "[   자신   ]", 160, 11, 1, null, count, inv);
        for (byte count = 50; count < 53; count++)
            Stack(ChatColor.RED + "" + ChatColor.BOLD + "[   상대   ]", 160, 14, 1, null, count, inv);

        for (byte count = 0; count < 4; count++)
            for (byte count2 = 10; count2 < 13; count2++)
                Stack(ChatColor.GRAY + "" + ChatColor.BOLD + "[아무것도 올려지지 않음]", 160, 8, 1, null, count2 + (count * 9), inv);

        for (byte count = 0; count < 4; count++)
            for (byte count2 = 14; count2 < 17; count2++)
                Stack(ChatColor.GRAY + "" + ChatColor.BOLD + "[아무것도 올려지지 않음]", 160, 8, 1, null, count2 + (count * 9), inv);

        if (SideItems != null) {
            if (SideItems.length != 0) {
                int SIC = 0;
                for (byte count = 0; count < 3; count++) {
                    for (byte count2 = 10; count2 < 13; count2++) {
                        if (SideItems[SIC] != null)
                            ItemStackStack(SideItems[SIC], count2 + (count * 9), inv);
                        SIC = SIC + 1;
                    }
                }
                for (byte count = 0; count < 3; count++) {
                    for (byte count2 = 14; count2 < 17; count2++) {
                        if (SideItems[SIC] != null)
                            ItemStackStack(SideItems[SIC], count2 + (count * 9), inv);
                        SIC = SIC + 1;
                    }
                }
            }
        }

        if (MyReady)
            Stack(ChatColor.BLUE + "" + ChatColor.BOLD + "[ 준비 완료 ]", 35, 11, 1, null, 45, inv);
        else
            Stack(ChatColor.RED + "" + ChatColor.BOLD + "[  대기중  ]", 35, 14, 1, null, 45, inv);

        if (TargetReady)
            Stack(ChatColor.BLUE + "" + ChatColor.BOLD + "[ 준비 완료 ]", 35, 11, 1, null, 53, inv);
        else
            Stack(ChatColor.RED + "" + ChatColor.BOLD + "[  대기중  ]", 35, 14, 1, null, 53, inv);

        player.openInventory(inv);
    }


    public void EquipSeeInventoryclick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Player target = (Player) Bukkit.getServer().getPlayer(ChatColor.stripColor(event.getInventory().getItem(16).getItemMeta().getLore().get(3)));

        int slot = event.getSlot();

        if (slot == 15)
            AddExchangeTarget(player, target);
        else if (slot == 16)
            SetFriends(player, target);
    }

    public void ExchangeInventoryclick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Player target = Bukkit.getServer().getPlayer(ChatColor.stripColor(event.getInventory().getItem(8).getItemMeta().getDisplayName()));

        if ((event.getAction() == InventoryAction.PICKUP_ALL ||
                event.getAction() == InventoryAction.PICKUP_HALF ||
                event.getAction() == InventoryAction.PICKUP_ONE ||
                event.getAction() == InventoryAction.PICKUP_SOME) &&
                ChatColor.stripColor(event.getClickedInventory().getName()).compareTo("교환") != 0) {
            ItemStack mySideSlot[] = new ItemStack[12];
            byte MSS = 0;
            for (byte count = 0; count < 4; count++) {
                for (byte count2 = 10; count2 < 13; count2++) {
                    if (event.getInventory().getItem(count2 + (count * 9)) != null)
                        mySideSlot[MSS] = event.getInventory().getItem(count2 + (count * 9));
                    MSS++;
                }
            }
            byte emptySlot = -1;
            for (byte count = 0; count < 12; count++) {
                if (mySideSlot[count] != null) {
                    if (mySideSlot[count].hasItemMeta()) {
                        if (!mySideSlot[count].getItemMeta().hasLore() &&
                                mySideSlot[count].getItemMeta().hasDisplayName()) {
                            if (mySideSlot[count].getItemMeta().getDisplayName().compareTo(ChatColor.GRAY + "" + ChatColor.BOLD + "[아무것도 올려지지 않음]") == 0) {
                                emptySlot = count;
                                break;
                            }
                        }
                    }
                }
            }
            if (emptySlot != -1) {
                SoundUtil.playSound(player, Sound.BLOCK_STONE_STEP, 1.0F, 1.0F);
                SoundUtil.playSound(target, Sound.BLOCK_STONE_STEP, 1.0F, 1.0F);
                switch (emptySlot) {
                    case 0:
                        player.getOpenInventory().setItem(10, event.getCurrentItem());
                        target.getOpenInventory().setItem(14, event.getCurrentItem());
                        break;
                    case 1:
                        player.getOpenInventory().setItem(11, event.getCurrentItem());
                        target.getOpenInventory().setItem(15, event.getCurrentItem());
                        break;
                    case 2:
                        player.getOpenInventory().setItem(12, event.getCurrentItem());
                        target.getOpenInventory().setItem(16, event.getCurrentItem());
                        break;
                    case 3:
                        player.getOpenInventory().setItem(19, event.getCurrentItem());
                        target.getOpenInventory().setItem(23, event.getCurrentItem());
                        break;
                    case 4:
                        player.getOpenInventory().setItem(20, event.getCurrentItem());
                        target.getOpenInventory().setItem(24, event.getCurrentItem());
                        break;
                    case 5:
                        player.getOpenInventory().setItem(21, event.getCurrentItem());
                        target.getOpenInventory().setItem(25, event.getCurrentItem());
                        break;
                    case 6:
                        player.getOpenInventory().setItem(28, event.getCurrentItem());
                        target.getOpenInventory().setItem(32, event.getCurrentItem());
                        break;
                    case 7:
                        player.getOpenInventory().setItem(29, event.getCurrentItem());
                        target.getOpenInventory().setItem(33, event.getCurrentItem());
                        break;
                    case 8:
                        player.getOpenInventory().setItem(30, event.getCurrentItem());
                        target.getOpenInventory().setItem(34, event.getCurrentItem());
                        break;
                    case 9:
                        player.getOpenInventory().setItem(37, event.getCurrentItem());
                        target.getOpenInventory().setItem(41, event.getCurrentItem());
                        break;
                    case 10:
                        player.getOpenInventory().setItem(38, event.getCurrentItem());
                        target.getOpenInventory().setItem(42, event.getCurrentItem());
                        break;
                    case 11:
                        player.getOpenInventory().setItem(39, event.getCurrentItem());
                        target.getOpenInventory().setItem(43, event.getCurrentItem());
                        break;
                }
                event.getClickedInventory().setItem(event.getSlot(), new ItemStack(0));

                ItemStack Icon = new MaterialData(35, (byte) 14).toItemStack();
                Icon.setAmount(1);
                ItemMeta Icon_Meta = Icon.getItemMeta();
                Icon_Meta.setLore(null);
                Icon_Meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "[  대기중  ]");
                Icon.setItemMeta(Icon_Meta);

                target.getOpenInventory().setItem(45, Icon);
                event.getInventory().setItem(45, Icon);

                target.getOpenInventory().setItem(53, Icon);
                event.getInventory().setItem(53, Icon);
            } else {
                SoundUtil.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
                player.sendMessage(ChatColor.RED + "[교환] : 더 이상 물건을 올릴 수 없습니다!");
            }
        } else if ((event.getAction() == InventoryAction.PICKUP_ALL ||
                event.getAction() == InventoryAction.PICKUP_HALF ||
                event.getAction() == InventoryAction.PICKUP_ONE ||
                event.getAction() == InventoryAction.PICKUP_SOME) &&
                ChatColor.stripColor(event.getClickedInventory().getName()).compareTo("교환") == 0) {
            if ((event.getSlot() >= 10 && event.getSlot() <= 12) ||
                    (event.getSlot() >= 19 && event.getSlot() <= 21) ||
                    (event.getSlot() >= 28 && event.getSlot() <= 30) ||
                    (event.getSlot() >= 37 && event.getSlot() <= 39)) {
                if (event.getCurrentItem() != null) {
                    if (event.getCurrentItem().hasItemMeta()) {
                        if (!event.getCurrentItem().getItemMeta().hasLore() &&
                                event.getCurrentItem().getItemMeta().hasDisplayName()) {
                            if (event.getCurrentItem().getItemMeta().getDisplayName().compareTo(ChatColor.GRAY + "" + ChatColor.BOLD + "[아무것도 올려지지 않음]") == 0) {
                                return;
                            }
                        }
                    }
                }
                ItemStack Icon = new MaterialData(160, (byte) 8).toItemStack();
                Icon.setAmount(1);
                ItemMeta Icon_Meta = Icon.getItemMeta();
                Icon_Meta.setLore(null);
                Icon_Meta.setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD + "[아무것도 올려지지 않음]");
                Icon.setItemMeta(Icon_Meta);
                player.getInventory().addItem(event.getCurrentItem());
                target.getOpenInventory().setItem(event.getSlot() + 4, Icon);
                event.getClickedInventory().setItem(event.getSlot(), Icon);


                Icon = new MaterialData(35, (byte) 14).toItemStack();
                Icon.setAmount(1);
                Icon_Meta = Icon.getItemMeta();
                Icon_Meta.setLore(null);
                Icon_Meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "[  대기중  ]");
                Icon.setItemMeta(Icon_Meta);

                target.getOpenInventory().setItem(45, Icon);
                event.getClickedInventory().setItem(45, Icon);

                target.getOpenInventory().setItem(53, Icon);
                event.getClickedInventory().setItem(53, Icon);
                SoundUtil.playSound(player, Sound.BLOCK_CLOTH_STEP, 1.0F, 1.0F);
                SoundUtil.playSound(target, Sound.BLOCK_CLOTH_STEP, 1.0F, 1.0F);
            }
        }
    }

    public void ExchangeGUIclick(InventoryClickEvent event) {
        Player player = Bukkit.getServer().getPlayer(ChatColor.stripColor(event.getInventory().getItem(0).getItemMeta().getDisplayName()));
        Player target = Bukkit.getServer().getPlayer(ChatColor.stripColor(event.getInventory().getItem(8).getItemMeta().getDisplayName()));
                if ((event.getSlot() >= 0 && event.getSlot() <= 9) ||
                (event.getSlot() >= 13 && event.getSlot() <= 18) ||
                (event.getSlot() >= 22 && event.getSlot() <= 27) ||
                (event.getSlot() >= 31 && event.getSlot() <= 36) ||
                (event.getSlot() >= 40 && event.getSlot() <= 53))
            event.setCancelled(true);

        switch (event.getSlot()) {
            case 45: {
                if (event.getInventory().getItem(45).getData().getData() == 14) {
                    if (event.getInventory().getItem(53).getData().getData() == 11) {
                        byte needSlot = 0;
                        byte mySlot = 0;

                        for (byte count = 0; count < 4; count++)
                            for (byte count2 = 10; count2 < 13; count2++) {
                                ItemStack item = target.getOpenInventory().getItem(count2 + (count * 9));
                                if (item != null) {
                                    if (item.getTypeId() == 160 && item.getData().getData() == 8)
                                        if (item.hasItemMeta())
                                            if (item.getItemMeta().hasDisplayName()) {
                                                if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).compareTo("[아무것도 올려지지 않음]") != 0)
                                                    needSlot++;
                                            } else
                                                needSlot++;
                                        else
                                            needSlot++;
                                    else
                                        needSlot++;
                                }
                            }

                        for (byte count = 0; count < 36; count++)
                            if (player.getInventory().getItem(count) == null)
                                mySlot++;
                        if (mySlot < needSlot) {
                            player.sendMessage(ChatColor.RED + "[교환] : 당신의 인벤토리 공간이 부족합니다!");
                            target.sendMessage(ChatColor.RED + "[교환] : 상대방의 인벤토리 공간이 부족합니다!");
                            return;
                        }

                        needSlot = 0;
                        mySlot = 0;

                        for (byte count = 0; count < 4; count++)
                            for (byte count2 = 10; count2 < 13; count2++) {
                                ItemStack item = event.getInventory().getItem(count2 + (count * 9));
                                if (item != null) {
                                    if (item.getTypeId() == 160 && item.getData().getData() == 8)
                                        if (item.hasItemMeta())
                                            if (item.getItemMeta().hasDisplayName()) {
                                                if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).compareTo("[아무것도 올려지지 않음]") != 0)
                                                    needSlot++;
                                            } else
                                                needSlot++;
                                        else
                                            needSlot++;
                                    else
                                        needSlot++;
                                }
                            }

                        for (byte count = 0; count < 36; count++)
                            if (target.getInventory().getItem(count) == null)
                                mySlot++;

                        if (mySlot < needSlot) {
                            player.sendMessage(ChatColor.RED + "[교환] : 상대방의 인벤토리 공간이 부족합니다!");
                            target.sendMessage(ChatColor.RED + "[교환] : 당신의 인벤토리 공간이 부족합니다!");
                            return;
                        }

                        for (byte count = 0; count < 4; count++) {
                            for (byte count2 = 10; count2 < 13; count2++) {
                                if (event.getInventory().getItem(count2 + (count * 9)) != null) {
                                    if (event.getInventory().getItem(count2 + (count * 9)).hasItemMeta()) {
                                        if (!event.getInventory().getItem(count2 + (count * 9)).getItemMeta().hasLore() &&
                                                event.getInventory().getItem(count2 + (count * 9)).getItemMeta().hasDisplayName()) {
                                            if (event.getInventory().getItem(count2 + (count * 9)).getItemMeta().getDisplayName().compareTo(ChatColor.GRAY + "" + ChatColor.BOLD + "[아무것도 올려지지 않음]") != 0)
                                                target.getInventory().addItem(event.getInventory().getItem(count2 + (count * 9)));
                                        } else
                                            target.getInventory().addItem(event.getInventory().getItem(count2 + (count * 9)));
                                    } else
                                        target.getInventory().addItem(event.getInventory().getItem(count2 + (count * 9)));
                                } else
                                    target.getInventory().addItem(event.getInventory().getItem(count2 + (count * 9)));
                            }
                        }

                        for (byte count = 0; count < 4; count++) {
                            for (byte count2 = 10; count2 < 13; count2++) {
                                if (target.getOpenInventory().getItem(count2 + (count * 9)) != null) {
                                    if (target.getOpenInventory().getItem(count2 + (count * 9)).hasItemMeta()) {
                                        if (!target.getOpenInventory().getItem(count2 + (count * 9)).getItemMeta().hasLore() &&
                                                target.getOpenInventory().getItem(count2 + (count * 9)).getItemMeta().hasDisplayName()) {
                                            if (target.getOpenInventory().getItem(count2 + (count * 9)).getItemMeta().getDisplayName().compareTo(ChatColor.GRAY + "" + ChatColor.BOLD + "[아무것도 올려지지 않음]") != 0)
                                                player.getInventory().addItem(target.getOpenInventory().getItem(count2 + (count * 9)));
                                        } else
                                            player.getInventory().addItem(target.getOpenInventory().getItem(count2 + (count * 9)));
                                    } else
                                        player.getInventory().addItem(target.getOpenInventory().getItem(count2 + (count * 9)));
                                } else
                                    player.getInventory().addItem(target.getOpenInventory().getItem(count2 + (count * 9)));
                            }
                        }
                        target.getOpenInventory().setItem(0, new ItemStack(2));
                        event.getInventory().setItem(0, new ItemStack(2));
                        target.closeInventory();
                        player.closeInventory();
                    } else {
                        ItemStack Icon = new MaterialData(35, (byte) 11).toItemStack();
                        Icon.setAmount(1);
                        ItemMeta Icon_Meta = Icon.getItemMeta();
                        Icon_Meta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "[ 준비 완료 ]");
                        Icon.setItemMeta(Icon_Meta);
                        event.getInventory().setItem(45, Icon);
                        target.getOpenInventory().setItem(53, Icon);
                        SoundUtil.playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1.0F, 1.0F);
                        SoundUtil.playSound(target, Sound.ENTITY_VILLAGER_AMBIENT, 1.0F, 1.0F);
                    }
                } else {
                    ItemStack Icon = new MaterialData(35, (byte) 14).toItemStack();
                    Icon.setAmount(1);
                    ItemMeta Icon_Meta = Icon.getItemMeta();
                    Icon_Meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "[  대기중  ]");
                    Icon.setItemMeta(Icon_Meta);
                    event.getInventory().setItem(45, Icon);
                    target.getOpenInventory().setItem(53, Icon);
                    SoundUtil.playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1.0F, 1.0F);
                    SoundUtil.playSound(target, Sound.ENTITY_VILLAGER_AMBIENT, 1.0F, 1.0F);
                }
            }
            return;
            case 49: {
                event.getInventory().setItem(0, new ItemStack(138, 1));
                for (byte count = 0; count < 4; count++) {
                    for (byte count2 = 10; count2 < 13; count2++) {
                        if (event.getInventory().getItem(count2 + (count * 9)) != null) {
                            if (event.getInventory().getItem(count2 + (count * 9)).hasItemMeta()) {
                                if (!event.getInventory().getItem(count2 + (count * 9)).getItemMeta().hasLore() &&
                                        event.getInventory().getItem(count2 + (count * 9)).getItemMeta().hasDisplayName()) {
                                    if (event.getInventory().getItem(count2 + (count * 9)).getItemMeta().getDisplayName().compareTo(ChatColor.GRAY + "" + ChatColor.BOLD + "[아무것도 올려지지 않음]") != 0)
                                        player.getInventory().addItem(event.getInventory().getItem(count2 + (count * 9)));
                                } else
                                    player.getInventory().addItem(event.getInventory().getItem(count2 + (count * 9)));
                            } else
                                player.getInventory().addItem(event.getInventory().getItem(count2 + (count * 9)));
                        } else
                            player.getInventory().addItem(event.getInventory().getItem(count2 + (count * 9)));
                    }
                }
                target.closeInventory();
                player.closeInventory();
            }
            return;
        }
    }


    public void ExchangeGUI_Close(InventoryCloseEvent event) {
                if (event.getInventory().getItem(0).getTypeId() == 138) {
            SoundUtil.playSound((Player) event.getPlayer(), Sound.BLOCK_PISTON_CONTRACT, 1.0F, 1.8F);
            event.getPlayer().sendMessage(ChatColor.RED + "[교환] : 교환을 취소하였습니다!");
        } else if (event.getInventory().getItem(0).getTypeId() == 397) {
            Player target = Bukkit.getServer().getPlayer(ChatColor.stripColor(event.getInventory().getItem(8).getItemMeta().getDisplayName()));
            Player player = (Player) event.getPlayer();
            SoundUtil.playSound((Player) event.getPlayer(), Sound.BLOCK_PISTON_CONTRACT, 1.0F, 1.8F);
            event.getPlayer().sendMessage(ChatColor.RED + "[교환] : 교환이 취소되었습니다!");
            for (byte count = 0; count < 4; count++) {
                for (byte count2 = 10; count2 < 13; count2++) {
                    if (event.getInventory().getItem(count2 + (count * 9)) != null) {
                        if (event.getInventory().getItem(count2 + (count * 9)).hasItemMeta()) {
                            if (!event.getInventory().getItem(count2 + (count * 9)).getItemMeta().hasLore() &&
                                    event.getInventory().getItem(count2 + (count * 9)).getItemMeta().hasDisplayName()) {
                                if (event.getInventory().getItem(count2 + (count * 9)).getItemMeta().getDisplayName().compareTo(ChatColor.GRAY + "" + ChatColor.BOLD + "[아무것도 올려지지 않음]") != 0)
                                    player.getInventory().addItem(event.getInventory().getItem(count2 + (count * 9)));
                            } else
                                player.getInventory().addItem(event.getInventory().getItem(count2 + (count * 9)));
                        } else
                            player.getInventory().addItem(event.getInventory().getItem(count2 + (count * 9)));
                    } else
                        player.getInventory().addItem(event.getInventory().getItem(count2 + (count * 9)));
                }
            }
            event.getInventory().setItem(0, new ItemStack(166, 1));
            target.closeInventory();
        } else if (event.getInventory().getItem(0).getTypeId() == 2) {
            SoundUtil.playSound((Player) event.getPlayer(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.8F);
            event.getPlayer().sendMessage(ChatColor.GREEN + "[교환] : 교환이 성사되었습니다!");
        }
    }


    public void SetFriends(Player player, Player target) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager FriendsList = YC.getNewConfig("Friend/" + player.getUniqueId().toString() + ".yml");
        if (!FriendsList.contains("Name")) {
            FriendsList.set("Name", player.getName());
            FriendsList.createSection("Friends");
            FriendsList.createSection("Waitting");
            FriendsList.saveConfig();
        }
        YamlManager SideFriendsList = YC.getNewConfig("Friend/" + target.getUniqueId().toString() + ".yml");
        if (!SideFriendsList.contains("Name")) {
            SideFriendsList.set("Name", target.getName());
            SideFriendsList.createSection("Friends");
            SideFriendsList.createSection("Waitting");
            SideFriendsList.saveConfig();
        }

                Long AddTime = new ETC().getSec();
        Object[] Friend = FriendsList.getConfigurationSection("Waitting").getKeys(false).toArray();
        Object[] SideFriend = SideFriendsList.getConfigurationSection("Waitting").getKeys(false).toArray();

        for (short counter = 0; counter < SideFriend.length; counter++) {
            if (SideFriend[counter].toString().equals(player.getName())) {
                SoundUtil.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
                player.sendMessage(ChatColor.RED + "[친구] : 이미 상대방에게 친구 요청을 한 상태입니다!");
                return;
            }
        }

        for (short counter = 0; counter < Friend.length; counter++) {
            if (Friend[counter].toString().equals(target.getName())) {
                SideFriendsList.removeKey("Waitting." + player.getName());
                SideFriendsList.set("Friends." + player.getName(), AddTime);
                SideFriendsList.saveConfig();
                FriendsList.removeKey("Waitting." + target.getName());
                FriendsList.set("Friends." + target.getName(), AddTime);
                FriendsList.saveConfig();
                SoundUtil.playSound(player, Sound.BLOCK_LAVA_POP, 1.0F, 1.2F);
                player.sendMessage(ChatColor.AQUA + "[친구] : " + ChatColor.YELLOW + target.getName() + ChatColor.AQUA + "님께서 친구로 등록되었습니다!");
                if (target.isOnline()) {
                    SoundUtil.playSound(target, Sound.BLOCK_LAVA_POP, 1.0F, 1.2F);
                    target.sendMessage(ChatColor.AQUA + "[친구] : " + ChatColor.YELLOW + player.getName() + ChatColor.AQUA + "님께서 친구로 등록되었습니다!");
                }
                return;
            }
        }
        Friend = FriendsList.getConfigurationSection("Friends").getKeys(false).toArray();
        for (short counter = 0; counter < Friend.length; counter++) {
            if (Friend[counter].toString().equals(target.getName())) {
                SoundUtil.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
                player.sendMessage(ChatColor.RED + "[친구] : 이미 친구로 등록된 플레이어 입니다!");
                return;
            }
        }
        SideFriendsList.removeKey("Friends." + player.getName());
        SideFriendsList.set("Waitting." + player.getName(), AddTime);
        SideFriendsList.saveConfig();
        SoundUtil.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.8F);
        player.sendMessage(ChatColor.YELLOW + "[친구] : 상대방에게 친구 신청을 하였습니다!");
        if (target.isOnline()) {
            SoundUtil.playSound(target, Sound.ENTITY_WOLF_AMBIENT, 1.0F, 1.0F);
            target.sendMessage(ChatColor.YELLOW + "[친구] : " + ChatColor.WHITE + player.getName() + ChatColor.YELLOW + "님께서 친구 요청을 하셨습니다!");
            target.sendMessage(ChatColor.GOLD + "/친구" + ChatColor.WHITE + " 명령어를 입력하여 친구 신청을 확인 해 주세요!");
        }
    }

    public void FriendJoinQuitMessage(Player player, boolean isJoinMessage) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager FriendsList = YC.getNewConfig("Friend/" + player.getUniqueId().toString() + ".yml");
        if (!FriendsList.contains("Name")) {
            FriendsList.set("Name", player.getName());
            FriendsList.createSection("Friends");
            FriendsList.createSection("Waitting");
            FriendsList.saveConfig();
            return;
        }
        Player target = null;
        YamlManager SideFriendsList = null;
                Object[] Friend = FriendsList.getConfigurationSection("Friends").getKeys(false).toArray();
        for (short counter = 0; counter < Friend.length; counter++) {
            target = Bukkit.getServer().getPlayer(Friend[counter].toString());
            if (target != null)
                if (target.isOnline()) {
                    SideFriendsList = YC.getNewConfig("Friend/" + target.getUniqueId().toString() + ".yml");
                    if (SideFriendsList.contains("Name")) {
                        if (SideFriendsList.contains("Friends." + player.getName())) {
                            if (isJoinMessage) {
                                SoundUtil.playSound(target, Sound.BLOCK_WOODEN_DOOR_OPEN, 0.5F, 1.0F);
                                target.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "[접속] " + ChatColor.WHITE + "" + ChatColor.BOLD + player.getName());
                            } else {
                                SoundUtil.playSound(target, Sound.BLOCK_WOODEN_DOOR_CLOSE, 0.5F, 1.0F);
                                target.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[퇴장] " + ChatColor.GRAY + "" + ChatColor.BOLD + player.getName());
                            }
                            break;
                        }
                    }
                }
        }
    }

    public void AddExchangeTarget(Player player, Player target) {
                if (ServerTick_Main.PlayerTaskList.containsKey(target.getName())) {
            SoundUtil.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
            player.sendMessage(ChatColor.RED + "[교환] : 해당 플레이어는 현재 다른 요청을 처리하고 있습니다.");
            player.sendMessage(ChatColor.GRAY + "(잠시 후 다시 시도 해 보세요.)");
            return;
        }
        if (ServerTick_Main.PlayerTaskList.containsKey(player.getName())) {
            SoundUtil.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
            player.sendMessage(ChatColor.RED + "[교환] : 당신은 먼저 요청받은 일을 처리해야 합니다.");
            return;
        }
        long UTC = ServerTick_Main.nowUTC - 1;
        ServerTick_Main.PlayerTaskList.put(player.getName(), "" + UTC);
        ServerTick_Main.PlayerTaskList.put(player.getName(), "" + UTC);
        player.closeInventory();
        ServerTick_Object STSO = new ServerTick_Object(UTC, "P_EC");
        STSO.setCount(0);//횟 수 초기화
        STSO.setMaxCount(10);//교환 요청 최대 대기 시간
        STSO.setString((byte) 0, player.getName());//교환 요청 한 플레이어 이름 저장(플레이어가 오프라인인지 확인해야 하므로)
        STSO.setString((byte) 1, target.getName());//교환 요청 받은 플레이어 이름 저장(플레이어가 오프라인인지 확인해야 하므로)
        ServerTick_Main.Schedule.put(UTC, STSO);
        SoundUtil.playSound(player, Sound.BLOCK_NOTE_PLING, 1.0F, 1.0F);
        player.sendMessage(ChatColor.YELLOW + "[교환] : 상대방에게 교환 신청을 하였습니다!");
        target.sendMessage(ChatColor.YELLOW + "[교환] : " + ChatColor.WHITE + ChatColor.BOLD + player.getName() + ChatColor.GREEN + " 님께서 교환 요청을 하였습니다!");
        target.sendMessage(ChatColor.GOLD + "/수락" + ChatColor.WHITE + " 상대의 요청에 수락합니다.");
        target.sendMessage(ChatColor.GOLD + "/거절" + ChatColor.WHITE + " 상대의 요청에 거절합니다.");
    }

}
