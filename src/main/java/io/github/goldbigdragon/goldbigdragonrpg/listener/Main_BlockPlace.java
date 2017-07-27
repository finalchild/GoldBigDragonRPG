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
import io.github.goldbigdragon.goldbigdragonrpg.effect.Effect_Sound;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;

public class Main_BlockPlace implements Listener {
    @EventHandler
    public void BlockPlaceE(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (event.getItemInHand().hasItemMeta() && !player.isOp()) {
            YamlController YC = new YamlController(Main_Main.plugin);
            YamlManager Config = YC.getNewConfig("config.yml");
            if (!Config.getBoolean("Server.CustomBlockPlace"))
                event.setCancelled(true);
            return;
        }
        if (event.getBlock().getLocation().getWorld().getName().compareTo("Dungeon") == 0) {
            if (event.getBlock().getType() != Material.TORCH)
                event.setCancelled(true);
            return;
        }

        Area_Main A = new Area_Main();
        String[] Area = A.getAreaName(event.getBlock());
        if (Area != null)
            if (!A.getAreaOption(Area[0], (char) 5) && !player.isOp()) {
                event.setCancelled(true);
                new Effect_Sound().SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
                player.sendMessage(ChatColor.RED + "[SYSTEM] : " + ChatColor.YELLOW + Area[1] + ChatColor.RED + " 지역 에서는 블록 설치가 불가능합니다!");
                return;
            }
        if (!player.isOp())
            EXPexceptionBlock(event.getBlock().getTypeId(), event.getBlock().getLocation());
    }

    public void EXPexceptionBlock(int id, Location loc) {
        if ((id >= 14 && id <= 17) || id == 21 || id == 56 || id == 129 || id == 73 || id == 153) {
            YamlController YC = new YamlController(Main_Main.plugin);
            YamlManager EXPexceptionBlockList = YC.getNewConfig("EXPexceptionBlock.yml");
            String Location = ((int) loc.getX() + "_" + (int) loc.getY() + "_" + (int) loc.getZ());
            EXPexceptionBlockList.createSection(loc.getWorld().getName() + "." + id + "." + Location);
            EXPexceptionBlockList.saveConfig();
        }
    }

}
