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

package io.github.goldbigdragon.goldbigdragonrpg.monster.AI_Monster;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import io.github.goldbigdragon.goldbigdragonrpg.monster.AI_Creature.CreatureMonster;
import net.minecraft.server.v1_12_R1.DataWatcher;
import net.minecraft.server.v1_12_R1.DataWatcherObject;
import net.minecraft.server.v1_12_R1.DataWatcherRegistry;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayOutNamedEntitySpawn;

public class NPC {
    int entityID;
    Location loc;
    GameProfile gameprofile;

    public NPC(Player player, Location loc) {
        entityID = (int) 8759;
        gameprofile = new GameProfile(player.getUniqueId(), player.getName());
        new NMSUtils().registerEntity(player.getName(), 49 + (Byte.MAX_VALUE + 1) * 2, CreatureMonster.class);
        this.loc = loc;
    }

    public void spawn() {
        PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();
        setValue(packet, "a", entityID);
        setValue(packet, "b", gameprofile.getId());
        setValue(packet, "c", loc.getX());
        setValue(packet, "d", loc.getY());
        setValue(packet, "e", loc.getZ());
        setValue(packet, "f", (byte) ((int) (loc.getYaw() * 256.0F / 360.0F)));
        setValue(packet, "g", (byte) ((int) (loc.getPitch() * 256.0F / 360.0F)));

        DataWatcher w = new DataWatcher(null);
        w.register(new DataWatcherObject<>(6, DataWatcherRegistry.c), (float) 20);
        w.register(new DataWatcherObject<>(10, DataWatcherRegistry.a), (byte) 127);
        setValue(packet, "h", w);
        sendPacket(packet);
    }

    public void setValue(Object obj, String name, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
        }

    }

    public Object getValue(Object obj, String name) {
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
        }
        return null;
    }

    public void sendPacket(Packet<?> packet, Player player) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public void sendPacket(Packet<?> packet) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendPacket(packet, player);
        }
    }
}
