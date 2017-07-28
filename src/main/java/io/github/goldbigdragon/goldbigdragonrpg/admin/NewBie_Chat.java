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

import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import io.github.goldbigdragon.goldbigdragonrpg.util.Util_Chat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import io.github.goldbigdragon.goldbigdragonrpg.user.UserData_Object;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;

public class NewBie_Chat extends Util_Chat {
    public void NewBieTypeChatting(PlayerChatEvent event) {
        UserData_Object u = new UserData_Object();
        Player player = event.getPlayer();
        YamlController YC = new YamlController(Main_Main.plugin);
        event.setCancelled(true);
        String Message = ChatColor.stripColor(event.getMessage());
        switch (u.getString(player, (byte) 1)) {
            case "NSM"://NewbieSupportMoney
                if (isIntMinMax(Message, player, 0, Integer.MAX_VALUE)) {
                    YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");
                    NewBieYM.set("SupportMoney", Integer.parseInt(Message));
                    NewBieYM.saveConfig();
                    NewBie_Gui NGUI = new NewBie_Gui();
                    NGUI.NewBieSupportItemGUI(player);
                    u.clearAll(player);
                }
                return;
        }
    }
}
