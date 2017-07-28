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

package io.github.goldbigdragon.goldbigdragonrpg.user;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class _UserGUIManager {
    //User GUI Click Unique Number = 00
    //유저 관련 GUI의 고유 번호는 00입니다.

    //If you want add this system, just Put it in for Main_InventoryClcik!
    //당신이 유저 관련 GUI 기능을 넣고싶을땐, 그냥 Main_InventoryClick 클래스 안에 넣으세요!

    public void ClickRouting(InventoryClickEvent event, String SubjectCode) {
        if (SubjectCode.compareTo("00") == 0)//스텟
            new Stats_Gui().StatusInventoryclick(event);
        if (SubjectCode.compareTo("01") == 0)//옵션
            new Option_Gui().optionInventoryclick(event);
        if (SubjectCode.compareTo("02") == 0)//기타
            new ETC_Gui().ETCInventoryclick(event);
        if (SubjectCode.compareTo("03") == 0)//가이드
            new ETC_Gui().GuideInventoryclick(event);
        if (SubjectCode.compareTo("04") == 0)//친구
            new ETC_Gui().FriendsGUIclick(event);
        if (SubjectCode.compareTo("05") == 0)//친구 신청 목록
            new ETC_Gui().WaittingFriendsGUIclick(event);
        if (SubjectCode.compareTo("06") == 0)//장비 구경
            new Equip_Gui().EquipSeeInventoryclick(event);
        if (SubjectCode.compareTo("07") == 0)//교환
        {
            new Equip_Gui().ExchangeInventoryclick(event);
            new Equip_Gui().ExchangeGUIclick(event);
        }
    }

    public void CloseRouting(InventoryCloseEvent event, String SubjectCode) {
        if (SubjectCode.compareTo("07") == 0)//교환
            new Equip_Gui().ExchangeGUI_Close(event);
    }
}
