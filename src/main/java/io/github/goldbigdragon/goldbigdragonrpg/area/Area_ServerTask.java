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

package io.github.goldbigdragon.goldbigdragonrpg.area;

import java.util.Collection;
import java.util.Iterator;

import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import io.github.goldbigdragon.goldbigdragonrpg.monster.Monster_Spawn;
import io.github.goldbigdragon.goldbigdragonrpg.util.Util_Number;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import io.github.goldbigdragon.goldbigdragonrpg.main.Main_ServerOption;
import io.github.goldbigdragon.goldbigdragonrpg.servertick.ServerTick_Main;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;

public class Area_ServerTask {
    public void AreaMobSpawn(long UTC) {
        String AreaName = ServerTick_Main.Schedule.get(UTC).getString((byte) 0);
        if (Main_ServerOption.PlayerCurrentArea.values().contains(AreaName) == false) {
            ServerTick_Main.MobSpawningAreaList.remove(AreaName);
            return;
        }
        if (ServerTick_Main.MobSpawningAreaList.contains(AreaName)) {
            YamlController YC = new YamlController(Main_Main.plugin);
            YamlManager AreaConfig = YC.getNewConfig("Area/AreaList.yml");
            if (AreaConfig.contains(ServerTick_Main.Schedule.get(UTC).getString((byte) 0) + ".MonsterSpawnRule." + ServerTick_Main.Schedule.get(UTC).getString((byte) 3))) {
                if (ServerTick_Main.Schedule.get(UTC).getCount() >= ServerTick_Main.Schedule.get(UTC).getMaxCount()) {
                    ServerTick_Main.Schedule.get(UTC).setCount(0);
                    YamlManager AreaList = YC.getNewConfig("Area/AreaList.yml");
                    String mob = null;
                    if (ServerTick_Main.Schedule.get(UTC).getString((byte) 2) == null) {
                        Object[] MobList = AreaList.getConfigurationSection(AreaName + ".Monster").getKeys(false).toArray();
                        if (MobList.length != 0)
                            mob = MobList[new Util_Number().RandomNum(0, MobList.length - 1)].toString();
                    } else
                        mob = ServerTick_Main.Schedule.get(UTC).getString((byte) 2);
                    if (Main_ServerOption.MonsterList.containsKey(mob)) {
                        Location loc = new Location(Bukkit.getServer().getWorld(ServerTick_Main.Schedule.get(UTC).getString((byte) 1)), ServerTick_Main.Schedule.get(UTC).getInt((byte) 0), ServerTick_Main.Schedule.get(UTC).getInt((byte) 1), ServerTick_Main.Schedule.get(UTC).getInt((byte) 2));
                        if (getEntitiesNearby(loc, 20) <= ServerTick_Main.Schedule.get(UTC).getInt((byte) 5)) {
                            Monster_Spawn MC = new Monster_Spawn();
                            for (short mobspawn = 0; mobspawn < ServerTick_Main.Schedule.get(UTC).getInt((byte) 4); mobspawn++)
                                MC.SpawnMob(loc.add(-0.5, -1, -0.5), mob, (byte) -1, null, (char) -1, false);
                        }
                    }
                } else {
                    ServerTick_Main.Schedule.get(UTC).setCount(ServerTick_Main.Schedule.get(UTC).getCount() + 1);
                }
                ServerTick_Main.Schedule.get(UTC).copyThisScheduleObject(UTC + 1000);
            }
        }
    }

    public int getEntitiesNearby(Location loc, double range) {
        Collection<Entity> entities = loc.getWorld().getNearbyEntities(loc, range, range, range);
        //List<Entity> entities = loc.getWorld().getEntities();
        for (Iterator<Entity> it = entities.iterator(); it.hasNext(); ) {
            Entity entity = it.next();
            if (entity.getType() == EntityType.DROPPED_ITEM)
                it.remove();
            else if (entity.getType() == EntityType.EXPERIENCE_ORB)
                it.remove();
            else if (entity.getType() == EntityType.ITEM_FRAME)
                it.remove();
            else if (entity.getType() == EntityType.FISHING_HOOK)
                it.remove();
            else if (entity.getType() == EntityType.PAINTING)
                it.remove();
            else if (entity.getType() == EntityType.ARROW)
                it.remove();
            else if (entity.getType() == EntityType.PLAYER) {
                if (Bukkit.getServer().getPlayer(entity.getName()) != null)
                    it.remove();
            }
        }
        return entities.size();
    }

    public void AreaRegenBlock(Long UTC) {
        Location loc = new Location(Bukkit.getServer().getWorld(ServerTick_Main.Schedule.get(UTC).getString((byte) 1)), ServerTick_Main.Schedule.get(UTC).getInt((byte) 0), ServerTick_Main.Schedule.get(UTC).getInt((byte) 1), ServerTick_Main.Schedule.get(UTC).getInt((byte) 2));
        loc.getWorld().getBlockAt(loc).setTypeId(ServerTick_Main.Schedule.get(UTC).getInt((byte) 3));
        loc.getWorld().getBlockAt(loc).setData((byte) ServerTick_Main.Schedule.get(UTC).getInt((byte) 4));
        ServerTick_Main.Schedule.remove(UTC);
    }

}
