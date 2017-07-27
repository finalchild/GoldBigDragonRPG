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

package io.github.goldbigdragon.goldbigdragonrpg.servertick;

import io.github.goldbigdragon.goldbigdragonrpg.effect.Effect_Sound;
import io.github.goldbigdragon.goldbigdragonrpg.structure.Structure_Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class ServerTask_Server {
    public void CreateStructureMain(Long UTC) {
        ServerTick_Object STSO = ServerTick_Main.Schedule.get(UTC);
        if (STSO.getInt((byte) 3) == 0) {
            if (ServerTick_Main.Schedule.get(UTC).getCount() == 0)
                CreateCommandBlock(new Location(Bukkit.getServer().getWorld(STSO.getString((byte) 1)), STSO.getInt((byte) 0), STSO.getInt((byte) 1), STSO.getInt((byte) 2)));
            String CMD = new Structure_Main().getCMD(STSO.getString((byte) 0), STSO.getCount(), STSO.getString((byte) 2), STSO.getString((byte) 3));
            if (CMD.compareTo("null") == 0) {
                ServerTick_Main.Schedule.remove(UTC);
                ServerTick_Main.ServerTask = "null";

                Location CommandLoc = new Location(Bukkit.getServer().getWorld(STSO.getString((byte) 1)), STSO.getInt((byte) 0), STSO.getInt((byte) 1), STSO.getInt((byte) 2));
                Block OriginalBlock = CommandLoc.getBlock();
                OriginalBlock.setTypeId(STSO.getInt((byte) 6));
                OriginalBlock.setData((byte) STSO.getInt((byte) 7));
                SetBlock(CommandLoc, OriginalBlock);

                if (STSO.getString((byte) 0).compareTo("CF") == 0) {
                    Object[] e = OriginalBlock.getLocation().getWorld().getNearbyEntities(OriginalBlock.getLocation(), 2, 2, 2).toArray();
                    for (int count = 0; count < e.length; count++)
                        if (((Entity) e[count]).getType() == EntityType.ARMOR_STAND)
                            if (((Entity) e[count]).getCustomName() != null)
                                if (((Entity) e[count]).getCustomName().compareTo(STSO.getString((byte) 2)) == 0)
                                    ((Entity) e[count]).setFireTicks(25565);
                }


                ServerTick_Main.Schedule.remove(UTC);
                return;
            } else {
                Location CommandBlockLoc = new Location(Bukkit.getServer().getWorld(STSO.getString((byte) 1)), STSO.getInt((byte) 0), STSO.getInt((byte) 1), STSO.getInt((byte) 2));
                Block Command = CommandBlockLoc.getBlock();
                CommandBlock CB = (CommandBlock) Command.getState();
                CB.setCommand(CMD);
                CB.update();
                CommandBlockLoc.setY(CommandBlockLoc.getY() - 1);
                CreateRedStone(CommandBlockLoc);
                new Effect_Sound().SL(CommandBlockLoc, Sound.BLOCK_STONE_STEP, 1.0F, 1.0F);

                STSO.setInt((byte) 3, 1);
                ServerTick_Main.Schedule.remove(UTC);
                for (short count = 0; count < 32767; count++) {
                    if (ServerTick_Main.Schedule.containsKey(UTC + (100 + count)) == false) {
                        ServerTick_Main.Schedule.put(UTC + (100 + count), STSO);
                        break;
                    }
                }
            }
        } else {
            Location RedStoneLoc = new Location(Bukkit.getServer().getWorld(STSO.getString((byte) 1)), STSO.getInt((byte) 0), STSO.getInt((byte) 1), STSO.getInt((byte) 2));
            RedStoneLoc.setY(RedStoneLoc.getY() - 1);
            Block OriginalBlock = RedStoneLoc.getBlock();
            OriginalBlock.setTypeId(STSO.getInt((byte) 6));
            OriginalBlock.setData((byte) STSO.getInt((byte) 7));
            SetBlock(RedStoneLoc, OriginalBlock);
            STSO.setInt((byte) 3, 0);
            STSO.setCount(STSO.getCount() + 1);
            ServerTick_Main.Schedule.remove(UTC);
            for (short count = 0; count < 32767; count++) {
                if (ServerTick_Main.Schedule.containsKey(UTC + (100 + count)) == false) {
                    ServerTick_Main.Schedule.put(UTC + (100 + count), STSO);
                    break;
                }
            }
        }
    }

    public void CreateCommandBlock(Location loc) {
        Block Command = loc.getBlock();
        Command.setType(Material.COMMAND);
        Command.setData((byte) 0);
        return;
    }

    public void CreateRedStone(Location loc) {
        Block RedStone = loc.getBlock();
        RedStone.setType(Material.REDSTONE_BLOCK);
        RedStone.setData((byte) 0);
        return;
    }

    public void SetBlock(Location loc, Block OriginalBlock) {
        Block Original = loc.getBlock();
        Original.setType(OriginalBlock.getType());
        Original.setData(OriginalBlock.getData());
        return;
    }
}
