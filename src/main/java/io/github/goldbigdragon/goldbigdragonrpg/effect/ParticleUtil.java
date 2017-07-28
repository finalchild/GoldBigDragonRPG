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

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ParticleUtil {
    public static void PL(Location loc, org.bukkit.Effect effect, int Direction) {
        World world = loc.getWorld();
        world.playEffect(loc.add(0.5, 0.5, 0.5), effect, Direction);
    }

    public static void PLC(Location loc, org.bukkit.Effect effect, int Direction) {
        World world = loc.getWorld();
        world.playEffect(loc, effect, Direction);
    }

    public static void PPL(Player player, Location loc, org.bukkit.Effect effect, int Direction) {
        player.playEffect(loc, effect, Direction);
    }

    //relativeLocation - Player Location
    public static void RLPL(Player player, double relativeX, double relativeY, double relativeZ, org.bukkit.Effect effect, int Direction) {
        player.playEffect(player.getLocation().add(relativeX, relativeY, relativeZ), effect, Direction);
    }

    //RelativeLocation - Player Location Rotate
    public static void RLPLR(Player player, double relativeX, double relativeY, double relativeZ, org.bukkit.Effect effect, int Direction, int Rotate) {
        switch (Rotate) {
            case 0://0도 회전(있는 그대로)
                player.playEffect(player.getLocation().add(relativeX, relativeY, relativeZ), effect, Direction);
                return;
            case 1://90도 회전(시계 방향으로 90도 회전)
                player.playEffect(player.getLocation().add(relativeZ, relativeY, relativeX), effect, Direction);
                return;
            case 2://180도 회전(시계 방향으로 180도 회전)
                player.playEffect(player.getLocation().add(relativeX, relativeY, -1 * relativeZ), effect, Direction);
                return;
            case 3://270도 회전(시계 방향으로 270도 회전)
                player.playEffect(player.getLocation().add(-1 * relativeZ, relativeY, relativeX), effect, Direction);
                return;
            case 4://대각선 형태의 파티클일 경우 - 360도 회전(시계 방향으로 360도 회전)
                player.playEffect(player.getLocation().add(-1 * relativeZ, relativeY, -1 * relativeX), effect, Direction);
        }
    }

    //RelativeLocation - Player Location Reflect
    public static void RLPLRR(Player player, double relativeX, double relativeY, double relativeZ, org.bukkit.Effect effect, int Direction, int Reflect) {
        switch (Reflect) {
            case 0://비대칭
                player.playEffect(player.getLocation().add(-1 * relativeX, relativeY, relativeZ), effect, Direction);
                return;
            case 1://좌우 대칭
                player.playEffect(player.getLocation().add(relativeZ, relativeY, relativeX), effect, Direction);
                return;
            case 2://전후 대칭
                player.playEffect(player.getLocation().add(relativeX, relativeY, -1 * relativeZ), effect, Direction);
                return;
            case 3://전후 대칭
                player.playEffect(player.getLocation().add(-1 * relativeZ, relativeY, -1 * relativeX), effect, Direction);
        }
    }
}
