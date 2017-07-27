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
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import io.github.goldbigdragon.goldbigdragonrpg.util.Util_Number;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftItem;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.inventory.ItemStack;

import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;

public class Main_Fishing implements Listener {
    @EventHandler
    public void PlayerFishing(PlayerFishEvent event) {
        if (event.getState() == State.CAUGHT_FISH) {
            Area_Main A = new Area_Main();
            if (A.getAreaName(event.getPlayer()) != null) {
                String Area = A.getAreaName(event.getPlayer())[0];
                if (event.getCaught().getType() == EntityType.DROPPED_ITEM) {
                    CraftItem fish = (CraftItem) event.getCaught();

                    Util_Number etc = new Util_Number();
                    YamlController YC = new YamlController(Main_Main.plugin);
                    YamlManager AreaConfig = YC.getNewConfig("Area/AreaList.yml");
                    byte randomnum = (byte) etc.RandomNum(1, 100);
                    byte size = 0;
                    ItemStack item = fish.getItemStack();
                    if (randomnum <= 54) {
                        size = (byte) AreaConfig.getConfigurationSection(Area + ".Fishing.54").getKeys(false).size();
                        if (size != 0) {
                            randomnum = (byte) (etc.RandomNum(1, size) - 1);
                            item = AreaConfig.getItemStack(Area + ".Fishing.54." + randomnum);
                        }
                    } else if (randomnum <= 84) {
                        size = (byte) AreaConfig.getConfigurationSection(Area + ".Fishing.30").getKeys(false).size();
                        if (size != 0) {
                            randomnum = (byte) (etc.RandomNum(1, size) - 1);
                            item = AreaConfig.getItemStack(Area + ".Fishing.30." + randomnum);
                        }
                    } else if (randomnum <= 94) {
                        size = (byte) AreaConfig.getConfigurationSection(Area + ".Fishing.10").getKeys(false).size();
                        if (size != 0) {
                            randomnum = (byte) (etc.RandomNum(1, size) - 1);
                            item = AreaConfig.getItemStack(Area + ".Fishing.10." + randomnum);
                        }
                    } else if (randomnum <= 99) {
                        size = (byte) AreaConfig.getConfigurationSection(Area + ".Fishing.5").getKeys(false).size();
                        if (size != 0) {
                            randomnum = (byte) (etc.RandomNum(1, size) - 1);
                            item = AreaConfig.getItemStack(Area + ".Fishing.5." + randomnum);
                        }
                    } else {
                        size = (byte) AreaConfig.getConfigurationSection(Area + ".Fishing.1").getKeys(false).size();
                        if (size != 0) {
                            randomnum = (byte) (etc.RandomNum(1, size) - 1);
                            item = AreaConfig.getItemStack(Area + ".Fishing.1." + randomnum);
                        }
                    }
                    fish.setItemStack(item);
                }
            }
        }
        return;
    }
}
