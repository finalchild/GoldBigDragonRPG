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

package io.github.goldbigdragon.goldbigdragonrpg.customitem;

import org.bukkit.event.inventory.InventoryClickEvent;

public class _ItemGUIManager {
    //Item GUI Click Unique Number = 03
    //아이템 관련 GUI의 고유 번호는 03입니다.

    //If you want add this system, just Put it in for Main_InventoryClcik!
    //당신이 아이템 관련 GUI 기능을 넣고싶을땐, 그냥 Main_InventoryClick 클래스 안에 넣으세요!

    public void ClickRouting(InventoryClickEvent event, String SubjectCode) {
        if (SubjectCode.compareTo("00") == 0)//아이템 목록
            new CustomItem_Gui().ItemListInventoryclick(event);
        else if (SubjectCode.compareTo("01") == 0)//아이템 설정
            new CustomItem_Gui().NewItemGUIclick(event);
        else if (SubjectCode.compareTo("02") == 0)//아이템 직업 제한
            new CustomItem_Gui().JobGUIClick(event);
        else if (SubjectCode.compareTo("03") == 0)//소모성 아이템 목록
            new UseableItem_Gui().UseableItemListGUIClick(event);
        else if (SubjectCode.compareTo("04") == 0)//소모성 아이템 타입 선택
            new UseableItem_Gui().ChooseUseableItemTypeGUIClick(event);
        else if (SubjectCode.compareTo("05") == 0)//소모성 아이템 설정
            new UseableItem_Gui().NewUseableItemGUIclick(event);
        else if (SubjectCode.compareTo("06") == 0)//스킬북 스킬 등록
            new UseableItem_Gui().SelectSkillGUIClick(event);
    }
}
