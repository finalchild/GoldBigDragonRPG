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
import io.github.goldbigdragon.goldbigdragonrpg.user.UserData;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;

public class WorldCreate_Chat {
    public void WorldCreaterTypeChatting(PlayerChatEvent event) {
        UserData u = new UserData();
        event.setCancelled(true);
        Player player = event.getPlayer();
        String Message = ChatColor.stripColor(event.getMessage());
        SoundUtil.playSound(player, Sound.BLOCK_ANVIL_USE, 1.0F, 0.8F);
        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[월드 생성] : 월드 생성 중...");
        WorldType TYPE = WorldType.FLAT;

        switch (u.getString(player, (byte) 3)) {
            case "NORMAL":
                TYPE = WorldType.NORMAL;
                break;
            case "FLAT":
                TYPE = WorldType.FLAT;
                break;
            case "LARGE_BIOMES":
                TYPE = WorldType.LARGE_BIOMES;
                break;
        }
        Message = Message.replace(" ", "_");
        WorldCreator world = new WorldCreator(Message);
        world.type(TYPE);
        world.generateStructures();
        Bukkit.createWorld(world);
        u.clearAll(player);
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager WorldConfig = YC.getNewConfig("WorldList.yml");
        WorldConfig.createSection(Message);
        WorldConfig.saveConfig();
        Object[] worldname = WorldConfig.getKeys().toArray();
        for (short count = 0; count < WorldConfig.getKeys().size(); count++)
            if (Bukkit.getWorld(worldname[count].toString()) == null)
                WorldCreator.name(worldname[count].toString()).createWorld();
        SoundUtil.playSound(player, Sound.ENTITY_WOLF_AMBIENT, 1.0F, 0.8F);
        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[월드 생성] : 월드 생성 성공!");
    }

}
