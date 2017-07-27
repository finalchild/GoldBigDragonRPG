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

package GBD_RPG.Admin;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import GBD_RPG.User.UserData_Object;
import GBD_RPG.Util.Util_GUI;

public class WorldCreate_GUI extends Util_GUI {
    public void WorldCreateGUIMain(Player player) {
        String UniqueCode = "§0§0§1§1§b§r";
        Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "§0월드 선택");

        Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "일반 월드", 6, 0, 1, Arrays.asList(ChatColor.GRAY + "일반적인 월드를 생성합니다."), 2, inv);
        Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "완전한 평지", 2, 0, 1, Arrays.asList(ChatColor.GRAY + "완전한 평지를 가진 월드를 생성합니다."), 4, inv);
        Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "넓은 바이옴", 175, 4, 1, Arrays.asList(ChatColor.GRAY + "바이옴이 넓은 월드를 생성합니다."), 6, inv);

        Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323, 0, 1, Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 0, inv);
        Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324, 0, 1, Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 8, inv);
        player.openInventory(inv);
    }

    public void WorldCreateGUIClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();

        GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();

        if (slot == 8)//나가기
        {
            s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
            player.closeInventory();
        } else {
            s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
            if (slot == 0)//이전 목록
                new OPbox_GUI().OPBoxGUI_Main(player, (byte) 2);
            else {
                UserData_Object u = new UserData_Object();
                u.setType(player, "WorldCreater");
                u.setString(player, (byte) 2, "WorldCreate");
                player.closeInventory();
                if (slot == 2)//일반 월드 생성
                    u.setString(player, (byte) 3, "NORMAL");
                else if (slot == 4)//평지 월드 생성
                    u.setString(player, (byte) 3, "FLAT");
                else if (slot == 6)//넓은 바이옴 월드 생성
                    u.setString(player, (byte) 3, "LARGE_BIOMES");

                player.sendMessage(ChatColor.GOLD + "[월드 생성] : 새로운 월드 이름을 작성 해 주세요!");
            }
        }
    }
}
