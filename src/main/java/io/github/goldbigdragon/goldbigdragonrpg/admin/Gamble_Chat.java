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

package io.github.goldbigdragon.goldbigdragonrpg.admin;

import io.github.goldbigdragon.goldbigdragonrpg.effect.SoundUtil;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import io.github.goldbigdragon.goldbigdragonrpg.user.UserData_Object;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;

public class Gamble_Chat {
    public void GambleChatting(PlayerChatEvent event) {
        UserData_Object u = new UserData_Object();
        Player player = event.getPlayer();

        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager GambleYML = YC.getNewConfig("Item/GamblePresent.yml");

        SoundUtil s = new SoundUtil();
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
                new Gamble_Gui().GamblePresentGUI(player, (short) 0, (byte) 0, (short) -1, null);
            }
        }
    }

}
