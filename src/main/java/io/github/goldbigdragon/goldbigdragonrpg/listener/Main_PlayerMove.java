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

package io.github.goldbigdragon.goldbigdragonrpg.listener;

import io.github.goldbigdragon.goldbigdragonrpg.area.Area_Main;
import io.github.goldbigdragon.goldbigdragonrpg.corpse.Corpse_Main;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_ServerOption;
import io.github.goldbigdragon.goldbigdragonrpg.quest.Quest_Gui;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;

public class Main_PlayerMove implements Listener {
    @EventHandler
    public void PlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (new Corpse_Main().DeathCapture(player, false))
            return;

        if (player.getLocation().getWorld().getName().compareTo("Dungeon") != 0) {
            if (Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).getDungeon_Enter() != null) {
                new io.github.goldbigdragon.goldbigdragonrpg.dependency.NoteBlockAPIMain().Stop(player);
                Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setDungeon_Enter(null);
                Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setDungeon_UTC(-1);
            }
            if (new Area_Main().getAreaName(event.getPlayer()) != null) {
                if (Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).getETC_CurrentArea() == null)
                    Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setETC_CurrentArea("null");
                Area_Main A = new Area_Main();
                String Area = A.getAreaName(event.getPlayer())[0];
                if (Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).getETC_CurrentArea().compareTo(Area) != 0) {
                    Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setETC_CurrentArea(Area);
                    Main_ServerOption.PlayerCurrentArea.put(player, Area);
                    A.AreaMonsterSpawnAdd(Area, "-1");
                    new io.github.goldbigdragon.goldbigdragonrpg.dependency.NoteBlockAPIMain().Stop(player);
                    Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setETC_CurrentArea(Area);
                    if (A.getAreaOption(Area, (char) 2))
                        Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setETC_LastVisited(Area);
                    if (Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isBgmOn()) {
                        if (A.getAreaOption(Area, (char) 6)) {
                            YamlController YC = new YamlController(Main_Main.plugin);
                            YamlManager AreaList = YC.getNewConfig("Area/AreaList.yml");
                            new io.github.goldbigdragon.goldbigdragonrpg.dependency.NoteBlockAPIMain().Play(player, AreaList.getInt(Area + ".BGM"));
                        }
                    }
                    if (A.getAreaOption(Area, (char) 4)) {
                        YamlController YC = new YamlController(Main_Main.plugin);
                        YamlManager QuestList = YC.getNewConfig("Quest/QuestList.yml");
                        YamlManager PlayerQuestList = YC.getNewConfig("Quest/PlayerData/" + player.getUniqueId() + ".yml");

                        Object[] b = PlayerQuestList.getConfigurationSection("Started").getKeys(false).toArray();
                        for (short count = 0; count < b.length; count++) {
                            String QuestName = (String) b[count];
                            if (PlayerQuestList.getString("Started." + QuestName + ".Type").compareTo("Visit") == 0) {
                                if (PlayerQuestList.getString("Started." + QuestName + ".AreaName").compareTo(Area) == 0) {
                                    PlayerQuestList.set("Started." + QuestName + ".Type", QuestList.getString(QuestName + ".FlowChart." + (PlayerQuestList.getInt("Started." + QuestName + ".Flow") + 1) + ".Type"));
                                    PlayerQuestList.set("Started." + QuestName + ".Flow", PlayerQuestList.getInt("Started." + QuestName + ".Flow") + 1);
                                    PlayerQuestList.removeKey("Started." + QuestName + ".AreaName");
                                    PlayerQuestList.saveConfig();
                                    Quest_Gui QGUI = new Quest_Gui();
                                    QGUI.QuestRouter(player, QuestName);
                                    //퀘스트 완료 메시지//
                                    break;
                                }
                            }
                        }
                        A.sendAreaTitle(player, Area);
                    }
                    return;
                }
            } else {
                Main_ServerOption.PlayerCurrentArea.put(player, "null");
                Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setETC_CurrentArea("null");
                new io.github.goldbigdragon.goldbigdragonrpg.dependency.NoteBlockAPIMain().Stop(player);
            }
        }
    }

}
