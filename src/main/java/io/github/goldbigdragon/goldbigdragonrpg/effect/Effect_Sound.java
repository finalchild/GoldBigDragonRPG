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

package io.github.goldbigdragon.goldbigdragonrpg.effect;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Effect_Sound {
    //해당 플레이어에게 소리를 내게하는 메소드//
    public void SP(Player player, org.bukkit.Sound sound, float volume, float pitch) {
        if (player.isOnline())
            player.playSound(player.getLocation(), sound, volume, pitch);
    }

    //해당 플레이어에게 해당 위치에서 소리를 내게하는 메소드//
    public void SPL(Player player, Location loc, org.bukkit.Sound sound, float volume, float pitch) {
        if (player.isOnline())
            player.playSound(loc, sound, volume, pitch);
    }

    //해당 위치에 사운드를 내게하는 메소드//
    public void SL(Location loc, org.bukkit.Sound sound, float volume, float pitch) {
        World world = loc.getWorld();
        world.playSound(loc, sound, volume, pitch);
    }

}
