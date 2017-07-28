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

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EffectUtil {
    public static void givePotionEffect(Player player, PotionEffectType type, int duration, int amplifier) {
        PotionEffect pe = new org.bukkit.potion.PotionEffect(type, duration * 20, amplifier, false, false);
        player.addPotionEffect(pe);
    }

    public static PotionEffect getPotionEffect(PotionEffectType type, int duration, int amplifier) {
        return new org.bukkit.potion.PotionEffect(type, duration * 20, amplifier, false, false);
    }
}
