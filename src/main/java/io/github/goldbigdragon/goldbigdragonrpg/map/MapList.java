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

package io.github.goldbigdragon.goldbigdragonrpg.map;

import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_ServerOption;
import io.github.goldbigdragon.goldbigdragonrpg.user.UserData_Object;
import org.bukkit.entity.Player;
import org.bukkit.map.MapView;

import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;

import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;

public class MapList extends MapRenderer {
    @Override
    public void render(MapView MV, MapCanvas MC, Player player) {
        if (Main_ServerOption.Mapping == true) {
            Main_ServerOption.Mapping = false;
            String URL = "null";
            int Xcenter = 0;
            int Ycenter = 0;

            YamlController YC = new YamlController(Main_Main.plugin);
            YamlManager MapConfig = YC.getNewConfig("MapImageURL.yml");
            String Name = new UserData_Object().getString(player, (byte) 1);
            URL = MapConfig.getString(Name + ".URL");
            Xcenter = MapConfig.getInt(Name + ".Xcenter");
            Ycenter = MapConfig.getInt(Name + ".Ycenter");
            new UserData_Object().clearAll(player);
            if (URL == "null")
                return;
            else {
                try {
                    MC.drawImage(Xcenter, Ycenter, ImageIO.read(new URL(URL)));
                    return;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
