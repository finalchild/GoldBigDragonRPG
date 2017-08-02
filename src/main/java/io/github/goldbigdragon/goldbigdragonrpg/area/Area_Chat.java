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

import io.github.goldbigdragon.goldbigdragonrpg.effect.SoundUtil;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import io.github.goldbigdragon.goldbigdragonrpg.user.UserData;
import io.github.goldbigdragon.goldbigdragonrpg.util.ChatUtil;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.Optional;

public class Area_Chat extends ChatUtil {
    public void AreaTypeChatting(PlayerChatEvent event) {
        UserData u = new UserData();
        Player player = event.getPlayer();
        YamlController yamlController = new YamlController(Main_Main.plugin);
        YamlManager areaConfig = yamlController.getNewConfig("Area/AreaList.yml");
        event.setCancelled(true);
        Area_Gui areaGui = new Area_Gui();
        String message = ChatColor.stripColor(event.getMessage());
        String subType = u.getString(player, (byte) 2);
        if (subType.compareTo("ARR") == 0)//AreaRegenBlock
        {
            if (isIntMinMax(message, player, 1, 3600)) {
                areaConfig.set(u.getString(player, (byte) 3) + ".RegenBlock", Integer.parseInt(message));
                areaConfig.saveConfig();
                SoundUtil.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                areaGui.AreaSettingGUI(player, u.getString(player, (byte) 3));
                u.clearAll(player);
            }
        } else if (subType.compareTo("AMSC") == 0)//AreaMonsterSpawnCount
        {
            if (isIntMinMax(message, player, 1, 100)) {
                areaConfig.set(u.getString(player, (byte) 3) + ".MonsterSpawnRule." + u.getString(player, (byte) 1) + ".count", Integer.parseInt(message));
                areaConfig.saveConfig();
                SoundUtil.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                u.setString(player, (byte) 2, "AMSMC");
                player.sendMessage(ChatColor.GREEN + "[영역] : 반경 20블록 이내 엔티티가 몇 마리 미만일 동안 스폰 할까요?");
                player.sendMessage(ChatColor.YELLOW + "(최소 1마리 ~ 최대 300마리)");
            }
        } else if (subType.compareTo("AMSMC") == 0)//AreaMonsterSpawnMaximumCount
        {
            if (isIntMinMax(message, player, 1, 300)) {
                areaConfig.set(u.getString(player, (byte) 3) + ".MonsterSpawnRule." + u.getString(player, (byte) 1) + ".max", Integer.parseInt(message));
                areaConfig.saveConfig();
                SoundUtil.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                u.setString(player, (byte) 2, "AMST");
                player.sendMessage(ChatColor.GREEN + "[영역] : 몇 초마다 스폰되게 할까요?");
                player.sendMessage(ChatColor.YELLOW + "(최소 1초 ~ 최대 7200초(2시간))");
            }
        } else if (subType.compareTo("AMST") == 0)//AreaMonsterSpawnTimer
        {
            if (isIntMinMax(message, player, 1, 7200)) {
                areaConfig.set(u.getString(player, (byte) 3) + ".MonsterSpawnRule." + u.getString(player, (byte) 1) + ".timer", Integer.parseInt(message));
                areaConfig.saveConfig();
                SoundUtil.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                u.setString(player, (byte) 2, "AMSM");
                player.sendMessage(ChatColor.GREEN + "[영역] : 특별히 스폰 하고 싶은 몬스터가 있나요?");
                player.sendMessage(ChatColor.YELLOW + "(O 혹은 X로 대답하세요!)");
            }
        } else if (subType.compareTo("AMSM") == 0)//AreaMonsterSpawnMonster
        {
            Optional<Boolean> optionalAnswer = askOx(message, player);
            optionalAnswer.ifPresent(answer -> {
                if(answer) {
                    player.playSound(player.getLocation(), Sound.ENTITY_HORSE_ARMOR, 1F, 1.7F);
                    areaGui.AreaSpawnSpecialMonsterListGUI(player, (short) 0, u.getString(player, (byte) 3), u.getString(player, (byte) 1));
                } else {
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1F, 1F);
                    areaGui.AreaMonsterSpawnSettingGUI(player, (short) 0, u.getString(player, (byte) 3));
                    String areaName = u.getString(player, (byte) 3);
                    new Area_Main().AreaMonsterSpawnAdd(areaName, u.getString(player, (byte) 1));

                }
                u.clearAll(player);
            });
        } else if (subType.compareTo("Priority") == 0)//영역 우선순위 설정
        {
            if (isIntMinMax(message, player, 0, 100)) {
                areaConfig.set(u.getString(player, (byte) 3) + ".Priority", Integer.parseInt(message));
                areaConfig.saveConfig();
                SoundUtil.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                areaGui.AreaSettingGUI(player, u.getString(player, (byte) 3));
                u.clearAll(player);
            }
        } else if (subType.equals("MinNLR"))//MinNowLevelRestrict
        {
            if (isIntMinMax(message, player, 0, Integer.MAX_VALUE)) {
                areaConfig.set(u.getString(player, (byte) 3) + ".Restrict.MinNowLevel", Integer.parseInt(message));
                areaConfig.saveConfig();
                SoundUtil.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                if (Integer.parseInt(message) == 0) {
                    areaConfig = yamlController.getNewConfig("config.yml");
                    if (areaConfig.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System")) {
                        u.setString(player, (byte) 2, "MinRLR");
                        player.sendMessage(ChatColor.GREEN + "[영역] : " + ChatColor.YELLOW + u.getString(player, (byte) 3) + ChatColor.GREEN + " 영역의 입장에 필요한 최소 누적 레벨을 입력 하세요!" + ChatColor.GRAY + " (0 입력시 제한 없음)");
                    } else {
                        areaConfig.set(u.getString(player, (byte) 3) + ".Restrict.MaxNowLevel", 0);
                        areaConfig.saveConfig();
                        areaGui.AreaSettingGUI(player, u.getString(player, (byte) 3));
                        u.clearAll(player);
                    }
                } else {
                    u.setString(player, (byte) 2, "MaxNLR");
                    player.sendMessage(ChatColor.GREEN + "[영역] : " + ChatColor.YELLOW + u.getString(player, (byte) 3) + ChatColor.GREEN + " 영역의 입장에 필요한 최대 레벨을 입력 하세요!" + ChatColor.GRAY + " (" + message + " 이상)");
                }
            }
        } else if (subType.compareTo("MaxNLR") == 0)//MaxNowLevelRestrict
        {
            if (isIntMinMax(message, player, areaConfig.getInt(u.getString(player, (byte) 3) + ".Restrict.MinNowLevel"), Integer.MAX_VALUE)) {
                areaConfig.set(u.getString(player, (byte) 3) + ".Restrict.MaxNowLevel", Integer.parseInt(message));
                areaConfig.saveConfig();
                areaConfig = yamlController.getNewConfig("config.yml");
                SoundUtil.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                if (areaConfig.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System")) {
                    u.setString(player, (byte) 2, "MinRLR");
                    player.sendMessage(ChatColor.GREEN + "[영역] : " + ChatColor.YELLOW + u.getString(player, (byte) 3) + ChatColor.GREEN + " 영역의 입장에 필요한 최소 누적 레벨을 입력 하세요!" + ChatColor.GRAY + " (0 입력시 제한 없음)");
                } else {
                    areaGui.AreaSettingGUI(player, u.getString(player, (byte) 3));
                    u.clearAll(player);
                }
            }
        } else if (subType.compareTo("MinRLR") == 0)//MinRealLevelRestrict
        {
            if (isIntMinMax(message, player, 0, Integer.MAX_VALUE)) {
                areaConfig.set(u.getString(player, (byte) 3) + ".Restrict.MinRealLevel", Integer.parseInt(message));
                areaConfig.saveConfig();
                SoundUtil.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                if (Integer.parseInt(message) == 0) {
                    areaConfig.set(u.getString(player, (byte) 3) + ".Restrict.MaxRealLevel", 0);
                    areaConfig.saveConfig();
                    areaGui.AreaSettingGUI(player, u.getString(player, (byte) 3));
                    u.clearAll(player);
                } else {
                    u.setString(player, (byte) 2, "MaxRLR");
                    player.sendMessage(ChatColor.GREEN + "[영역] : " + ChatColor.YELLOW + u.getString(player, (byte) 3) + ChatColor.GREEN + " 영역의 입장에 필요한 최대 누적 레벨을 입력 하세요!" + ChatColor.GRAY + " (" + message + " 이상)");
                }
            }
        } else if (subType.compareTo("MaxRLR") == 0)//MaxRealLevelRestrict
        {
            if (isIntMinMax(message, player, areaConfig.getInt(u.getString(player, (byte) 3) + ".Restrict.MinRealLevel"), Integer.MAX_VALUE)) {
                areaConfig.set(u.getString(player, (byte) 3) + ".Restrict.MaxRealLevel", Integer.parseInt(message));
                areaConfig.saveConfig();
                SoundUtil.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                areaGui.AreaSettingGUI(player, u.getString(player, (byte) 3));
                u.clearAll(player);
            }
        }

    }

}
