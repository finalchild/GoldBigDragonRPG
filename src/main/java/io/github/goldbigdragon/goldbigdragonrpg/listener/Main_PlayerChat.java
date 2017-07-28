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

package io.github.goldbigdragon.goldbigdragonrpg.listener;

import java.util.Calendar;
import java.util.Collection;

import io.github.goldbigdragon.goldbigdragonrpg.admin.*;
import io.github.goldbigdragon.goldbigdragonrpg.area.Area_Chat;
import io.github.goldbigdragon.goldbigdragonrpg.customitem.CustomItem_Chat;
import io.github.goldbigdragon.goldbigdragonrpg.dungeon.Dungeon_Chat;
import io.github.goldbigdragon.goldbigdragonrpg.effect.SoundUtil;
import io.github.goldbigdragon.goldbigdragonrpg.job.Job_Chat;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_ServerOption;
import io.github.goldbigdragon.goldbigdragonrpg.monster.Monster_Chat;
import io.github.goldbigdragon.goldbigdragonrpg.npc.NPC_Chat;
import io.github.goldbigdragon.goldbigdragonrpg.quest.Quest_Chat;
import io.github.goldbigdragon.goldbigdragonrpg.skill.Skill_Chat;
import io.github.goldbigdragon.goldbigdragonrpg.structure.Structure_Chat;
import io.github.goldbigdragon.goldbigdragonrpg.user.ETC_Gui;
import io.github.goldbigdragon.goldbigdragonrpg.user.Equip_Gui;
import io.github.goldbigdragon.goldbigdragonrpg.user.UserData_Object;
import io.github.goldbigdragon.goldbigdragonrpg.util.Util_Chat;
import io.github.goldbigdragon.goldbigdragonrpg.warp.Warp_Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;

public class Main_PlayerChat extends Util_Chat implements Listener {
    @EventHandler
    public void PlayerChatting(PlayerChatEvent event) {
        event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
        UserData_Object u = new UserData_Object();
        SoundUtil sound = new SoundUtil();
        Player player = event.getPlayer();
        String playerUUID = event.getPlayer().getUniqueId().toString();
        if (u.getTemp(player) != null) {
            TEMProuter(event, u.getTemp(player));
            return;
        }
        if (player.isOp())
            if (u.getType(player) != null)
                if (u.getType(player).compareTo("Quest") == 0) {
                    new Quest_Chat().QuestTypeChatting(event);
                    return;
                } else if (u.getType(player).compareTo("WorldCreater") == 0) {
                    new WorldCreate_Chat().WorldCreaterTypeChatting(event);
                    return;
                } else if (u.getType(player).compareTo("UseableItem") == 0
                        || u.getType(player).compareTo("Upgrade") == 0
                        || u.getType(player).compareTo("Item") == 0) {
                    new CustomItem_Chat().ItemTypeChatting(event);
                    return;
                } else if (u.getType(player).compareTo("Area") == 0) {
                    new Area_Chat().AreaTypeChatting(event);
                    return;
                } else if (u.getType(player).compareTo("NPC") == 0) {
                    new NPC_Chat().NPCTypeChatting(event);
                    return;
                } else if (u.getType(player).compareTo("NewBie") == 0) {
                    new NewBie_Chat().NewBieTypeChatting(event);
                    return;
                } else if (u.getType(player).compareTo("Skill") == 0) {
                    new Skill_Chat().SkillTypeChatting(event);
                    return;
                } else if (u.getType(player).compareTo("Job") == 0) {
                    new Job_Chat().JobTypeChatting(event);
                    return;
                } else if (u.getType(player).compareTo("Monster") == 0) {
                    new Monster_Chat().MonsterTypeChatting(event);
                    return;
                } else if (u.getType(player).compareTo("Teleport") == 0) {
                    new Warp_Chat().TeleportTypeChatting(event);
                    return;
                } else if (u.getType(player).compareTo("Event") == 0) {
                    new Event_Chat().EventChatting(event);
                    return;
                } else if (u.getType(player).compareTo("System") == 0) {
                    new OPbox_Chat().SystemTypeChatting(event);
                    return;
                } else if (u.getType(player).compareTo("Navi") == 0) {
                    new Navigation_Chat().NaviTypeChatting(event);
                    return;
                } else if (u.getType(player).compareTo("Gamble") == 0) {
                    new Gamble_Chat().GambleChatting(event);
                    return;
                }
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager Config = YC.getNewConfig("config.yml");
        String Prefix = "";
        if (Config.contains("Server.ChatPrefix")) {
            Calendar C = Calendar.getInstance();
            Prefix = Config.getString("Server.ChatPrefix");
            Prefix = Prefix.replace("%t%", C.get(Calendar.HOUR_OF_DAY) + "시" + C.get(Calendar.MINUTE) + "분");
            if (player.getGameMode() == GameMode.SURVIVAL)
                Prefix = Prefix.replace("%gm%", "서바이벌");
            else if (player.getGameMode() == GameMode.ADVENTURE)
                Prefix = Prefix.replace("%gm%", "어드밴쳐");
            else if (player.getGameMode() == GameMode.CREATIVE)
                Prefix = Prefix.replace("%gm%", "크리에이티브");
            else if (player.getGameMode() == GameMode.SPECTATOR)
                Prefix = Prefix.replace("%gm%", "관전");

            if (Main_ServerOption.PlayerList.get(playerUUID).getOption_ChattingType() == 0)
                Prefix = Prefix.replace("%ct%", "일반");
            else if (Main_ServerOption.PlayerList.get(playerUUID).getOption_ChattingType() == 1)
                Prefix = Prefix.replace("%ct%", "파티");
            else if (Main_ServerOption.PlayerList.get(playerUUID).getOption_ChattingType() == 3)
                Prefix = Prefix.replace("%ct%", "관리자");
            Prefix = Prefix.replace("%lv%", Main_ServerOption.PlayerList.get(playerUUID).getStat_Level() + "");
            Prefix = Prefix.replace("%rlv%", Main_ServerOption.PlayerList.get(playerUUID).getStat_RealLevel() + "");
            YamlManager Job = YC.getNewConfig("Skill/PlayerData/" + player.getUniqueId() + ".yml");
            Prefix = Prefix.replace("%job%", Job.getString("Job.Type"));
            Prefix = Prefix.replace("%player%", player.getName());
            Prefix = Prefix.replace("%message%", event.getMessage());
            event.setCancelled(true);
            switch (Main_ServerOption.PlayerList.get(playerUUID).getOption_ChattingType()) {
                case 0:
                    Bukkit.broadcastMessage(Prefix);
                    return;
                case 1:
                    if (!Main_ServerOption.PartyJoiner.containsKey(player)) {
                        player.sendMessage(ChatColor.BLUE + "[파티] : 파티에 가입되어 있지 않습니다!");
                        sound.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
                    } else {
                        Main_ServerOption.Party.get(Main_ServerOption.PartyJoiner.get(player)).PartyBroadCastMessage(ChatColor.BLUE + "[파티] " + Prefix, null);
                        Bukkit.getConsoleSender().sendMessage("[파티] " + Prefix);
                    }
                    return;
                case 2:
                    event.setCancelled(true);
                    return;
                case 3:
                    event.setCancelled(true);
                    if (!player.isOp()) {
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "[관리자] : 당신은 관리자가 아닙니다!");
                        sound.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
                    } else {
                        Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
                        Player[] a = new Player[playerlist.size()];
                        playerlist.toArray(a);
                        for (int count = 0; count < a.length; count++) {
                            if (a[count].isOp())
                                a[count].sendMessage(ChatColor.LIGHT_PURPLE + "[관리자] " + Prefix);
                        }
                        Bukkit.getConsoleSender().sendMessage("[관리자] " + Prefix);
                    }
            }
        } else {
            switch (Main_ServerOption.PlayerList.get(playerUUID).getOption_ChattingType()) {
                case 0:
                    return;
                case 1:
                    event.setCancelled(true);
                    if (!Main_ServerOption.PartyJoiner.containsKey(player)) {
                        player.sendMessage(ChatColor.BLUE + "[파티] : 파티에 가입되어 있지 않습니다!");
                        sound.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
                    } else {
                        Main_ServerOption.Party.get(Main_ServerOption.PartyJoiner.get(player)).PartyBroadCastMessage(ChatColor.BLUE + "[파티] " + player.getName() + " : " + event.getMessage(), null);
                        Bukkit.getConsoleSender().sendMessage("[파티] " + player.getName() + " : " + event.getMessage());
                    }
                    return;
                case 2:
                    event.setCancelled(true);
                    return;
                case 3:
                    event.setCancelled(true);
                    if (!player.isOp()) {
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "[관리자] : 당신은 관리자가 아닙니다!");
                        sound.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
                    } else {
                        Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
                        Player[] a = new Player[playerlist.size()];
                        playerlist.toArray(a);
                        for (short count = 0; count < a.length; count++) {
                            if (a[count].isOnline()) {
                                Player send = (Player) Bukkit.getOfflinePlayer(((Player) a[count]).getName());
                                send.sendMessage(ChatColor.LIGHT_PURPLE + "[관리자] " + player.getName() + " : " + event.getMessage());
                            }
                        }
                        Bukkit.getConsoleSender().sendMessage("[관리자] " + player.getName() + " : " + event.getMessage());
                    }
            }
        }
    }

    public void TEMProuter(PlayerChatEvent event, String Temp) {
        event.setCancelled(true);
        Player player = event.getPlayer();
        SoundUtil s = new SoundUtil();
        String Message = ChatColor.stripColor(event.getMessage());
        if (Temp.compareTo("FA") == 0) {
            if (Message.compareTo(player.getName()) == 0) {
                s.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
                player.sendMessage(ChatColor.RED + "[친구] : 자기 자신을 추가할 수 없습니다!");
            } else {
                Message.replace(".", "");
                if (Bukkit.getServer().getPlayer(Message) != null)
                    new Equip_Gui().SetFriends(player, Bukkit.getServer().getPlayer(Message));
                else {
                    s.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
                    player.sendMessage(ChatColor.RED + "[친구] : 해당 플레이어를 찾을 수 없습니다!");
                }
            }
            new ETC_Gui().FriendsGUI(player, (short) 0);
            new UserData_Object().initTemp(player);
        } else if (Temp.compareTo("Structure") == 0)
            new Structure_Chat().PlayerChatrouter(event);
        else if (Temp.compareTo("Dungeon") == 0)
            new Dungeon_Chat().PlayerChatrouter(event);
    }

}
