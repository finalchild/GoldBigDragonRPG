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

package GBD_RPG.NPC;

import org.bukkit.entity.Player;

import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class NPC_Config {
    public void PlayerNPCconfig(Player player, String NPCuuid) {
        YamlManager NPCscript = null;
        YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
        if (YC.isExit("NPC/PlayerData/" + player.getUniqueId() + ".yml") == false) {
            NPCscript = YC.getNewConfig("NPC/PlayerData/" + player.getUniqueId() + ".yml");
            NPCscript.set(NPCuuid + ".love", 0);
            NPCscript.set(NPCuuid + ".Career", 0);
            NPCscript.saveConfig();
        }
        NPCscript = YC.getNewConfig("NPC/PlayerData/" + player.getUniqueId() + ".yml");
        if (NPCscript.contains(NPCuuid) == false) {
            NPCscript = YC.getNewConfig("NPC/PlayerData/" + player.getUniqueId() + ".yml");
            NPCscript.set(NPCuuid + ".love", 0);
            NPCscript.set(NPCuuid + ".Career", 0);
            NPCscript.saveConfig();
        }
    }

    public void NPCNPCconfig(String NPCuuid) {
        YamlManager NPCscript = null;
        YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
        if (YC.isExit("NPC/NPCData/" + NPCuuid + ".yml") == false) {
            NPCscript = YC.getNewConfig("NPC/NPCData/" + NPCuuid + ".yml");
            NPCscript.set("NPCuuid", "NPC's uuid");
            NPCscript.set("KoreaLanguage(UTF-8)->JavaEntityLanguage", "http://itpro.cz/juniconv/");
            NPCscript.set("NatureTalk.1.love", 0);
            NPCscript.set("NatureTalk.1.Script", "§f안녕, §e%player%?");
            NPCscript.set("NatureTalk.2.love", 0);
            NPCscript.set("NatureTalk.2.Script", "§f띄워 쓰기는%enter%§f이렇게 하지.");
            NPCscript.set("NatureTalk.3.love", 0);
            NPCscript.set("NatureTalk.3.Script", "§1색깔은 §4이렇게 §f넣을 수 있어!");
            NPCscript.set("NearByNEWS.1.love", 0);
            NPCscript.set("NearByNEWS.1.Script", "§f너희집에서 어제 다이아몬드를 본 것 같은데...");
            NPCscript.set("NearByNEWS.2.love", 0);
            NPCscript.set("NearByNEWS.2.Script", "§f조심하는게 좋아.%enter%§f어제 §4히로빈 §f이 이 근처에 있었거든...");
            NPCscript.set("NearByNEWS.3.love", 0);
            NPCscript.set("NearByNEWS.3.Script", "§f음...");
            NPCscript.set("AboutSkills.1.love", 0);
            NPCscript.set("AboutSkills.1.giveSkill", "null");
            NPCscript.set("AboutSkills.1.AlreadyGetScript", "null");
            NPCscript.set("AboutSkills.1.Script", "§f나는 §e채광 스킬§f이 있지!%enter%§f뭐? 너도 있다고?");
            NPCscript.set("AboutSkills.2.love", 0);
            NPCscript.set("AboutSkills.2.giveSkill", "null");
            NPCscript.set("AboutSkills.2.AlreadyGetScript", "null");
            NPCscript.set("AboutSkills.2.Script", "§f달리기는 너의 건강에도 좋지만%enter%§f생존에도 좋지.");
            NPCscript.set("AboutSkills.3.love", 0);
            NPCscript.set("AboutSkills.3.giveSkill", "null");
            NPCscript.set("AboutSkills.3.AlreadyGetScript", "null");
            NPCscript.set("AboutSkills.3.Script", "§f너에게 가르쳐 줄만한%enter%§f기술이 없는것 같은데...");

            NPCscript.createSection("Shop.Sell");
            NPCscript.createSection("Shop.Buy");
            NPCscript.set("Quest.0", null);

            NPCscript.saveConfig();
        }
    }
}
