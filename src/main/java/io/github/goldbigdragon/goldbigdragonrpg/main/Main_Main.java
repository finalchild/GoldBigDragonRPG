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

package io.github.goldbigdragon.goldbigdragonrpg.main;

import java.util.Collection;
import java.util.List;

import io.github.goldbigdragon.goldbigdragonrpg.admin.Admin_Command;
import io.github.goldbigdragon.goldbigdragonrpg.area.Area_Command;
import io.github.goldbigdragon.goldbigdragonrpg.area.Area_Main;
import io.github.goldbigdragon.goldbigdragonrpg.battle.Battle_Main;
import io.github.goldbigdragon.goldbigdragonrpg.corpse.Corpse_Gui;
import io.github.goldbigdragon.goldbigdragonrpg.corpse.Corpse_Main;
import io.github.goldbigdragon.goldbigdragonrpg.customitem.CustomItem_Command;
import io.github.goldbigdragon.goldbigdragonrpg.dungeon.Dungeon_Main;
import io.github.goldbigdragon.goldbigdragonrpg.effect.ParticleUtil;
import io.github.goldbigdragon.goldbigdragonrpg.effect.SoundUtil;
import io.github.goldbigdragon.goldbigdragonrpg.listener.*;
import io.github.goldbigdragon.goldbigdragonrpg.map.Map;
import io.github.goldbigdragon.goldbigdragonrpg.monster.Monster_Gui;
import io.github.goldbigdragon.goldbigdragonrpg.monster.Monster_Kill;
import io.github.goldbigdragon.goldbigdragonrpg.monster.Monster_Spawn;
import io.github.goldbigdragon.goldbigdragonrpg.npc.NPC_Command;
import io.github.goldbigdragon.goldbigdragonrpg.party.Party_Command;
import io.github.goldbigdragon.goldbigdragonrpg.quest.Quest_Command;
import io.github.goldbigdragon.goldbigdragonrpg.servertick.ServerTick_ScheduleManager;
import io.github.goldbigdragon.goldbigdragonrpg.skill.OPboxSkill_Gui;
import io.github.goldbigdragon.goldbigdragonrpg.skill.UserSkill_Gui;
import io.github.goldbigdragon.goldbigdragonrpg.structure.Structure_Main;
import io.github.goldbigdragon.goldbigdragonrpg.user.*;
import io.github.goldbigdragon.goldbigdragonrpg.util.ETC;
import io.github.goldbigdragon.goldbigdragonrpg.util.Util_Number;
import io.github.goldbigdragon.goldbigdragonrpg.util.Util_Player;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;
import io.github.goldbigdragon.goldbigdragonrpg.warp.Warp_Command;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.goldbigdragon.goldbigdragonrpg.party.Party_DataManager;
import io.github.goldbigdragon.goldbigdragonrpg.user.UserData_Object;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;
import net.milkbowl.vault.economy.Economy;
import net.minecraft.server.v1_12_R1.PacketPlayInClientCommand;

public class Main_Main extends JavaPlugin implements Listener {
    public static JavaPlugin plugin = null;

    @EventHandler
    public void SongEndEvent(com.xxmicloxx.NoteBlockAPI.SongEndEvent event) {
        event.getSongPlayer().setPlaying(false);
        Player player = null;
        for (int count = 0; count < event.getSongPlayer().getPlayerList().size(); count++) {
            player = Bukkit.getPlayer(event.getSongPlayer().getPlayerList().get(count));
            new io.github.goldbigdragon.goldbigdragonrpg.dependency.NoteBlockAPIMain().SongPlay(player, event.getSongPlayer().getSong());
        }
    }

    @EventHandler
    public void craftItem(PrepareItemCraftEvent event) {
        Inventory inv = event.getInventory();
        ItemStack item = null;
        boolean cantCraft = false;
        for (int count = 0; count < inv.getSize(); count++) {
            item = inv.getItem(count);
            if (item != null && item.getType() != Material.AIR) {
                if (item.hasItemMeta() && item.getItemMeta().hasItemFlag(ItemFlag.HIDE_UNBREAKABLE) && item.getItemMeta().hasLore() && item.getItemMeta().hasDisplayName() &&
                        item.getItemMeta().getLore().get(0).contains("[돈]")) {
                    cantCraft = true;
                    break;
                }

            }
        }
        if (cantCraft)
            inv.setItem(0, new ItemStack(Material.AIR));
    }

    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new Battle_Main(), this);
        getServer().getPluginManager().registerEvents(new Main_BlockPlace(), this);
        getServer().getPluginManager().registerEvents(new Map(), this);
        getServer().getPluginManager().registerEvents(new Main_BlockBreak(), this);
        getServer().getPluginManager().registerEvents(new Main_Fishing(), this);
        getServer().getPluginManager().registerEvents(new Main_PlayerChat(), this);
        getServer().getPluginManager().registerEvents(new Main_ChangeHotBar(), this);
        getServer().getPluginManager().registerEvents(new Main_PlayerJoin(), this);
        new io.github.goldbigdragon.goldbigdragonrpg.dependency.NoteBlockAPIMain(Main_Main.plugin);
        new Main_ServerOption().Initialize();

        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> rspE = getServer().getServicesManager().getRegistration(Economy.class);
            if (rspE != null)
                Main_ServerOption.economy = rspE.getProvider();
        }
    }

    public void onDisable() {
        new Corpse_Main().RemoveAllCorpse();
        Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
        Player[] a = new Player[playerlist.size()];
        playerlist.toArray(a);
        for (short count = 0; count < a.length; count++)
            new io.github.goldbigdragon.goldbigdragonrpg.dependency.NoteBlockAPIMain().Stop(a[count]);
        new Party_DataManager().saveParty();

        new ServerTick_ScheduleManager().saveCategoriFile();
        Object[] players = Bukkit.getOnlinePlayers().toArray();
        for (short count = 0; count < players.length; count++)
            Main_ServerOption.PlayerList.get(((Player) players[count]).getUniqueId().toString()).saveAll();
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Clossing GoldBigDragon Advanced...]");
    }

    @EventHandler
    private void PlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        YamlController YC = new YamlController(this);
        if (player.getLocation().getWorld().getName().compareTo("Dungeon") == 0)
            new Dungeon_Main().EraseAllDungeonKey(player, true);

        if (new Corpse_Main().DeathCapture(player, false))
            new Corpse_Main().RemoveCorpse(player.getName());

        if (Main_ServerOption.PartyJoiner.containsKey(player))
            Main_ServerOption.Party.get(Main_ServerOption.PartyJoiner.get(player)).QuitParty(player);

        new io.github.goldbigdragon.goldbigdragonrpg.dependency.NoteBlockAPIMain().Stop(event.getPlayer());

        YamlManager UserData = YC.getNewConfig("UserData/" + player.getUniqueId() + ".yml");
        UserData.removeKey("Data");
        UserData.saveConfig();
        Main_ServerOption.PlayerCurrentArea.remove(player);
        new Equip_Gui().FriendJoinQuitMessage(player, false);

        YamlManager Config = YC.getNewConfig("config.yml");
        if (Config.getString("Server.QuitMessage") != null) {
            String message = Config.getString("Server.QuitMessage").replace("%player%", event.getPlayer().getName());
            event.setQuitMessage(message);
        } else
            event.setQuitMessage(null);
        Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).saveAll();
        Main_ServerOption.PlayerList.remove(player.getUniqueId().toString());
    }

    @EventHandler
    private void PlayerRespawn(PlayerRespawnEvent event) {
        YamlController YC = new YamlController(this);
        YamlManager Config = YC.getNewConfig("config.yml");
        if (Config.getBoolean("Death.SystemOn")) {
            Player player = event.getPlayer();
            event.setRespawnLocation(Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getLastDeathPoint());
            player.setGameMode(GameMode.SPECTATOR);
            if (Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isBgmOn()) {
                new io.github.goldbigdragon.goldbigdragonrpg.dependency.NoteBlockAPIMain().Stop(player);
                if (Config.contains("Death.Track"))
                    if (Config.getInt("Death.Track") != -1)
                        new io.github.goldbigdragon.goldbigdragonrpg.dependency.NoteBlockAPIMain().Play(player, Config.getInt("Death.Track"));
            }
            new Corpse_Gui().OpenReviveSelectGUI(player);
        }
    }


    @EventHandler
    private void PlayerItemDrop(PlayerDropItemEvent event) {
        ItemStack IT = event.getItemDrop().getItemStack();
        if (IT.hasItemMeta())
            if (IT.getItemMeta().hasLore())
                if (IT.getItemMeta().getLore().size() == 4)
                    if (IT.getItemMeta().getLore().get(3).equals(ChatColor.YELLOW + "[클릭시 퀵슬롯에서 삭제]"))
                        event.setCancelled(true);
    }

    @EventHandler
    private void AmorStand(PlayerArmorStandManipulateEvent event) {
        Player player = event.getPlayer();
        ArmorStand AS = event.getRightClicked();
        String name = AS.getCustomName();
        if (name != null) {
            if (name.charAt(0) == '§' && name.charAt(1) == '0' && name.charAt(2) == '§' && name.charAt(3) == 'l') {
                event.setCancelled(true);
                new Structure_Main().StructureUse(player, AS.getCustomName());
            } else if (name.charAt(0) == '§' && name.charAt(1) == 'c' && name.charAt(2) == '§' && name.charAt(3) == '0') {
                event.setCancelled(true);
                if (player.getInventory().getItemInMainHand() != null) {
                    if (Main_ServerOption.DeathRescue != null) {
                        if (Main_ServerOption.DeathRescue.getTypeId() == player.getInventory().getItemInMainHand().getTypeId()) {
                            ItemStack Pitem = player.getInventory().getItemInMainHand();
                            if (Main_ServerOption.DeathRescue.getAmount() <= Pitem.getAmount()) {
                                String Name = null;
                                if (AS.getItemInHand().getType() != Material.AIR)
                                    Name = AS.getItemInHand().getItemMeta().getDisplayName();
                                else if (AS.getHelmet().getType() != Material.AIR)
                                    Name = AS.getHelmet().getItemMeta().getDisplayName();
                                if (Name != null) {
                                    Player target = Bukkit.getPlayer(Name);
                                    if (target != null) {
                                        if (Main_ServerOption.PlayerList.get(target.getUniqueId().toString()).isDeath()) {
                                            if (new Util_Player().deleteItem(player, Main_ServerOption.DeathRescue, Main_ServerOption.DeathRescue.getAmount())) {
                                                new Corpse_Main().RemoveCorpse(Name);
                                                player.updateInventory();
                                                player.sendMessage(ChatColor.LIGHT_PURPLE + "[구조] : " + ChatColor.YELLOW + target.getName() + ChatColor.LIGHT_PURPLE + "님을 부활시켰습니다!");
                                                target.sendMessage(ChatColor.LIGHT_PURPLE + "[부활] : " + ChatColor.YELLOW + player.getName() + ChatColor.LIGHT_PURPLE + "님에 의해 부활하였습니다!");
                                                target.setGameMode(GameMode.SURVIVAL);
                                                target.closeInventory();
                                                Location l = target.getLocation();
                                                l.add(0, 1, 0);
                                                target.teleport(l);
                                                for (short count2 = 0; count2 < 210; count2++)
                                                    new ParticleUtil().PL(target.getLocation(), org.bukkit.Effect.SMOKE, new Util_Number().RandomNum(0, 14));
                                                SoundUtil.playSound(target.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 0.5F, 1.8F);
                                                new io.github.goldbigdragon.goldbigdragonrpg.dependency.NoteBlockAPIMain().Stop(target);
                                                YamlController YC = new YamlController(Main_Main.plugin);
                                                YamlManager Config = YC.getNewConfig("config.yml");
                                                new Corpse_Gui().Penalty(target, Config.getString("Death.Spawn_Help.SetHealth"), Config.getString("Death.Spawn_Help.PenaltyEXP"), Config.getString("Death.Spawn_Help.PenaltyMoney"));
                                            } else {
                                                SoundUtil.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
                                                player.sendMessage(ChatColor.RED + "[SYSTEM] : 부활 아이템이 부족하여 부활시킬 수 없습니다!");
                                            }
                                        }
                                    } else {
                                        Collection<Entity> aa = player.getLocation().getWorld().getNearbyEntities(player.getLocation(), 3, 3, 3);
                                        for (int count = 0; count < aa.size(); count++) {
                                            Entity now = ((Entity) aa.toArray()[count]);
                                            if (now.getType() == EntityType.ARMOR_STAND) {
                                                String CustomName = now.getCustomName();
                                                if (CustomName != null) {
                                                    if (CustomName.charAt(0) == '§' && CustomName.charAt(1) == 'c' && CustomName.charAt(2) == '§' && CustomName.charAt(3) == '0') {
                                                        String Name2 = null;
                                                        if (AS.getItemInHand().getType() != Material.AIR)
                                                            Name2 = AS.getItemInHand().getItemMeta().getDisplayName();
                                                        else if (AS.getHelmet().getType() != Material.AIR)
                                                            Name2 = AS.getHelmet().getItemMeta().getDisplayName();
                                                        if (Name.compareTo(Name2) == 0)
                                                            now.remove();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    AS.remove();
                                }
                            }
                        }
                    }
                }
            } else {
                if (!event.getPlayer().isOp()) {
                    String TargetArea = null;
                    Area_Main A = new Area_Main();
                    if (A.getAreaName(AS) != null)
                        TargetArea = A.getAreaName(AS)[0];
                    if (TargetArea != null && !A.getAreaOption(TargetArea, (char) 7)) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    private void BlockBurnEvent(BlockBurnEvent event) {
        if (Main_ServerOption.AntiExplode || event.getBlock().getLocation().getWorld().getName().compareTo("Dungeon") == 0)
            event.setCancelled(true);
    }

    @EventHandler
    private void BlockIgniteEvent(BlockIgniteEvent event) {
        if ((Main_ServerOption.AntiExplode || event.getBlock().getLocation().getWorld().getName().compareTo("Dungeon") == 0) && event.getIgnitingEntity() == null)
            event.setCancelled(true);
    }


    @EventHandler
    private void PlayerDeath(PlayerDeathEvent event) {
        final Player player = event.getEntity();
        YamlController YC = new YamlController(this);
        YamlManager Config = YC.getNewConfig("config.yml");
        if (Config.getBoolean("Death.SystemOn")) {
            Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setDeath(true);
            Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                public void run() {
                    PacketPlayInClientCommand packet = new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN);
                    ((CraftPlayer) player).getHandle().playerConnection.a(packet);
                }
            }, 1L);
        }
        Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setLastDeathPoint(new Location(event.getEntity().getLocation().getWorld(), event.getEntity().getLocation().getX(), event.getEntity().getLocation().getY(), event.getEntity().getLocation().getZ(), event.getEntity().getLocation().getYaw(), event.getEntity().getLocation().getPitch()));
        if (!event.getKeepInventory()) {
            List<ItemStack> Ilist = event.getDrops();
            event.setKeepInventory(true);
            event.getEntity().getInventory().clear();
            for (byte count = 0; count < Ilist.size(); count++) {
                ItemStack IT = Ilist.get(count);
                if (IT.isSimilar(Main_ServerOption.DeathRevive)) {
                    Ilist.remove(count);
                    event.getEntity().getInventory().addItem(IT);
                }

				/*
                else if(IT.hasItemMeta() == true)
					if(IT.getItemMeta().hasLore() == true)
						if(IT.getItemMeta().getLore().size() >= 4)
							if(IT.getItemMeta().getLore().get(3).equals(ChatColor.YELLOW+"[클릭시 퀵슬롯에서 삭제]")==true)
								Ilist.remove(count);
				*/
            }
            for (int count = 0; count < Ilist.size(); count++)
                new Main_ItemDrop().CustomItemDrop(event.getEntity().getLocation(), Ilist.get(count));
        }
    }

    @EventHandler
    private void KeepItemDurability(PlayerItemDamageEvent event) {
        ItemStack item = event.getItem();
        if (item.hasItemMeta())
            if (item.getItemMeta().hasLore())
                if (item.getItemMeta().getLore().toString().contains("내구도"))
                    event.setCancelled(true);
    }

    @EventHandler
    private void EntitySpawn(CreatureSpawnEvent event) {
        new Monster_Spawn().EntitySpawn(event);
    }

    @EventHandler
    private void ITBlock(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL) {
            Block block = event.getClickedBlock();
            if (block != null) {
                if (block.getTypeId() == 60) {
                    Area_Main A = new Area_Main();
                    String[] Area = A.getAreaName(event.getClickedBlock());
                    if (Area != null) {
                        if (!A.getAreaOption(Area[0], (char) 7)) {
                            event.setCancelled(true);
                            if (!event.getPlayer().isOp()) {
                                SoundUtil.playSound(event.getPlayer(), org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
                                event.getPlayer().sendMessage(ChatColor.RED + "[SYSTEM] : " + ChatColor.YELLOW + Area[1] + ChatColor.RED + " 지역에 있는 작물은 손 댈 수없습니다!");
                            }
                            return;
                        }
                    }
                }
            }
        }
        new ETC().UpdatePlayerHPMP(event.getPlayer());
        new Main_Interact().PlayerInteract(event);
    }

    @EventHandler
    private void ITEnity(PlayerInteractEntityEvent event) {
        new ETC().UpdatePlayerHPMP(event.getPlayer());
        new Main_ServerOption().CitizensCatch();
        new Main_Interact().PlayerInteractEntity(event);
    }

    @EventHandler
    private void ItemGetMessage(PlayerPickupItemEvent event) {
        new Main_Interact().PlayerGetItem(event);
    }

    @EventHandler
    private void MonsterKill(EntityDeathEvent event) {
        new Monster_Kill().MonsterKilling(event);
    }

    @EventHandler
    private void EntityExplode(EntityExplodeEvent event) {
        if (Main_ServerOption.AntiExplode || event.getEntity().getLocation().getWorld().getName().compareTo("Dungeon") == 0)
            event.blockList().clear();
    }

    @EventHandler
    private void ExplosionPrime(ExplosionPrimeEvent event) {
        if (event.getEntity().getLocation().getWorld().getName().compareTo("Dungeon") == 0) {
            if (event.getEntityType() == EntityType.ENDER_CRYSTAL || event.getEntityType() == EntityType.DRAGON_FIREBALL
                    || event.getEntityType() == EntityType.FIREBALL || event.getEntityType() == EntityType.SMALL_FIREBALL)
                return;
            else {
                event.setCancelled(true);
                new Monster_Kill().Boomb(event.getEntity());
                new Monster_Kill().DungeonKilled((LivingEntity) event.getEntity(), true);
            }
            event.getEntity().remove();
        }
    }

    @EventHandler
    private void onArrowHitBlock(ProjectileHitEvent event) {
        if (event.getEntity().getLocation().getWorld().getName().compareTo("Dungeon") == 0) {
            if (event.getEntity().getType() == EntityType.ARROW) {
                Arrow a = (Arrow) event.getEntity();
                if (a.getShooter() instanceof Player) {
                    Player player = (Player) a.getShooter();
                    if (player.isOnline()) {
                        Location down = new Location(event.getEntity().getWorld(), event.getEntity().getLocation().getX(), event.getEntity().getLocation().getY(), event.getEntity().getLocation().getZ());
                        Block b = null;
                        int yaw = (int) event.getEntity().getLocation().getYaw();
                        for (byte count = 0; count < 2; count++) {
                            if (yaw >= -46 && yaw <= 45)
                                down.add(0, 0, 1);
                            else if (yaw >= 46 && yaw <= 135)
                                down.add(1, 0, 0);
                            else if (yaw >= -136 && yaw <= -45)
                                down.add(-1, 0, 0);
                            else
                                down.add(0, 0, -1);
                            b = down.getBlock();
                            if (b.getTypeId() != 0)
                                break;
                        }
                        if (b.getTypeId() == 146)
                            new Dungeon_Main().TrapChestOpen(b);
                        else if (b.getTypeId() == 95)
                            new Dungeon_Main().TrapGlassTouch(b, player);
                    }
                }
            }
        }
    }

    @EventHandler
    private void applyHealthRegen(EntityRegainHealthEvent event) {
        if (event.isCancelled())
            return;
        if (((event.getEntity() instanceof Player)) && (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED)) {
            ETC ETC = new ETC();
            ETC.UpdatePlayerHPMP((Player) event.getEntity());
        }
    }

    @EventHandler
    private void InventoryClick(InventoryClickEvent event) {
        if (Bukkit.getPluginManager().isPluginEnabled("MagicSpells") && Main_ServerOption.MagicSpellsCatched) {
            ETC ETC = new ETC();
            ETC.UpdatePlayerHPMP((Player) event.getWhoClicked());
        }
        if (event.getClickedInventory() == null || event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR)
            return;
        if (event.getCurrentItem().hasItemMeta()) {
            if (event.getCurrentItem().getItemMeta().hasLore()) {
                if (event.getCurrentItem().getItemMeta().getLore().size() == 4) {
                    if (event.getCurrentItem().getItemMeta().getLore().get(3).equals((ChatColor.YELLOW + "[클릭시 퀵슬롯에서 삭제]"))) {
                        event.setCancelled(true);
                        event.getWhoClicked().getInventory().setItem(event.getSlot(), null);
                        SoundUtil.playSound((Player) event.getWhoClicked(), Sound.BLOCK_ANVIL_LAND, 1.0F, 1.9F);
                        return;
                    }
                }
            }
        }
        if (event.getInventory().getName().charAt(0) == '§') {
            String InventoryCode = event.getInventory().getName().split("§r")[0].replaceAll("§", "");
            //[§0] [§0§0] [§0§0] [§r]
            //1번째 색 코드 표 = 클릭시 이벤트 캔슬 여부
            //2,3번째 색 코드 표 = 해당 GUI화면 타입
            //4,5번째 색 코드 표 = 해당 GUI화면 타입 중, 몇 번째 GUI인지
            //6번째 되돌림 색 코드 표 = split을 위한 코드

            //1번째 색 코드가 0이면, 클릭시 무조건 취소되는 것
            if (event.getInventory().getName().charAt(1) == '0')
                event.setCancelled(true);
            new Main_InventoryClick().InventoryClickRouter(event, InventoryCode);
        }
    }

    @EventHandler
    private void InventoryClose(InventoryCloseEvent event) {
        if (Bukkit.getPluginManager().isPluginEnabled("MagicSpells")
                && Main_ServerOption.MagicSpellsCatched) {
            ETC ETC = new ETC();
            ETC.UpdatePlayerHPMP((Player) event.getPlayer());
        }

        if (event.getInventory().getName().charAt(0) == '§') {
            String InventoryCode = event.getInventory().getName().split("§r")[0].replaceAll("§", "");
            //[§0] [§0§0] [§0§0] [§r]
            //1번째 색 코드 표 = 클릭시 이벤트 캔슬 여부
            //2,3번째 색 코드 표 = 해당 GUI화면 타입
            //4,5번째 색 코드 표 = 해당 GUI화면 타입 중, 몇 번째 GUI인지
            //6번째 되돌림 색 코드 표 = split을 위한 코드
            new Main_InventoryClose().InventoryCloseRouter(event, InventoryCode);
        }
    }

    public boolean onCommand(CommandSender talker, org.bukkit.command.Command command, String string, String[] args) {
        new Main_ServerOption().MagicSpellCatch();
        new Main_ServerOption().CitizensCatch();
        for (byte count = 0; count < args.length; count++)
            args[count] = ChatColor.translateAlternateColorCodes('&', args[count]);

        if (talker instanceof Player) {
            Player player = (Player) talker;

            switch (string) {
                case "gui사용":
                case "gbdenablegui":
                    if (player.isOp()) {
                        SoundUtil.playSound((Player) talker, org.bukkit.Sound.ENTITY_VILLAGER_YES, 1.0F, 1.8F);
                        player.sendMessage(ChatColor.GREEN + "[NPC] : GUI를 활성화 시킬 NPC를 우클릭 하세요!");
                        new UserData_Object().setInt(player, (byte) 4, 114);
                    } else {
                        talker.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
                        SoundUtil.playSound((Player) talker, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
                    }
                    return true;
                case "친구":
                case "gbdfriend":
                    SoundUtil.playSound((Player) talker, org.bukkit.Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
                    new ETC_Gui().FriendsGUI(player, (short) 0);
                    return true;
                case "스킬":
                case "gbdskill":
                    SoundUtil.playSound((Player) talker, org.bukkit.Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
                    UserSkill_Gui PSKGUI = new UserSkill_Gui();
                    PSKGUI.MainSkillsListGUI(player, (short) 0);
                    return true;
                case "아이템":
                case "gbditem":
                    CustomItem_Command ItemC = new CustomItem_Command();
                    if (args.length <= 0) {
                        if (args.length <= 0) {
                            ItemC.HelpMessage(player);
                            return true;
                        }
                        if (ChatColor.stripColor(args[0]).equalsIgnoreCase("설명제거"))
                            ItemC.onCommand2(talker, command, string, args);
                        else {
                            ItemC.HelpMessage(player);
                            return true;
                        }
                    }
                    if (!ChatColor.stripColor(args[0]).equalsIgnoreCase("목록") && !ChatColor.stripColor(args[0]).equalsIgnoreCase("등록") && !ChatColor.stripColor(args[0]).equalsIgnoreCase("삭제") && !ChatColor.stripColor(args[0]).equalsIgnoreCase("받기") && !ChatColor.stripColor(args[0]).equalsIgnoreCase("주기"))
                        ItemC.onCommand2(talker, command, string, args);
                    else
                        ItemC.onCommand1(talker, command, string, args);
                    break;
                case "파티":
                case "gbdparty":
                    Party_Command PartyC = new Party_Command();
                    PartyC.onCommand(talker, command, string, args);
                    return true;
                case "테스트":
                case "테스트2":
                case "타입추가":
                case "엔티티제거":
                case "아이템제거":
                case "강제철거":
                case "오피박스":
                case "스텟초기화권":
                case "경주":
                case "경험치주기":
                case "gbdtest":
                case "gbdtest2":
                case "gbdaddtype":
                case "gbdremoveentity":
                case "gbdremoveitem":
                case "gbdforceremove":
                case "opbox":
                case "gbdbacktothenewbie":
                case "giveexp":
                    new Admin_Command().onCommand(player, args, string);
                    return true;
                case "수락":
                case "거절":
                case "돈":
                case "gbdaccept":
                case "gbddeny":
                case "gbdmoney":
                    new User_Command().onCommand(player, args, string);
                    return true;
                case "스텟":
                case "gbdstat":
                    SoundUtil.playSound((Player) talker, org.bukkit.Sound.ENTITY_HORSE_ARMOR, 0.8F, 1.8F);
                    new Stats_Gui().StatusGUI((Player) talker);
                    return true;
                case "옵션":
                case "gbdoption":
                    SoundUtil.playSound((Player) talker, org.bukkit.Sound.ENTITY_HORSE_ARMOR, 0.8F, 1.8F);
                    new Option_Gui().optionGUI((Player) talker);
                    return true;
                case "기타":
                case "gbdetc":
                    SoundUtil.playSound((Player) talker, org.bukkit.Sound.ENTITY_HORSE_ARMOR, 0.8F, 1.8F);
                    new ETC_Gui().ETCGUI_Main((Player) talker);
                    return true;
                case "몬스터":
                case "gbdmobs":
                    if (talker.isOp()) {
                        SoundUtil.playSound((Player) talker, org.bukkit.Sound.ENTITY_HORSE_ARMOR, 0.8F, 1.8F);
                        new Monster_Gui().MonsterListGUI(player, 0);
                    } else {
                        talker.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
                        SoundUtil.playSound((Player) talker, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
                    }
                    return true;
                case "워프":
                case "gbdwarp":
                    Warp_Command WarpC = new Warp_Command();
                    WarpC.onCommand(talker, command, string, args);
                    return true;
                case "영역":
                case "gbdarea":
                    Area_Command AreaC = new Area_Command();
                    AreaC.onCommand(talker, command, string, args);
                    return true;
                case "상점":
                case "gbdshop":
                    NPC_Command NPCC = new NPC_Command();
                    NPCC.onCommand(talker, command, string, args);
                    return true;
                case "퀘스트":
                case "gbdquest":
                    Quest_Command QC = new Quest_Command();
                    QC.onCommand(talker, command, string, args);
                    return true;
                case "커맨드":
                case "gbdcommand":
                    if (player.isOp()) {
                        UserData_Object u = new UserData_Object();
                        if (u.getType(player) != null && u.getType(player).compareTo("Skill") == 0) {
                            if (u.getString(player, (byte) 1).equalsIgnoreCase("SKC")) {
                                String CommandString = "";
                                for (byte count = 0; count < args.length - 1; count++)
                                    CommandString = CommandString + args[count] + " ";
                                CommandString = CommandString + args[args.length - 1];
                                YamlController YC = new YamlController(this);
                                YamlManager SkillList = YC.getNewConfig("Skill/SkillList.yml");
                                if (!CommandString.contains("/"))
                                    CommandString = "/" + CommandString;
                                if (CommandString.equalsIgnoreCase("/없음"))
                                    SkillList.set(u.getString(player, (byte) 2) + ".SkillRank." + u.getInt(player, (byte) 4) + ".Command", "null");
                                else
                                    SkillList.set(u.getString(player, (byte) 2) + ".SkillRank." + u.getInt(player, (byte) 4) + ".Command", CommandString);
                                SkillList.saveConfig();
                                OPboxSkill_Gui SKGUI = new OPboxSkill_Gui();
                                SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte) 2), (short) u.getInt(player, (byte) 4));
                                u.clearAll(player);
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "[스킬 설정] : 이 명령어는 스킬 설정시 사용됩니다!");
                            SoundUtil.playSound((Player) talker, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
                            return true;
                        }
                    } else {
                        talker.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 명령어를 실행하기 위해서는 관리자 권한이 필요합니다!");
                        SoundUtil.playSound((Player) talker, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
                    }
                    return true;
            }
            return false;
        } else {
            if (string.compareTo("경주") == 0 || string.compareTo("giveexp") == 0 || string.compareTo("경험치주기") == 0) {
                if (args.length == 2) {
                    Player target = Bukkit.getServer().getPlayer(args[0]);
                    if (target.isOnline()) {
                        int EXP = 0;
                        try {
                            EXP = Integer.parseInt(args[1]);
                        } catch (NumberFormatException e) {
                            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[SYSTEM] : 정수 형태의 값(숫자)을 입력하세요!");
                            return true;
                        }
                        Main_ServerOption.PlayerList.get(target.getUniqueId().toString()).addStat_MoneyAndEXP(0, EXP, true);
                        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[SYSTEM] : " + args[0] + "님에게 경험치 " + EXP + "을 지급하였습니다!");
                    } else {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[SYSTEM] : 해당 플레이어는 접속중이 아닙니다!");
                    }
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[SYSTEM] : /경주 [닉네임] [경험치]");
                }
            }
        }
        return false;
    }

    @EventHandler
    public void Sign(SignChangeEvent event) {
        for (int i = 0; i <= 3; i++) {
            String line = event.getLine(i);
            line = ChatColor.translateAlternateColorCodes('&', line);
            event.setLine(i, line);
        }
    }
}
