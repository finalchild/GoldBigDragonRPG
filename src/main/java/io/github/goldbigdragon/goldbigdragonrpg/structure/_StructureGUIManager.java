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

import io.github.goldbigdragon.goldbigdragonrpg.user.UserData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;

public class _StructureGUIManager {
    //Structure GUI Click Unique Number = 0d
    //개체 관련 GUI의 고유 번호는 0d입니다.

    //If you want add this system, just Put it in for Main_InventoryClcik!
    //당신이 개체 관련 GUI 기능을 넣고싶을땐, 그냥 Main_InventoryClick 클래스 안에 넣으세요!

    public void InventoryClickRouter(InventoryClickEvent event, String InventoryName) {
        String Striped = ChatColor.stripColor(event.getInventory().getName().toString());
        if (event.getInventory().getType() == InventoryType.CHEST) {
            if (!(Striped.compareTo("보낼 아이템") == 0
            ))
                event.setCancelled(true);
        }
        if (InventoryName.compareTo("우편함") == 0)
            new Struct_PostBox().PostBoxMainGUIClick(event);
        else if (InventoryName.compareTo("보낼 아이템") == 0)
            new Struct_PostBox().ItemPutterGUIClick(event);
        else if (InventoryName.contains("게시판")) {
            if (InventoryName.contains("거래")) {
                if (InventoryName.contains("메뉴"))
                    new Struct_TradeBoard().SelectTradeTypeGUIClick(event);
                else if (InventoryName.contains("설정"))
                    new Struct_TradeBoard().TradeBoardSettingGUIClick(event);
                else
                    new Struct_TradeBoard().TradeBoardMainGUIClick(event);
            } else {
                if (InventoryName.contains("설정"))
                    new Struct_Board().BoardSettingGUIClick(event);
                else
                    new Struct_Board().BoardMainGUIClick(event);
            }
        } else if (InventoryName.compareTo("판매할 아이템을 고르세요") == 0)
            new Struct_TradeBoard().SelectSellItemGUIClick(event);
        else if (InventoryName.compareTo("구매할 아이템을 고르세요") == 0)
            new Struct_TradeBoard().SelectBuyItemGUIClick(event);
        else if (InventoryName.contains("일반 아이템"))
            new Struct_TradeBoard().SelectNormalItemGUIClick(event);
        else if (InventoryName.compareTo("받고싶은 아이템을 고르세요") == 0)
            new Struct_TradeBoard().SelectExchangeItem_YouGUIClick(event);
        else if (InventoryName.compareTo("내가 줄 아이템을 고르세요") == 0)
            new Struct_TradeBoard().SelectExchangeItem_MyGUIClick(event);
        else if (InventoryName.compareTo("모닥불") == 0)
            new Struct_CampFire().CampFireGUIClick(event);
    }

    public void InventoryCloseRouter(InventoryCloseEvent event, String InventoryName) {
        UserData u = new UserData();
        Player player = (Player) event.getPlayer();

        if (InventoryName.compareTo("보낼 아이템") == 0)
            new Struct_PostBox().ItemPutterGUIClose(event);
        else if (InventoryName.compareTo("판매할 아이템을 고르세요") == 0 || InventoryName.compareTo("구매할 아이템을 고르세요") == 0) {
            if (u.getItemStack((Player) event.getPlayer()) == null)
                u.clearAll(player);
        } else if (InventoryName.contains("일반 아이템"))
            u.clearAll(player);
    }


    public void ClickRouting(InventoryClickEvent event, String SubjectCode) {
        if (SubjectCode.compareTo("00") == 0)//전체 개체 목록
            new Structure_Gui().StructureListGUIClick(event);
        else if (SubjectCode.compareTo("01") == 0)//개체 타입 선택
            new Structure_Gui().SelectStructureTypeGUIClick(event);
        else if (SubjectCode.compareTo("02") == 0)//개체 방향 선택
            new Structure_Gui().SelectStructureDirectionGUIClick(event);
        else if (SubjectCode.compareTo("03") == 0)//우편함 메인
            new Struct_PostBox().PostBoxMainGUIClick(event);
        else if (SubjectCode.compareTo("04") == 0)//우편함 아이템 배송
            new Struct_PostBox().ItemPutterGUIClick(event);
        else if (SubjectCode.compareTo("05") == 0)//게시판 목록
            new Struct_Board().BoardMainGUIClick(event);
        else if (SubjectCode.compareTo("06") == 0)//게시판 설정
            new Struct_Board().BoardSettingGUIClick(event);
        else if (SubjectCode.compareTo("07") == 0)//거래 게시판 목록
            new Struct_TradeBoard().TradeBoardMainGUIClick(event);
        else if (SubjectCode.compareTo("08") == 0)//거래 게시판 설정
            new Struct_TradeBoard().TradeBoardSettingGUIClick(event);
        else if (SubjectCode.compareTo("09") == 0)//거래 게시판 메뉴
            new Struct_TradeBoard().SelectTradeTypeGUIClick(event);
        else if (SubjectCode.compareTo("0a") == 0)//판매할 아이템 선택
            new Struct_TradeBoard().SelectSellItemGUIClick(event);
        else if (SubjectCode.compareTo("0b") == 0)//구매할 아이템 선택
            new Struct_TradeBoard().SelectBuyItemGUIClick(event);
        else if (SubjectCode.compareTo("0c") == 0)//교환시 내가 받을 아이템 선택
            new Struct_TradeBoard().SelectExchangeItem_YouGUIClick(event);
        else if (SubjectCode.compareTo("0d") == 0)//교환시 내가 줄 아이템 선택
            new Struct_TradeBoard().SelectExchangeItem_MyGUIClick(event);
        else if (SubjectCode.compareTo("0e") == 0)//일반 아이템 목록
            new Struct_TradeBoard().SelectNormalItemGUIClick(event);
        else if (SubjectCode.compareTo("0f") == 0)//모닥불
            new Struct_CampFire().CampFireGUIClick(event);
    }

    public void CloseRouting(InventoryCloseEvent event, String SubjectCode) {
        if (SubjectCode.compareTo("04") == 0)//우편함 아이템 배송
            new Struct_PostBox().ItemPutterGUIClose(event);
    }
}
