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

package io.github.goldbigdragon.goldbigdragonrpg.customitem;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.github.goldbigdragon.goldbigdragonrpg.effect.SoundUtil;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_ServerOption;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class CustomItem_Command {
    public void onCommand1(CommandSender talker, Command command, String string, String[] args) {
        Player player = (Player) talker;
        if (talker.isOp()) {
            if (args.length == 0 || args.length >= 4) {
                HelpMessage(player);
                return;
            }
            switch (ChatColor.stripColor(args[0])) {
                case "목록": {
                    CustomItem_Gui IGUI = new CustomItem_Gui();
                    IGUI.ItemListGUI(player, 0);
                }
            }
        } else {
            talker.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
            SoundUtil.playSound((Player) talker, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
        }
    }

    public void onCommand2(CommandSender talker, Command command, String string, String[] args) {
        Player player = (Player) talker;
        if (talker.isOp()) {
            String s = "";
            switch (ChatColor.stripColor(args[0])) {
                case "태그삭제": {
                    ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
                    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                    itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    player.getInventory().getItemInMainHand().setItemMeta(itemMeta);
                }
                return;
                case "ID": {
                    if (args.length != 2) {
                        HelpMessage(player);
                        return;
                    }
                    SettingItemMeta(player, (byte) 0, Integer.parseInt(args[1]));
                }
                return;
                case "DATA": {
                    if (args.length != 2) {
                        HelpMessage(player);
                        return;
                    }
                    SettingItemMeta(player, (byte) 1, Integer.parseInt(args[1]));
                }
                return;
                case "개수": {
                    if (args.length != 2) {
                        HelpMessage(player);
                        return;
                    }
                    SettingItemMeta(player, (byte) 2, Integer.parseInt(args[1]));
                }
                return;
                case "이름": {
                    if (args.length < 2) {
                        HelpMessage(player);
                        return;
                    }
                    if (args.length >= 2) {
                        s = args[1];
                        for (byte a = 2; a <= ((args.length) - 1); a++)
                            s = s + " " + args[a];
                    }
                    SettingItemMeta(player, (byte) 0, s);
                }
                return;
                case "설명추가": {
                    if (args.length < 2) {
                        HelpMessage(player);
                        return;
                    }
                    if (args.length >= 2) {
                        s = args[1];
                        for (byte a = 2; a <= ((args.length) - 1); a++)
                            s = s + " " + args[a];
                    }
                    SettingItemMeta(player, (byte) 1, s);
                }
                return;
                case "설명제거": {
                    SettingItemMeta(player, (byte) 2, null);
                }
                return;
                case "수리": {
                    if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                        SoundUtil sound = new SoundUtil();
                        SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
                        player.sendMessage(ChatColor.RED + "[SYSTEM] : 손에 수리할 아이템을 쥐고 있어야 합니다!");
                        return;
                    }
                    player.getInventory().getItemInMainHand().setDurability((short) -player.getInventory().getItemInMainHand().getType().getMaxDurability());
                    ItemStack item = player.getInventory().getItemInMainHand();
                    if (item.hasItemMeta()) {
                        if (item.getItemMeta().hasLore()) {
                            for (short count = 0; count < item.getItemMeta().getLore().size(); count++) {
                                ItemMeta Meta = item.getItemMeta();
                                if (Meta.getLore().get(count).contains("내구도")) {
                                    String[] Lore = Meta.getLore().get(count).split(" : ");
                                    String[] SubLore = Lore[1].split(" / ");
                                    List<String> PLore = Meta.getLore();
                                    PLore.set(count, Lore[0] + " : " + SubLore[1] + " / " + SubLore[1]);
                                    Meta.setLore(PLore);
                                    item.setItemMeta(Meta);
                                }
                            }
                        }
                    }
                }
                return;
                case "포션":
                    return;
                case "인챈트":
                    return;
                default: {
                    HelpMessage(player);
                }
            }
        } else {
            talker.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
            SoundUtil.playSound((Player) talker, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
        }
    }

    public void SettingItemMeta(Player player, byte type, int value) {
        if (!player.isOp()) {
            SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
            player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
            return;
        }
        if (player.getInventory().getItemInMainHand().getType() == Material.AIR || player.getInventory().getItemInMainHand().getTypeId() == 0) {
            SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
            player.sendMessage(ChatColor.RED + "[SYSTEM] : 손에 아이템을 들고 있어야 합니다!");
            return;
        } else {
            ItemStack item = player.getInventory().getItemInMainHand();
            switch (type) {
                case 0: {
                    item.setTypeId(value);
                    player.getInventory().setItemInMainHand(item);
                }
                break;
                case 1: {
                    ItemStack it = new MaterialData(item.getTypeId(), (byte) value).toItemStack();
                    it.setAmount(item.getAmount());
                    if (item.hasItemMeta())
                        it.setItemMeta(item.getItemMeta());
                    player.getInventory().setItemInMainHand(it);
                }
                break;
                case 2: {
                    if (value >= 127) value = 127;
                    if (value <= 0) value = 1;
                    item.setAmount(value);
                }
                break;
            }
        }
    }

    public void SettingItemMeta(Player player, byte type, String value) {
        if (!player.isOp()) {
            SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
            player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
            return;
        }
        if (player.getInventory().getItemInMainHand().getType() == Material.AIR || player.getInventory().getItemInMainHand().getTypeId() == 0) {
            SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
            player.sendMessage(ChatColor.RED + "[SYSTEM] : 손에 아이템을 들고 있어야 합니다!");
            return;
        } else {
            ItemStack item = player.getInventory().getItemInMainHand();
            ItemMeta itemMeta = item.getItemMeta();
            List<String> Lore = Collections.emptyList();
            switch (type) {
                case 0: {
                    value = ChatColor.WHITE + value;
                    itemMeta.setDisplayName(value);
                    item.setItemMeta(itemMeta);
                    player.getInventory().setItemInMainHand(item);
                }
                break;
                case 1: {
                    value = ChatColor.WHITE + value;
                    if (!itemMeta.hasLore())
                        itemMeta.setLore(Collections.singletonList(value));
                    else {
                        Lore = itemMeta.getLore();
                        Lore.add(Lore.size(), value);
                        itemMeta.setLore(Lore);
                    }
                    item.setItemMeta(itemMeta);
                    player.getInventory().setItemInMainHand(item);
                }
                break;
                case 2: {
                    if (!itemMeta.hasLore()) {
                        return;
                    } else {
                        Lore = itemMeta.getLore();
                        for (byte repeat = 0; repeat < 5; repeat++)
                            for (byte count = 0; count < Lore.size(); count++)
                                Lore.remove(count);
                        itemMeta.setLore(Lore);
                    }
                    item.setItemMeta(itemMeta);
                    player.getInventory().setItemInMainHand(item);
                }
                break;
            }
        }
    }

    public void HelpMessage(Player player) {
        player.sendMessage(ChatColor.YELLOW + "────────────[아이템 명령어]────────────");
        player.sendMessage(ChatColor.GOLD + "/아이템 수리" + ChatColor.YELLOW + " - 손에 들고있는 아이템을 수리합니다.");
        player.sendMessage(ChatColor.GOLD + "/아이템 이름 <문자열>" + ChatColor.YELLOW + " - 해당 아이템의 보여질 이름을 설정합니다.");
        player.sendMessage(ChatColor.GOLD + "/아이템 ID <숫자>" + ChatColor.YELLOW + " - 해당 아이템의 ID값을 설정합니다.");
        player.sendMessage(ChatColor.GOLD + "/아이템 DATA <숫자>" + ChatColor.YELLOW + " - 해당 아이템의 DATA값을 설정합니다.");
        player.sendMessage(ChatColor.GOLD + "/아이템 개수 <숫자>" + ChatColor.YELLOW + " - 해당 아이템의 개수를 설정합니다.");
        player.sendMessage(ChatColor.GOLD + "/아이템 태그삭제" + ChatColor.YELLOW + " - [1.8.0은 안됨] 다이아몬드 검의 +7 공격피해와 같은 아이템 태그를 삭제합니다.");
        //player.sendMessage(ChatColor.GOLD + "/아이템 포션 [효과] [강도]" + ChatColor.YELLOW + " - 아이템에 포션 효과를 더합니다.");
        //player.sendMessage(ChatColor.GOLD + "/아이템 인챈트 [인챈트] [레벨]" + ChatColor.YELLOW + " - 아이템에 인챈트 효과를 더합니다.");
        player.sendMessage(ChatColor.GOLD + "/아이템 설명추가 <문자열>" + ChatColor.YELLOW + " - 해당 아이템의 로어 한 줄을 추가합니다.");
        player.sendMessage(ChatColor.GOLD + "/아이템 설명제거" + ChatColor.YELLOW + " - 해당 아이템의 모든 로어를 삭제합니다.");
        player.sendMessage(ChatColor.GREEN + "[아래와 같은 설명을 추가할 시, 아이템에 효과가 생깁니다.]");
        player.sendMessage(ChatColor.AQUA + "/아이템 설명추가 " + Main_ServerOption.Damage + " : 3 ~ 6" + ChatColor.RED + " (아이템 장착시 " + Main_ServerOption.Damage + " 3 ~ 6 상승)");
        player.sendMessage(ChatColor.AQUA + "/아이템 설명추가 방어 : 3" + ChatColor.RED + " (아이템 장착시 방어 3상승)");
        player.sendMessage(ChatColor.GREEN + "[추가 가능한 옵션 태그]");
        player.sendMessage(ChatColor.AQUA + "[" + Main_ServerOption.Damage + " : <숫자> ~ <숫자>] [방어 : <숫자>] [보호 : <숫자>]\n"
                + "[" + Main_ServerOption.MagicDamage + " : <숫자> ~ <숫자>] [마법 방어 : <숫자>] [마법 보호 : <숫자>]\n"
                + "[" + Main_ServerOption.STR + " : <숫자>] [" + Main_ServerOption.DEX + " : <숫자>] [" + Main_ServerOption.INT + " : <숫자>] [" + Main_ServerOption.WILL + " : <숫자>] [" + Main_ServerOption.LUK + " : <숫자>]\n"
                + "[크리티컬 : <숫자>] [밸런스 : <숫자>] [내구도 : <숫자> / <숫자>] \n"
                + "[업그레이드 : <숫자> / <숫자>] [생명력 : <숫자>] [마나 : <숫자>]\n" + ChatColor.GREEN + "[아이템 타입 태그] - 퀵슬롯 등록된 스킬의 무기 제한 옵션에 관여" + ChatColor.AQUA + "\n[소비] [근접 무기] [한손 검] [양손 검] [원거리 무기] [활] [석궁] [도끼] [낫] [마법 무기] [원드] [스태프]");
        player.sendMessage(ChatColor.YELLOW + "────────────────────────────────");
    }
}
