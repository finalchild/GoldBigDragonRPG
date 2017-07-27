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

package GBD_RPG.ServerTick;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;

public class ServerTask_Effect
{
	public void PlaySoundEffect(ServerTick_Object STSO)
	{
		if(STSO.getCount()<STSO.getMaxCount())
		{
			Location loc = new Location(Bukkit.getServer().getWorld(STSO.getString((byte)1)),STSO.getInt((byte)0),STSO.getInt((byte)1),STSO.getInt((byte)2));
			switch(Integer.parseInt(STSO.getString((byte)0).charAt(STSO.getCount())+""))
			{
			case 0:
				new GBD_RPG.Effect.Effect_Sound().SL(loc, Sound.ENTITY_IRONGOLEM_ATTACK, 2.0F, 0.5F);
				break;
			case 1:
				new GBD_RPG.Effect.Effect_Sound().SL(loc, Sound.ENTITY_IRONGOLEM_HURT, 2.0F, 0.5F);
				break;
			}
			STSO.setCount(STSO.getCount()+1);
			STSO.copyThisScheduleObject(ServerTick_Main.nowUTC+(STSO.getInt((byte)5)*100));
		}
	}
}
