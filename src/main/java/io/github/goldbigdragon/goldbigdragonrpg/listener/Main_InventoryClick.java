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

package io.github.goldbigdragon.goldbigdragonrpg.listener;

import io.github.goldbigdragon.goldbigdragonrpg.admin._AdminGUIManager;
import io.github.goldbigdragon.goldbigdragonrpg.area._AreaGUIManager;
import io.github.goldbigdragon.goldbigdragonrpg.corpse._CorpseGUIManager;
import io.github.goldbigdragon.goldbigdragonrpg.customitem._ItemGUIManager;
import io.github.goldbigdragon.goldbigdragonrpg.dungeon._DungeonGUIManager;
import io.github.goldbigdragon.goldbigdragonrpg.job._JobGUIManager;
import io.github.goldbigdragon.goldbigdragonrpg.making._MakingGUIManager;
import io.github.goldbigdragon.goldbigdragonrpg.monster._MonsterGUIManager;
import io.github.goldbigdragon.goldbigdragonrpg.npc._NPCGUIManager;
import io.github.goldbigdragon.goldbigdragonrpg.party._PartyGUIManager;
import io.github.goldbigdragon.goldbigdragonrpg.quest._QuestGUIManager;
import io.github.goldbigdragon.goldbigdragonrpg.skill._SkillGUIManager;
import io.github.goldbigdragon.goldbigdragonrpg.structure._StructureGUIManager;
import io.github.goldbigdragon.goldbigdragonrpg.user._UserGUIManager;
import io.github.goldbigdragon.goldbigdragonrpg.warp._WarpGUIManager;
import org.bukkit.event.inventory.InventoryClickEvent;

public class Main_InventoryClick {
    public void InventoryClickRouter(InventoryClickEvent event, String InventoryCode) {
        String UniqueCode = InventoryCode.charAt(1) + "" + InventoryCode.charAt(2);
        String SubjectCode = InventoryCode.charAt(3) + "" + InventoryCode.charAt(4);
        if (UniqueCode.compareTo("00") == 0)//User 패키지 속의 GUI Click을 관리함.
            new _UserGUIManager().ClickRouting(event, SubjectCode);
        else if (UniqueCode.compareTo("01") == 0)//Admin 패키지 속의 GUI Click을 관리함.
            new _AdminGUIManager().ClickRouting(event, SubjectCode);
        else if (UniqueCode.compareTo("02") == 0)//Area 패키지 속의 GUI Click을 관리함.
            new _AreaGUIManager().ClickRouting(event, SubjectCode);
        else if (UniqueCode.compareTo("03") == 0)//CustomItem 패키지 속의 GUI Click을 관리함.
            new _ItemGUIManager().ClickRouting(event, SubjectCode);
        else if (UniqueCode.compareTo("04") == 0)//Party 패키지 속의 GUI Click을 관리함.
            new _PartyGUIManager().ClickRouting(event, SubjectCode);
        else if (UniqueCode.compareTo("05") == 0)//Quest 패키지 속의 GUI Click을 관리함.
            new _QuestGUIManager().ClickRouting(event, SubjectCode);
        else if (UniqueCode.compareTo("06") == 0)//Job 패키지 속의 GUI Click을 관리함.
            new _JobGUIManager().ClickRouting(event, SubjectCode);
        else if (UniqueCode.compareTo("07") == 0)//NPC 패키지 속의 GUI Click을 관리함.
            new _NPCGUIManager().ClickRouting(event, SubjectCode);
        else if (UniqueCode.compareTo("08") == 0)//Monster 패키지 속의 GUI Click을 관리함.
            new _MonsterGUIManager().ClickRouting(event, SubjectCode);
        else if (UniqueCode.compareTo("09") == 0)//Corpse 패키지 속의 GUI Click을 관리함.
            new _CorpseGUIManager().ClickRouting(event, SubjectCode);
        else if (UniqueCode.compareTo("0a") == 0)//Dungeon 패키지 속의 GUI Click을 관리함.
            new _DungeonGUIManager().ClickRouting(event, SubjectCode);
        else if (UniqueCode.compareTo("0b") == 0)//Skill 패키지 속의 GUI Click을 관리함.
            new _SkillGUIManager().ClickRouting(event, SubjectCode);
        else if (UniqueCode.compareTo("0c") == 0)//Warp 패키지 속의 GUI Click을 관리함.
            new _WarpGUIManager().ClickRouting(event, SubjectCode);
        else if (UniqueCode.compareTo("0d") == 0)//Structure 패키지 속의 GUI Click을 관리함.
            new _StructureGUIManager().ClickRouting(event, SubjectCode);
        else if (UniqueCode.compareTo("0e") == 0)//Making 패키지 속의 GUI Click을 관리함.
            new _MakingGUIManager().ClickRouting(event, SubjectCode);
    }
}
