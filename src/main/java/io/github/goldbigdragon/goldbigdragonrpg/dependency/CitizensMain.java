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

package io.github.goldbigdragon.goldbigdragonrpg.dependency;

import java.io.File;

import io.github.goldbigdragon.goldbigdragonrpg.effect.SoundUtil;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_ServerOption;
import io.github.goldbigdragon.goldbigdragonrpg.npc.NPC_Config;
import io.github.goldbigdragon.goldbigdragonrpg.npc.NPC_Gui;
import io.github.goldbigdragon.goldbigdragonrpg.quest.Quest_Gui;
import io.github.goldbigdragon.goldbigdragonrpg.util.Util_Player;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.goldbigdragon.goldbigdragonrpg.user.UserData;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;
import net.citizensnpcs.api.event.NPCCreateEvent;
import net.citizensnpcs.api.event.NPCRemoveEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;

public class CitizensMain implements Listener {
    public CitizensMain(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public CitizensMain() {
    }

    public void NPCquest(NPCRightClickEvent event) {
        YamlManager YM;

        Player player = event.getClicker();
        net.citizensnpcs.api.npc.NPC target = event.getNPC();
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager QuestList = YC.getNewConfig("Quest/QuestList.yml");
        YamlManager PlayerQuestList = YC.getNewConfig("Quest/PlayerData/" + player.getUniqueId() + ".yml");

        if (PlayerQuestList.contains("Started")) {
            Object[] a = PlayerQuestList.getConfigurationSection("Started").getKeys(false).toArray();

            for (short count = 0; count < a.length; count++) {
                String QuestName = a[count].toString();
                short QuestFlow = (short) PlayerQuestList.getInt("Started." + QuestName + ".Flow");
                Quest_Gui QGUI = new Quest_Gui();
                boolean isThatTarget = false;
                if (QuestList.contains(QuestName + ".FlowChart." + QuestFlow + ".Type"))
                    switch (QuestList.getString(QuestName + ".FlowChart." + QuestFlow + ".Type")) {
                        case "Script":
                            if (ChatColor.stripColor(QuestList.getString(QuestName + ".FlowChart." + QuestFlow + ".NPCuuid")).equalsIgnoreCase(ChatColor.stripColor(target.getUniqueId().toString())))
                                isThatTarget = true;
                            if (ChatColor.stripColor(QuestList.getString(QuestName + ".FlowChart." + QuestFlow + ".NPCname")).equalsIgnoreCase(ChatColor.stripColor(target.getName())))
                                isThatTarget = true;
                            if (isThatTarget) {
                                event.setCancelled(true);
                                QGUI.QuestRouter(player, QuestName);
                                return;
                            }
                            break;
                        case "Choice":
                            QGUI.Quest_UserChoice(player, QuestName, (short) PlayerQuestList.getInt("Started." + QuestName + ".Flow"));
                            return;
                        case "PScript":
                            event.setCancelled(true);
                            QGUI.QuestRouter(player, QuestName);
                            return;
                        case "Talk":
                            if (ChatColor.stripColor(QuestList.getString(QuestName + ".FlowChart." + QuestFlow + ".TargetNPCuuid")).equalsIgnoreCase(ChatColor.stripColor(target.getUniqueId().toString())))
                                isThatTarget = true;
                            if (ChatColor.stripColor(QuestList.getString(QuestName + ".FlowChart." + QuestFlow + ".TargetNPCname")).equalsIgnoreCase(ChatColor.stripColor(target.getName())))
                                isThatTarget = true;
                            if (isThatTarget) {
                                event.setCancelled(true);
                                PlayerQuestList.set("Started." + QuestName + ".Flow", PlayerQuestList.getInt("Started." + QuestName + ".Flow") + 1);
                                PlayerQuestList.saveConfig();
                                QGUI.QuestRouter(player, QuestName);
                                return;
                            }
                            break;
                        case "Give":
                            if (ChatColor.stripColor(QuestList.getString(QuestName + ".FlowChart." + QuestFlow + ".TargetNPCuuid")).equalsIgnoreCase(ChatColor.stripColor(target.getUniqueId().toString())))
                                isThatTarget = true;
                            if (ChatColor.stripColor(QuestList.getString(QuestName + ".FlowChart." + QuestFlow + ".TargetNPCname")).equalsIgnoreCase(ChatColor.stripColor(target.getName())))
                                isThatTarget = true;
                            if (isThatTarget) {
                                if (!QuestList.contains(QuestName + ".FlowChart." + QuestFlow + ".Item")) {
                                    if (PlayerQuestList.getInt("Started." + QuestName + ".Flow") == 0) {
                                        QGUI.QuestRouter(player, QuestName);
                                    } else {
                                        PlayerQuestList.set("Started." + QuestName + ".Flow", PlayerQuestList.getInt("Started." + QuestName + ".Flow") + 1);
                                        PlayerQuestList.saveConfig();
                                        QGUI.QuestRouter(player, QuestName);
                                    }
                                    return;
                                }
                                Object[] p = QuestList.getConfigurationSection(QuestName + ".FlowChart." + QuestFlow + ".Item").getKeys(false).toArray();
                                ItemStack item[] = new ItemStack[p.length];

                                for (byte counter = 0; counter < p.length; counter++)
                                    item[counter] = QuestList.getItemStack(QuestName + ".FlowChart." + QuestFlow + ".Item." + counter);

                                int getfinished = 0;
                                for (byte eight = 0; eight < 8; eight++) {
                                    for (byte counter = 0; counter < player.getInventory().getSize(); counter++) {
                                        if (player.getInventory().getItem(counter) != null)
                                            if (player.getInventory().getItem(counter).isSimilar(item[getfinished])) {
                                                if (player.getInventory().getItem(counter).getAmount() >= item[getfinished].getAmount()) {
                                                    getfinished = getfinished + 1;
                                                    break;
                                                }
                                            }
                                    }
                                    if (getfinished == item.length)
                                        break;
                                }
                                if (getfinished == item.length) {
                                    getfinished = 0;
                                    ItemStack Pitem = null;

                                    for (byte eight = 0; eight < 8; eight++) {
                                        for (byte counter = 0; counter < player.getInventory().getSize(); counter++) {
                                            if (player.getInventory().getItem(counter) != null)
                                                if (player.getInventory().getItem(counter).isSimilar(item[getfinished])) {
                                                    if (player.getInventory().getItem(counter).getAmount() >= item[getfinished].getAmount()) {
                                                        Pitem = player.getInventory().getItem(counter);
                                                        Pitem.setAmount(Pitem.getAmount() - item[getfinished].getAmount());
                                                        if (Pitem.getAmount() - item[getfinished].getAmount() == 0)
                                                            player.getInventory().remove(counter);
                                                        else
                                                            player.getInventory().setItem(counter, Pitem);

                                                        getfinished = getfinished + 1;
                                                        if (getfinished == item.length) {
                                                            event.setCancelled(true);
                                                            PlayerQuestList.set("Started." + QuestName + ".Flow", PlayerQuestList.getInt("Started." + QuestName + ".Flow") + 1);
                                                            PlayerQuestList.saveConfig();
                                                            QGUI.QuestRouter(player, QuestName);
                                                            return;
                                                        }
                                                    }
                                                }
                                        }
                                    }
                                }
                            }
                            break;

                        case "Present":
                            if (ChatColor.stripColor(QuestList.getString(QuestName + ".FlowChart." + QuestFlow + ".TargetNPCuuid")).equalsIgnoreCase(ChatColor.stripColor(target.getUniqueId().toString())))
                                isThatTarget = true;
                            if (ChatColor.stripColor(QuestList.getString(QuestName + ".FlowChart." + QuestFlow + ".TargetNPCname")).equalsIgnoreCase(ChatColor.stripColor(target.getName())))
                                isThatTarget = true;
                            if (isThatTarget) {
                                event.setCancelled(true);
                                if (QuestList.contains(QuestName + ".FlowChart." + QuestFlow + ".Item")) {
                                    Object[] p = QuestList.getConfigurationSection(QuestName + ".FlowChart." + QuestFlow + ".Item").getKeys(false).toArray();
                                    byte emptySlot = 0;
                                    ItemStack item[] = new ItemStack[p.length];

                                    for (byte counter = 0; counter < p.length; counter++)
                                        item[counter] = QuestList.getItemStack(QuestName + ".FlowChart." + QuestFlow + ".Item." + counter);

                                    for (byte counter = 0; counter < player.getInventory().getSize(); counter++) {
                                        if (player.getInventory().getItem(counter) == null)
                                            emptySlot++;
                                    }

                                    if (emptySlot >= item.length) {
                                        for (byte counter = 0; counter < p.length; counter++)
                                            player.getInventory().addItem(item[counter]);

                                        PlayerQuestList.set("Started." + QuestName + ".Flow", PlayerQuestList.getInt("Started." + QuestName + ".Flow") + 1);
                                        PlayerQuestList.saveConfig();

                                        Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(QuestList.getLong(QuestName + ".FlowChart." + QuestFlow + ".Money"), 0, false);

                                        if (!YC.isExit("NPC/PlayerData/" + player.getUniqueId() + ".yml")) {
                                            YM = YC.getNewConfig("NPC/PlayerData/" + player.getUniqueId() + ".yml");
                                            YM.set(QuestList.getString(QuestName + ".FlowChart." + QuestFlow + ".TargetNPCuuid") + ".love", QuestList.getInt(QuestName + ".FlowChart." + QuestFlow + ".Love"));
                                            YM.set(QuestList.getString(QuestName + ".FlowChart." + QuestFlow + ".TargetNPCuuid") + ".Career", 0);
                                            YM.saveConfig();
                                        } else {
                                            YM = YC.getNewConfig("NPC/PlayerData/" + player.getUniqueId() + ".yml");
                                            int ownlove = YM.getInt(QuestList.getString(QuestName + ".FlowChart." + QuestFlow + ".TargetNPCuuid") + ".love");
                                            int owncareer = YM.getInt(QuestList.getString(QuestName + ".FlowChart." + QuestFlow + ".TargetNPCuuid") + ".Career");
                                            YM.set(QuestList.getString(QuestName + ".FlowChart." + QuestFlow + ".TargetNPCuuid") + ".love", ownlove + QuestList.getInt(QuestName + ".FlowChart." + QuestFlow + ".Love"));
                                            YM.set(QuestList.getString(QuestName + ".FlowChart." + QuestFlow + ".TargetNPCuuid") + ".Career", owncareer + QuestList.getInt(QuestName + ".FlowChart." + QuestFlow + ".Career"));
                                            YM.saveConfig();
                                        }
                                        if (QuestList.getInt(QuestName + ".FlowChart." + QuestFlow + ".EXP") != 0)
                                            new Util_Player().addMoneyAndEXP(player, 0, QuestList.getLong(QuestName + ".FlowChart." + QuestFlow + ".EXP"), null, false, false);

                                        event.setCancelled(true);
                                        QGUI.QuestRouter(player, QuestName);
                                        return;
                                    } else {
                                        SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
                                        player.sendMessage(ChatColor.YELLOW + "[퀘스트] : 현재 플레이어의 인벤토리 공간이 충분하지 않아 보상을 받을 수 없습니다!");
                                        return;
                                    }
                                }
                            }
                            break;
                    }//switch 끝
            }
        }
    }

    @EventHandler
    public void NPCRightClick(NPCRightClickEvent event) {
        Player player = event.getClicker();
        UserData u = new UserData();
        u.setNPCuuid(player, event.getNPC().getUniqueId().toString());
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager DNPC = YC.getNewConfig("NPC/DistrictNPC.yml");
        if (player.isOp()) {
            if (new UserData().getInt(player, (byte) 4) == 114) {
                DNPC.removeKey(event.getNPC().getUniqueId().toString());
                DNPC.saveConfig();
                player.sendMessage(ChatColor.GREEN + "[NPC] : 해당 NPC의 GUI창이 활성화 되었습니다!");
                SoundUtil.playSound(player, Sound.ENTITY_VILLAGER_YES, 1.0F, 1.0F);
                new UserData().setInt(player, (byte) 4, -1);
            }
        }

        if (!DNPC.contains(event.getNPC().getUniqueId().toString())) {
            NPC_Gui NPGUI = new NPC_Gui();
            NPGUI.MainGUI(event.getClicker(), event.getNPC().getName(), event.getClicker().isOp());
        }

        NPCquest(event);
    }

    @EventHandler
    public void NPCCreating(NPCCreateEvent event) {
        NPC_Config NPCC = new NPC_Config();
        NPCC.NPCNPCconfig(event.getNPC().getUniqueId().toString());
    }

    @EventHandler
    public void NPCRemove(NPCRemoveEvent event) {
        File file = new File("plugins/GoldBigDragonRPG/NPC/NPCData/" + event.getNPC().getUniqueId().toString() + ".yml");
        file.delete();
    }

}
