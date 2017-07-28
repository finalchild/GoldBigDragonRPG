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

package io.github.goldbigdragon.goldbigdragonrpg.user;

import java.util.Arrays;

import io.github.goldbigdragon.goldbigdragonrpg.effect.SoundUtil;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_ServerOption;
import io.github.goldbigdragon.goldbigdragonrpg.quest.Quest_Gui;
import io.github.goldbigdragon.goldbigdragonrpg.util.GuiUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import io.github.goldbigdragon.goldbigdragonrpg.skill.UserSkill_Gui;

public class Option_Gui extends GuiUtil {
    public void optionGUI(Player player) {
        String UniqueCode = "§0§0§0§0§1§r";
        Inventory inv = Bukkit.createInventory(null, 45, UniqueCode + "§0옵션");

        Stack2(ChatColor.WHITE + "스텟", 397, 3, 1, Arrays.asList(ChatColor.GRAY + "스텟을 확인합니다."), 0, inv);
        Stack2(ChatColor.WHITE + "스킬", 403, 0, 1, Arrays.asList(ChatColor.GRAY + "스킬을 확인합니다."), 9, inv);
        Stack2(ChatColor.WHITE + "퀘스트", 358, 0, 1, Arrays.asList(ChatColor.GRAY + "현재 진행중인 퀘스트를 확인합니다."), 18, inv);
        Stack2(ChatColor.WHITE + "옵션", 160, 4, 1, Arrays.asList(ChatColor.GRAY + "기타 설정을 합니다."), 27, inv);
        Stack2(ChatColor.WHITE + "기타", 354, 0, 1, Arrays.asList(ChatColor.GRAY + "기타 내용을 확인합니다."), 36, inv);

        Stack2(ChatColor.RED + " ", 66, 0, 1, Arrays.asList(""), 1, inv);
        Stack2(ChatColor.RED + " ", 66, 0, 1, Arrays.asList(""), 7, inv);
        Stack2(ChatColor.RED + " ", 66, 0, 1, Arrays.asList(""), 10, inv);
        Stack2(ChatColor.RED + " ", 66, 0, 1, Arrays.asList(""), 16, inv);
        Stack2(ChatColor.RED + " ", 66, 0, 1, Arrays.asList(""), 19, inv);
        Stack2(ChatColor.RED + " ", 66, 0, 1, Arrays.asList(""), 25, inv);
        Stack2(ChatColor.RED + " ", 66, 0, 1, Arrays.asList(""), 28, inv);
        Stack2(ChatColor.RED + " ", 66, 0, 1, Arrays.asList(""), 34, inv);
        Stack2(ChatColor.RED + " ", 66, 0, 1, Arrays.asList(""), 37, inv);
        Stack2(ChatColor.RED + " ", 66, 0, 1, Arrays.asList(""), 43, inv);

        if (Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_EXPget()) {
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "돈, 경험치 획득 알림", 384, 0, 1, Arrays.asList(ChatColor.GREEN + "[활성화]", ChatColor.GRAY + "돈과 경험치 획득을 알립니다."), 2, inv);
        } else {
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "돈, 경험치 획득 알림", 166, 0, 1, Arrays.asList(ChatColor.RED + "[비활성화]", ChatColor.GRAY + "돈과 경험치 획득을 알리지 않습니다."), 2, inv);
        }
        if (Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_ItemPickUp()) {
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "아이템 획득 알림", 154, 0, 1, Arrays.asList(ChatColor.GREEN + "[활성화]", ChatColor.GRAY + "아이템 획득을 알립니다."), 3, inv);
        } else {
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "아이템 획득 알림", 166, 0, 1, Arrays.asList(ChatColor.RED + "[비활성화]", ChatColor.GRAY + "아이템 획득을 알리지 않습니다."), 3, inv);
        }
        if (Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_MobHealth()) {
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "전투 도우미", 381, 0, 1, Arrays.asList(ChatColor.GREEN + "[활성화]", ChatColor.GRAY + "각종 전투 상황을 봅니다."), 4, inv);
        } else {
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "전투 도우미", 166, 0, 1, Arrays.asList(ChatColor.RED + "[비활성화]", ChatColor.GRAY + "각종 전투 상황을 보지 않습니다."), 4, inv);
        }
        if (Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_Damage()) {
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "데미지 알림", 267, 0, 1, Arrays.asList(ChatColor.GREEN + "[활성화]", ChatColor.GRAY + "적에게 입힌 피해를 알립니다."), 5, inv);
        } else {
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "데미지 알림", 166, 0, 1, Arrays.asList(ChatColor.RED + "[비활성화]", ChatColor.GRAY + "적에게 입힌 피해를 알리지 않습니다."), 5, inv);
        }
        if (Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isClickUse()) {
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "클릭시 사용", 438, 0, 1, Arrays.asList(ChatColor.GREEN + "[활성화]", ChatColor.GRAY + "소비 아이템을 클릭시 사용합니다."), 6, inv);
        } else {
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "클릭시 사용", 166, 0, 1, Arrays.asList(ChatColor.RED + "[비활성화]", ChatColor.GRAY + "소비 아이템을 단축키 처럼 사용합니다."), 6, inv);
        }
        if (Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isBgmOn()) {
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "BGM 재생", 2256, 0, 1, Arrays.asList(ChatColor.GREEN + "[활성화]", ChatColor.GRAY + "영역 BGM을 실행 시킵니다."), 14, inv);
        } else {
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "BGM 재생", 166, 0, 1, Arrays.asList(ChatColor.RED + "[비활성화]", ChatColor.GRAY + "영역 BGM을 듣지 않습니다."), 14, inv);
        }


        if (Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_EquipLook()) {
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "장비 구경", 416, 0, 1, Arrays.asList(ChatColor.GREEN + "[허용]", ChatColor.GRAY + "다른 플레이어가 자신의 장비를", ChatColor.GRAY + "구경할 수 있습니다."), 11, inv);
        } else {
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "장비 구경", 166, 0, 1, Arrays.asList(ChatColor.RED + "[비허용]", ChatColor.GRAY + "다른 플레이어가 자신의 장비를", ChatColor.GRAY + "구경할 수 없습니다."), 11, inv);
        }

        switch (Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getOption_ChattingType()) {
            case 0:
                Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "채팅 옵션", 2264, 0, 1, Arrays.asList(ChatColor.WHITE + "[일반]", ChatColor.GRAY + "일반적인 채팅을 합니다."), 12, inv);
                break;
            case 1:
                Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "채팅 옵션", 2261, 0, 1, Arrays.asList(ChatColor.BLUE + "[파티]", ChatColor.GRAY + "파티 채팅을 합니다."), 12, inv);
                break;
            case 2:
                Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "채팅 옵션", 2260, 0, 1, Arrays.asList(ChatColor.GREEN + "[무음]", ChatColor.GRAY + "욕 하고 싶으나 용기가 나지 않을때...."), 12, inv);
                break;
            case 3:
                Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "채팅 옵션", 2258, 0, 1, Arrays.asList(ChatColor.LIGHT_PURPLE + "[관리자]", ChatColor.GRAY + "관리자 끼리의 채팅을 합니다.", ChatColor.RED + "※ 일반 유저는 사용할 수 없습니다."), 12, inv);
                break;
        }
        if (Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_HotBarSound()) {
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "장비 전환 사운드", 307, 0, 1, Arrays.asList(ChatColor.GREEN + "[허용]", ChatColor.GRAY + "핫바를 움직일 때 마다 소리가 납니다."), 13, inv);
        } else {
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "장비 전환 사운드", 166, 0, 1, Arrays.asList(ChatColor.RED + "[비허용]", ChatColor.GRAY + "핫바를 움직여도 소리가 나지 않습니다."), 13, inv);
        }

        if (Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_SeeInventory()) {
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "우 클릭시 상대방 정보 보기", 307, 0, 1, Arrays.asList(ChatColor.GREEN + "[허용]", ChatColor.GRAY + "대상을 우 클릭하면 정보창이 뜹니다."), 15, inv);
        } else {
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "우 클릭시 상대방 정보 보기", 166, 0, 1, Arrays.asList(ChatColor.RED + "[비허용]", ChatColor.GRAY + "대상을 우 클릭하여도 정보창이 뜨지 않습니다."), 15, inv);
        }

        Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324, 0, 1, Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 26, inv);

        player.openInventory(inv);
    }

    public void optionInventoryclick(InventoryClickEvent event) {
        SoundUtil s = new SoundUtil();
        Player player = (Player) event.getWhoClicked();

        int slot = event.getSlot();

        if (event.getSlot() == 26) {
            SoundUtil.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
            player.closeInventory();
        } else {
            SoundUtil.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
            if (slot == 0)
                new Stats_Gui().StatusGUI(player);
            else if (slot == 9)
                new UserSkill_Gui().MainSkillsListGUI(player, (short) 0);
            else if (slot == 18)
                new Quest_Gui().MyQuestListGUI(player, (short) 0);
            else if (slot == 36)
                new ETC_Gui().ETCGUI_Main(player);
            else {
                if (slot == 2)//경험치 획득 알림
                    Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_EXPget(!Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_EXPget());
                else if (slot == 3)//아이템 획득 알림
                    Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_ItemPickUp(!Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_ItemPickUp());
                else if (slot == 4)//전투 도우미
                    Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_MobHealth(!Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_MobHealth());
                else if (slot == 5)//데미지 알림
                    Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_Damage(!Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_Damage());
                else if (slot == 6)//클릭시 사용
                    Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setClickUse(!Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isClickUse());
                else if (slot == 11)//장비 구경
                    Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_EquipLook(!Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_EquipLook());
                else if (slot == 12)//채팅 옵션
                {
                    if (Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getOption_ChattingType() < 3)
                        Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_ChattingType((byte) (Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getOption_ChattingType() + 1));
                    else
                        Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_ChattingType((byte) (0));
                } else if (slot == 13)//장비 전환 사운드
                    Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_HotBarSound(!Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_HotBarSound());
                else if (slot == 14)//BGM 재생
                {
                    Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setBgm(!Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isBgmOn());
                    new io.github.goldbigdragon.goldbigdragonrpg.dependency.NoteBlockAPIMain().Stop(player);
                } else if (slot == 15)//우클릭시 상대방 정보 보기
                    Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_SeeInventory(!Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_SeeInventory());

                optionGUI(player);
            }
        }
    }

}
