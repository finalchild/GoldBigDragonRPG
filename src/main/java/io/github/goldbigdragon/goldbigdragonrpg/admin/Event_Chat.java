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

package io.github.goldbigdragon.goldbigdragonrpg.admin;

import io.github.goldbigdragon.goldbigdragonrpg.effect.PacketUtil;
import io.github.goldbigdragon.goldbigdragonrpg.effect.SoundUtil;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_ServerOption;
import io.github.goldbigdragon.goldbigdragonrpg.user.UserData_Object;
import io.github.goldbigdragon.goldbigdragonrpg.util.Util_Chat;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

public class Event_Chat extends Util_Chat {
    public void EventChatting(PlayerChatEvent event) {
        UserData_Object u = new UserData_Object();
        Player player = event.getPlayer();
        SoundUtil s = new SoundUtil();
        PacketUtil PS = new PacketUtil();
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager Config = YC.getNewConfig("config.yml");
        event.setCancelled(true);
        String message = ChatColor.stripColor(event.getMessage());

        switch (u.getString(player, (byte) 1)) {
            case "SKP"://SkillPoint
                if (isIntMinMax(message, player, 1, 10)) {
                    int Value = Integer.parseInt(message);
                    SoundUtil.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
                    if (Config.getInt("Event.Multiple_Level_Up_SkillPoint") == 1) {
                        if (Value != 1) {
                            Config.set("Event.Multiple_Level_Up_SkillPoint", Value);
                            PacketUtil.sendTitleAllPlayers("\'" + ChatColor.WHITE + "스킬 포인트 " + ChatColor.YELLOW + "" + Config.getInt("Event.Multiple_Level_Up_SkillPoint") + ChatColor.WHITE + "배 이벤트를 시작합니다.\'");
                            PacketUtil.sendActionBarAllPlayers(ChatColor.BOLD + "" + ChatColor.YELLOW + "[레벨업시 얻는 스킬 포인트가 증가됩니다.]");
                            Main_ServerOption.Event_SkillPoint = (byte) Value;
                        }
                    } else {
                        if (Value != 1) {
                            Config.set("Event.Multiple_Level_Up_SkillPoint", Value);
                            PacketUtil.sendTitleAllPlayers("\'" + ChatColor.WHITE + "스킬 포인트 " + ChatColor.YELLOW + "" + Config.getInt("Event.Multiple_Level_Up_SkillPoint") + ChatColor.WHITE + "배 이벤트를 시작합니다.\'");
                            PacketUtil.sendActionBarAllPlayers(ChatColor.BOLD + "" + ChatColor.YELLOW + "[레벨업시 얻는 스킬 포인트가 증가됩니다.]");
                            Main_ServerOption.Event_SkillPoint = (byte) Value;
                        } else {
                            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[Server] : 스킬 포인트  " + Config.getInt("Event.Multiple_Level_Up_SkillPoint") + "배 이벤트를 종료합니다.");
                            PacketUtil.sendTitleAllPlayers("\'" + ChatColor.WHITE + "스킬 포인트 " + ChatColor.YELLOW + "" + Config.getInt("Event.Multiple_Level_Up_SkillPoint") + ChatColor.WHITE + "배 이벤트가 종료되었습니다.\'");
                            PacketUtil.sendActionBarAllPlayers(ChatColor.BOLD + "" + ChatColor.YELLOW + "[레벨업시 얻는 스킬 포인트가 원래대로 돌아옵니다.]");
                            Config.set("Event.Multiple_Level_Up_SkillPoint", 1);
                            Main_ServerOption.Event_SkillPoint = 1;
                        }
                    }
                    Config.saveConfig();
                    new Event_Gui().EventGUI_Main(player);
                    u.clearAll(player);
                }
                return;
            case "STP"://StatPoint
                if (isIntMinMax(message, player, 1, 10)) {
                    int Value = Integer.parseInt(message);
                    SoundUtil.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
                    if (Config.getInt("Event.Multiple_Level_Up_StatPoint") == 1) {
                        if (Value != 1) {
                            Config.set("Event.Multiple_Level_Up_StatPoint", Value);
                            PacketUtil.sendTitleAllPlayers("\'" + ChatColor.WHITE + "스텟 포인트 " + ChatColor.YELLOW + "" + Config.getInt("Event.Multiple_Level_Up_StatPoint") + ChatColor.WHITE + "배 이벤트를 시작합니다.\'");
                            PacketUtil.sendActionBarAllPlayers(ChatColor.BOLD + "" + ChatColor.YELLOW + "[레벨업시 얻는 스텟 포인트가 증가됩니다.]");
                            Main_ServerOption.Event_StatPoint = (byte) Value;
                        }
                    } else {
                        if (Value != 1) {
                            Config.set("Event.Multiple_Level_Up_StatPoint", Value);
                            PacketUtil.sendTitleAllPlayers("\'" + ChatColor.WHITE + "스텟 포인트 " + ChatColor.YELLOW + "" + Config.getInt("Event.Multiple_Level_Up_StatPoint") + ChatColor.WHITE + "배 이벤트를 시작합니다.\'");
                            PacketUtil.sendActionBarAllPlayers(ChatColor.BOLD + "" + ChatColor.YELLOW + "[레벨업시 얻는 스텟 포인트가 증가됩니다.]");
                            Main_ServerOption.Event_StatPoint = (byte) Value;
                        } else {
                            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[Server] : 스텟 포인트  " + Config.getInt("Event.Multiple_Level_Up_StatPoint") + "배 이벤트를 종료합니다.");
                            PacketUtil.sendTitleAllPlayers("\'" + ChatColor.WHITE + "스텟 포인트 " + ChatColor.YELLOW + "" + Config.getInt("Event.Multiple_Level_Up_StatPoint") + ChatColor.WHITE + "배 이벤트가 종료되었습니다.\'");
                            PacketUtil.sendActionBarAllPlayers(ChatColor.BOLD + "" + ChatColor.YELLOW + "[레벨업시 얻는 스텟 포인트가 원래대로 돌아옵니다.]");
                            Config.set("Event.Multiple_Level_Up_StatPoint", 1);
                            Main_ServerOption.Event_StatPoint = 1;
                        }
                    }
                    Config.saveConfig();
                    new Event_Gui().EventGUI_Main(player);
                    u.clearAll(player);
                }
                return;

            case "EXP"://EXP
                if (isIntMinMax(message, player, 1, 10)) {
                    int Value = Integer.parseInt(message);
                    SoundUtil.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
                    if (Config.getInt("Event.Multiple_EXP_Get") == 1) {
                        if (Value != 1) {
                            Config.set("Event.Multiple_EXP_Get", Value);
                            PacketUtil.sendTitleAllPlayers("\'" + ChatColor.WHITE + "경험치 " + ChatColor.YELLOW + "" + Config.getInt("Event.Multiple_EXP_Get") + ChatColor.WHITE + "배 이벤트를 시작합니다.\'");
                            PacketUtil.sendActionBarAllPlayers(ChatColor.BOLD + "" + ChatColor.YELLOW + "[획득하는 경험치량이 증가됩니다.]");
                            Main_ServerOption.Event_Exp = (byte) Value;
                        }
                    } else {
                        if (Value != 1) {
                            Config.set("Event.Multiple_EXP_Get", Value);
                            PacketUtil.sendTitleAllPlayers("\'" + ChatColor.WHITE + "경험치 " + ChatColor.YELLOW + "" + Config.getInt("Event.Multiple_EXP_Get") + ChatColor.WHITE + "배 이벤트를 시작합니다.\'");
                            PacketUtil.sendActionBarAllPlayers(ChatColor.BOLD + "" + ChatColor.YELLOW + "[획득하는 경험치량이 증가됩니다.]");
                            Main_ServerOption.Event_Exp = (byte) Value;
                        } else {
                            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[Server] : 경험치  " + Config.getInt("Event.Multiple_EXP_Get") + "배 이벤트를 종료합니다.");
                            PacketUtil.sendTitleAllPlayers("\'" + ChatColor.WHITE + "경험치 " + ChatColor.YELLOW + "" + Config.getInt("Event.Multiple_EXP_Get") + ChatColor.WHITE + "배 이벤트가 종료되었습니다.\'");
                            PacketUtil.sendActionBarAllPlayers(ChatColor.BOLD + "" + ChatColor.YELLOW + "[획득하는 경험치량이 원래대로 돌아옵니다.]");
                            Config.set("Event.Multiple_EXP_Get", 1);
                            Main_ServerOption.Event_Exp = 1;
                        }
                    }
                    Config.saveConfig();
                    new Event_Gui().EventGUI_Main(player);
                    u.clearAll(player);
                }
                return;
            case "DROP"://DropChance
                if (isIntMinMax(message, player, 1, 10)) {
                    int Value = Integer.parseInt(message);
                    SoundUtil.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
                    if (Config.getInt("Event.DropChance") == 1) {
                        if (Value != 1) {
                            Config.set("Event.DropChance", Value);
                            PacketUtil.sendTitleAllPlayers("\'" + ChatColor.WHITE + "드랍률 " + ChatColor.YELLOW + "" + Config.getInt("Event.DropChance") + ChatColor.WHITE + "배 이벤트를 시작합니다.\'");
                            PacketUtil.sendActionBarAllPlayers(ChatColor.BOLD + "" + ChatColor.YELLOW + "[아이템 드랍률이 증가됩니다.]");
                            Main_ServerOption.Event_DropChance = (byte) Value;
                        }
                    } else {
                        if (Value != 1) {
                            Config.set("Event.DropChance", Value);
                            PacketUtil.sendTitleAllPlayers("\'" + ChatColor.WHITE + "드랍률 " + ChatColor.YELLOW + "" + Config.getInt("Event.DropChance") + ChatColor.WHITE + "배 이벤트를 시작합니다.\'");
                            PacketUtil.sendActionBarAllPlayers(ChatColor.BOLD + "" + ChatColor.YELLOW + "[아이템 드랍률이 증가됩니다.]");
                            Main_ServerOption.Event_DropChance = (byte) Value;
                        } else {
                            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[Server] : 드랍률  " + Config.getInt("Event.DropChance") + "배 이벤트를 종료합니다.");
                            PacketUtil.sendTitleAllPlayers("\'" + ChatColor.WHITE + "드랍률 " + ChatColor.YELLOW + "" + Config.getInt("Event.DropChance") + ChatColor.WHITE + "배 이벤트가 종료되었습니다.\'");
                            PacketUtil.sendActionBarAllPlayers(ChatColor.BOLD + "" + ChatColor.YELLOW + "[아이템 드랍률이 원래대로 돌아옵니다.]");
                            Config.set("Event.DropChance", 1);
                            Main_ServerOption.Event_DropChance = 1;
                        }
                    }
                    Config.saveConfig();
                    new Event_Gui().EventGUI_Main(player);
                    u.clearAll(player);
                }
                return;
            case "Proficiency"://Proficiency
                if (isIntMinMax(message, player, 1, 10)) {
                    int Value = Integer.parseInt(message);
                    SoundUtil.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
                    if (Config.getInt("Event.Multiple_Proficiency_Get") == 1) {
                        if (Value != 1) {
                            Config.set("Event.Multiple_Proficiency_Get", Value);
                            PacketUtil.sendTitleAllPlayers("\'" + ChatColor.WHITE + "숙련도 " + ChatColor.YELLOW + "" + Config.getInt("Event.Multiple_Proficiency_Get") + ChatColor.WHITE + "배 이벤트를 시작합니다.\'");
                            PacketUtil.sendActionBarAllPlayers(ChatColor.BOLD + "" + ChatColor.YELLOW + "[장비 숙련도 상승량이 증가됩니다.]");
                            Main_ServerOption.Event_Proficiency = (byte) Value;
                        }
                    } else {
                        if (Value != 1) {
                            Config.set("Event.Multiple_Proficiency_Get", Value);
                            PacketUtil.sendTitleAllPlayers("\'" + ChatColor.WHITE + "숙련도 " + ChatColor.YELLOW + "" + Config.getInt("Event.Multiple_Proficiency_Get") + ChatColor.WHITE + "배 이벤트를 시작합니다.\'");
                            PacketUtil.sendActionBarAllPlayers(ChatColor.BOLD + "" + ChatColor.YELLOW + "[장비 숙련도 상승량이 증가됩니다.]");
                            Main_ServerOption.Event_Proficiency = (byte) Value;
                        } else {
                            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[Server] : 숙련도  " + Config.getInt("Event.Multiple_Proficiency_Get") + "배 이벤트를 종료합니다.");
                            PacketUtil.sendTitleAllPlayers("\'" + ChatColor.WHITE + "숙련도 " + ChatColor.YELLOW + "" + Config.getInt("Event.Multiple_Proficiency_Get") + ChatColor.WHITE + "배 이벤트가 종료되었습니다.\'");
                            PacketUtil.sendActionBarAllPlayers(ChatColor.BOLD + "" + ChatColor.YELLOW + "[장비 숙련도 상승량이 원래대로 돌아옵니다.]");
                            Config.set("Event.Multiple_Proficiency_Get", 1);
                            Main_ServerOption.Event_Proficiency = 1;
                        }
                    }
                    Config.saveConfig();
                    new Event_Gui().EventGUI_Main(player);
                    u.clearAll(player);
                }
                return;
        }
    }

}
