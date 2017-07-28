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

import io.github.goldbigdragon.goldbigdragonrpg.util.Util_Chat;

import io.github.goldbigdragon.goldbigdragonrpg.effect.SoundUtil;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import io.github.goldbigdragon.goldbigdragonrpg.util.ETC;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import io.github.goldbigdragon.goldbigdragonrpg.user.UserData_Object;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;

public class Navigation_Chat extends Util_Chat {
    public void NaviTypeChatting(PlayerChatEvent event) {
        UserData_Object u = new UserData_Object();
        Player player = event.getPlayer();

        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager NavigationConfig = YC.getNewConfig("Navigation/NavigationList.yml");

        SoundUtil s = new SoundUtil();
        event.setCancelled(true);
        String message = ChatColor.stripColor(event.getMessage());
        switch (u.getString(player, (byte) 0)) {
            case "NN"://NewNavigation
                SoundUtil.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                long UTC = new ETC().getNowUTC();
                NavigationConfig.set(UTC + ".Name", event.getMessage());
                NavigationConfig.set(UTC + ".world", player.getLocation().getWorld().getName());
                NavigationConfig.set(UTC + ".x", (int) player.getLocation().getX());
                NavigationConfig.set(UTC + ".y", (short) player.getLocation().getY());
                NavigationConfig.set(UTC + ".z", (int) player.getLocation().getZ());
                NavigationConfig.set(UTC + ".time", -1);
                NavigationConfig.set(UTC + ".sensitive", 5);
                NavigationConfig.set(UTC + ".onlyOPuse", true);
                NavigationConfig.set(UTC + ".ShowArrow", 0);
                NavigationConfig.saveConfig();
                u.clearAll(player);
                new Navigation_Gui().NavigationOptionGUI(player, UTC + "");
                return;
            case "CNN"://ChangeNavigationName이름 변경
                SoundUtil.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                NavigationConfig.set(u.getString(player, (byte) 1) + ".Name", event.getMessage());
                NavigationConfig.saveConfig();
                new Navigation_Gui().NavigationOptionGUI(player, u.getString(player, (byte) 1));
                u.clearAll(player);
                return;
            case "CNT"://ChangeNavigationTimer지속 시간
                if (isIntMinMax(message, player, -1, 3600)) {
                    SoundUtil.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                    NavigationConfig.set(u.getString(player, (byte) 1) + ".time", Integer.parseInt(message));
                    NavigationConfig.saveConfig();
                    new Navigation_Gui().NavigationOptionGUI(player, u.getString(player, (byte) 1));
                    u.clearAll(player);
                }
                return;
            case "CNS"://ChangeNavigationSensitive도착 반경
                if (isIntMinMax(message, player, 1, 1000)) {
                    SoundUtil.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                    NavigationConfig.set(u.getString(player, (byte) 1) + ".sensitive", Integer.parseInt(message));
                    NavigationConfig.saveConfig();
                    new Navigation_Gui().NavigationOptionGUI(player, u.getString(player, (byte) 1));
                    u.clearAll(player);
                }
                return;
            case "CNA"://ChangeNavigationArrow네비 타입
                if (isIntMinMax(message, player, 0, 10)) {
                    SoundUtil.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                    NavigationConfig.set(u.getString(player, (byte) 1) + ".ShowArrow", Integer.parseInt(message));
                    NavigationConfig.saveConfig();
                    new Navigation_Gui().NavigationOptionGUI(player, u.getString(player, (byte) 1));
                    u.clearAll(player);
                }
                return;
        }
    }

}
