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

package io.github.goldbigdragon.goldbigdragonrpg.corpse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_ServerOption;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.EulerAngle;

public class Corpse_Main {
    public static HashMap<String, ArrayList<ArmorStand>> Corpses = new HashMap<String, ArrayList<ArmorStand>>();

    public boolean DeathCapture(Player player, boolean isJoin) {
        if (player.getGameMode() == GameMode.SPECTATOR) {
            if (Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isDeath()) {
                Location l = Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getLastDeathPoint();
                if (l.getBlockY() < 0)
                    l.setY(0);
                if (l.getBlockX() != player.getLocation().getBlockX() || l.getBlockY() != player.getLocation().getBlockY() || l.getBlockZ() != player.getLocation().getBlockZ())
                    player.teleport(l);
                if (!isJoin)
                    new Corpse_Gui().OpenReviveSelectGUI(player);
                else if (Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isBgmOn()) {
                    new io.github.goldbigdragon.goldbigdragonrpg.dependency.NoteBlockAPIMain().Stop(player);
                    YamlController YC = new YamlController(Main_Main.plugin);
                    YamlManager Config = YC.getNewConfig("config.yml");
                    if (Config.getInt("Death.Track") != -1)
                        new io.github.goldbigdragon.goldbigdragonrpg.dependency.NoteBlockAPIMain().Play(player, Config.getInt("Death.Track"));
                }
                if (!Corpses.containsKey(player.getName()))
                    CreateCorpse(player);
                return true;
            }
        }
        return false;
    }

    public void asyncDeathCapture(final Player player) {
        if (player.getGameMode() == GameMode.SPECTATOR) {
            if (Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isDeath()) {
                Location l = Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getLastDeathPoint();
                if (l.getBlockY() < 0)
                    l.setY(0);
                if (l.getBlockX() != player.getLocation().getBlockX() || l.getBlockY() != player.getLocation().getBlockY() || l.getBlockZ() != player.getLocation().getBlockZ()) {
                    final Location loc = l.clone();
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main_Main.plugin, new Runnable() {
                        @Override
                        public void run() {
                            try {
                                player.teleport(loc);
                            } catch (Exception e) {
                            }
                        }
                    }, 0);
                }
                new Corpse_Gui().OpenReviveSelectGUI(player);
                if (!Corpses.containsKey(player.getName())) {
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main_Main.plugin, new Runnable() {
                        @Override
                        public void run() {
                            CreateCorpse(player);
                        }
                    }, 0);
                }
            }
        }
    }

    public void CreateCorpse(Player player) {
        if (Corpses.containsKey(player.getName())) {
        } else {
            RemoveCorpse(player.getName());
            int CorpseStyle = new Random().nextInt((int) (11)); //(0 ~ 10 까지의 수)
            String name = player.getName();
            ArrayList<ArmorStand> AL = new ArrayList<ArmorStand>();
            player.setGameMode(GameMode.SPECTATOR);
            int playerRandom = new Random().nextInt((int) (91)) - 45;
            Location playerLoc = player.getLocation();
            //if(CorpseStyle == 0)
            {
                Location loc = new Location(player.getLocation().getWorld(), player.getLocation().getX(), player.getLocation().getY() - 0.1, player.getLocation().getZ());
                loc.setDirection(loc.getDirection());
                ArmorStand stand = loc.getWorld().spawn(loc, ArmorStand.class);
                stand.setBasePlate(false);
                stand.setVisible(false);
                stand.setRemoveWhenFarAway(false);
                stand.setGravity(false);
                stand.setArms(true);
                stand.setCustomName(ChatColor.RED + "" + ChatColor.BLACK + "h");
                ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                SkullMeta meta = (SkullMeta) head.getItemMeta();
                meta.setDisplayName(name);
                meta.setOwner(name);
                head.setItemMeta(meta);
                stand.setHelmet(head);
                stand.setHeadPose(new EulerAngle(0.75d, playerRandom, 0));
                player.teleport(stand.getLocation());
                //player.setSpectatorTarget(stand);
                AL.add(stand);

                loc.add(0.41, -0.5, -0.6);
                ItemStack item = new ItemStack(Material.STICK);
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(name);
                item.setItemMeta(itemMeta);
                stand = loc.getWorld().spawn(loc, ArmorStand.class);
                stand.setBasePlate(false);
                stand.setVisible(false);
                stand.setRemoveWhenFarAway(false);
                stand.setGravity(false);
                stand.setArms(true);
                stand.setCustomName(ChatColor.RED + "" + ChatColor.BLACK + "b");
                stand.setItemInHand(item);
                stand.setRightArmPose(new EulerAngle(55f, 0, 0));
                AL.add(stand);

                loc.add(0, -0.8, -0.155);
                stand = loc.getWorld().spawn(loc, ArmorStand.class);
                stand.setBasePlate(false);
                stand.setVisible(false);
                stand.setRemoveWhenFarAway(false);
                stand.setGravity(false);
                stand.setArms(true);
                stand.setCustomName(ChatColor.RED + "" + ChatColor.BLACK + "b");
                stand.setItemInHand(item);
                stand.setRightArmPose(new EulerAngle(55f, 0, 0));
                AL.add(stand);

                loc.add(0.42, 1.45, 0.7);
                stand = loc.getWorld().spawn(loc, ArmorStand.class);
                stand.setBasePlate(false);
                stand.setVisible(false);
                stand.setRemoveWhenFarAway(false);
                stand.setGravity(false);
                stand.setArms(true);
                stand.setCustomName(ChatColor.RED + "" + ChatColor.BLACK + "b");
                stand.setItemInHand(item);
                stand.setRightArmPose(new EulerAngle(0, 1.5d, 0.2d));
                AL.add(stand);

                Corpses.put(name, AL);

                //아머 스탠드의 손에 든 아이템 이름에 모두 플레이어 이름 적고
                //없앨 때 아머 스탠드 이름을 보고, h면 머리 아이템을, b면 손 아이템의
                //이름에 적힌 플레이어 이름을 보고 없애고 살리고 하면 됨.
            }
        }
    }

    public void RemoveCorpse(String player) {
        if (Corpses.containsKey(player)) {
            for (int count = 0; count < Corpses.get(player).size(); count++)
                Corpses.get(player).get(count).remove();
            Corpses.remove(player);
        }
    }

    public void RemoveAllCorpse() {
        Object[] Players = Corpses.keySet().toArray();
        for (int count = 0; count < Corpses.size(); count++)
            if (Corpses.containsKey(Players[count].toString()))
                for (int count2 = 0; count2 < Corpses.get(Players[count].toString()).size(); count2++)
                    Corpses.get(Players[count].toString()).get(count2).remove();
        Corpses.clear();
    }
}
