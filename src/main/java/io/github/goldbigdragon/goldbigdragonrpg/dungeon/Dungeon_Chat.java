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

import io.github.goldbigdragon.goldbigdragonrpg.effect.Effect_Sound;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_ServerOption;
import io.github.goldbigdragon.goldbigdragonrpg.servertick.ServerTick_Main;
import io.github.goldbigdragon.goldbigdragonrpg.user.UserData_Object;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;
import net.md_5.bungee.api.ChatColor;

public class Dungeon_Chat {
    public void PlayerChatrouter(PlayerChatEvent event) {
        Player player = event.getPlayer();
        UserData_Object u = new UserData_Object();
        if (u.getType(player).compareTo("DungeonMain") == 0)
            DungeonMainChatting(event);
        else if (u.getType(player).compareTo("EnterCard") == 0)
            EnterCardChatting(event);
        else if (u.getType(player).compareTo("Altar") == 0)
            AltarChatting(event);
    }


    private void DungeonMainChatting(PlayerChatEvent event) {
        Effect_Sound s = new Effect_Sound();
        Player player = event.getPlayer();
        UserData_Object u = new UserData_Object();
        String Message = ChatColor.stripColor(event.getMessage());
        YamlController YC = new YamlController(Main_Main.plugin);
        if (u.getString(player, (byte) 0).compareTo("ND") == 0) {
            YamlManager DungeonList = YC.getNewConfig("Dungeon/DungeonList.yml");
            if (Message.length() >= 11) {
                s.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
                player.sendMessage(ChatColor.RED + "[던전] : 이름이 너무 깁니다! (10자 이내)");
                return;
            }
            if (DungeonList.contains(Message)) {
                s.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
                player.sendMessage(ChatColor.RED + "[던전] : 해당 이름의 던전은 이미 존재합니다!");
                return;
            } else {
                DungeonList.set(Message, ServerTick_Main.nowUTC);
                DungeonList.saveConfig();
                DungeonList = YC.getNewConfig("Dungeon/Dungeon/" + Message + "/Option.yml");
                DungeonList.set("Type.ID", 1);
                DungeonList.set("Type.DATA", 0);
                DungeonList.set("Type.Name", Main_ServerOption.DungeonTheme.get(0));
                DungeonList.set("Size", 5);
                DungeonList.set("Maze_Level", 1);
                DungeonList.set("District.Level", 0);
                DungeonList.set("District.RealLevel", 0);
                DungeonList.set("Reward.Money", 1000);
                DungeonList.set("Reward.EXP", 10000);
                DungeonList.saveConfig();
                DungeonList = YC.getNewConfig("Dungeon/Dungeon/" + Message + "/Monster.yml");
                for (int count = 0; count < 8; count++) {
                    DungeonList.createSection("Boss." + count);
                    DungeonList.createSection("SubBoss." + count);
                    DungeonList.createSection("High." + count);
                    DungeonList.createSection("Middle." + count);
                    DungeonList.createSection("Normal." + count);
                }
                DungeonList.saveConfig();
                DungeonList = YC.getNewConfig("Dungeon/Dungeon/" + Message + "/Reward.yml");
                for (int count = 0; count < 8; count++) {
                    DungeonList.createSection("100." + count);
                    DungeonList.createSection("90." + count);
                    DungeonList.createSection("50." + count);
                    DungeonList.createSection("10." + count);
                    DungeonList.createSection("1." + count);
                    DungeonList.createSection("0." + count);
                }
                DungeonList.saveConfig();
                s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                player.sendMessage(ChatColor.GREEN + "[던전] : 던전 추가 완료!");
            }
            u.clearAll(player);
            new Dungeon_GUI().DungeonListMainGUI(player, 0, 52);
        } else if (u.getString(player, (byte) 0).compareTo("DS") == 0) {
            if (isIntMinMax(event.getMessage(), player, 5, 50)) {
                YamlManager DungeonList = YC.getNewConfig("Dungeon/Dungeon/" + u.getString(player, (byte) 1) + "/Option.yml");
                DungeonList.set("Size", Integer.parseInt(event.getMessage()));
                DungeonList.saveConfig();
                s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                new Dungeon_GUI().DungeonSetUpGUI(player, u.getString(player, (byte) 1));
                u.clearAll(player);
            }
        } else if (u.getString(player, (byte) 0).compareTo("DML") == 0) {
            if (isIntMinMax(event.getMessage(), player, 0, 10)) {
                YamlManager DungeonList = YC.getNewConfig("Dungeon/Dungeon/" + u.getString(player, (byte) 1) + "/Option.yml");
                DungeonList.set("Maze_Level", Integer.parseInt(event.getMessage()));
                DungeonList.saveConfig();
                s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                new Dungeon_GUI().DungeonSetUpGUI(player, u.getString(player, (byte) 1));
                u.clearAll(player);
            }
        } else if (u.getString(player, (byte) 0).compareTo("DDL") == 0) {
            if (isIntMinMax(event.getMessage(), player, 0, 2000000)) {
                YamlManager DungeonList = YC.getNewConfig("Dungeon/Dungeon/" + u.getString(player, (byte) 1) + "/Option.yml");
                DungeonList.set("District.Level", Integer.parseInt(event.getMessage()));
                DungeonList.saveConfig();
                u.setString(player, (byte) 0, "DDRL");//DungeonDistrictRealLevel
                s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                player.sendMessage(ChatColor.GREEN + "[던전] : 던전 입장 가능 누적 레벨을 입력 해 주세요!");
            }
        } else if (u.getString(player, (byte) 0).compareTo("DDRL") == 0) {
            if (isIntMinMax(event.getMessage(), player, 0, 2000000)) {
                YamlManager DungeonList = YC.getNewConfig("Dungeon/Dungeon/" + u.getString(player, (byte) 1) + "/Option.yml");
                DungeonList.set("District.RealLevel", Integer.parseInt(event.getMessage()));
                DungeonList.saveConfig();
                s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                new Dungeon_GUI().DungeonSetUpGUI(player, u.getString(player, (byte) 1));
                u.clearAll(player);
            }
        } else if (u.getString(player, (byte) 0).compareTo("DRM") == 0) {
            if (isIntMinMax(event.getMessage(), player, 0, 20000000)) {
                YamlManager DungeonList = YC.getNewConfig("Dungeon/Dungeon/" + u.getString(player, (byte) 1) + "/Option.yml");
                DungeonList.set("Reward.Money", Integer.parseInt(event.getMessage()));
                DungeonList.saveConfig();
                u.setString(player, (byte) 0, "DRE");//DungeonRewardEXP
                s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                player.sendMessage(ChatColor.GREEN + "[던전] : 던전 클리어 보상 경험치를 입력 해 주세요!");
            }
        } else if (u.getString(player, (byte) 0).compareTo("DRE") == 0) {
            if (isIntMinMax(event.getMessage(), player, 0, 100000000)) {
                YamlManager DungeonList = YC.getNewConfig("Dungeon/Dungeon/" + u.getString(player, (byte) 1) + "/Option.yml");
                DungeonList.set("Reward.EXP", Integer.parseInt(event.getMessage()));
                DungeonList.saveConfig();
                s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                new Dungeon_GUI().DungeonSetUpGUI(player, u.getString(player, (byte) 1));
                u.clearAll(player);
            }
        }
    }

    private void EnterCardChatting(PlayerChatEvent event) {
        Effect_Sound s = new Effect_Sound();
        Player player = event.getPlayer();
        UserData_Object u = new UserData_Object();
        String Message = ChatColor.stripColor(event.getMessage());
        YamlController YC = new YamlController(Main_Main.plugin);
        if (u.getString(player, (byte) 0).compareTo("NEC") == 0) {
            YamlManager EnterCardList = YC.getNewConfig("Dungeon/EnterCardList.yml");
            if (Message.length() >= 16) {
                s.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
                player.sendMessage(ChatColor.RED + "[던전] : 이름이 너무 깁니다! (15자 이내)");
                return;
            }
            if (EnterCardList.contains(Message)) {
                s.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
                player.sendMessage(ChatColor.RED + "[던전] : 해당 이름의 통행증은 이미 존재합니다!");
                return;
            } else {
                EnterCardList.set(Message + ".ID", 358);
                EnterCardList.set(Message + ".DATA", 0);
                EnterCardList.createSection(Message + ".Dungeon");
                EnterCardList.set(Message + ".Capacity", 8);
                EnterCardList.set(Message + ".Hour", -1);
                EnterCardList.set(Message + ".Min", 0);
                EnterCardList.set(Message + ".Sec", 0);
                EnterCardList.saveConfig();
                s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                player.sendMessage(ChatColor.GREEN + "[던전] : 통행증 추가 완료!");
            }
            u.clearAll(player);
            new Dungeon_GUI().DungeonListMainGUI(player, 0, 358);
        } else if (u.getString(player, (byte) 0).compareTo("ECID") == 0) {
            if (isIntMinMax(event.getMessage(), player, 1, 2267)) {
                YamlManager EnterCardConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
                EnterCardConfig.set(u.getString(player, (byte) 1) + ".ID", Integer.parseInt(event.getMessage()));
                EnterCardConfig.saveConfig();
                s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                u.setString(player, (byte) 0, "ECDATA");
                player.sendMessage(ChatColor.GREEN + "[던전] : 통행증 아이템 타입 DATA를 입력 해 주세요.");
            }
        } else if (u.getString(player, (byte) 0).compareTo("ECDATA") == 0) {
            if (isIntMinMax(event.getMessage(), player, 0, 20)) {
                YamlManager EnterCardConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
                EnterCardConfig.set(u.getString(player, (byte) 1) + ".DATA", Integer.parseInt(event.getMessage()));
                EnterCardConfig.saveConfig();
                s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                new Dungeon_GUI().EnterCardSetUpGUI(player, u.getString(player, (byte) 1));
                u.clearAll(player);
            }
        } else if (u.getString(player, (byte) 0).compareTo("ECC") == 0) {
            if (isIntMinMax(event.getMessage(), player, 1, 32)) {
                YamlManager EnterCardConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
                EnterCardConfig.set(u.getString(player, (byte) 1) + ".Capacity", Integer.parseInt(event.getMessage()));
                EnterCardConfig.saveConfig();
                s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                new Dungeon_GUI().EnterCardSetUpGUI(player, u.getString(player, (byte) 1));
                u.clearAll(player);
            }
        } else if (u.getString(player, (byte) 0).compareTo("ECUH") == 0) {
            if (isIntMinMax(event.getMessage(), player, -1, 24)) {
                YamlManager EnterCardConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
                if (Integer.parseInt(event.getMessage()) == -1) {
                    EnterCardConfig.set(u.getString(player, (byte) 1) + ".Hour", -1);
                    EnterCardConfig.saveConfig();
                    s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                    new Dungeon_GUI().EnterCardSetUpGUI(player, u.getString(player, (byte) 1));
                    u.clearAll(player);
                } else {
                    EnterCardConfig.set(u.getString(player, (byte) 1) + ".Hour", Integer.parseInt(event.getMessage()));
                    EnterCardConfig.saveConfig();
                    u.setString(player, (byte) 0, "ECUM");//EnterCardUseableMinute
                    s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                    player.sendMessage(ChatColor.GREEN + "[통행증] : 유효 분을 입력 해 주세요. (최대 59분)");
                }
            }
        } else if (u.getString(player, (byte) 0).compareTo("ECUM") == 0) {
            if (isIntMinMax(event.getMessage(), player, 0, 59)) {
                YamlManager EnterCardConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
                EnterCardConfig.set(u.getString(player, (byte) 1) + ".Min", Integer.parseInt(event.getMessage()));
                EnterCardConfig.saveConfig();
                u.setString(player, (byte) 0, "ECUS");//EnterCardUseableSecond
                s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                player.sendMessage(ChatColor.GREEN + "[통행증] : 유효 초를 입력 해 주세요. (최대 59초)");
            }
        } else if (u.getString(player, (byte) 0).compareTo("ECUS") == 0) {
            if (isIntMinMax(event.getMessage(), player, 0, 59)) {
                YamlManager EnterCardConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
                EnterCardConfig.set(u.getString(player, (byte) 1) + ".Sec", Integer.parseInt(event.getMessage()));
                EnterCardConfig.saveConfig();
                s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                new Dungeon_GUI().EnterCardSetUpGUI(player, u.getString(player, (byte) 1));
                u.clearAll(player);
            }
        }
    }

    private void AltarChatting(PlayerChatEvent event) {
        Effect_Sound s = new Effect_Sound();
        Player player = event.getPlayer();
        UserData_Object u = new UserData_Object();
        String Message = ChatColor.stripColor(event.getMessage());
        YamlController YC = new YamlController(Main_Main.plugin);
        if (u.getString(player, (byte) 0).compareTo("NA_Q") == 0) {
            if (askOX(Message, player) == 1) {
                s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.2F);
                u.clearAll(player);
                new Dungeon_GUI().AltarShapeListGUI(player);
            } else if (Message.compareTo("아니오") == 0 || Message.compareTo("x") == 0
                    || Message.compareTo("X") == 0) {
                s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                player.sendMessage(ChatColor.GREEN + "[던전] : 제단 설치가 취소되었습니다.");
                u.clearAll(player);
                new Dungeon_GUI().DungeonListMainGUI(player, 0, 120);
            }
        } else if (u.getString(player, (byte) 0).compareTo("EAN") == 0) {
            YamlManager AltarConfig = YC.getNewConfig("Dungeon/AltarList.yml");
            String AltarName = u.getString(player, (byte) 1).substring(2, u.getString(player, (byte) 1).length());
            AltarConfig.set(AltarName + ".Name", event.getMessage());
            AltarConfig.saveConfig();
            s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
            new Dungeon_GUI().AltarSettingGUI(player, AltarName);
            u.clearAll(player);
        }
    }


    private byte askOX(String message, Player player) {
        Effect_Sound sound = new Effect_Sound();
        if (message.split(" ").length <= 1) {
            if (message.compareTo("x") == 0 || message.compareTo("X") == 0 || message.compareTo("아니오") == 0)
                return 0;
            else if (message.compareTo("o") == 0 || message.compareTo("O") == 0 || message.compareTo("네") == 0)
                return 1;
            else {
                player.sendMessage(ChatColor.RED + "[SYSTEM] : [네/O] 혹은 [아니오/X]를 입력 해 주세요!");
                sound.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
            }

        } else {
            player.sendMessage(ChatColor.RED + "[SYSTEM] : [네/O] 혹은 [아니오/X]를 입력 해 주세요!");
            sound.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
        }
        return -1;
    }

    private boolean isIntMinMax(String message, Player player, int Min, int Max) {
        Effect_Sound sound = new Effect_Sound();
        try {
            if (message.split(" ").length <= 1 && Integer.parseInt(message) >= Min && Integer.parseInt(message) <= Max)
                return true;
            else {
                player.sendMessage(ChatColor.RED + "[SYSTEM] : 최소 " + ChatColor.YELLOW + "" + Min + ChatColor.RED + ", 최대 " + ChatColor.YELLOW + "" + Max + ChatColor.RED + " 이하의 숫자를 입력하세요!");
                sound.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
            }
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "[SYSTEM] : 정수 형태의 값(숫자)을 입력하세요. (" + ChatColor.YELLOW + "" + Min + ChatColor.RED + " ~ " + ChatColor.YELLOW + "" + Max + ChatColor.RED + ")");
            sound.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
        }
        return false;
    }
}
