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

package io.github.goldbigdragon.goldbigdragonrpg.warp;

import io.github.goldbigdragon.goldbigdragonrpg.user.UserData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

public class Warp_Chat {
    public void TeleportTypeChatting(PlayerChatEvent event) {
        UserData u = new UserData();
        Player player = event.getPlayer();
        event.setCancelled(true);
        String message = ChatColor.stripColor(event.getMessage());
        switch (u.getString(player, (byte) 1)) {
            case "NW"://NewWarp
                message = message.replace(".", "");
                new Warp_Main().CreateNewTeleportSpot(player, message);
                u.clearAll(player);
                return;
        }
    }
}
