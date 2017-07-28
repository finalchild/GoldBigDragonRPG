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

package io.github.goldbigdragon.goldbigdragonrpg.npc;

import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class _NPCGUIManager {
    //NPC GUI Click Unique Number = 07
    //NPC 관련 GUI의 고유 번호는 07입니다.

    //If you want add this system, just Put it in for Main_InventoryClcik!
    //당신이 NPC 관련 GUI 기능을 넣고싶을땐, 그냥 Main_InventoryClick 클래스 안에 넣으세요!

    public void ClickRouting(InventoryClickEvent event, String SubjectCode) {
        if (SubjectCode.compareTo("00") == 0)//NPC 메인 GUI
            new NPC_Gui().MainGUIClick(event, ChatColor.stripColor(event.getInventory().getName()).split("C] ")[1]);
        else if (SubjectCode.compareTo("01") == 0)//NPC 상점 목록 GUI
            new NPC_Gui().ShopGUIClick(event, ChatColor.stripColor(event.getInventory().getName()).split("C] ")[1]);
        else if (SubjectCode.compareTo("02") == 0)//NPC 대화 GUI
            new NPC_Gui().TalkGUIClick(event, ChatColor.stripColor(event.getInventory().getName()).split("C] ")[1]);
        else if (SubjectCode.compareTo("03") == 0)//NPC 모든 퀘스트 목록 GUI
            new NPC_Gui().QuestAddGUIClick(event);
        else if (SubjectCode.compareTo("04") == 0)//진행 가능 퀘스트 목록 GUI
            new NPC_Gui().QuestListGUIClick(event);
        else if (SubjectCode.compareTo("05") == 0)//NPC 직업 선택 GUI
            new NPC_Gui().NPCjobGUIClick(event, ChatColor.stripColor(event.getInventory().getItem(18).getItemMeta().getLore().get(1)));
        else if (SubjectCode.compareTo("06") == 0)//NPC 워프 목록 GUI
            new NPC_Gui().WarpMainGUIClick(event);
        else if (SubjectCode.compareTo("07") == 0)//NPC 워프 등록 GUI
            new NPC_Gui().WarperGUIClick(event);
        else if (SubjectCode.compareTo("08") == 0)//NPC 개조 목록 GUI
            new NPC_Gui().UpgraderGUIClick(event);
        else if (SubjectCode.compareTo("09") == 0)//NPC 개조식 등록 GUI
            new NPC_Gui().SelectUpgradeRecipeGUIClick(event);
        else if (SubjectCode.compareTo("0a") == 0)//NPC 룬 세공사 GUI
            new NPC_Gui().RuneEquipGUIClick(event);
        else if (SubjectCode.compareTo("0b") == 0)//NPC 대화 목록 GUI
            new NPC_Gui().TalkGUIClick(event);
        else if (SubjectCode.compareTo("0c") == 0)//NPC 대화 설정 GUI
            new NPC_Gui().TalkSettingGUIClick(event);
        else if (SubjectCode.compareTo("0d") == 0)//NPC 가르칠 스킬 선택 GUI
            new NPC_Gui().AddAbleSkillsGUIClick(event);
        else if (SubjectCode.compareTo("0e") == 0)//NPC 물품 수량 설정 GUI
            new NPC_Gui().ItemBuyGuiClick(event);
        else if (SubjectCode.compareTo("0f") == 0)//NPC 수리 GUI
            new NPC_Gui().ItemFixGuiClick(event);
        else if (SubjectCode.compareTo("10") == 0)//NPC 선물 아이템 목록 GUI
            new NPC_Gui().PresentGuiClick(event);
        else if (SubjectCode.compareTo("11") == 0)//NPC 선물 아이템 설정 GUI
            new NPC_Gui().PresentGuiClick(event);
    }

    public void CloseRouting(InventoryCloseEvent event, String SubjectCode) {
        if (SubjectCode.compareTo("0a") == 0)//NPC 룬 세공사 GUI
            new NPC_Gui().RuneEquipGUIClose(event);
        else if (SubjectCode.compareTo("11") == 0)//NPC 선물 아이템 설정 GUI
            new NPC_Gui().PresentInventoryClose(event);
    }
}
