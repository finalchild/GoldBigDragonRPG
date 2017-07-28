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

package io.github.goldbigdragon.goldbigdragonrpg.structure;

import java.util.Arrays;

import io.github.goldbigdragon.goldbigdragonrpg.effect.SoundUtil;
import io.github.goldbigdragon.goldbigdragonrpg.util.GuiUtil;
import io.github.goldbigdragon.goldbigdragonrpg.util.Util_Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class Struct_CampFire extends GuiUtil {

    public void CampFireMainGUI(Player player, String BoardCode) {
        String UniqueCode = "§0§0§d§0§f§r";
        BoardCode = BoardCode.replace("§", "&");
        Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "§c§0모닥불");
        Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "불 끄기", 326, 0, 1, Arrays.asList(ChatColor.WHITE + "모닥불의 불을 끕니다."), 3, inv);
        Stack2(ChatColor.RED + "" + ChatColor.BOLD + "불 지피기", 259, 0, 1, Arrays.asList(ChatColor.WHITE + "모닥불에 불을 지핍니다.", "", ChatColor.YELLOW + "[막대기 10개 필요]", ChatColor.BLACK + BoardCode), 5, inv);
        player.openInventory(inv);
    }

    public String CreateCampFire(int LineNumber, String StructureCode, byte Direction) {
        if (LineNumber <= 36)
            return "/summon ArmorStand ~0 ~0.36 ~0 {CustomName:\"" + StructureCode + "\",Invisible:1,ShowArms:1,NoBasePlate:1,NoGravity:1,Rotation:[" + (LineNumber * 10) + "f,0.0f],HandItems:[{id:stick,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[40f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
        else if (LineNumber == 37)
            return "/summon ArmorStand ~0 ~0.56 ~0 {CustomName:\"" + StructureCode + "\",Invisible:1,ShowArms:1,NoBasePlate:1,NoGravity:1}";
        else if (LineNumber == 38)
            return "/summon ArmorStand ~0 ~0.56 ~0 {CustomName:\"" + StructureCode + "\",CustomNameVisible:1,Invisible:1,ShowArms:1,NoBasePlate:1,NoGravity:1}";
        else
            return "null";
    }

    public void CampFireGUIClick(InventoryClickEvent event) {
                Player player = (Player) event.getWhoClicked();
        String CampFireName = ChatColor.stripColor(event.getInventory().getItem(5).getItemMeta().getLore().get(3)).replace("&", "§");

        if (event.getSlot() == 3 || event.getSlot() == 5) {
            if (event.getSlot() == 5) {
                ItemStack item = new MaterialData(280, (byte) 0).toItemStack(1);
                if (!new Util_Player().deleteItem(player, item, 10)) {
                    SoundUtil.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
                    player.sendMessage(ChatColor.RED + "[SYSTEM] : 불을 지피기 위해 필요한 막대기 개수가 모자랍니다!");
                    return;
                } else
                    SoundUtil.playSound(player, Sound.ITEM_FLINTANDSTEEL_USE, 1.0F, 1.8F);
            } else
                SoundUtil.playSound(player, Sound.BLOCK_FIRE_EXTINGUISH, 1.0F, 1.0F);

            Object[] e = player.getLocation().getWorld().getNearbyEntities(player.getLocation(), 6, 6, 6).toArray();
            for (int count = 0; count < e.length; count++)
                if (((Entity) e[count]).getType() == EntityType.ARMOR_STAND)
                    if (((Entity) e[count]).getCustomName() != null)
                        if (((Entity) e[count]).getCustomName().compareTo(CampFireName) == 0) {
                            if (event.getSlot() == 3)
                                ((Entity) e[count]).setFireTicks(0);
                            if (event.getSlot() == 5)
                                ((Entity) e[count]).setFireTicks(25565);
                        }
        }
    }
}
