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

package GBD_RPG.Main_Event;

import org.bukkit.event.inventory.InventoryClickEvent;

public class Main_InventoryClick
{
	public void InventoryClickRouter(InventoryClickEvent event, String InventoryCode)
	{
		String UniqueCode = InventoryCode.charAt(1) + "" + InventoryCode.charAt(2);
		String SubjectCode = InventoryCode.charAt(3) + "" + InventoryCode.charAt(4);
		if(UniqueCode.compareTo("00")==0)//User 패키지 속의 GUI Click을 관리함.
		    new GBD_RPG.User._UserGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.compareTo("01")==0)//Admin 패키지 속의 GUI Click을 관리함.
		    new GBD_RPG.Admin._AdminGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.compareTo("02")==0)//Area 패키지 속의 GUI Click을 관리함.
		    new GBD_RPG.Area._AreaGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.compareTo("03")==0)//CustomItem 패키지 속의 GUI Click을 관리함.
		    new GBD_RPG.CustomItem._ItemGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.compareTo("04")==0)//Party 패키지 속의 GUI Click을 관리함.
		    new GBD_RPG.Party._PartyGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.compareTo("05")==0)//Quest 패키지 속의 GUI Click을 관리함.
		    new GBD_RPG.Quest._QuestGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.compareTo("06")==0)//Job 패키지 속의 GUI Click을 관리함.
		    new GBD_RPG.Job._JobGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.compareTo("07")==0)//NPC 패키지 속의 GUI Click을 관리함.
		    new GBD_RPG.NPC._NPCGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.compareTo("08")==0)//Monster 패키지 속의 GUI Click을 관리함.
		    new GBD_RPG.Monster._MonsterGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.compareTo("09")==0)//Corpse 패키지 속의 GUI Click을 관리함.
		    new GBD_RPG.Corpse._CorpseGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.compareTo("0a")==0)//Dungeon 패키지 속의 GUI Click을 관리함.
		    new GBD_RPG.Dungeon._DungeonGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.compareTo("0b")==0)//Skill 패키지 속의 GUI Click을 관리함.
		    new GBD_RPG.Skill._SkillGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.compareTo("0c")==0)//Warp 패키지 속의 GUI Click을 관리함.
		    new GBD_RPG.Warp._WarpGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.compareTo("0d")==0)//Structure 패키지 속의 GUI Click을 관리함.
		    new GBD_RPG.Structure._StructureGUIManager().ClickRouting(event, SubjectCode);
		else if(UniqueCode.compareTo("0e")==0)//Making 패키지 속의 GUI Click을 관리함.
		    new GBD_RPG.Making._MakingGUIManager().ClickRouting(event, SubjectCode);
	}
}
