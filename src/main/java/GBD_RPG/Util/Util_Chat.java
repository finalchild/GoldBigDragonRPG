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

package GBD_RPG.Util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Util_Chat {
    public boolean isIntMinMax(String message, Player player, int Min, int Max) {
        GBD_RPG.Effect.Effect_Sound sound = new GBD_RPG.Effect.Effect_Sound();
        try {
            if (message.split(" ").length <= 1 && Integer.parseInt(message) >= Min && Integer.parseInt(message) <= Max)
                return true;
            else {
                player.sendMessage(ChatColor.RED + "[SYSTEM] : 최소 " + ChatColor.YELLOW + "" + Min + ChatColor.RED + ", 최대 " + ChatColor.YELLOW + "" + Max + ChatColor.RED + " 이하의 숫자를 입력하세요!");
                sound.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
            }
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "[SYSTEM] : 정수 형태의 값(숫자)을 입력하세요. (" + ChatColor.YELLOW + "" + Min + ChatColor.RED + " ~ " + ChatColor.YELLOW + "" + Max + ChatColor.RED + ")");
            sound.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
        }
        return false;
    }

    public byte askOX(String message, Player player) {
        GBD_RPG.Effect.Effect_Sound sound = new GBD_RPG.Effect.Effect_Sound();
        if (message.split(" ").length <= 1) {
            if (message.compareTo("x") == 0 || message.compareTo("X") == 0 || message.compareTo("아니오") == 0)
                return 0;
            else if (message.compareTo("o") == 0 || message.compareTo("O") == 0 || message.compareTo("네") == 0)
                return 1;
            else {
                player.sendMessage(ChatColor.RED + "[SYSTEM] : O 혹은 X를 입력 해 주세요!");
                sound.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
            }

        } else {
            player.sendMessage(ChatColor.RED + "[SYSTEM] : O 혹은 X를 입력 해 주세요!");
            sound.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
        }
        return -1;
    }
}
