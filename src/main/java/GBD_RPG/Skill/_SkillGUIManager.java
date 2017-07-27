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

package GBD_RPG.Skill;

import org.bukkit.event.inventory.InventoryClickEvent;

public class _SkillGUIManager {
    //Skill GUI Click Unique Number = 0b
    //스킬 관련 GUI의 고유 번호는 0b입니다.

    //If you want add this system, just Put it in for Main_InventoryClcik!
    //당신이 스킬 관련 GUI 기능을 넣고싶을땐, 그냥 Main_InventoryClick 클래스 안에 넣으세요!

    public void ClickRouting(InventoryClickEvent event, String SubjectCode) {
        if (SubjectCode.compareTo("00") == 0)//전체 스킬 목록
            new GBD_RPG.Skill.OPboxSkill_GUI().AllSkillsGUIClick(event);
        else if (SubjectCode.compareTo("01") == 0)//스킬 관리
            new GBD_RPG.Skill.OPboxSkill_GUI().IndividualSkillOptionGUIClick(event);
        else if (SubjectCode.compareTo("02") == 0)//스킬 랭크별 관리
            new GBD_RPG.Skill.OPboxSkill_GUI().SkillRankOptionGUIClick(event);
        else if (SubjectCode.compareTo("03") == 0)//직업군 선택
            new GBD_RPG.Skill.UserSkill_GUI().MapleStory_MainSkillsListGUIClick(event);
        else if (SubjectCode.compareTo("04") == 0)//카테고리 선택
            new GBD_RPG.Skill.UserSkill_GUI().Mabinogi_MainSkillsListGUIClick(event);
        else if (SubjectCode.compareTo("05") == 0)//보유 스킬 목록
            new GBD_RPG.Skill.UserSkill_GUI().SkillListGUIClick(event);
        else if (SubjectCode.compareTo("06") == 0)//퀵슬롯 등록
            new GBD_RPG.Skill.UserSkill_GUI().AddQuickBarGUIClick(event);
        else if (SubjectCode.compareTo("07") == 0)//모든 매직스펠 보기
            new OtherPlugins.SpellMain().ShowAllMaigcGUIClick(event);
    }
}
