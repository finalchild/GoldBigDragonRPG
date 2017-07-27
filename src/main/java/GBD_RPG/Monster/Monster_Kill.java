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

package GBD_RPG.Monster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Slime;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import GBD_RPG.Effect.Effect_Packet;
import GBD_RPG.Main_Main.Main_ServerOption;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class Monster_Kill
{
	public String getNormalName(Entity entity)
	{
		switch(entity.getType())
		{
			case ZOMBIE : return "놂좀비";
			case GIANT : return "놂자이언트";
			case SKELETON :
			{
				Skeleton s = (Skeleton) entity;
				if(s.getSkeletonType()==SkeletonType.WITHER)
					return "놂네더스켈레톤";
				else
					return "놂스켈레톤";
			}
			case ENDERMAN : return "놂엔더맨";
			case CREEPER :
			{
				Creeper c = (Creeper) entity;
				if(c.isPowered())
					return "놂번개크리퍼";
				else
					 return "놂크리퍼";
			}
			case SPIDER : return "놂거미";
			case CAVE_SPIDER : return "놂동굴거미";
			case SILVERFISH : return "놂좀벌레";
			case ENDERMITE : return "놂엔더진드기";
			case SLIME : return "놂슬라임";
			case MAGMA_CUBE : return "놂마그마큐브";
			case BLAZE : return "놂블레이즈";
			case GHAST : return "놂가스트";
			case PIG_ZOMBIE : return "놂좀비피그맨";
			case WITCH : return "놂마녀";
			case WITHER : return "놂위더";
			case ENDER_DRAGON : return "놂엔더드래곤";
			case ENDER_CRYSTAL : return "놂엔더크리스탈";
			case GUARDIAN : return "놂수호자";
			case SHEEP : return "놂양";
			case COW : return "놂소";
			case PIG : return "놂돼지";
			case HORSE : return "놂말";
			case RABBIT : return "놂토끼";
			case OCELOT : return "놂오셀롯";
			case WOLF : return "놂늑대";
			case CHICKEN : return "놂닭";
			case MUSHROOM_COW : return "놂버섯소";
			case BAT : return "놂박쥐";
			case SQUID : return "놂오징어";
			case VILLAGER : return "놂주민";
			case SNOWMAN : return "놂눈사람";
			case IRON_GOLEM : return "놂골렘";
			case SHULKER : return "놂셜커";
			case POLAR_BEAR : return "놂북극곰";
			default : return null;
		}
	}
	
	public String getRealName(Entity entity)
	{
		String name=entity.getCustomName();
		if(name == null || ChatColor.stripColor(name).length() <= 0)
			return getNormalName(entity);
		if(entity.getLocation().getWorld().getName().compareTo("Dungeon")==0)
		{
			if(name.length() >= 6)
			{
				if(name.charAt(0)=='§'&&name.charAt(1)=='2'&&
					name.charAt(2)=='§'&&name.charAt(3)=='0'&&
					name.charAt(4)=='§'&&name.charAt(5)=='2')
				{
					name = name.substring(12, name.length());
				}
			}
		}
		if(name.length()<=0)
			return getNormalName(entity);
		else
			return GBD_RPG.Main_Main.Main_ServerOption.MonsterNameMatching.get(name);
	}
	
	public void Boomb(Entity entity)
	{
		String EntityName = getRealName(entity);
		int MonsterINT = 10;
		int radius = 5;
		if(GBD_RPG.Main_Main.Main_ServerOption.MonsterList.containsKey(EntityName))
			MonsterINT = GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(EntityName).getINT();
		else
		{
			if(entity.getType()==EntityType.CREEPER)
			{
				Creeper c = (Creeper)entity;
				if(c.isPowered())
					MonsterINT = 40;
				else
					MonsterINT = 20;
			}
			else if(entity.getType() == EntityType.ENDER_CRYSTAL)
				MonsterINT = 200;
			else if(entity.getType() == EntityType.PRIMED_TNT || entity.getType() == EntityType.MINECART_TNT)
				MonsterINT = 90;
		}
		int MinPower = (int)(MonsterINT/4);
		int MaxPower = (int)(MonsterINT/2.5);
		
		int Power = new Random().nextInt((int) (MaxPower-MinPower+1))+MinPower;
		radius = (int)((Power/3)*2);
		if(radius < 3)
			radius = 3;
		else if(radius > 8)
			radius = 8;

		entity.getLocation().getWorld().playEffect(entity.getLocation(), Effect.EXPLOSION_LARGE, 0);
		entity.getLocation().getWorld().playSound(entity.getLocation(), Sound.ENTITY_GENERIC_EXPLODE ,1.5F, 1.0F);
		Iterator<Entity> e = Bukkit.getWorld("Dungeon").getNearbyEntities(entity.getLocation(), radius, radius, radius).iterator();
	    while(e.hasNext())
	    {
			int Temp = Power;
	        Entity Choosedentity = e.next();
	        if(Choosedentity!=null)
	        {
				String name = Choosedentity.getCustomName();
				if(ChatColor.stripColor(name) == null)
					name = null;
				else if(ChatColor.stripColor(name).length() == 0)
					name = null;
		        if(Choosedentity.isDead()==false)
		        {
			        int DEF = 0;
			        int PRO = 0;
			        
			        if(Choosedentity.getType()==EntityType.PLAYER)
			        {
			        	Player p = (Player) Choosedentity;
			        	if(p.getGameMode()==GameMode.SURVIVAL||p.getGameMode()==GameMode.ADVENTURE)
			        	{
			        		if(p.isOnline())
			        		{
			        			DEF = GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(p.getUniqueId().toString()).getStat_DEF();
			        			PRO = GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(p.getUniqueId().toString()).getStat_Protect();
			        		}
			        		else if(name!=null)
			        		{
								name = getRealName(Choosedentity);
			        			DEF = GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(name).getDEF();
			        			PRO = GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(name).getPRO();
			        		}
			        	}
			        }
			        else if(name!=null)
			        {
						name = getRealName(Choosedentity);
						DEF = GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(name).getDEF();
	        			PRO = GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(name).getPRO();
	        		}

					if(Power >= 100)
						Temp =(int)(Power*(100-PRO)/100);
					else if(Power >= 10)
						Temp =(int)(Power*((100-PRO)/10)/10);
					else
						Temp =(int)(Power-PRO);
					Temp = Temp-DEF;
					if(Choosedentity.getType()!=EntityType.DROPPED_ITEM&&Choosedentity.getType()!=EntityType.ARMOR_STAND&&
						Choosedentity.getType()!=EntityType.ARROW&&Choosedentity.getType()!=EntityType.BOAT&&
						Choosedentity.getType()!=EntityType.EGG&&Choosedentity.getType()!=EntityType.ENDER_PEARL&&
						Choosedentity.getType()!=EntityType.ENDER_SIGNAL&&Choosedentity.getType()!=EntityType.EXPERIENCE_ORB&&
						Choosedentity.getType()!=EntityType.FALLING_BLOCK&&Choosedentity.getType()!=EntityType.FIREBALL&&
						Choosedentity.getType()!=EntityType.FIREWORK&&Choosedentity.getType()!=EntityType.FISHING_HOOK&&
						Choosedentity.getType()!=EntityType.ITEM_FRAME&&Choosedentity.getType()!=EntityType.LEASH_HITCH&&
						Choosedentity.getType()!=EntityType.LIGHTNING&&Choosedentity.getType()!=EntityType.PAINTING&&
						Choosedentity.getType()!=EntityType.PRIMED_TNT&&Choosedentity.getType()!=EntityType.SMALL_FIREBALL&&
						Choosedentity.getType()!=EntityType.SNOWBALL&&Choosedentity.getType()!=EntityType.SPLASH_POTION&&
						Choosedentity.getType()!=EntityType.THROWN_EXP_BOTTLE&&Choosedentity.getType()!=EntityType.UNKNOWN&&
						Choosedentity.getType()!=EntityType.WITHER_SKULL)
					{
						if(Choosedentity != entity)
							if(Choosedentity.isDead()==false)
							{
								LivingEntity LE = (LivingEntity) Choosedentity;
								LE.damage(Temp, entity);
							}
					}
		        }
	        }
	    }
		
		//만일 커스텀 이름이 없는 일반 몹일 경우,
		//일반 크리퍼는 4 ~ 6
		//차지 크리퍼는 10 ~ 16
		//엔더 크리스탈은 48 ~ 105
		//TNT는 16 ~ 35로 하기.
		//반경이 멀어질수록 데미지 낮게 주며, 이는 for문 이용하기
		//마지막에 폭발 사운드와 이펙트 넣기.
	}

	public void DungeonKilled(LivingEntity entity, boolean isBoomed)
	{
		if(entity.getCustomName()!=null)
		{
			if(entity.getCustomName().length() >= 6)
			{
				String name = entity.getCustomName().toString();
				if(name.charAt(0)=='§'&&name.charAt(1)=='2'&&
					name.charAt(2)=='§'&&name.charAt(3)=='0'&&
					name.charAt(4)=='§'&&name.charAt(5)=='2')
				{
					Location loc = new Location(entity.getLocation().getWorld(), entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ());
					ItemStack item = entity.getEquipment().getHelmet();
					List<String> lore = item.getItemMeta().getLore();
					if(lore!=null)
					{
						String locString = lore.get(lore.size()-1).split(":")[1];
						ItemMeta im = item.getItemMeta();
						lore.remove(lore.size()-1);
						im.setLore(lore);
						item.setItemMeta(im);
						entity.getEquipment().setHelmet(item);
						loc = new Location(entity.getLocation().getWorld(), Integer.parseInt(locString.split(",")[0]), Integer.parseInt(locString.split(",")[1]), Integer.parseInt(locString.split(",")[2]));
					}
					if(name.charAt(7)!='2'&&isBoomed)
						entity.setCustomName("爆死");
					switch(name.charAt(7))
					{
					case 'e' : //일반
						if(SearchRoomMonster((byte) 20, name.charAt(9), loc) <= 0)
							new GBD_RPG.Dungeon.Dungeon_Main().DungeonTrapDoorWorker(loc, false);
						break;
					case '1' : //다음 웨이브 존재
						if(SearchRoomMonster((byte) 20, name.charAt(9), loc) <= 0)
							new GBD_RPG.Dungeon.Dungeon_Main().MonsterSpawn(loc);
						break;
					case '4' : //열쇠 가진 놈
						if(SearchRoomMonster((byte) 20, name.charAt(9), loc) <= 0)
							new GBD_RPG.Dungeon.Dungeon_Main().DungeonTrapDoorWorker(loc, false);
						loc.setY(loc.getY()+1);
						item = new ItemStack(292);
						ItemMeta im = item.getItemMeta();
						im.setDisplayName(ChatColor.GREEN+""+ChatColor.BLACK+""+ChatColor.GREEN+""+ChatColor.WHITE+""+ChatColor.BOLD+"[던전 룸 열쇠]");
						im.setLore(Arrays.asList("",ChatColor.WHITE+"던전 룸을 열 수 있는",ChatColor.WHITE+"낡은 열쇠이다."));
						im.addEnchant(Enchantment.DURABILITY, 6000, true);
						item.setItemMeta(im);
						new GBD_RPG.Main_Event.Main_ItemDrop().CustomItemDrop(loc, item);
						break;
					case '2' : //보스 [보스방 문을 탐지하기 위해서 보상방 철창 중앙의 위치를 던전 콘피그에 저장 시킨다.]
						Player player = entity.getKiller();
						if(entity.getKiller() == null||entity.getKiller().isOnline()==false)
						{
							List<Entity> e = (List<Entity>) loc.getWorld().getNearbyEntities(loc, 35D, 20D, 35D);
							for(short count = 0; count < e.size(); count++)
							{
								if(e.get(count).getType() == EntityType.PLAYER)
								{
									Player p = (Player) e.get(count);
									if(p.isOnline())
									{
										player = (Player) e.get(count);
										break;
									}
								}
							}
						}
						name = getRealName(entity);
						if(name != null)
						{
							YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
							if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_Enter() != null && GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_UTC() > 1)
							{
								YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_Enter()+"/Entered/"+GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_UTC()+".yml");
								if(DungeonConfig.contains("Boss"))
								{
									int BossCount = DungeonConfig.getConfigurationSection("Boss").getKeys(false).size();
									ArrayList<String> BossList = new ArrayList<String>();
									boolean isChecked = false;
									for(byte count = 0; count < BossCount; count++)
									{
										if(isChecked==false&&DungeonConfig.getString("Boss."+count).compareTo(name)==0)
											isChecked = true;
										else
											BossList.add(DungeonConfig.getString("Boss."+count));
									}
									DungeonConfig.removeKey("Boss");
									DungeonConfig.saveConfig();
									if(BossList.isEmpty() == false)
									{
							    		for(int count = 0; count < BossList.size(); count++)
							    			DungeonConfig.set("Boss."+count, BossList.get(count));
							    		DungeonConfig.saveConfig();
									}
									else
										new GBD_RPG.Dungeon.Dungeon_Main().DungeonClear(player, loc);
								}
							}
						}
						break;
					}
				}
			}
		}
	}
	
	public void MonsterKilling(EntityDeathEvent event)
	{
		if(event.getEntity().getLocation().getWorld().getName().compareTo("Dungeon")==0)
			DungeonKilled(event.getEntity(), false);
    	if(event.getEntity()!=null && event.getEntity().getKiller() != null)
    	{
    		if(event.getEntity().getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK  || event.getEntity().getLastDamageCause().getCause() == DamageCause.PROJECTILE
    				|| event.getEntity().getLastDamageCause().getCause() == DamageCause.MAGIC)
    		{
				if(Bukkit.getServer().getPlayer(event.getEntity().getKiller().getName()).isOnline() == true)
				{
					Player player = (Player) Bukkit.getServer().getPlayer(event.getEntity().getKiller().getName());
					if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_MobHealth())
					    new Effect_Packet().sendTitleSubTitle(player, "\'" +ChatColor.BLACK+"■■■■■■■■■■"+ "\'", "\'" +ChatColor.DARK_RED+""+ChatColor.BOLD+"[DEAD]"+ "\'", (byte)0, (byte)0, (byte)1);
    				Reward(event,player);
    				Quest(event, player);
					return;
				}
			}
		}
    	return;
	}

	public byte SearchRoomMonster(byte searchSize, char Group, Location loc)
	{
		byte mobs = 0;
		List<Entity> e = (List<Entity>) loc.getWorld().getNearbyEntities(loc, searchSize, searchSize, searchSize);
		
		for(short i = 0; i < e.size(); i++)
		{
			String name = e.get(i).getCustomName();
			if(name != null)
			{
				if(name.length() >= 6)
				{
					if(e.get(i).isDead() == false)
					{
						if(name.compareTo("爆死")!=0)
						{
							if(name.charAt(0)=='§'&&name.charAt(1)=='2'&&
								name.charAt(2)=='§'&&name.charAt(3)=='0'&&
								name.charAt(4)=='§'&&name.charAt(5)=='2')
							{
								if(name.charAt(9)==Group)
									mobs++;
							}
						}
					}
				}
			}
		}
		return mobs;
	}
	
	
	public void Reward(EntityDeathEvent event, Player player)
	{
		GBD_RPG.Util.Util_Number N = new GBD_RPG.Util.Util_Number();
		byte amount = 1;
		if(40 <= N.RandomNum(0, 100) * Main_ServerOption.Event_DropChance)
		{
			int lucky = GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK()/30;
			if(lucky >= 10) lucky =10;
			if(lucky <= 0) lucky = 1;
			if(lucky >= N.RandomNum(0, 100))
			{
				GBD_RPG.Effect.Effect_Sound sound = new GBD_RPG.Effect.Effect_Sound();
				int luckysize = N.RandomNum(0, 100);
				if(luckysize <= 80){player.sendMessage(ChatColor.YELLOW +""+ChatColor.BOLD+ "[SYSTEM] : 럭키 피니시!");amount = 2;	sound.SP(player, org.bukkit.Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 0.9F);}
				else if(luckysize <= 95){player.sendMessage(ChatColor.YELLOW +""+ChatColor.BOLD+ "[SYSTEM] : 빅 럭키 피니시!");amount = 5;	sound.SP(player, org.bukkit.Sound.ENTITY_PLAYER_LEVELUP, 0.7F, 1.0F);}
				else{player.sendMessage(ChatColor.YELLOW +""+ChatColor.BOLD+ "[SYSTEM] : 휴즈 럭키 피니시!");amount = 20;	sound.SP(player, org.bukkit.Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.1F);}
			}
		}
		else
			amount = 0;
		String name = getRealName(event.getEntity());
		if(GBD_RPG.Main_Main.Main_ServerOption.MonsterList.containsKey(name))
		{
			if(amount == 0)
				new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, 0, GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(name).getEXP(), event.getEntity().getLocation(), true, false);
			else
				new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, amount* N.RandomNum(GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(name).getMinMoney(), GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(name).getMaxMoney()), GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(name).getEXP(), event.getEntity().getLocation(), true, false);
			return;
		}
		else
		{
			YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
			YamlManager Config = YC.getNewConfig("config.yml");
			EntityType ET = event.getEntityType();
			if(ET == EntityType.SKELETON)
			{
				Skeleton s = (Skeleton)event.getEntity();
				if(s.getSkeletonType() == SkeletonType.NORMAL)
					new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, N.RandomNum(Config.getInt("Normal_Monster.SKELETON.MIN_MONEY"), Config.getInt("Normal_Monster.SKELETON.MAX_MONEY"))*amount, Config.getLong("Normal_Monster.SKELETON.EXP"), event.getEntity().getLocation(), true, false);
				else
					new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, N.RandomNum(Config.getInt("Normal_Monster.NETHER_SKELETON.MIN_MONEY"), Config.getInt("Normal_Monster.NETHER_SKELETON.MAX_MONEY"))*amount, Config.getLong("Normal_Monster.NETHER_SKELETON.EXP"), event.getEntity().getLocation(), true, false);
			}
			else if(ET == EntityType.CREEPER)
			{
				Creeper c = (Creeper)event.getEntity();
				if(c.isPowered() == false)
					new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, N.RandomNum(Config.getInt("Normal_Monster.CREEPER.MIN_MONEY"), Config.getInt("Normal_Monster.CREEPER.MAX_MONEY"))*amount, Config.getLong("Normal_Monster.CREEPER.EXP"), event.getEntity().getLocation(), true, false);
				else
					new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, N.RandomNum(Config.getInt("Normal_Monster.CHARGED_CREEPER.MIN_MONEY"), Config.getInt("Normal_Monster.CHARGED_CREEPER.MAX_MONEY"))*amount, Config.getLong("Normal_Monster.CHARGED_CREEPER.EXP"), event.getEntity().getLocation(), true, false);
			}
			else if(ET == EntityType.SLIME)
			{
				Slime sl = (Slime)event.getEntity();
				if(sl.getSize() == 1)
					new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, N.RandomNum(Config.getInt("Normal_Monster.SLIME_SMALL.MIN_MONEY"), Config.getInt("Normal_Monster.SLIME_SMALL.MAX_MONEY"))*amount, Config.getLong("Normal_Monster.SLIME_SMALL.EXP"), event.getEntity().getLocation(), true, false);
				else if(sl.getSize() <= 3)
					new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, N.RandomNum(Config.getInt("Normal_Monster.SLIME_MIDDLE.MIN_MONEY"), Config.getInt("Normal_Monster.SLIME_MIDDLE.MAX_MONEY"))*amount, Config.getLong("Normal_Monster.SLIME_MIDDLE.EXP"), event.getEntity().getLocation(), true, false);
				else if(sl.getSize() == 4)
					new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, N.RandomNum(Config.getInt("Normal_Monster.SLIME_BIG.MIN_MONEY"), Config.getInt("Normal_Monster.SLIME_BIG.MAX_MONEY"))*amount, Config.getLong("Normal_Monster.SLIME_BIG.EXP"), event.getEntity().getLocation(), true, false);
				else
					new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, N.RandomNum(Config.getInt("Normal_Monster.SLIME_HUGE.MIN_MONEY"), Config.getInt("Normal_Monster.SLIME_HUGE.MAX_MONEY"))*amount, Config.getLong("Normal_Monster.SLIME_HUGE.EXP"), event.getEntity().getLocation(), true, false);
			}
			else if(ET == EntityType.MAGMA_CUBE)
			{
				MagmaCube ma = (MagmaCube)event.getEntity();
				if(ma.getSize() == 1)
					new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, N.RandomNum(Config.getInt("Normal_Monster.MAGMA_CUBE_SMALL.MIN_MONEY"), Config.getInt("Normal_Monster.MAGMA_CUBE_SMALL.MAX_MONEY"))*amount, Config.getLong("Normal_Monster.MAGMA_CUBE_SMALL.EXP"), event.getEntity().getLocation(), true, false);
				else if(ma.getSize() <= 3)
					new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, N.RandomNum(Config.getInt("Normal_Monster.MAGMA_CUBE_MIDDLE.MIN_MONEY"), Config.getInt("Normal_Monster.MAGMA_CUBE_MIDDLE.MAX_MONEY"))*amount, Config.getLong("Normal_Monster.MAGMA_CUBE_MIDDLE.EXP"), event.getEntity().getLocation(), true, false);
				else if(ma.getSize() == 4)
					new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, N.RandomNum(Config.getInt("Normal_Monster.MAGMA_CUBE_BIG.MIN_MONEY"), Config.getInt("Normal_Monster.MAGMA_CUBE_BIG.MAX_MONEY"))*amount, Config.getLong("Normal_Monster.MAGMA_CUBE_BIG.EXP"), event.getEntity().getLocation(), true, false);
				else
					new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, N.RandomNum(Config.getInt("Normal_Monster.MAGMA_CUBE_HUGE.MIN_MONEY"), Config.getInt("Normal_Monster.MAGMA_CUBE_HUGE.MAX_MONEY"))*amount, Config.getLong("Normal_Monster.MAGMA_CUBE_HUGE.EXP"), event.getEntity().getLocation(), true, false);
			}
			else
			{
				if(Config.contains("Normal_Monster."+ET.toString()))
					new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, N.RandomNum(Config.getInt("Normal_Monster."+ET.toString()+".MIN_MONEY"), Config.getInt("Normal_Monster."+ET.toString()+".MAX_MONEY"))*amount, Config.getLong("Normal_Monster."+ET.toString()+".EXP"), event.getEntity().getLocation(), true, false);
			}
		}
		return;
	}

	public void Quest(EntityDeathEvent event, Player player)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
		YamlManager PlayerQuestList  = YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");

		if(Main_ServerOption.PartyJoiner.containsKey(player)==false)
		{
			Object[] a = PlayerQuestList.getConfigurationSection("Started.").getKeys(false).toArray();
			for(short count = 0; count < a.length; count++)
			{
				String QuestName = (String) a[count];
				short Flow = (short) PlayerQuestList.getInt("Started."+QuestName+".Flow");
				if(PlayerQuestList.getString("Started."+QuestName+".Type").equalsIgnoreCase("Hunt"))
				{
					if(QuestList.contains(QuestName)==false)
					{
						PlayerQuestList.removeKey("Started."+QuestName);
						PlayerQuestList.saveConfig();
						return;
					}
					Object[] MobList = QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Monster").getKeys(false).toArray();
					int Finish = 0;
					for(short counter = 0; counter < MobList.length; counter++)
					{
						String QMobName = QuestList.getString(QuestName+".FlowChart."+Flow+".Monster."+counter+".MonsterName");
						int MAX = QuestList.getInt(QuestName+".FlowChart."+Flow+".Monster."+counter+".Amount");
						String KilledName = "null";
						KilledName = event.getEntity().getName();
						if(event.getEntity().isCustomNameVisible() == true)
						{
							KilledName = event.getEntity().getCustomName();
							if(event.getEntity().getLocation().getWorld().getName().compareTo("Dungeon")==0)
							{
								if(KilledName.length() >= 6)
								{
									if(KilledName.charAt(0)=='§'&&KilledName.charAt(1)=='2'&&
										KilledName.charAt(2)=='§'&&KilledName.charAt(3)=='0'&&
										KilledName.charAt(4)=='§'&&KilledName.charAt(5)=='2')
									{
										KilledName = KilledName.substring(12, KilledName.length());
									}
								}
							}
						}
						if(QMobName.equalsIgnoreCase(KilledName) == true && MAX > PlayerQuestList.getInt("Started."+QuestName+".Hunt."+counter))
						{
							//퀘스트 진행도 알림//
							PlayerQuestList.set("Started."+QuestName+".Hunt."+counter, PlayerQuestList.getInt("Started."+QuestName+".Hunt."+counter)+1);
							PlayerQuestList.saveConfig();
						}
						if(MAX == PlayerQuestList.getInt("Started."+QuestName+".Hunt."+counter))
						{
							Finish++;
						}
						if(Finish == MobList.length)
						{
							PlayerQuestList.set("Started."+QuestName+".Type",QuestList.getString(QuestName+".FlowChart."+(PlayerQuestList.getInt("Started."+QuestName+".Flow")+1)+".Type"));
							PlayerQuestList.set("Started."+QuestName+".Flow",PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
							PlayerQuestList.removeKey("Started."+QuestName+".Hunt");
							PlayerQuestList.saveConfig();
							GBD_RPG.Quest.Quest_GUI QGUI = new GBD_RPG.Quest.Quest_GUI();
							QGUI.QuestRouter(player, QuestName);
							//퀘스트 완료 메시지//
							break;
						}
					}
				}
			}
		}
		else
		{
			Player[] PartyMember = Main_ServerOption.Party.get(Main_ServerOption.PartyJoiner.get(player)).getMember();
			for(byte counta = 0; counta < PartyMember.length; counta++)
			{
				player = PartyMember[counta];
				if(event.getEntity().getLocation().getWorld() == player.getLocation().getWorld())
				{
					YamlManager Config = YC.getNewConfig("config.yml");
					
					if(event.getEntity().getLocation().distance(player.getLocation()) <= Config.getInt("Party.EXPShareDistance"))
					{
						PlayerQuestList  = YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
						
						Object[] a = PlayerQuestList.getConfigurationSection("Started.").getKeys(false).toArray();
						for(short count = 0; count < a.length; count++)
						{
							String QuestName = (String) a[count];
							short Flow = (short) PlayerQuestList.getInt("Started."+QuestName+".Flow");
							if(PlayerQuestList.getString("Started."+QuestName+".Type").equalsIgnoreCase("Hunt"))
							{
								Object[] MobList = QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Monster").getKeys(false).toArray();
								int Finish = 0;
								for(int counter = 0; counter < MobList.length; counter++)
								{
									String QMobName = QuestList.getString(QuestName+".FlowChart."+Flow+".Monster."+counter+".MonsterName");
									int MAX = QuestList.getInt(QuestName+".FlowChart."+Flow+".Monster."+counter+".Amount");
									String KilledName = "null";
									KilledName = event.getEntity().getName();
									if(event.getEntity().isCustomNameVisible() == true)
									{
										KilledName = event.getEntity().getCustomName();
										if(event.getEntity().getLocation().getWorld().getName().compareTo("Dungeon")==0)
										{
											if(KilledName.length() >= 6)
											{
												if(KilledName.charAt(0)=='§'&&KilledName.charAt(1)=='2'&&
													KilledName.charAt(2)=='§'&&KilledName.charAt(3)=='0'&&
													KilledName.charAt(4)=='§'&&KilledName.charAt(5)=='2')
												{
													KilledName = KilledName.substring(12, KilledName.length());
												}
											}
										}
									}
									if(QMobName.equalsIgnoreCase(KilledName) == true && MAX > PlayerQuestList.getInt("Started."+QuestName+".Hunt."+counter))
									{
										//퀘스트 진행도 알림//
										PlayerQuestList.set("Started."+QuestName+".Hunt."+counter, PlayerQuestList.getInt("Started."+QuestName+".Hunt."+counter)+1);
										PlayerQuestList.saveConfig();
									}
									if(MAX == PlayerQuestList.getInt("Started."+QuestName+".Hunt."+counter))
									{
										Finish = Finish+1;
									}
									if(Finish == MobList.length)
									{
										PlayerQuestList.set("Started."+QuestName+".Type",QuestList.getString(QuestName+".FlowChart."+(PlayerQuestList.getInt("Started."+QuestName+".Flow")+1)+".Type"));
										PlayerQuestList.set("Started."+QuestName+".Flow",PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
										PlayerQuestList.removeKey("Started."+QuestName+".Hunt");
										PlayerQuestList.saveConfig();
										GBD_RPG.Quest.Quest_GUI QGUI = new GBD_RPG.Quest.Quest_GUI();
										QGUI.QuestRouter(player, QuestName);
										//퀘스트 완료 메시지//
										break;
									}
								}
							}
						}	
					}
				}
			}
		}
		return;
	}
}
