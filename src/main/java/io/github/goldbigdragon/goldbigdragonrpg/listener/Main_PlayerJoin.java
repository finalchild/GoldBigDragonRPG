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

import io.github.goldbigdragon.goldbigdragonrpg.corpse.Corpse_Main;
import io.github.goldbigdragon.goldbigdragonrpg.dungeon.Dungeon_Main;
import io.github.goldbigdragon.goldbigdragonrpg.effect.Effect_Packet;
import io.github.goldbigdragon.goldbigdragonrpg.job.Job_Main;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_ServerOption;
import io.github.goldbigdragon.goldbigdragonrpg.quest.Quest_Config;
import io.github.goldbigdragon.goldbigdragonrpg.skill.Skill_Config;
import io.github.goldbigdragon.goldbigdragonrpg.user.Equip_GUI;
import io.github.goldbigdragon.goldbigdragonrpg.user.UserData_Object;
import io.github.goldbigdragon.goldbigdragonrpg.user.User_Object;
import io.github.goldbigdragon.goldbigdragonrpg.util.ETC;
import io.github.goldbigdragon.goldbigdragonrpg.util.Util_Player;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;

public class Main_PlayerJoin implements Listener {
    @EventHandler
    private void PlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerUUID = player.getUniqueId().toString();
        new User_Object(player);
        YamlController YC = new YamlController(Main_Main.plugin);
        if (!YC.isExit("Skill/PlayerData/" + playerUUID + ".yml"))
            new Skill_Config().CreateNewPlayerSkill(player);
        else
            new Job_Main().PlayerFixAllSkillAndJobYML(player);

        if (player.getLocation().getWorld().getName().compareTo("Dungeon") == 0) {
            new Util_Player().teleportToCurrentArea(player, true);
            new Dungeon_Main().EraseAllDungeonKey(player, false);
            Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setDungeon_Enter(null);
            Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setDungeon_UTC(-1);
        }
        if (new Corpse_Main().DeathCapture(player, true))
            new Corpse_Main().CreateCorpse(player);

        new Main_ServerOption().MagicSpellCatch();
        new Main_ServerOption().CitizensCatch();

        new UserData_Object().UserDataInit(player);

        if (player.isOp())
            new Effect_Packet().sendTitleSubTitle(player, "\'§e/오피박스\'", "\'§eGoldBigDragonAdvanced 가이드 및 서버 설정이 가능합니다.\'", (byte) 1, (byte) 10, (byte) 1);
        else {
            YamlManager Config = YC.getNewConfig("config.yml");
            if (Config.getInt("Event.DropChance") >= 2 || Config.getInt("Event.Multiple_EXP_Get") >= 2 || Config.getInt("Event.Multiple_Level_Up_StatPoint") >= 2 || Config.getInt("Event.Multiple_Level_Up_SkillPoint") >= 2) {
                String alert = "[";
                if (Config.getInt("Event.DropChance") >= 2)
                    alert = alert + "드롭률 증가 " + Config.getInt("Event.DropChance") + "배";
                if (Config.getInt("Event.DropChance") >= 2)
                    alert = alert + ", ";
                if (Config.getInt("Event.Multiple_EXP_Get") >= 2)
                    alert = alert + "경험치 " + Config.getInt("Event.Multiple_EXP_Get") + "배 획득";
                if (Config.getInt("Event.Multiple_EXP_Get") >= 2)
                    alert = alert + ", ";
                if (Config.getInt("Event.Multiple_Level_Up_StatPoint") >= 2)
                    alert = alert + "스텟 포인트 " + Config.getInt("Event.Multiple_Level_Up_StatPoint") + "배 획득";
                if (Config.getInt("Event.Multiple_Level_Up_StatPoint") >= 2)
                    alert = alert + ", ";
                if (Config.getInt("Event.Multiple_Level_Up_SkillPoint") >= 2)
                    alert = alert + "스킬 포인트 " + Config.getInt("Event.Multiple_Level_Up_SkillPoint") + "배 획득";
                alert = alert + "]";
                new Effect_Packet().sendTitleSubTitle(player, "\'현재 이벤트가 진행중입니다.\'", "\'" + alert + "\'", (byte) 1, (byte) 10, (byte) 1);
            }
        }
        if (!YC.isExit("Quest/PlayerData/" + playerUUID + ".yml")) {
            new Quest_Config().CreateNewPlayerConfig(player);

            YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");
            for (byte count = 0; count < YC.getNewConfig("ETC/NewBie.yml").getConfigurationSection("SupportItem").getKeys(false).toArray().length; count++)
                if (NewBieYM.getItemStack("SupportItem." + count) != null)
                    player.getInventory().addItem(NewBieYM.getItemStack("SupportItem." + count));
            player.teleport(new Location(Bukkit.getWorld(NewBieYM.getString("TelePort.World")), NewBieYM.getInt("TelePort.X"), NewBieYM.getInt("TelePort.Y"), NewBieYM.getInt("TelePort.Z"), NewBieYM.getInt("TelePort.Yaw"), NewBieYM.getInt("TelePort.Pitch")));
        }
        new ETC().UpdatePlayerHPMP(event.getPlayer());
        new Equip_GUI().FriendJoinQuitMessage(player, true);

        if (YC.getNewConfig("config.yml").getString("Server.JoinMessage") != null)
            event.setJoinMessage(YC.getNewConfig("config.yml").getString("Server.JoinMessage").replace("%player%", event.getPlayer().getName()));
        else
            event.setJoinMessage(null);
    }
}
