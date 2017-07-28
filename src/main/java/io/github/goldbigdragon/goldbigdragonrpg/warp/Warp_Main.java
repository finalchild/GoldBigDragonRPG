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

import io.github.goldbigdragon.goldbigdragonrpg.effect.EffectUtil;
import io.github.goldbigdragon.goldbigdragonrpg.effect.SoundUtil;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;

public class Warp_Main {
    public void CreateNewTeleportSpot(Player player, String TeleportName) {

        if (!player.isOp()) {
            SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
            player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
        } else {
            YamlController YC = new YamlController(Main_Main.plugin);
            YamlManager TeleportList = YC.getNewConfig("Teleport/TeleportList.yml");

            if (TeleportList.contains(TeleportName)) {
                SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
                player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 워프 지점은 이미 등록되어 있습니다!");
                return;
            }

            TeleportList.set(TeleportName + ".World", player.getLocation().getWorld().getName());
            TeleportList.set(TeleportName + ".X", player.getLocation().getX());
            TeleportList.set(TeleportName + ".Y", player.getLocation().getY());
            TeleportList.set(TeleportName + ".Z", player.getLocation().getZ());
            TeleportList.set(TeleportName + ".Pitch", player.getLocation().getPitch());
            TeleportList.set(TeleportName + ".Yaw", player.getLocation().getYaw());
            TeleportList.set(TeleportName + ".OnlyOpUse", true);
            TeleportList.saveConfig();

            SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_CHICKEN_EGG, 2.0F, 1.7F);
            player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 현재 위치로 워프 지점이 등록되었습니다!");

        }
    }

    public void setTeleportPermission(Player player, String TeleportSpotName) {
        if (player.isOp()) {
            YamlController YC = new YamlController(Main_Main.plugin);
            YamlManager TeleportList = YC.getNewConfig("Teleport/TeleportList.yml");
            if (TeleportList.contains(TeleportSpotName)) {
                if (TeleportList.getBoolean(TeleportSpotName + ".OnlyOpUse")) {
                    TeleportList.set(TeleportSpotName + ".OnlyOpUse", false);
                    player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 해당 지역은 일반 유저도 워프할 수 있게 되었습니다!");
                } else {
                    TeleportList.set(TeleportSpotName + ".OnlyOpUse", true);
                    player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 지역은 일반 유저가 워프할 수 없게 되었습니다!");

                }
                TeleportList.saveConfig();
                SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_CHICKEN_EGG, 2.0F, 1.7F);
            } else {
                SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
                player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 이름으로 등록된 워프 지점이 없습니다!");
            }
        } else {
            SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
            player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
        }
    }


    public void RemoveTeleportList(Player player, String TeleportName) {
        if (!player.isOp()) {
            SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
            player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
            return;
        } else {
            YamlController YC = new YamlController(Main_Main.plugin);
            YamlManager TeleportList = YC.getNewConfig("Teleport/TeleportList.yml");

            if (TeleportList.contains(TeleportName)) {
                TeleportList.removeKey(TeleportName + ".World");
                TeleportList.removeKey(TeleportName + ".X");
                TeleportList.removeKey(TeleportName + ".Y");
                TeleportList.removeKey(TeleportName + ".Z");
                TeleportList.removeKey(TeleportName + ".Pitch");
                TeleportList.removeKey(TeleportName + ".Yaw");
                TeleportList.removeKey(TeleportName + ".OnlyOpUse");
                TeleportList.removeKey(TeleportName + "");
                TeleportList.saveConfig();

                SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.7F, 1.0F);
                player.sendMessage(ChatColor.GREEN + "[SYSTEM] : " + ChatColor.YELLOW + TeleportName + ChatColor.GREEN + " 워프 지점을 성공적으로 삭제하였습니다!");
            } else {
                SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_ITEM_BREAK, 0.7F, 1.0F);
                player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 이름으로 등록된 워프 지점이 없습니다!");
            }
        }
    }

    public void TeleportUser(Player player, String TeleportSpotName) {
                EffectUtil p = new EffectUtil();

        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager TeleportList = YC.getNewConfig("Teleport/TeleportList.yml");

        if (TeleportList.contains(TeleportSpotName)) {
            if (TeleportList.getBoolean(TeleportSpotName + ".OnlyOpUse") && !player.isOp()) {
                SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
                player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 구역은 제한된 지역입니다!");
                return;
            } else {
                SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_ENDERMEN_TELEPORT, 0.8F, 1.0F);
                Location loc = new Location(Bukkit.getWorld(TeleportList.getString(TeleportSpotName + ".World")), TeleportList.getInt(TeleportSpotName + ".X") + 0.5, TeleportList.getInt(TeleportSpotName + ".Y") + 0.5, TeleportList.getInt(TeleportSpotName + ".Z") + 0.5, TeleportList.getInt(TeleportSpotName + ".Yaw"), TeleportList.getInt(TeleportSpotName + ".Pitch"));
                player.teleport(loc);
                SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_ENDERMEN_TELEPORT, 0.8F, 1.0F);
                EffectUtil.givePotionEffect(player, PotionEffectType.BLINDNESS, 1, 15);
                return;
            }
        }
        for (short counter = 0; counter < Bukkit.getServer().getWorlds().size(); counter++) {
            if (Bukkit.getServer().getWorlds().get(counter).getName().equalsIgnoreCase(TeleportSpotName)) {
                if (player.isOp()) {
                    SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_ENDERMEN_TELEPORT, 0.8F, 1.0F);
                    player.teleport(Bukkit.getServer().getWorld(TeleportSpotName).getSpawnLocation());
                    EffectUtil.givePotionEffect(player, PotionEffectType.BLINDNESS, 1, 15);
                    SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_ENDERMEN_TELEPORT, 0.8F, 1.0F);
                } else {
                    SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
                    player.sendMessage(ChatColor.RED + "[SYSTEM] : 월드간 이동은 관리자만 허용됩니다!");
                }
                return;
            }
        }
        SoundUtil.playSound(player, org.bukkit.Sound.ENTITY_ITEM_BREAK, 0.7F, 1.0F);
        player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 이름으로 등록된 워프 지점이 없습니다!");
    }
}
