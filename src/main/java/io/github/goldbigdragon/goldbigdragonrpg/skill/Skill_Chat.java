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

package io.github.goldbigdragon.goldbigdragonrpg.skill;

import io.github.goldbigdragon.goldbigdragonrpg.effect.SoundUtil;
import io.github.goldbigdragon.goldbigdragonrpg.listener.Main_Interact;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import io.github.goldbigdragon.goldbigdragonrpg.util.Util_Chat;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import io.github.goldbigdragon.goldbigdragonrpg.user.UserData_Object;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;

public class Skill_Chat extends Util_Chat {
    public void SkillTypeChatting(PlayerChatEvent event) {
        UserData_Object u = new UserData_Object();
        SoundUtil sound = new SoundUtil();
        Player player = event.getPlayer();
        OPboxSkill_Gui SKGUI = new OPboxSkill_Gui();
        YamlController YC = new YamlController(Main_Main.plugin);

        YamlManager SkillList = YC.getNewConfig("Skill/SkillList.yml");
        event.setCancelled(true);

        String Message = ChatColor.stripColor(event.getMessage());

        switch (u.getString(player, (byte) 1)) {
            case "SKL"://SkillLore
                SoundUtil.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 1.0F, 0.5F);
                SkillList.set(u.getString(player, (byte) 2) + ".SkillRank." + u.getInt(player, (byte) 4) + ".Lore", event.getMessage());
                SkillList.saveConfig();
                SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte) 2), (short) u.getInt(player, (byte) 4));
                u.clearAll(player);
                return;

            case "CS"://CreateSkill
                Message.replace(".", "");
                Message.replace("\"", "");
                Message.replace("\'", "");
                Message.replace("\\", "");
                if (Message.compareTo("") == 0 || Message == null)
                    Message = "이름없는 스킬";
                SkillList.set(Message + ".ID", 403);
                SkillList.set(Message + ".DATA", 0);
                SkillList.set(Message + ".Amount", 1);
                SkillList.set(Message + ".SkillRank." + 1 + ".Command", "null");
                SkillList.set(Message + ".SkillRank." + 1 + ".BukkitPermission", false);
                SkillList.set(Message + ".SkillRank." + 1 + ".MagicSpells", "null");
                SkillList.set(Message + ".SkillRank." + 1 + ".Lore", ChatColor.GRAY + "     [설명 없음]     ");
                SkillList.set(Message + ".SkillRank." + 1 + ".AffectStat", "없음");
                SkillList.set(Message + ".SkillRank." + 1 + ".DistrictWeapon", "없음");
                SkillList.saveConfig();
                SoundUtil.SP(player, org.bukkit.Sound.ENTITY_HORSE_SADDLE, 1.0F, 0.5F);
                SKGUI.AllSkillsGUI(player, (short) 0, false, "Maple");
                u.clearAll(player);
                return;
            case "CSID"://ChangeSkillID
                if (isIntMinMax(Message, player, 1, 2267)) {
                    Main_Interact I = new Main_Interact();
                    if (I.SetItemDefaultName(Short.parseShort(Message), (byte) 0).compareTo("지정되지 않은 아이템") == 0) {
                        player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 아이템은 존재하지 않습니다!");
                        SoundUtil.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
                        return;
                    }
                    SkillList.set(u.getString(player, (byte) 2) + ".ID", Integer.parseInt(Message));
                    SkillList.saveConfig();
                    u.setType(player, "Skill");
                    u.setString(player, (byte) 1, "CSD");
                    SoundUtil.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "[스킬] : 스킬 아이콘의 DATA값을 입력 해 주세요!!");
                }
                return;
            case "CSD"://ChangeSkillData
                if (isIntMinMax(Message, player, 0, Integer.MAX_VALUE)) {
                    SkillList.set(u.getString(player, (byte) 2) + ".DATA", Integer.parseInt(Message));
                    SkillList.saveConfig();
                    u.setType(player, "Skill");
                    u.setString(player, (byte) 1, "CSA");
                    SoundUtil.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "[스킬] : 스킬 아이콘의 개수를 입력 해 주세요!!");
                }
                return;
            case "CSA"://ChangeSkillAmount
                if (isIntMinMax(Message, player, 1, 127)) {
                    SkillList.set(u.getString(player, (byte) 2) + ".Amount", Integer.parseInt(Message));
                    SkillList.saveConfig();
                    SoundUtil.SP(player, org.bukkit.Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
                    SKGUI.AllSkillsGUI(player, (short) 0, false, "Maple");
                    u.clearAll(player);
                }
                return;
            case "SP"://SkillPoint
                if (isIntMinMax(Message, player, 0, Integer.MAX_VALUE)) {
                    SkillList.set(u.getString(player, (byte) 2) + ".SkillRank." + u.getInt(player, (byte) 4) + ".SkillPoint", Integer.parseInt(Message));
                    SkillList.saveConfig();
                    SoundUtil.SP(player, org.bukkit.Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
                    SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte) 2), (short) u.getInt(player, (byte) 4));
                    u.clearAll(player);
                }
                return;
            case "NeedLV"://NeedLevel
                if (isIntMinMax(Message, player, 0, Integer.MAX_VALUE)) {
                    SkillList.set(u.getString(player, (byte) 2) + ".SkillRank." + u.getInt(player, (byte) 4) + ".NeedLevel", Integer.parseInt(Message));
                    SkillList.saveConfig();
                    SoundUtil.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "[스킬] : 스킬을 배울 수 있는 누적 레벨을 설정해 주세요!");
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "[제한 없음 : 0] [최대 : " + Integer.MAX_VALUE + "]");
                    u.setType(player, "Skill");
                    u.setString(player, (byte) 1, "NeedRealLV");
                    u.setString(player, (byte) 2, u.getString(player, (byte) 2));
                    u.setInt(player, (byte) 4, u.getInt(player, (byte) 4));
                }
                return;
            case "NeedRealLV"://SkillPoint
                if (isIntMinMax(Message, player, 0, Integer.MAX_VALUE)) {
                    SkillList.set(u.getString(player, (byte) 2) + ".SkillRank." + u.getInt(player, (byte) 4) + ".NeedRealLevel", Integer.parseInt(Message));
                    SkillList.saveConfig();
                    SoundUtil.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                    SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte) 2), (short) u.getInt(player, (byte) 4));
                    u.clearAll(player);
                }
                return;
            case "BH"://BonusHealth
                if (isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE)) {
                    SkillList.set(u.getString(player, (byte) 2) + ".SkillRank." + u.getInt(player, (byte) 4) + ".BonusHP", Integer.parseInt(Message));
                    SkillList.saveConfig();
                    SoundUtil.SP(player, org.bukkit.Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
                    SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte) 2), (short) u.getInt(player, (byte) 4));
                    u.clearAll(player);
                }
                return;
            case "BM"://BonusMana
                if (isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE)) {
                    SkillList.set(u.getString(player, (byte) 2) + ".SkillRank." + u.getInt(player, (byte) 4) + ".BonusMP", Integer.parseInt(Message));
                    SkillList.saveConfig();
                    SoundUtil.SP(player, org.bukkit.Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
                    SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte) 2), (short) u.getInt(player, (byte) 4));
                    u.clearAll(player);
                }
                return;
            case "BSTR"://BonusSTR
                if (isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE)) {
                    SkillList.set(u.getString(player, (byte) 2) + ".SkillRank." + u.getInt(player, (byte) 4) + ".BonusSTR", Integer.parseInt(Message));
                    SkillList.saveConfig();
                    SoundUtil.SP(player, org.bukkit.Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
                    SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte) 2), (short) u.getInt(player, (byte) 4));
                    u.clearAll(player);
                }
                return;
            case "BDEX"://BonusDEX
                if (isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE)) {
                    SkillList.set(u.getString(player, (byte) 2) + ".SkillRank." + u.getInt(player, (byte) 4) + ".BonusDEX", Integer.parseInt(Message));
                    SkillList.saveConfig();
                    SoundUtil.SP(player, org.bukkit.Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
                    SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte) 2), (short) u.getInt(player, (byte) 4));
                    u.clearAll(player);
                }
                return;
            case "BINT"://BonusINT
                if (isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE)) {
                    SkillList.set(u.getString(player, (byte) 2) + ".SkillRank." + u.getInt(player, (byte) 4) + ".BonusINT", Integer.parseInt(Message));
                    SkillList.saveConfig();
                    SoundUtil.SP(player, org.bukkit.Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
                    SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte) 2), (short) u.getInt(player, (byte) 4));
                    u.clearAll(player);
                }
                return;
            case "BWILL"://BonusWILL
                if (isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE)) {
                    SkillList.set(u.getString(player, (byte) 2) + ".SkillRank." + u.getInt(player, (byte) 4) + ".BonusWILL", Integer.parseInt(Message));
                    SkillList.saveConfig();
                    SoundUtil.SP(player, org.bukkit.Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
                    SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte) 2), (short) u.getInt(player, (byte) 4));
                    u.clearAll(player);
                }
                return;
            case "BLUK"://BonusLUK
                if (isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE)) {
                    SkillList.set(u.getString(player, (byte) 2) + ".SkillRank." + u.getInt(player, (byte) 4) + ".BonusLUK", Integer.parseInt(Message));
                    SkillList.saveConfig();
                    SoundUtil.SP(player, org.bukkit.Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
                    SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte) 2), (short) u.getInt(player, (byte) 4));
                    u.clearAll(player);
                }
                return;
            case "BBAL"://BonusBalance
                if (isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE)) {
                    SkillList.set(u.getString(player, (byte) 2) + ".SkillRank." + u.getInt(player, (byte) 4) + ".BonusBAL", Integer.parseInt(Message));
                    SkillList.saveConfig();
                    SoundUtil.SP(player, org.bukkit.Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
                    SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte) 2), (short) u.getInt(player, (byte) 4));
                    u.clearAll(player);
                }
                return;
            case "BCRI"://BonusCritical
                if (isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE)) {
                    SkillList.set(u.getString(player, (byte) 2) + ".SkillRank." + u.getInt(player, (byte) 4) + ".BonusCRI", Integer.parseInt(Message));
                    SkillList.saveConfig();
                    SoundUtil.SP(player, org.bukkit.Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
                    SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte) 2), (short) u.getInt(player, (byte) 4));
                    u.clearAll(player);
                }
                return;
            case "BDEF"://BonusDefense
                if (isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE)) {
                    SkillList.set(u.getString(player, (byte) 2) + ".SkillRank." + u.getInt(player, (byte) 4) + ".BonusDEF", Integer.parseInt(Message));
                    SkillList.saveConfig();
                    SoundUtil.SP(player, org.bukkit.Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
                    SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte) 2), (short) u.getInt(player, (byte) 4));
                    u.clearAll(player);
                }
                return;
            case "BPRO"://BonusProtect
                if (isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE)) {
                    SkillList.set(u.getString(player, (byte) 2) + ".SkillRank." + u.getInt(player, (byte) 4) + ".BonusPRO", Integer.parseInt(Message));
                    SkillList.saveConfig();
                    SoundUtil.SP(player, org.bukkit.Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
                    SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte) 2), (short) u.getInt(player, (byte) 4));
                    u.clearAll(player);
                }
                return;
            case "BMDEF"://BonusMagicDefense
                if (isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE)) {
                    SkillList.set(u.getString(player, (byte) 2) + ".SkillRank." + u.getInt(player, (byte) 4) + ".BonusMDEF", Integer.parseInt(Message));
                    SkillList.saveConfig();
                    SoundUtil.SP(player, org.bukkit.Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
                    SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte) 2), (short) u.getInt(player, (byte) 4));
                    u.clearAll(player);
                }
                return;
            case "BMPRO"://BonusMagicProtect
                if (isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE)) {
                    SkillList.set(u.getString(player, (byte) 2) + ".SkillRank." + u.getInt(player, (byte) 4) + ".BonusMPRO", Integer.parseInt(Message));
                    SkillList.saveConfig();
                    SoundUtil.SP(player, org.bukkit.Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
                    SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte) 2), (short) u.getInt(player, (byte) 4));
                    u.clearAll(player);
                }
        }//Main.JobHashMap1를 비교하는 switch의 끝
    }

}
