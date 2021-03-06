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

package io.github.goldbigdragon.goldbigdragonrpg.effect;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.minecraft.server.v1_12_R1.PacketPlayOutHeldItemSlot;
import net.minecraft.server.v1_12_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_12_R1.PacketPlayOutOpenWindow;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_12_R1.PlayerConnection;

public class PacketUtil {
    public static void sendTitleSubTitle(Player player, String title, String subtitle, byte FadeInTime, byte ShowTime, byte FadeOutTime) {
        CraftPlayer p = (CraftPlayer) player;
        PlayerConnection c = p.getHandle().playerConnection;
        IChatBaseComponent TitleText = ChatSerializer.b(title);
        IChatBaseComponent SubtitleText = ChatSerializer.b(subtitle);
        Packet Length = new PacketPlayOutTitle(EnumTitleAction.TIMES, TitleText, FadeInTime * 20, ShowTime * 20, FadeOutTime * 20);
        Packet TitlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, TitleText, FadeInTime * 20, ShowTime * 20, FadeOutTime * 20);
        Packet SubtitlePacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, SubtitleText, FadeInTime * 20, ShowTime * 20, FadeOutTime * 20);
        c.sendPacket(TitlePacket);
        c.sendPacket(Length);
        c.sendPacket(SubtitlePacket);
        c.sendPacket(SubtitlePacket);
    }

    public static void sendTitle(Player player, String title, byte FadeInTime, byte ShowTime, byte FadeOutTime) {
        CraftPlayer p = (CraftPlayer) player;
        PlayerConnection c = p.getHandle().playerConnection;
        IChatBaseComponent TitleText = ChatSerializer.b(title);
        Packet Length = new PacketPlayOutTitle(EnumTitleAction.TIMES, TitleText, FadeInTime * 20, ShowTime * 20, FadeOutTime * 20);
        Packet TitlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, TitleText, FadeInTime * 20, ShowTime * 20, FadeOutTime * 20);
        c.sendPacket(TitlePacket);
        c.sendPacket(Length);
    }

    public static void sendTitleAllPlayers(String message) {
        Object[] PlayerList = Bukkit.getServer().getOnlinePlayers().toArray();
        for (int count = 0; count < PlayerList.length; count++)
            if (((Player) PlayerList[count]).isOnline())
                sendTitle(((Player) PlayerList[count]), message, (byte) 1, (byte) 5, (byte) 1);
    }

    public static void sendActionBarAllPlayers(String message) {
        Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
        Player[] player = new Player[playerlist.size()];
        playerlist.toArray(player);
        for (int count = 0; count < player.length; count++)
            sendActionBar(player[count], message);
    }

    public static void sendActionBar(Player player, String message) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.CHAT);
        packet.getChatComponents().write(0, WrappedChatComponent.fromText(message));
        packet.getChatTypes().write(0, EnumWrappers.ChatType.GAME_INFO);
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void switchHotbarSlot(Player p, int slot) {
        PacketPlayOutHeldItemSlot ppoc = new PacketPlayOutHeldItemSlot(slot);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
    }

    public static void changeItemSlot(Player p, int slot) {
        Packet slotChange = new PacketPlayOutHeldItemSlot(slot);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(slotChange);
    }

    public static void openOwnInventory(Player p) {
        /*
            minecraft:chest
			minecraft:crafting_table
			minecraft:furnace
			minecraft:dispenser
			minecraft:enchanting_table
			minecraft:brewing_stand
			minecraft:villager
			minecraft:beacon
			minecraft:anvil
			minecraft:hopper
			minecraft:dropper
			EntityHorse
		 */
        Packet slotChange = new PacketPlayOutOpenWindow(1, "PLAYER", IChatBaseComponent.ChatSerializer.a("인벤토리"), 63, 0);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(slotChange);
    }

    public static void SpawnHallucinations(Player player, Player Hallucination) {
        PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn(((CraftPlayer) Hallucination).getHandle());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(spawn);
    }

}
