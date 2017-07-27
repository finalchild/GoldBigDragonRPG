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

package GBD_RPG.Util;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
//import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

public class Util_GUI
{
	public void Stack(String Display, int ID, int DATA,  int Stack, List<String> Lore,  int Loc, Inventory inventory)
	{
		ItemStack Icon = new MaterialData(ID, (byte) DATA).toItemStack(Stack);
		ItemMeta Icon_Meta = Icon.getItemMeta();
		Icon_Meta.setDisplayName(Display);
		Icon_Meta.setLore(Lore);
		Icon.setItemMeta(Icon_Meta);
		inventory.setItem(Loc, Icon);
		return;
	}
	
	public void Stack2(String Display, int ID,  int DATA,  int Stack, List<String> Lore,  int Loc, Inventory inventory)
	{
		ItemStack Icon = new MaterialData(ID, (byte) DATA).toItemStack(Stack);
		ItemMeta Icon_Meta = Icon.getItemMeta();
		Icon_Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		Icon_Meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		Icon_Meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		Icon_Meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
		Icon_Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		Icon_Meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		Icon_Meta.addItemFlags();
		Icon_Meta.spigot().setUnbreakable(true);
		Icon_Meta.setDisplayName(Display);
		Icon_Meta.setLore(Lore);
		Icon.setItemMeta(Icon_Meta);
		inventory.setItem(Loc, Icon);
		return;
	}
	
	public ItemStack getPlayerSkull(String Display,  int Stack, List<String> Lore, String PlayerName)
	{
		ItemStack i = new ItemStack(Material.SKULL_ITEM, Stack);
		i.setDurability((short)3);
	    SkullMeta meta = (SkullMeta)i.getItemMeta();
	    meta.setOwner(PlayerName);
	    meta.setDisplayName(Display);
	    meta.setLore(Lore);
	    i.setItemMeta(meta);
	    return i;
	}
	
	public void ItemStackStack(ItemStack Item,  int Loc, Inventory inventory)
	{
		inventory.setItem(Loc, Item);
		return;
	}

	public ItemStack getItemStack(String Display, int ID,  int DATA, int Stack, List<String> Lore)
	{
		ItemStack Icon = new MaterialData(ID, (byte) DATA).toItemStack(Stack);
		ItemMeta Icon_Meta = Icon.getItemMeta();
		Icon_Meta.setDisplayName(Display);
		Icon_Meta.setLore(Lore);
		Icon.setItemMeta(Icon_Meta);
		return Icon;
	}
	
}
