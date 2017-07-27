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

package GBD_RPG.Monster;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class _MonsterGUIManager
{
	//Monster GUI Click Unique Number = 08
	//몬스터 관련 GUI의 고유 번호는 08입니다.
	
	//If you want add this system, just Put it in for Main_InventoryClcik!
	//당신이 몬스터 관련 GUI 기능을 넣고싶을땐, 그냥 Main_InventoryClick 클래스 안에 넣으세요!
	
	public void ClickRouting(InventoryClickEvent event, String SubjectCode)
	{
		if(SubjectCode.compareTo("00")==0)//몬스터 목록
			new GBD_RPG.Monster.Monster_GUI().MonsterListGUIClick(event);
		else if(SubjectCode.compareTo("01")==0)//몬스터 설정
			new GBD_RPG.Monster.Monster_GUI().MonsterOptionSettingGUIClick(event);
		else if(SubjectCode.compareTo("02")==0)//몬스터 포션 설정
			new GBD_RPG.Monster.Monster_GUI().MonsterPotionGUIClick(event);
		else if(SubjectCode.compareTo("03")==0)//몬스터 장비 설정
			new GBD_RPG.Monster.Monster_GUI().ArmorGUIClick(event);
		else if(SubjectCode.compareTo("0b")==0)//몬스터 타입 선택
			new GBD_RPG.Monster.Monster_GUI().MonsterTypeGUIClick(event);
		
	}
	
	public void CloseRouting(InventoryCloseEvent event, String SubjectCode)
	{
		if(SubjectCode.compareTo("03")==0)//장비 설정창
			new GBD_RPG.Monster.Monster_GUI().ArmorGUIClose(event);
	}
}
