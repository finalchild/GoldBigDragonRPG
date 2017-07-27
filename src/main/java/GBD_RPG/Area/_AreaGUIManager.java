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

package GBD_RPG.Area;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class _AreaGUIManager
{
	//Area GUI Click Unique Number = 02
	//영역 관련 GUI의 고유 번호는 02입니다.
	
	//If you want add this system, just Put it in for Main_InventoryClcik!
	//당신이 영역 관련 GUI 기능을 넣고싶을땐, 그냥 Main_InventoryClick 클래스 안에 넣으세요!
	
	public void ClickRouting(InventoryClickEvent event, String SubjectCode)
	{
		if(SubjectCode.compareTo("00")==0)//전체 영역 목록
			new GBD_RPG.Area.Area_GUI().AreaListGUIClick(event);
		else if(SubjectCode.compareTo("01")==0)//영역 설정
			new GBD_RPG.Area.Area_GUI().AreaSettingGUIInventoryclick(event);
		else if(SubjectCode.compareTo("02")==0)//영역 몬스터 스폰 룰 설정
			new GBD_RPG.Area.Area_GUI().AreaAddMonsterSpawnRuleGUIClick(event);
		else if(SubjectCode.compareTo("03")==0)//영역 대체 몬스터 설정
			new GBD_RPG.Area.Area_GUI().AreaMonsterSettingGUIClick(event);
		else if(SubjectCode.compareTo("04")==0)//영역 낚시 보상 설정
			new GBD_RPG.Area.Area_GUI().AreaFishSettingGUIClick(event);
		else if(SubjectCode.compareTo("05")==0)//영역 특산품 목록
			new GBD_RPG.Area.Area_GUI().AreaBlockSettingGUIClick(event);
		else if(SubjectCode.compareTo("06")==0)//영역 특산품 설정
			new GBD_RPG.Area.Area_GUI().AreaBlockItemSettingGUIClick(event);
		else if(SubjectCode.compareTo("07")==0)//영역 몬스터 선택
			new GBD_RPG.Area.Area_GUI().AreaAddMonsterListGUIClick(event);
		else if(SubjectCode.compareTo("08")==0)//영역 특수 몬스터 선택
			new GBD_RPG.Area.Area_GUI().AreaSpawnSpecialMonsterListGUIClick(event);
		else if(SubjectCode.compareTo("09")==0)//영역 배경음 선택
			new GBD_RPG.Area.Area_GUI().AreaMusicSettingGUIClick(event);
	}
	
	public void CloseRouting(InventoryCloseEvent event, String SubjectCode)
	{
		if(SubjectCode.compareTo("04")==0)//영역 낚시 보상 설정
			new GBD_RPG.Area.Area_GUI().FishingSettingInventoryClose(event);
		else if(SubjectCode.compareTo("06")==0)//영역 특산품 설정
			new GBD_RPG.Area.Area_GUI().BlockItemSettingInventoryClose(event);
	}
}
