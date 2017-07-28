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

package io.github.goldbigdragon.goldbigdragonrpg.dungeon;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class _DungeonGUIManager {
    //Dungeon GUI Click Unique Number = 0a
    //던전 관련 GUI의 고유 번호는 0a입니다.

    //If you want add this system, just Put it in for Main_InventoryClcik!
    //당신이 아이템 관련 GUI 기능을 넣고싶을땐, 그냥 Main_InventoryClick 클래스 안에 넣으세요!

    public void ClickRouting(InventoryClickEvent event, String SubjectCode) {
        if (SubjectCode.compareTo("00") == 0)//던전 목록
            new Dungeon_Gui().DungeonListMainGUIClick(event);
        else if (SubjectCode.compareTo("01") == 0)//던전 설정
            new Dungeon_Gui().DungeonSetUpGUIClick(event);
        else if (SubjectCode.compareTo("02") == 0)//던전 보상
            new Dungeon_Gui().DungeonChestRewardClick(event);
        else if (SubjectCode.compareTo("03") == 0)//던전 몬스터 메인
            new Dungeon_Gui().DungeonMonsterGUIMainClick(event);
        else if (SubjectCode.compareTo("04") == 0)//던전 몬스터 타입 선택
            new Dungeon_Gui().DungeonMonsterChooseMainClick(event);
        else if (SubjectCode.compareTo("05") == 0)//던전 일반 몬스터
            new Dungeon_Gui().DungeonSelectNormalMonsterChooseClick(event);
        else if (SubjectCode.compareTo("06") == 0)//던전 커스텀 몬스터
            new Dungeon_Gui().DungeonSelectCustomMonsterChooseClick(event);
        else if (SubjectCode.compareTo("07") == 0)//던전 배경 음악
            new Dungeon_Gui().DungeonMusicSettingGUIClick(event);
        else if (SubjectCode.compareTo("08") == 0)//던전 통행증 설정
            new Dungeon_Gui().EnterCardSetUpGUIClick(event);
        else if (SubjectCode.compareTo("09") == 0)//던전 통행증 연결
            new Dungeon_Gui().EnterCardDungeonSettingGUIClick(event);
        else if (SubjectCode.compareTo("0a") == 0)//던전 제단 목록
            new Dungeon_Gui().AltarShapeListGUIClick(event);
        else if (SubjectCode.compareTo("0b") == 0)//던전 제단 설정
            new Dungeon_Gui().AltarSettingGUIClick(event);
        else if (SubjectCode.compareTo("0c") == 0)//던전 제단 연결
            new Dungeon_Gui().AltarDungeonSettingGUIClick(event);
        else if (SubjectCode.compareTo("0d") == 0)//던전 제단에 등록된 통행증 목록
            new Dungeon_Gui().AltarEnterCardSettingGUIClick(event);
        else if (SubjectCode.compareTo("0e") == 0)//생성된 통행증 목록
            new Dungeon_Gui().AltarEnterCardListGUIClick(event);
        else if (SubjectCode.compareTo("0f") == 0)//던전 제단 열람 화면
            new Dungeon_Gui().AltarUseGUIClick(event);
        else if (SubjectCode.compareTo("10") == 0)//던전 잔류 화인
            new Dungeon_Gui().DungeonEXITClick(event);

    }

    public void CloseRouting(InventoryCloseEvent event, String SubjectCode) {
        if (SubjectCode.compareTo("02") == 0)//던전 보상
            new Dungeon_Gui().DungeonChestRewardClose(event);
        else if (SubjectCode.compareTo("0f") == 0)//던전 제단 열람 화면
            new Dungeon_Gui().AltarUSEGuiClose(event);
    }
}
