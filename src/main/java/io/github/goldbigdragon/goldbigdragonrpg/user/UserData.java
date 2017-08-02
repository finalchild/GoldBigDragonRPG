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

package io.github.goldbigdragon.goldbigdragonrpg.user;

import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;

public class UserData {
    public static void UserDataInit(Player player) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager userDataConfig = YC.getNewConfig("UserData/" + player.getUniqueId() + ".yml");
        userDataConfig.set("Name", player.getName());
        userDataConfig.removeKey("Data");
        userDataConfig.saveConfig();
        userDataConfig.createSection("Data.Type");
        userDataConfig.createSection("Data.Temp");
        userDataConfig.createSection("Data.NPCuuid");
        for (byte count = 0; count < 9; count++)
            userDataConfig.createSection("Data.String." + count);
        for (byte count = 0; count < 6; count++)
            userDataConfig.set("Data.Int." + count, 0);
        for (byte count = 0; count < 2; count++)
            userDataConfig.set("Data.Boolean." + count, false);
        userDataConfig.saveConfig();
    }

    public static void setType(Player player, String TypeName) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager userDataConfig = YC.getNewConfig("UserData/" + player.getUniqueId() + ".yml");
        userDataConfig.set("Data.Type", TypeName);
        userDataConfig.saveConfig();
    }

    public static String getType(Player player) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager userDataConfig = YC.getNewConfig("UserData/" + player.getUniqueId() + ".yml");
        String Type = userDataConfig.getString("Data.Type");
        if (Type != null && Type.compareTo("MemorySection[path='Data.Type', root='YamlConfiguration']") != 0)
            return Type;
        else
            return null;
    }

    public static String getString(Player player, byte StringNumber) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager userDataConfig = YC.getNewConfig("UserData/" + player.getUniqueId() + ".yml");
        String string = userDataConfig.getString("Data.String." + StringNumber);
        if (string != null)
            return string;
        else
            return null;
    }

    public static int getInt(Player player, byte IntNumber) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager UserData = YC.getNewConfig("UserData/" + player.getUniqueId() + ".yml");
        return UserData.getInt("Data.Int." + IntNumber);
    }

    public static Boolean getBoolean(Player player, byte BooleanNumber) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager userDataConfig = YC.getNewConfig("UserData/" + player.getUniqueId() + ".yml");
        return userDataConfig.getBoolean("Data.Boolean." + BooleanNumber);
    }

    public static String getNpcUuid(Player player) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager userDataConfig = YC.getNewConfig("UserData/" + player.getUniqueId() + ".yml");
        return userDataConfig.getString("Data.NPCuuid");
    }

    public static ItemStack getItemStack(Player player) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager userDataConfig = YC.getNewConfig("UserData/" + player.getUniqueId() + ".yml");
        return userDataConfig.getItemStack("Data.Item");
    }

    public void setNPCuuid(Player player, String Value) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager userDataConfig = YC.getNewConfig("UserData/" + player.getUniqueId() + ".yml");
        userDataConfig.set("Data.NPCuuid", Value);
        userDataConfig.saveConfig();
    }

    public static String getTemp(Player player) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager userDataConfig = YC.getNewConfig("UserData/" + player.getUniqueId() + ".yml");
        String Temp = userDataConfig.getString("Data.Temp");
        if (Temp != null && !Temp.isEmpty() && Temp.compareTo("MemorySection[path='Data.Temp', root='YamlConfiguration']") != 0)
            return Temp;
        else
            return null;
    }

    public static void setTemp(Player player, String Value) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager userDataConfig = YC.getNewConfig("UserData/" + player.getUniqueId() + ".yml");
        userDataConfig.set("Data.Temp", Value);
        userDataConfig.saveConfig();
    }

    public static void initTemp(Player player) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager userDataConfig = YC.getNewConfig("UserData/" + player.getUniqueId() + ".yml");
        userDataConfig.set("Data.Temp", null);
        userDataConfig.saveConfig();
    }

    public static void setString(Player player, byte StringNumber, String Value) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager userDataConfig = YC.getNewConfig("UserData/" + player.getUniqueId() + ".yml");
        userDataConfig.set("Data.String." + StringNumber, Value);
        userDataConfig.saveConfig();
    }

    public static void setInt(Player player, byte IntNumber, int Value) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager userDataConfig = YC.getNewConfig("UserData/" + player.getUniqueId() + ".yml");
        userDataConfig.set("Data.Int." + IntNumber, Value);
        userDataConfig.saveConfig();
    }

    public static void setItemStack(Player player, ItemStack item) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager userDataConfig = YC.getNewConfig("UserData/" + player.getUniqueId() + ".yml");
        userDataConfig.set("Data.Item", item);
        userDataConfig.saveConfig();
    }

    public static void setBoolean(Player player, byte BooleanNumber, boolean Value) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager userDataConfig = YC.getNewConfig("UserData/" + player.getUniqueId() + ".yml");
        userDataConfig.set("Data.Boolean." + BooleanNumber, Value);
        userDataConfig.saveConfig();
    }

    public static void clearType(Player player) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager userDataConfig = YC.getNewConfig("UserData/" + player.getUniqueId() + ".yml");
        userDataConfig.set("Data.Type", null);
        userDataConfig.saveConfig();
    }

    public static void clearAll(Player player) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager userDataConfig = YC.getNewConfig("UserData/" + player.getUniqueId() + ".yml");
        String npcUuid = getNpcUuid(player);
        userDataConfig.removeKey("Data");
        userDataConfig.saveConfig();
        userDataConfig.createSection("Data.Type");
        userDataConfig.set("Data.Temp", null);
        userDataConfig.set("Data.Item", null);
        userDataConfig.set("Data.NPCuuid", npcUuid);
        for (byte count = 0; count < 9; count++)
            userDataConfig.createSection("Data.String." + count);
        for (byte count = 0; count < 6; count++)
            userDataConfig.set("Data.Int." + count, 0);
        for (byte count = 0; count < 2; count++)
            userDataConfig.set("Data.Boolean." + count, false);
        userDataConfig.saveConfig();
    }
}
