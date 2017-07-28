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

import java.util.Arrays;

import io.github.goldbigdragon.goldbigdragonrpg.effect.SoundUtil;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import io.github.goldbigdragon.goldbigdragonrpg.servertick.ServerTick_Object;
import io.github.goldbigdragon.goldbigdragonrpg.user.ETC_Gui;
import io.github.goldbigdragon.goldbigdragonrpg.user.UserData_Object;
import io.github.goldbigdragon.goldbigdragonrpg.util.GuiUtil;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import io.github.goldbigdragon.goldbigdragonrpg.servertick.ServerTick_Main;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;

public class Navigation_Gui extends GuiUtil {
    public void NavigationListGUI(Player player, short page) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager NavigationConfig = YC.getNewConfig("Navigation/NavigationList.yml");

        String UniqueCode = "§0§0§1§1§4§r";
        Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0네비 목록 : " + (page + 1));

        Object[] Navi = NavigationConfig.getConfigurationSection("").getKeys(false).toArray();

        byte loc = 0;
        for (int count = page * 45; count < Navi.length; count++) {
            if (count > Navi.length || loc >= 45) break;
            String NaviName = NavigationConfig.getString(Navi[count] + ".Name");
            String world = NavigationConfig.getString(Navi[count] + ".world");
            int x = NavigationConfig.getInt(Navi[count] + ".x");
            int y = NavigationConfig.getInt(Navi[count] + ".y");
            int z = NavigationConfig.getInt(Navi[count] + ".z");
            int Time = NavigationConfig.getInt(Navi[count] + ".time");
            int sensitive = NavigationConfig.getInt(Navi[count] + ".sensitive");
            boolean Permition = NavigationConfig.getBoolean(Navi[count] + ".onlyOPuse");
            int ShowArrow = NavigationConfig.getInt(Navi[count] + ".ShowArrow");


            String TimeS = ChatColor.DARK_AQUA + "<도착할 때 까지 유지>";
            String PermitionS = ChatColor.DARK_AQUA + "<OP만 사용 가능>";
            String sensitiveS = ChatColor.BLUE + "<반경 " + sensitive + "블록 이내를 도착지로 판정>";
            String ShowArrowS = ChatColor.DARK_AQUA + "<기본 화살표 모양>";
            if (!Permition)
                PermitionS = ChatColor.DARK_AQUA + "<모두 사용 가능>";
            if (Time >= 0)
                TimeS = ChatColor.DARK_AQUA + "<" + Time + "초 동안 유지>";
            switch (ShowArrow) {
                default:
                    ShowArrowS = ChatColor.DARK_AQUA + "<기본 화살표 모양>";
                    break;
            }
            Stack2(ChatColor.BLACK + "" + ChatColor.BOLD + Navi[count].toString(), 395, 0, 1, Arrays.asList(
                    ChatColor.YELLOW + "" + ChatColor.BOLD + NaviName, "",
                    ChatColor.BLUE + "[도착 지점]", ChatColor.BLUE + "월드 : " + ChatColor.WHITE + world,
                    ChatColor.BLUE + "좌표 : " + ChatColor.WHITE + x + "," + y + "," + z, sensitiveS, "",
                    ChatColor.DARK_AQUA + "[기타 옵션]", TimeS, PermitionS, ShowArrowS, ""
                    , ChatColor.YELLOW + "[좌 클릭시 네비 설정]", ChatColor.RED + "[Shift + 우클릭시 네비 삭제]"), loc, inv);
            loc++;
        }

        if (Navi.length - (page * 44) > 45)
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323, 0, 1, Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
        if (page != 0)
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323, 0, 1, Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);
        Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "새 네비", 386, 0, 1, Arrays.asList(ChatColor.GRAY + "새 네비를 생성합니다."), 49, inv);
        Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323, 0, 1, Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
        Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324, 0, 1, Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
        player.openInventory(inv);
    }

    public void NavigationOptionGUI(Player player, String NaviUTC) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager NavigationConfig = YC.getNewConfig("Navigation/NavigationList.yml");

        String UniqueCode = "§0§0§1§1§5§r";
        Inventory inv = Bukkit.createInventory(null, 36, UniqueCode + "§0네비 설정");

        String NaviName = NavigationConfig.getString(NaviUTC + ".Name");
        String world = NavigationConfig.getString(NaviUTC + ".world");
        int x = NavigationConfig.getInt(NaviUTC + ".x");
        short y = (short) NavigationConfig.getInt(NaviUTC + ".y");
        int z = NavigationConfig.getInt(NaviUTC + ".z");
        int Time = NavigationConfig.getInt(NaviUTC + ".time");
        short sensitive = (short) NavigationConfig.getInt(NaviUTC + ".sensitive");
        boolean Permition = NavigationConfig.getBoolean(NaviUTC + ".onlyOPuse");
        byte ShowArrow = (byte) NavigationConfig.getInt(NaviUTC + ".ShowArrow");

        String TimeS = ChatColor.BLUE + "[도착할 때 까지 유지]";
        String PermitionS = ChatColor.BLUE + "[OP만 사용 가능]";
        String sensitiveS = ChatColor.BLUE + "[반경 " + sensitive + "블록 이내를 도착지로 판정]";
        String ShowArrowS = ChatColor.BLUE + "[기본 화살표 모양]";
        if (!Permition)
            PermitionS = ChatColor.BLUE + "[모두 사용 가능]";
        if (Time >= 0)
            TimeS = ChatColor.BLUE + "[" + Time + "초 동안 유지]";
        switch (ShowArrow) {
            default:
                ShowArrowS = ChatColor.BLUE + "[기본 화살표 모양]";
                break;
        }

        Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이름 변경", 386, 0, 1, Arrays.asList(ChatColor.GRAY + "네비게이션 이름을 변경합니다.", "", ChatColor.BLUE + "[현재 이름]", ChatColor.WHITE + NaviName, ""), 10, inv);
        Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "좌표 변경", 358, 0, 1, Arrays.asList(ChatColor.GRAY + "클릭시 현재 위치로 변경합니다.", "", ChatColor.BLUE + "[도착 지점]", ChatColor.BLUE + "월드 : " + ChatColor.WHITE + world, ChatColor.BLUE + "좌표 : " + ChatColor.WHITE + x + "," + y + "," + z, ""), 11, inv);
        Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "지속 시간", 347, 0, 1, Arrays.asList(ChatColor.GRAY + "네비게이션 지속 시간을 변경합니다.", "", TimeS, ""), 12, inv);
        Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "도착 반경", 420, 0, 1, Arrays.asList(ChatColor.GRAY + "도착 판정 범위를 변경합니다.", "", sensitiveS, ""), 13, inv);
        Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "사용 권한", 137, 0, 1, Arrays.asList(ChatColor.GRAY + "네비게이션 사용 권한을 설정합니다.", "", PermitionS, ""), 14, inv);
        Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "네비 타입", 381, 0, 1, Arrays.asList(ChatColor.GRAY + "네비게이션 타입을 설정합니다.", "", ShowArrowS, ""), 15, inv);

        Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323, 0, 1, Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 27, inv);
        Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324, 0, 1, Arrays.asList(ChatColor.GRAY + "창을 닫습니다.", ChatColor.BLACK + NaviUTC), 35, inv);
        player.openInventory(inv);
    }

    public void UseNavigationGUI(Player player, short page) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager NavigationConfig = YC.getNewConfig("Navigation/NavigationList.yml");

        String UniqueCode = "§0§0§1§1§6§r";
        Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0네비 사용 : " + (page + 1));

        Object[] Navi = NavigationConfig.getConfigurationSection("").getKeys(false).toArray();

        byte loc = 0;
        for (int count = page * 45; count < Navi.length; count++) {
            if (count > Navi.length || loc >= 45) break;
            boolean Permition = NavigationConfig.getBoolean(Navi[count] + ".onlyOPuse");
            if (Permition) {
                if (player.isOp()) {
                    String NaviName = NavigationConfig.getString(Navi[count] + ".Name");
                    String world = NavigationConfig.getString(Navi[count] + ".world");
                    int x = NavigationConfig.getInt(Navi[count] + ".x");
                    short y = (short) NavigationConfig.getInt(Navi[count] + ".y");
                    int z = NavigationConfig.getInt(Navi[count] + ".z");

                    int Time = NavigationConfig.getInt(Navi[count] + ".time");
                    String TimeS = ChatColor.DARK_AQUA + "<도착할 때 까지 유지>";
                    if (Time >= 0)
                        TimeS = ChatColor.DARK_AQUA + "<" + Time + "초 동안 유지>";

                    Stack2(ChatColor.BLACK + "" + ChatColor.BOLD + Navi[count].toString(), 395, 0, 1, Arrays.asList(
                            ChatColor.YELLOW + "" + ChatColor.BOLD + NaviName, "",
                            ChatColor.BLUE + "[설정 지점]", ChatColor.BLUE + "월드 : " + ChatColor.WHITE + world,
                            ChatColor.BLUE + "좌표 : " + ChatColor.WHITE + x + "," + y + "," + z, "", TimeS, "", ChatColor.YELLOW + "[좌 클릭시 네비 사용]"), loc, inv);
                    loc++;
                }
            } else {
                String NaviName = NavigationConfig.getString(Navi[count] + ".Name");
                String world = NavigationConfig.getString(Navi[count] + ".world");
                int x = NavigationConfig.getInt(Navi[count] + ".x");
                short y = (short) NavigationConfig.getInt(Navi[count] + ".y");
                int z = NavigationConfig.getInt(Navi[count] + ".z");

                int Time = NavigationConfig.getInt(Navi[count] + ".time");
                String TimeS = ChatColor.DARK_AQUA + "<도착할 때 까지 유지>";
                if (Time >= 0)
                    TimeS = ChatColor.DARK_AQUA + "<" + Time + "초 동안 유지>";

                Stack2(ChatColor.BLACK + "" + ChatColor.BOLD + Navi[count].toString(), 395, 0, 1, Arrays.asList(
                        ChatColor.YELLOW + "" + ChatColor.BOLD + NaviName, "",
                        ChatColor.BLUE + "[설정 지점]", ChatColor.BLUE + "월드 : " + ChatColor.WHITE + world,
                        ChatColor.BLUE + "좌표 : " + ChatColor.WHITE + x + "," + y + "," + z, "", TimeS, "", ChatColor.YELLOW + "[좌 클릭시 네비 사용]"), loc, inv);
                loc++;
            }
        }

        if (Navi.length - (page * 44) > 45)
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323, 0, 1, Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
        if (page != 0)
            Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323, 0, 1, Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);
        Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323, 0, 1, Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
        Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324, 0, 1, Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
        player.openInventory(inv);
    }


    public void NavigationListGUIClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        short page = (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1]) - 1);
        int slot = event.getSlot();


        if (slot == 53)//나가기
        {
            SoundUtil.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
            player.closeInventory();
        } else {
            SoundUtil.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
            if (slot == 45)//이전 목록
                new OPbox_Gui().OPBoxGUI_Main(player, (byte) 1);
            else if (slot == 48)//이전 페이지
                NavigationListGUI(player, (short) (page - 1));
            else if (slot == 49)//새 네비
            {
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "[네비] : 새로운 네비게이션 이름을 입력 해 주세요!");
                UserData_Object u = new UserData_Object();
                u.setType(player, "Navi");
                u.setString(player, (byte) 0, "NN");
            } else if (slot == 50)//다음 페이지
                NavigationListGUI(player, (short) (page + 1));
            else {
                if (event.isLeftClick()) {
                    SoundUtil.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
                    NavigationOptionGUI(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                } else if (event.isShiftClick() && event.isRightClick()) {
                    SoundUtil.playSound(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
                    YamlController YC = new YamlController(Main_Main.plugin);
                    YamlManager NavigationConfig = YC.getNewConfig("Navigation/NavigationList.yml");
                    NavigationConfig.removeKey(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                    NavigationConfig.saveConfig();
                    NavigationListGUI(player, page);
                }
            }
        }
    }

    public void NavigationOptionGUIClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();


        if (slot == 35)//나가기
        {
            SoundUtil.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
            player.closeInventory();
        } else {
            SoundUtil.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
            if (slot == 27)//이전 목록
                NavigationListGUI(player, (short) 0);
            else {
                YamlController YC = new YamlController(Main_Main.plugin);
                YamlManager NavigationConfig = YC.getNewConfig("Navigation/NavigationList.yml");
                String UTC = ChatColor.stripColor(event.getInventory().getItem(35).getItemMeta().getLore().get(1));

                if (slot == 11)//좌표 변경
                {
                    NavigationConfig.set(UTC + ".world", player.getLocation().getWorld().getName());
                    NavigationConfig.set(UTC + ".x", (int) player.getLocation().getX());
                    NavigationConfig.set(UTC + ".y", (int) player.getLocation().getY());
                    NavigationConfig.set(UTC + ".z", (int) player.getLocation().getZ());
                    NavigationConfig.saveConfig();
                    NavigationOptionGUI(player, UTC);
                } else if (slot == 14)//사용 권한
                {
                    if (NavigationConfig.getBoolean(UTC + ".onlyOPuse"))
                        NavigationConfig.set(UTC + ".onlyOPuse", false);
                    else
                        NavigationConfig.set(UTC + ".onlyOPuse", true);
                    NavigationConfig.saveConfig();
                    NavigationOptionGUI(player, UTC);
                } else {
                    player.closeInventory();
                    UserData_Object u = new UserData_Object();
                    u.setType(player, "Navi");
                    u.setString(player, (byte) 1, UTC);
                    if (slot == 10)//이름 변경
                    {
                        player.sendMessage(ChatColor.GREEN + "[네비] : 원하는 네비게이션 이름을 입력 해 주세요!");
                        u.setString(player, (byte) 0, "CNN");
                    } else if (slot == 12)//지속 시간
                    {
                        player.sendMessage(ChatColor.GREEN + "[네비] : 네비게이션 지속 시간을 입력 해 주세요!");
                        player.sendMessage(ChatColor.YELLOW + "(-1초(찾을 때 까지) ~ 3600초(1시간))");
                        u.setString(player, (byte) 0, "CNT");
                    } else if (slot == 13)//도착 반경
                    {
                        player.sendMessage(ChatColor.GREEN + "[네비] : 도착 판정 범위를 입력 해 주세요!");
                        player.sendMessage(ChatColor.YELLOW + "(1 ~ 1000)");
                        u.setString(player, (byte) 0, "CNS");
                    } else if (slot == 15)//네비 타입
                    {
                        player.sendMessage(ChatColor.GREEN + "[네비] : 네비게이션 화살표 모양을 선택하세요!");
                        player.sendMessage(ChatColor.YELLOW + "(0 ~ 10)");
                        u.setString(player, (byte) 0, "CNA");
                    }
                }
            }
        }
    }

    public void UseNavigationGUIClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        short page = (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1]) - 1);
        int slot = event.getSlot();


        if (slot == 53)//나가기
        {
            SoundUtil.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
            player.closeInventory();
        } else {
            SoundUtil.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
            if (slot == 45)//이전 목록
                new ETC_Gui().ETCGUI_Main(player);
            else if (slot == 48)//이전 페이지
                UseNavigationGUI(player, (short) (page - 1));
            else if (slot == 50)//다음 페이지
                UseNavigationGUI(player, (short) (page + 1));
            else if (event.isLeftClick()) {
                for (int count = 0; count < ServerTick_Main.NaviUsingList.size(); count++) {
                    if (ServerTick_Main.NaviUsingList.get(count).equals(player.getName())) {
                        SoundUtil.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
                        player.sendMessage(ChatColor.RED + "[네비게이션] : 당신은 이미 네비게이션을 사용 중입니다!");
                        return;
                    }
                }
                ServerTick_Main.NaviUsingList.add(player.getName());
                YamlController YC = new YamlController(Main_Main.plugin);
                YamlManager NavigationConfig = YC.getNewConfig("Navigation/NavigationList.yml");
                String UTC = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
                player.closeInventory();
                SoundUtil.playSound(player, Sound.BLOCK_NOTE_PLING, 1.0F, 1.0F);

                ServerTick_Object STSO = new ServerTick_Object(ServerTick_Main.nowUTC, "NV");
                STSO.setCount(0);//횟 수 초기화
                STSO.setMaxCount(NavigationConfig.getInt(UTC + ".time"));//N초간 네비게이션
                //-1초 설정시, N초간이 아닌, 찾아 갈 때 까지 네비게이션 지원
                STSO.setString((byte) 1, NavigationConfig.getString(UTC + ".world"));//목적지 월드 이름 저장
                STSO.setString((byte) 2, player.getName());//플레이어 이름 저장

                STSO.setInt((byte) 0, NavigationConfig.getInt(UTC + ".x"));//목적지X 위치저장
                STSO.setInt((byte) 1, NavigationConfig.getInt(UTC + ".y"));//목적지Y 위치저장
                STSO.setInt((byte) 2, NavigationConfig.getInt(UTC + ".z"));//목적지Z 위치저장
                STSO.setInt((byte) 3, NavigationConfig.getInt(UTC + ".sensitive"));//판정 범위 저장
                STSO.setInt((byte) 4, NavigationConfig.getInt(UTC + ".ShowArrow"));//파티클 설정

                ServerTick_Main.Schedule.put(ServerTick_Main.nowUTC, STSO);
                player.sendMessage(ChatColor.YELLOW + "[네비게이션] : 길찾기 시스템이 가동됩니다!");
                player.sendMessage(ChatColor.YELLOW + "(화살표가 보이지 않을 경우, [ESC] → [설정] → [비디오 설정] 속의 [입자]를 [모두]로 변경해 주세요!)");
            }
        }
    }
}
