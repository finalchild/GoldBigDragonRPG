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

import io.github.goldbigdragon.goldbigdragonrpg.effect.SoundUtil;
import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import io.github.goldbigdragon.goldbigdragonrpg.user.UserData_Object;
import io.github.goldbigdragon.goldbigdragonrpg.util.Util_Number;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class NPC_Main {
    public String[] getScript(Player player, char ScriptType) {
        UserData_Object u = new UserData_Object();
        if (ScriptType == -1) {
            String[] script = new String[1];
            script[0] = "a";
            return script;
        }
        YamlController YC = new YamlController(Main_Main.plugin);
        String TalkSubject = "NatureTalk";

        NPC_Config NPCconfig = new NPC_Config();
        NPCconfig.PlayerNPCconfig(player, u.getNPCuuid(player));
        NPCconfig.NPCNPCconfig(u.getNPCuuid(player));

        YamlManager PlayerNPC = YC.getNewConfig("NPC/PlayerData/" + player.getUniqueId() + ".yml");
        YamlManager NPCscript = YC.getNewConfig("NPC/NPCData/" + u.getNPCuuid(player) + ".yml");

        if (ScriptType == 2)
            TalkSubject = "NatureTalk";
        if (ScriptType == 4)
            TalkSubject = "NearByNEWS";
        if (ScriptType == 6)
            TalkSubject = "AboutSkills";

        int Size = NPCscript.getConfigurationSection(TalkSubject).getKeys(false).toArray().length;

        if (Size <= 0) {
            byte randomScript = (byte) new Util_Number().RandomNum(0, 2);
            String[] script = new String[1];
            if (randomScript == 0)
                script[0] = ChatColor.GRAY + "....";
            if (randomScript == 1)
                script[0] = ChatColor.GRAY + "(할 말이 없는것 같다.)";
            if (randomScript == 2)
                script[0] = ChatColor.GRAY + "....?";
            return script;
        }

        boolean scriptget = false;
        String scriptString = "";

        boolean textOK = false;
        byte randomScript = 0;
        for (int counter = 1; counter < 125; counter++) {
            randomScript = (byte) new Util_Number().RandomNum(1, Size);
            if (PlayerNPC.getInt(u.getNPCuuid(player) + ".love") >= NPCscript.getInt(TalkSubject + "." + randomScript + ".love")) {
                if (NPCscript.getInt(TalkSubject + "." + randomScript + ".loveMax") != 0) {
                    if (PlayerNPC.getInt(u.getNPCuuid(player) + ".love") <= NPCscript.getInt(TalkSubject + "." + randomScript + ".loveMax"))
                        textOK = true;
                } else
                    textOK = true;
                break;
            }
        }
        if (textOK) {
            scriptString = NPCscript.getString(TalkSubject + "." + randomScript + ".Script");
            scriptget = true;
            YamlManager SkillList = YC.getNewConfig("Skill/SkillList.yml");
            String Skillname = NPCscript.getString(TalkSubject + "." + randomScript + ".giveSkill");
            if (ScriptType == 6 && SkillList.contains(NPCscript.getString(TalkSubject + "." + randomScript + ".giveSkill"))) {
                YamlManager JobList = YC.getNewConfig("Skill/JobList.yml");
                YamlManager PlayerSkill = YC.getNewConfig("Skill/PlayerData/" + player.getUniqueId() + ".yml");
                String Categori = JobList.getString("Mabinogi.Added." + NPCscript.getString(TalkSubject + "." + randomScript + ".giveSkill"));
                if (!PlayerSkill.contains("Mabinogi." + Categori + "." + NPCscript.getString(TalkSubject + "." + randomScript + ".giveSkill"))) {
                    SoundUtil s = new SoundUtil();
                    PlayerSkill.set("Mabinogi." + Categori + "." + Skillname, 1);
                    PlayerSkill.saveConfig();
                    SoundUtil.SP(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.8F);
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "[새로운 스킬을 획득 하였습니다!] " + ChatColor.YELLOW + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + Skillname);
                } else {
                    scriptString = NPCscript.getString(TalkSubject + "." + randomScript + ".AlreadyGetScript");
                }
            }
        }
        if (!scriptget) {
            String[] script = new String[1];
            randomScript = (byte) new Util_Number().RandomNum(0, 2);
            if (randomScript == 0)
                script[0] = ChatColor.GRAY + "....";
            if (randomScript == 1)
                script[0] = ChatColor.GRAY + "(할 말이 없는것 같다.)";
            if (randomScript == 2)
                script[0] = ChatColor.GRAY + "....?";
            return script;
        }

        String[] script = scriptString.split("%enter%");
        for (byte count = 0; count < script.length; count++)
            script[count] = script[count].replace("%player%", player.getName());
        return script;
    }
}
