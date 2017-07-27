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

package io.github.goldbigdragon.goldbigdragonrpg.skill;

import io.github.goldbigdragon.goldbigdragonrpg.main.Main_Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import io.github.goldbigdragon.goldbigdragonrpg.util.YamlController;
import io.github.goldbigdragon.goldbigdragonrpg.util.YamlManager;

public class Skill_Config {
    public void CreateNewPlayerSkill(Player player) {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager PlayerSkillYML = YC.getNewConfig("Skill/PlayerData/" + player.getUniqueId() + ".yml");
        YamlManager Config = YC.getNewConfig("config.yml");
        YamlManager JobList = YC.getNewConfig("Skill/JobList.yml");

        PlayerSkillYML.set("Job.Root", Config.getString("Server.DefaultJob"));
        PlayerSkillYML.set("Job.Type", Config.getString("Server.DefaultJob"));
        PlayerSkillYML.set("Job.LV", 1);
        PlayerSkillYML.createSection("MapleStory");
        PlayerSkillYML.createSection("MapleStory." + Config.getString("Server.DefaultJob") + ".Skill");
        Object[] DefaultSkills = null;
        if (JobList.contains(("MapleStory." + Config.getString("Server.DefaultJob") + "." + Config.getString("Server.DefaultJob") + ".Skill")))
            DefaultSkills = JobList.getConfigurationSection("MapleStory." + Config.getString("Server.DefaultJob") + "." + Config.getString("Server.DefaultJob") + ".Skill").getKeys(false).toArray();
        if (DefaultSkills != null)
            for (short count = 0; count < DefaultSkills.length; count++)
                PlayerSkillYML.set("MapleStory." + Config.getString("Server.DefaultJob") + ".Skill." + DefaultSkills[count], 1);
        PlayerSkillYML.createSection("Mabinogi");
        PlayerSkillYML.saveConfig();
    }

    public void CreateNewJobList() {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager SkillList = YC.getNewConfig("Skill/JobList.yml");

        SkillList.createSection("Mabinogi.Added");
        SkillList.set("MapleStory.초보자.초보자.NeedLV", 0);
        SkillList.set("MapleStory.초보자.초보자.NeedSTR", 0);
        SkillList.set("MapleStory.초보자.초보자.NeedDEX", 0);
        SkillList.set("MapleStory.초보자.초보자.NeedINT", 0);
        SkillList.set("MapleStory.초보자.초보자.NeedWILL", 0);
        SkillList.set("MapleStory.초보자.초보자.NeedLUK", 0);
        SkillList.set("MapleStory.초보자.초보자.PrevJob", "null");
        SkillList.set("MapleStory.초보자.초보자.IconID", 268);
        SkillList.set("MapleStory.초보자.초보자.IconData", 0);
        SkillList.set("MapleStory.초보자.초보자.IconAmount", 1);
        SkillList.createSection("MapleStory.초보자.초보자.Skill");
        SkillList.saveConfig();
    }

    public void CreateNewSkillList() {
        YamlController YC = new YamlController(Main_Main.plugin);
        YamlManager SkillList = YC.getNewConfig("Skill/SkillList.yml");
        SkillList.set("CloudKill.ID", 151);
        SkillList.set("CloudKill.DATA", 0);
        SkillList.set("CloudKill.Amount", 1);
        SkillList.set("CloudKill.SkillRank." + (int) 1 + ".Command", "/weather clear 9999");
        SkillList.set("CloudKill.SkillRank." + (int) 1 + ".BukkitPermission", true);
        SkillList.set("CloudKill.SkillRank." + (int) 1 + ".MagicSpells", "null");
        SkillList.set("CloudKill.SkillRank." + (int) 1 + ".Lore", ChatColor.GRAY + "     [설명 없음]     ");
        SkillList.set("CloudKill.SkillRank." + (int) 1 + ".AffectStat", "없음");
        SkillList.set("CloudKill.SkillRank." + (int) 1 + ".DistrictWeapon", "없음");
        SkillList.saveConfig();
    }
}
