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

import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;

public class Quest_Config {
    public void CreateNewQuestConfig() {
        YamlManager QuestConfig;
        YamlController YC = new YamlController(Main_Main.plugin);
        QuestConfig = YC.getNewConfig("Quest/QuestList.yml");

        if (!YC.isExit("Quest/QuestList.yml")) {
            QuestConfig.set("Do_not_Touch_This", true);
            QuestConfig.saveConfig();
        }
    }

    public void CreateNewPlayerConfig(Player player) {
        YamlManager QuestConfig;
        YamlController YC = new YamlController(Main_Main.plugin);
        QuestConfig = YC.getNewConfig("Quest/PlayerData/" + player.getUniqueId() + ".yml");

        QuestConfig.set("PlayerName", player.getName());
        QuestConfig.set("PlayerUUID", player.getUniqueId().toString());
        QuestConfig.createSection("Started");
        QuestConfig.createSection("Ended");
        QuestConfig.saveConfig();

        String QuestName = YC.getNewConfig("ETC/NewBie.yml").getString("FirstQuest");
        if (QuestName.compareTo("null") != 0) {
            YamlManager QuestList = YC.getNewConfig("Quest/QuestList.yml");
            if (QuestList.contains(QuestName)) {
                if (QuestList.getConfigurationSection(QuestName + ".FlowChart").getKeys(false).toArray().length != 0) {
                    String QuestType = QuestList.getString(QuestName + ".FlowChart.0.Type");
                    QuestConfig.set("Started." + QuestName + ".Flow", 0);
                    QuestConfig.set("Started." + QuestName + ".Type", QuestType);
                    if (QuestType.compareTo("Visit") == 0)
                        QuestConfig.set("Started." + QuestName + ".AreaName", QuestList.getString(QuestName + ".FlowChart.0.AreaName"));
                    else if (QuestType.compareTo("Hunt") == 0) {
                        Object[] MobList = QuestList.getConfigurationSection(QuestName + ".FlowChart.0.Monster").getKeys(false).toArray();
                        for (short counter = 0; counter < MobList.length; counter++)
                            QuestConfig.set("Started." + QuestName + ".Hunt." + counter, 0);
                    } else if (QuestType.compareTo("Harvest") == 0) {
                        Object[] BlockList = QuestList.getConfigurationSection(QuestName + ".FlowChart.0.Block").getKeys(false).toArray();
                        for (short counter = 0; counter < BlockList.length; counter++)
                            QuestConfig.set("Started." + QuestName + ".Block." + counter, 0);
                    }
                    QuestConfig.saveConfig();
                    player.sendMessage(ChatColor.YELLOW + "[퀘스트] : 새로운 퀘스트가 도착했습니다! " + ChatColor.GOLD + "" + ChatColor.BOLD + "/퀘스트");
                    if (QuestType.compareTo("Nevigation") == 0 || QuestType.compareTo("Whisper") == 0 ||
                            QuestType.compareTo("BroadCast") == 0 || QuestType.compareTo("BlockPlace") == 0 ||
                            QuestType.compareTo("VarChange") == 0 || QuestType.compareTo("TelePort") == 0)
                        new Quest_Gui().QuestRouter(player, QuestName);
                }
            }
        }
        QuestConfig.saveConfig();
    }

}
