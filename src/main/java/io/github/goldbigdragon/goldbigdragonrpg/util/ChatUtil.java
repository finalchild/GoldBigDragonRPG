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

package io.github.goldbigdragon.goldbigdragonrpg.util;

import io.github.goldbigdragon.goldbigdragonrpg.effect.SoundUtil;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Optional;

public class ChatUtil {
    public static boolean isIntMinMax(String message, Player player, int Min, int Max) {
        try {
            if (message.split(" ").length <= 1 && Integer.parseInt(message) >= Min && Integer.parseInt(message) <= Max)
                return true;
            else {
                player.sendMessage(ChatColor.RED + "[SYSTEM] : 최소 " + ChatColor.YELLOW + "" + Min + ChatColor.RED + ", 최대 " + ChatColor.YELLOW + "" + Max + ChatColor.RED + " 이하의 숫자를 입력하세요!");
                SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
            }
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "[SYSTEM] : 정수 형태의 값(숫자)을 입력하세요. (" + ChatColor.YELLOW + "" + Min + ChatColor.RED + " ~ " + ChatColor.YELLOW + "" + Max + ChatColor.RED + ")");
            SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
        }
        return false;
    }

    public static Optional<Boolean> askOx(String message, Player player) {
        if (message.split(" ").length <= 1) {
            switch (message) {
                case "x":
                case "X":
                case "아니오":
                    return Optional.of(false);
                case "o":
                case "O":
                case "네":
                    return Optional.of(true);
                default:

            }
        }
        player.sendMessage(ChatColor.RED + "[SYSTEM] : O 혹은 X를 입력 해 주세요!");
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2F, 1.7F);
        return Optional.empty();
    }
}
