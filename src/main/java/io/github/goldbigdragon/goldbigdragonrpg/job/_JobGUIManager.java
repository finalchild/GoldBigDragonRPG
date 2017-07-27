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

package io.github.goldbigdragon.goldbigdragonrpg.job;

import org.bukkit.event.inventory.InventoryClickEvent;

public class _JobGUIManager {
    //Job GUI Click Unique Number = 06
    //직업 관련 GUI의 고유 번호는 06입니다.

    //If you want add this system, just Put it in for Main_InventoryClcik!
    //당신이 직업 관련 GUI 기능을 넣고싶을땐, 그냥 Main_InventoryClick 클래스 안에 넣으세요!

    public void ClickRouting(InventoryClickEvent event, String SubjectCode) {
        if (SubjectCode.compareTo("00") == 0)//직업 시스템 선택
            new Job_GUI().ChooseSystemGUIClick(event);
        else if (SubjectCode.compareTo("01") == 0)//메이플 스토리 형식 전체 직업 목록
            new Job_GUI().MapleStory_ChooseJobClick(event);
        else if (SubjectCode.compareTo("02") == 0)//메이플 스토리 형식 전체 직업 설정
            new Job_GUI().MapleStory_JobSettingClick(event);
        else if (SubjectCode.compareTo("03") == 0)//마비노기 전체 카테고리 목록
            new Job_GUI().Mabinogi_ChooseCategoryClick(event);
        else if (SubjectCode.compareTo("04") == 0)//마비노기 스킬 관리
            new Job_GUI().Mabinogi_SkillSettingClick(event);
        else if (SubjectCode.compareTo("05") == 0)//등록된 스킬 관리
            new Job_GUI().AddedSkillsListGUIClick(event);
    }
}
