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

package io.github.goldbigdragon.goldbigdragonrpg.party;

import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_ServerOption;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;

public class Party_DataManager {
    public void saveParty() {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager PartyConfig = YC.getNewConfig("Party/PartyList.yml");

        Object[] PartyList = Main_ServerOption.Party.keySet().toArray();
        for (short count = 0; count < PartyList.length; count++) {
            Player[] PartyMember = Main_ServerOption.Party.get(PartyList[count]).getMember();
            PartyConfig.set("Party." + PartyList[count].toString() + ".Title", Main_ServerOption.Party.get(PartyList[count]).getTitle());
            PartyConfig.set("Party." + PartyList[count].toString() + ".Leader", Main_ServerOption.Party.get(PartyList[count]).getLeader());
            PartyConfig.set("Party." + PartyList[count].toString() + ".PartyLock", Main_ServerOption.Party.get(PartyList[count]).getLock());
            PartyConfig.set("Party." + PartyList[count].toString() + ".Password", Main_ServerOption.Party.get(PartyList[count]).getPassword());
            PartyConfig.set("Party." + PartyList[count].toString() + ".Capacity", Main_ServerOption.Party.get(PartyList[count]).getCapacity());
            for (byte counter = 0; counter < PartyMember.length; counter++) {
                Player m = Main_ServerOption.Party.get(PartyList[count]).getMember()[counter];
                if (m != null)
                    PartyConfig.set("Party." + PartyList[count].toString() + ".Member." + counter, PartyMember[counter].getName());
                else
                    PartyConfig.set("Party." + PartyList[count].toString() + ".Member." + counter, "null");
            }
        }
        PartyConfig.saveConfig();
        Main_ServerOption.Party.clear();
        Main_ServerOption.PartyJoiner.clear();
        return;
    }

    public void loadParty() {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager PartyConfig = YC.getNewConfig("Party/PartyList.yml");

        if (PartyConfig.contains("Party")) {
            Object[] PartyList = PartyConfig.getConfigurationSection("Party").getKeys(false).toArray();
            for (short count = 0; count < PartyList.length; count++) {
                if (PartyConfig.contains("Party." + PartyList[count].toString() + ".Member")) {
                    long PCT = Long.parseLong(PartyList[count].toString());
                    String PT = PartyConfig.getString("Party." + PartyList[count].toString() + ".Title");
                    String PL = PartyConfig.getString("Party." + PartyList[count].toString() + ".Leader");
                    boolean PLock = PartyConfig.getBoolean("Party." + PartyList[count].toString() + ".PartyLock");
                    String PP = PartyConfig.getString("Party." + PartyList[count].toString() + ".Password");
                    byte PC = (byte) PartyConfig.getInt("Party." + PartyList[count].toString() + ".Capacity");
                    String[] PM = new String[PC];
                    for (byte counter = 0; counter < PartyConfig.getConfigurationSection("Party." + PartyList[count].toString() + ".Member").getKeys(false).size(); counter++)
                        PM[counter] = PartyConfig.getString("Party." + PCT + ".Member." + counter);
                    Main_ServerOption.Party.put(PCT, new Party_Object(PCT, PL, PT, PLock, PP, PC, PM));

                    for (byte counter = 0; counter < PM.length; counter++)
                        if (PM[counter] != null)
                            if (Bukkit.getServer().getPlayer(PM[counter]) == null)
                                Main_ServerOption.Party.get(PCT).QuitPartyOfflinePlayer(PM[counter]);
                            else {
                                if (!Bukkit.getServer().getPlayer(PM[counter]).isOnline())
                                    Main_ServerOption.Party.get(PCT).QuitParty((Player) Bukkit.getServer().getOfflinePlayer(PM[counter]));
                                else
                                    Main_ServerOption.PartyJoiner.put((Player) Bukkit.getServer().getPlayer(PM[counter]), PCT);
                            }

                    if (!Bukkit.getServer().getOfflinePlayer(PL).isOnline())
                        Main_ServerOption.Party.get(PCT).QuitPartyOfflinePlayer(PL);

                    if (Main_ServerOption.Party.get(PCT).getPartyMembers() == 0)
                        Main_ServerOption.Party.remove(PCT);
                }
            }
        }

        PartyConfig.removeKey("Party");
        PartyConfig.removeKey("PartyJoiner");
        PartyConfig.saveConfig();
        return;
    }
}
