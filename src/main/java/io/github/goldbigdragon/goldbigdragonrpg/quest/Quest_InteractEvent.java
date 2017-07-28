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

package io.github.goldbigdragon.goldbigdragonrpg.quest;

import io.github.goldbigdragon.goldbigdragonrpg.effect.SoundUtil;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import io.github.goldbigdragon.goldbigdragonrpg.user.UserData_Object;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;

public class Quest_InteractEvent {
    public void EntityInteract(PlayerInteractEntityEvent event, String Type) {
        Entity target = event.getRightClicked();
        Player player = event.getPlayer();
        event.setCancelled(true);
        if (Type.compareTo("Quest") == 0) {
            Quest_Gui QGUI = new Quest_Gui();
            YamlController YC = new YamlController(Main_Main.plugin);
            YamlManager QuestConfig = YC.getNewConfig("Quest/QuestList.yml");
            UserData_Object u = new UserData_Object();
            if ((u.getString(player, (byte) 1).compareTo("Give") == 0 || u.getString(player, (byte) 1).compareTo("Present") == 0)
                    || u.getString(player, (byte) 1).compareTo("Hunt") == 0
                    && u.getString(player, (byte) 3) != null) {
                u.setType(player, "Quest");
                if (u.getString(player, (byte) 1).compareTo("Hunt") == 0) {
                    if (target.getType() == EntityType.PLAYER)
                        u.setString(player, (byte) 3, target.getName());
                    else {
                        if (!target.isCustomNameVisible())
                            u.setString(player, (byte) 3, target.getName());
                        else
                            u.setString(player, (byte) 3, target.getCustomName());
                    }
                    player.sendMessage(ChatColor.GREEN + "[SYSTEM] : " + ChatColor.YELLOW + QGUI.SkullType(u.getString(player, (byte) 3)) + ChatColor.GREEN + " 몬스터를 얼마나 사냥할지 설정하세요! (" + ChatColor.YELLOW + "0" + ChatColor.GREEN + " ~ " + ChatColor.YELLOW + "" + Integer.MAX_VALUE + ChatColor.GREEN + "마리)");
                    player.closeInventory();
                    return;
                }

                if (target.getType() == EntityType.PLAYER)
                    u.setString(player, (byte) 2, target.getName());
                else {
                    if (!target.isCustomNameVisible())
                        u.setString(player, (byte) 2, target.getName());
                    else
                        u.setString(player, (byte) 2, target.getCustomName());
                }
                SoundUtil.playSound(event.getPlayer(), org.bukkit.Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.2F);
                if (u.getString(player, (byte) 1).compareTo("Give") == 0) {
                    player.sendMessage(ChatColor.GREEN + "[SYSTEM] : NPC가 유저에게 받을 물건을 설정하세요!");
                    player.closeInventory();
                    u.setBoolean(player, (byte) 1, true);
                    QGUI.GetterItemSetingGUI(player, u.getString(player, (byte) 3));
                } else if (u.getString(player, (byte) 1).compareTo("Present") == 0) {
                    player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 보상으로 줄 물건을 설정하세요!");
                    player.closeInventory();
                    u.setBoolean(player, (byte) 1, true);
                    QGUI.PresentItemSettingGUI(player, u.getString(player, (byte) 3));
                }
                u.setString(player, (byte) 1, target.getUniqueId().toString());
                return;
            }
            switch (u.getString(player, (byte) 1)) {
                case "Script": {
                    short b = (short) QuestConfig.getConfigurationSection(u.getString(player, (byte) 2) + ".FlowChart").getKeys(false).size();
                    if (u.getString(player, (byte) 3) != null) {
                        QuestConfig.set(u.getString(player, (byte) 2) + ".FlowChart." + b + ".Type", "Script");
                        QuestConfig.set(u.getString(player, (byte) 2) + ".FlowChart." + b + ".Script", u.getString(player, (byte) 3));

                        if (target.getType() == EntityType.PLAYER) {
                            QuestConfig.set(u.getString(player, (byte) 2) + ".FlowChart." + b + ".NPCname", target.getName());
                            QuestConfig.set(u.getString(player, (byte) 2) + ".FlowChart." + b + ".NPCuuid", ((Player) target).getUniqueId().toString());
                        } else {
                            if (target.isCustomNameVisible())
                                QuestConfig.set(u.getString(player, (byte) 2) + ".FlowChart." + b + ".NPCname", target.getCustomName());
                            else
                                QuestConfig.set(u.getString(player, (byte) 2) + ".FlowChart." + b + ".NPCname", target.getName());
                            QuestConfig.set(u.getString(player, (byte) 2) + ".FlowChart." + b + ".NPCuuid", target.getUniqueId().toString());
                        }
                        SoundUtil.playSound(event.getPlayer(), org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F, 1.2F);
                        player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 성공적으로 등록되었습니다!");
                        QuestConfig.saveConfig();
                        QGUI.FixQuestGUI(player, (short) 0, u.getString(player, (byte) 2));
                        u.clearAll(player);
                    }
                }
                break;
                case "Talk": {
                    short c = (short) QuestConfig.getConfigurationSection(u.getString(player, (byte) 2) + ".FlowChart").getKeys(false).size();
                    QuestConfig.set(u.getString(player, (byte) 2) + ".FlowChart." + c + ".Type", "Talk");
                    if (target.getType() == EntityType.PLAYER) {
                        QuestConfig.set(u.getString(player, (byte) 2) + ".FlowChart." + c + ".TargetNPCuuid", ((Player) target).getUniqueId().toString());
                        QuestConfig.set(u.getString(player, (byte) 2) + ".FlowChart." + c + ".TargetNPCname", target.getName());
                    } else {
                        if (target.isCustomNameVisible())
                            QuestConfig.set(u.getString(player, (byte) 2) + ".FlowChart." + c + ".TargetNPCname", target.getCustomName());
                        else
                            QuestConfig.set(u.getString(player, (byte) 2) + ".FlowChart." + c + ".TargetNPCname", target.getName());
                        QuestConfig.set(u.getString(player, (byte) 2) + ".FlowChart." + c + ".TargetNPCuuid", target.getUniqueId().toString());
                    }
                    SoundUtil.playSound(event.getPlayer(), org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F, 1.2F);
                    player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 성공적으로 등록되었습니다!");
                    QuestConfig.saveConfig();
                    QGUI.FixQuestGUI(player, (short) 0, u.getString(player, (byte) 2));
                    u.clearAll(player);
                }
                break;
            }
            return;
        }
    }

    public void BlockInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        UserData_Object u = new UserData_Object();

        event.setCancelled(true);
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (u.getString(player, (byte) 1).compareTo("TelePort") == 0) {
                String QuestName = u.getString(player, (byte) 3);
                Quest_Gui QGUI = new Quest_Gui();

                YamlController YC = new YamlController(Main_Main.plugin);
                YamlManager QuestConfig = YC.getNewConfig("Quest/QuestList.yml");
                short size = (short) QuestConfig.getConfigurationSection(QuestName + ".FlowChart").getKeys(false).toArray().length;

                QuestConfig.set(QuestName + ".FlowChart." + size + ".Type", "TelePort");
                QuestConfig.set(QuestName + ".FlowChart." + size + ".World", block.getLocation().getWorld().getName());
                QuestConfig.set(QuestName + ".FlowChart." + size + ".X", block.getLocation().getX());
                QuestConfig.set(QuestName + ".FlowChart." + size + ".Y", block.getLocation().getY());
                QuestConfig.set(QuestName + ".FlowChart." + size + ".Z", block.getLocation().getZ());
                QuestConfig.set(QuestName + ".FlowChart." + size + ".NPCname", " ");
                QuestConfig.saveConfig();
                QGUI.FixQuestGUI(player, (short) 0, QuestName);
                u.clearAll(player);
                SoundUtil.playSound(event.getPlayer(), org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F, 1.2F);
                player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 성공적으로 등록되었습니다!");
                return;
            }
        }
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (u.getString(player, (byte) 1) != null &&
                    u.getString(player, (byte) 3) != null) {
                if (u.getString(player, (byte) 1).compareTo("Harvest") == 0) {
                    u.setInt(player, (byte) 1, block.getTypeId());
                    u.setInt(player, (byte) 2, (int) block.getData());
                    player.sendMessage(ChatColor.GREEN + "[SYSTEM] : " + ChatColor.GREEN + "블록의 데이터까지 완벽히 같아야 하나요? (" + ChatColor.RED + "X" + ChatColor.GREEN + " 혹은 " + ChatColor.DARK_AQUA + "O" + ChatColor.GREEN + ")");
                    player.closeInventory();
                    return;
                } else if (u.getString(player, (byte) 1).compareTo("BlockPlace") == 0) {
                    String QuestName = u.getString(player, (byte) 2);
                    YamlController YC = new YamlController(Main_Main.plugin);
                    YamlManager QuestConfig = YC.getNewConfig("Quest/QuestList.yml");
                    short size = (short) QuestConfig.getConfigurationSection(QuestName + ".FlowChart").getKeys(false).toArray().length;

                    QuestConfig.set(QuestName + ".FlowChart." + size + ".Type", "BlockPlace");
                    QuestConfig.set(QuestName + ".FlowChart." + size + ".World", block.getLocation().getWorld().getName());
                    QuestConfig.set(QuestName + ".FlowChart." + size + ".X", block.getLocation().getX());
                    QuestConfig.set(QuestName + ".FlowChart." + size + ".Y", block.getLocation().getY());
                    QuestConfig.set(QuestName + ".FlowChart." + size + ".Z", block.getLocation().getZ());
                    QuestConfig.set(QuestName + ".FlowChart." + size + ".ID", 1);
                    QuestConfig.set(QuestName + ".FlowChart." + size + ".DATA", 0);
                    QuestConfig.saveConfig();
                    u.setString(player, (byte) 3, null);
                    u.setString(player, (byte) 1, "BPID");
                    u.setInt(player, (byte) 1, size);
                    SoundUtil.playSound(event.getPlayer(), org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F, 1.2F);
                    player.sendMessage(ChatColor.GREEN + "[퀘스트] : 설치 될 블록 ID를 입력 해 주세요!");
                    return;
                }
            }
        }
    }
}
