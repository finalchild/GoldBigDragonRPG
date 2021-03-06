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

package io.github.goldbigdragon.goldbigdragonrpg.admin;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class _AdminGUIManager {
    //Admin GUI Click Unique Number = 01
    //어드민 관련 GUI의 고유 번호는 01입니다.

    //If you want add this system, just Put it in for Main_InventoryClcik!
    //당신이 어드민 관련 GUI 기능을 넣고싶을땐, 그냥 Main_InventoryClick 클래스 안에 넣으세요!

    public void ClickRouting(InventoryClickEvent event, String SubjectCode) {
        if (SubjectCode.compareTo("00") == 0)//오피박스
            new OPbox_Gui().OPBoxGUIInventoryclick(event);
        else if (SubjectCode.compareTo("01") == 0)//관리자 옵션
            new OPbox_Gui().OPBoxGUI_SettingInventoryClick(event);
        else if (SubjectCode.compareTo("02") == 0)//관리자 공지사항
            new OPbox_Gui().OPBoxGUI_BroadCastClick(event);
        else if (SubjectCode.compareTo("03") == 0)//관리자 스텟 설정
            new OPbox_Gui().OPBoxGUI_StatChangeClick(event);
        else if (SubjectCode.compareTo("04") == 0)//관리자 화폐 설정
            new OPbox_Gui().OPBoxGUI_MoneyClick(event);
        else if (SubjectCode.compareTo("05") == 0)//관리자 사망 설정
            new OPbox_Gui().OPBoxGUI_DeathClick(event);
        else if (SubjectCode.compareTo("06") == 0 || SubjectCode.compareTo("07") == 0)//관리자 부활 설정
            new OPbox_Gui().OPBoxGUI_RescueOrReviveClick(event);
        else if (SubjectCode.compareTo("08") == 0)//관리자 가이드
            new OPbox_Gui().OPBoxGuideInventoryclick(event);
        else if (SubjectCode.compareTo("09") == 0)//이벤트 메인
            new Event_Gui().EventGUIInventoryclick(event);
        else if (SubjectCode.compareTo("0a") == 0 || SubjectCode.compareTo("0b") == 0)//이벤트 아이템 지급
            new Event_Gui().AllPlayerGiveEventGUIclick(event, SubjectCode);
        else if (SubjectCode.compareTo("0c") == 0)//도박 메인
            new Gamble_Gui().GambleMainGUI_Click(event);
        else if (SubjectCode.compareTo("0d") == 0)//도박 상품 목록
            new Gamble_Gui().GamblePresentGUI_Click(event);
        else if (SubjectCode.compareTo("0e") == 0)//도박 상품 정보
            new Gamble_Gui().GambleDetailViewPackageGUI_Click(event);
        else if (SubjectCode.compareTo("0f") == 0)//도박 기계 목록
            new Gamble_Gui().SlotMachine_MainGUI_Click(event);
        else if (SubjectCode.compareTo("10") == 0)//도박 기계 설정
            new Gamble_Gui().SlotMachine_DetailGUI_Click(event);
        else if (SubjectCode.compareTo("11") == 0)//도박 기계 코인
            new Gamble_Gui().SlotMachineCoinGUI_Click(event);
        else if (SubjectCode.compareTo("12") == 0)//슬롯 머신
            new Gamble_Gui().SlotMachine_PlayGUI_Click(event);
        else if (SubjectCode.compareTo("13") == 0)//슬롯 머신 (회전 화면)
            event.setCancelled(true);
        else if (SubjectCode.compareTo("14") == 0)//네비게이션 목록
            new Navigation_Gui().NavigationListGUIClick(event);
        else if (SubjectCode.compareTo("15") == 0)//네비게이션 설정
            new Navigation_Gui().NavigationOptionGUIClick(event);
        else if (SubjectCode.compareTo("16") == 0)//네비게이션 사용
            new Navigation_Gui().UseNavigationGUIClick(event);
        else if (SubjectCode.compareTo("17") == 0)//초심자 옵션 메인
            new NewBie_Gui().NewBieGUIMainInventoryclick(event);
        else if (SubjectCode.compareTo("18") == 0 || SubjectCode.compareTo("1a") == 0)//초심자 지원 및 초심자 가이드
            new NewBie_Gui().NewBieSupportItemGUIInventoryclick(event, SubjectCode);
        else if (SubjectCode.compareTo("19") == 0)//초심자 기본 퀘스트
            new NewBie_Gui().NewBieQuestGUIInventoryclick(event);
        else if (SubjectCode.compareTo("1b") == 0)//월드 생성 메인
            new WorldCreate_Gui().WorldCreateGUIClick(event);
        else if (SubjectCode.compareTo("1c") == 0)//개조식 목록
            new Upgrade_Gui().UpgradeRecipeGUIClick(event);
        else if (SubjectCode.compareTo("1d") == 0)//개조식 설정
            new Upgrade_Gui().UpgradeRecipeSettingGUIClick(event);
    }

    public void CloseRouting(InventoryCloseEvent event, String SubjectCode) {
        if (SubjectCode.compareTo("06") == 0 || SubjectCode.compareTo("07") == 0)//부활 아이템 설정
            new OPbox_Gui().OPBoxGUI_RescueOrReviveClose(event, SubjectCode);
        else if (SubjectCode.compareTo("0e") == 0)//도박 상품 정보
            new Gamble_Gui().GambleDetailViewPackageGUI_Close(event);
        else if (SubjectCode.compareTo("11") == 0)//도박 코인 설정
            new Gamble_Gui().SlotMachineCoinGUI_Close(event);
        else if (SubjectCode.compareTo("18") == 0 || SubjectCode.compareTo("1a") == 0)//초심자 지원 및 초심자 가이드
            new NewBie_Gui().InventoryClose_NewBie(event, SubjectCode);
    }
}
