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

package GBD_RPG.Admin;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import GBD_RPG.User.UserData_Object;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class Gamble_Chat {
    public void GambleChatting(PlayerChatEvent event) {
        UserData_Object u = new UserData_Object();
        Player player = event.getPlayer();

        YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
        YamlManager GambleYML = YC.getNewConfig("Item/GamblePresent.yml");

        GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
        event.setCancelled(true);
        String message = ChatColor.stripColor(event.getMessage().replace(".", ""));
        switch (u.getString(player, (byte) 0)) {
            case "NP"://New Package
            {
                if (GambleYML.contains(message)) {
                    s.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
                    player.sendMessage(ChatColor.RED + "[도박] : 해당 이름의 상품은 이미 존재합니다!");
                    return;
                }
                s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                GambleYML.set(message + ".Grade", ChatColor.WHITE + "[일반]");
                GambleYML.createSection(message + ".Present");
                GambleYML.saveConfig();
                u.clearAll(player);
                new GBD_RPG.Admin.Gamble_GUI().GamblePresentGUI(player, (short) 0, (byte) 0, (short) -1, null);
            }
            return;
        }
    }

}
