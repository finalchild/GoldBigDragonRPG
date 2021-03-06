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

package io.github.goldbigdragon.goldbigdragonrpg.party;

import org.bukkit.event.inventory.InventoryClickEvent;

public class _PartyGUIManager {
    //Party GUI Click Unique Number = 04
    //파티 관련 GUI의 고유 번호는 04입니다.

    //If you want add this system, just Put it in for Main_InventoryClcik!
    //당신이 파티 관련 GUI 기능을 넣고싶을땐, 그냥 Main_InventoryClick 클래스 안에 넣으세요!

    public void ClickRouting(InventoryClickEvent event, String SubjectCode) {
        if (SubjectCode.compareTo("00") == 0)//파티 메인 GUI
            new Party_Gui().PartyGUI_MainClick(event);
        else if (SubjectCode.compareTo("01") == 0)//파티 목록 GUI
            new Party_Gui().PartyListGUIClick(event);
        else if (SubjectCode.compareTo("02") == 0)//파티 멤버 목록 GUI
            new Party_Gui().PartyMemberInformationGUIClick(event);
    }
}
