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

package io.github.goldbigdragon.goldbigdragonrpg.monster.AI_Creature;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.UUID;

public class NPC {

    private int entityId;
    private Location location;
    private WrappedGameProfile gameProfile;

    private static final Random random = new Random();

    public NPC(String name, Location location) {
        entityId = random.nextInt(1000) + 2000;
        gameProfile = new WrappedGameProfile(UUID.randomUUID(), name);
        changeSkin();
        this.location = location.clone();
    }

    public void changeSkin() {
        String value = "eyJ0aW1lc3RhbXAiOjE0NDI4MzY1MTU1NzksInByb2ZpbGVJZCI6IjkwZWQ3YWY0NmU4YzRkNTQ4MjRkZTc0YzI1MTljNjU1IiwicHJvZmlsZU5hbWUiOiJDb25DcmFmdGVyIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8xMWNlZDMzMjNmYjczMmFjMTc3MTc5Yjg5NWQ5YzJmNjFjNzczZWYxNTVlYmQ1Y2M4YzM5NTZiZjlhMDlkMTIifX19";
        String signature = "tFGNBQNpxNGvD27SN7fqh3LqNinjJJFidcdF8LTRHOdoMNXcE5ezN172BnDlRsExspE9X4z7FPglqh/b9jrLFDfQrdqX3dGm1cKjYbvOXL9BO2WIOEJLTDCgUQJC4/n/3PZHEG2mVADc4v125MFYMfjzkznkA6zbs7w6z8f7pny9eCWNXPOQklstcdc1h/LvflnR+E4TUuxCf0jVsdT5AZsUYIsJa6fvr0+vItUXUdQ3pps0zthObPEnBdLYMtNY3G6ZLGVKcSGa/KRK2D/k69fmu/uTKbjAWtniFB/sdO0VNhLuvyr/PcZVXB78l1SfBR88ZMiW6XSaVqNnSP+MEfRkxgkJWUG+aiRRLE8G5083EQ8vhIle5GxzK68ZR48IrEX/JwFjALslCLXAGR05KrtuTD3xyq2Nut12GCaooBEhb46sipWLq4AXI9IpJORLOW8+GvY+FcDwMqXYN94juDQtbJGCQo8PX670YjbmVx7+IeFjLJJTZotemXu1wiQmDmtAAmug4U5jgMYIJryXMitD7r5pEop/cw42JbCO2u0b5NB7sI/mr4OhBKEesyC5usiARzuk6e/4aJUvwQ9nsiXfeYxZz8L/mh6e8YPJMyhVkFtblbt/4jPe0bs3xSUXO9XrDyhy9INC0jlLT22QjNzrDkD8aiGAopVvfnTTAug=";
        gameProfile.getProperties().put("textures", new WrappedSignedProperty("textures", value, signature));
    }


    public void animation(int animation) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ANIMATION);
        packet.getIntegers()
                .write(0, entityId)
                .write(1, animation);

        sendPacket(packet);
    }

    public void status(int status) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_STATUS);
        packet.getIntegers().write(0, entityId);
        packet.getBytes().write(0, (byte) status);

        sendPacket(packet);
    }

    public void spawn() {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.NAMED_ENTITY_SPAWN);
        packet.getIntegers().write(0, entityId);
        packet.getUUIDs().write(0, gameProfile.getUUID());
        packet.getDoubles()
                .write(0, location.getX())
                .write(1, location.getY())
                .write(2, location.getZ());
        packet.getBytes()
                .write(0, getFixRotation(location.getYaw()))
                .write(1, getFixRotation(location.getPitch()));
        packet.getDataWatcherModifier().write(0, new WrappedDataWatcher());
        addToTablist();
        sendPacket(packet);
        headRotation(location.getYaw(), location.getPitch());
    }

    public void teleport(Location location) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_TELEPORT);
        packet.getIntegers().write(0, entityId);
        packet.getDoubles()
                .write(0, location.getX())
                .write(1, location.getY())
                .write(2, location.getZ());
        packet.getBytes()
                .write(0, getFixRotation(location.getYaw()))
                .write(1, getFixRotation(location.getPitch()));
        packet.getBooleans().write(0, true);

        sendPacket(packet);
        headRotation(location.getYaw(), location.getPitch());
        this.location = location.clone();
    }

    public void headRotation(float yaw, float pitch) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_LOOK);
        packet.getIntegers().write(0, entityId);
        packet.getBytes()
                .write(0, getFixRotation(yaw))
                .write(1, getFixRotation(pitch));
        packet.getBooleans()
                .write(0, true)
                .write(1, true);
        PacketContainer packetHead = new PacketContainer(PacketType.Play.Server.ENTITY_HEAD_ROTATION);
        packetHead.getIntegers().write(0, entityId);
        packetHead.getBytes().write(0, getFixRotation(yaw));

        sendPacket(packet);
        sendPacket(packetHead);
    }

    public void destroy() {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
        packet.getIntegerArrays().write(0, new int[]{entityId});
        rmvFromTablist();
        sendPacket(packet);
    }

    public void addToTablist() {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
        packet.getPlayerInfoDataLists().write(0, Lists.newArrayList(new PlayerInfoData(gameProfile, 1, EnumWrappers.NativeGameMode.NOT_SET, WrappedChatComponent.fromText(gameProfile.getName()))));

        sendPacket(packet);
    }

    public void rmvFromTablist() {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
        packet.getPlayerInfoDataLists().write(0, Lists.newArrayList(new PlayerInfoData(gameProfile, 1, EnumWrappers.NativeGameMode.NOT_SET, WrappedChatComponent.fromText(gameProfile.getName()))));

        sendPacket(packet);
    }

    public static int getFixLocation(double pos) {
        return (int) (pos * 32);
    }

    public static byte getFixRotation(float yawpitch) {
        return (byte) (yawpitch * 256f / 360f);
    }

    public static void sendPacket(PacketContainer packet) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            try {
                protocolManager.sendServerPacket(onlinePlayer, packet);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }
}
