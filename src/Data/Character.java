package Data;

import java.io.Serializable;
import java.util.ArrayList;
import Commands.RollDice;
import net.dv8tion.jda.core.entities.MessageChannel;

public class Character implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String[] RACES = {"Dragonborn", "Dwarf", "Elf", 
									"Gnome", "Half-Elf", "Halfling", "Half-Orc", "Human", 
									"Tiefling", "Aarakocra", "Genasi", "Golitah", "Dwarf", 
									"Aasimar", "Goblin", "Firbolg", "Kenku", "Kobold", 
									"Hobgoblin", "Lizarkfolk", "Orc", "Tabaxi", "Yuan-ti", 
									"Changeling", "Shifter", "Warforged", "Loxodon", "Centaur"};
	private final String[] CLASSES = {"Barbarian", "Bard", "Cleric", "Fighter", 
									  "Monk", "Paladin", "Ranger", "Rogue", 
									  "Sorcerer", "Warlock", "Wizard", "Blood Hunter", "Druid", };
	private int level;
	private String race;
	private String charClass;
	private String name;
	private Player player;
	private Campaign campaign;
	private int ac;
	private int hp;
	private int speed;
	private int inte;
	private int str;
	private int dex;
	private int con;
	private int wis;
	private int cha;
	private ArrayList<String> bag;
	private ArrayList<Spell> spells;
	private ArrayList<String> feats;
	private ArrayList<String> proficiencies;
	private int attunedItems;
	
	public Character(Player player, String name) {
		this.player = player;
		this.name = name;
		this.attunedItems = 0;
		spells = new ArrayList<Spell>();
		feats = new ArrayList<String>();
		bag = new ArrayList<String>();
		proficiencies = new ArrayList<String>();
	}
	
	public String getName(){
		return name;
	}
	
	public void display(MessageChannel channel) {
		
		channel.sendMessage("**" + name + "**\r\n" + 
				"```" + player.getName() + "'s Character\r\n" + 
				"Campaign: [Campaign]\r\n" + 
				"Level: " + level + "\r\n" + 
				"Class: " + charClass + "\r\n" + 
				"Race: " + race + "\r\n" + 
				"\r\n" + 
				"AC: " + ac + "\r\n" + 
				"HP: " + hp + "\r\n" + 
				"Speed: " + speed + "\r\n" + 
				"\r\n" + 
				"Con: " + con + " (" + (getModifier(con) > 0 ? "+" : "") + getModifier(con) + ")" + "\r\n" + 
				"Str: " + str + " (" + (getModifier(str) > 0 ? "+" : "") + getModifier(str) + ")" +"\r\n" + 
				"Dex: " + dex + " (" + (getModifier(dex) > 0 ? "+" : "") + getModifier(dex) + ")" + "\r\n" + 
				"Int: " + inte + " (" + (getModifier(inte) > 0 ? "+" : "") + getModifier(inte) + ")" + "\r\n" + 
				"Wis: " + wis + " (" + (getModifier(wis) > 0 ? "+" : "") + getModifier(wis) + ")" + "\r\n" + 
				"Cha: " + cha + " (" + (getModifier(cha) > 0 ? "+" : "") + getModifier(cha) + ")" + "\r\n" +
				"\r\n" +
				"Attuned Items: " + attunedItems +  "\r\n" +
				"Spells Known: " + spells.size() +  "\r\n" +
				"Spells Prepared: " + getSpellsPrepared() +  "\r\n" +
				"Carrying " + bag.size() + " Items" + "\r\n" +
				"```").queue();
	}
	
	public void generate() {
		int[] stat = new int[1];
		int classs = (int)(Math.random()*CLASSES.length);
		int race = (int)(Math.random()*RACES.length);
		
		setClass(CLASSES[classs]);
		setRace(RACES[race]);
		
		stat = RollDice.rollStats(stat);
		setStat("con", stat[0]);
		
		stat = RollDice.rollStats(stat);
		setStat("str", stat[0]);
		
		stat = RollDice.rollStats(stat);
		setStat("dex", stat[0]);
		
		stat = RollDice.rollStats(stat);
		setStat("int", stat[0]);
		
		stat = RollDice.rollStats(stat);
		setStat("wis", stat[0]);
		
		stat = RollDice.rollStats(stat);
		setStat("cha", stat[0]);
		
		setSpeed(30);
		setAC(getModifier(dex)+10);
		setHP((int)(Math.random()*9)+10 + getModifier(con)*level);
		setLevel(1);
	}
	
	public void levelUp() {
		setLevel(level++);
	}
	
	public void attune(MessageChannel channel){
		if(attunedItems >= 3){
			attunedItems = 3;
			channel.sendMessage("That is too many for you to handle..");
		}else{
			attunedItems++;		
		}
	}
	
	public void setLevel(int levelUp) {
		level = levelUp;
	}
	
	public void setClass(String classs) {
		this.charClass = classs;
	}
	
	public void setRace(String race) {
		this.race = race;
	}
	
	public void setAC(int ac) {
		this.ac = ac;
	}
	
	public void setHP(int hp) {
		this.hp = hp;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public void setStat(String stat, int value) {
		String formatted = stat.toLowerCase();
		switch(formatted) {
			case "con":
				con = value;
				break;
			case "str":
				str = value;
				break;
			case "dex":
				dex = value;
				break;
			case "int":
				inte = value;
				break;
			case "wis":
				wis = value;
				break;
			case "cha":
				cha = value;
				break;
		}
	}
	
	public void addSpell(String name, int level){
		spells.add(new Spell(name, level));
	}
	
	public void addItem(String name){
		bag.add(name);
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public int getSpellsPrepared(){
		int prep = 0;
		for(int i = 0; i < spells.size(); i++){
			if(spells.get(i).isPrepared()){
				prep++;
			}
		}
		
		return prep;
	}
	
	public int getModifier(int stat){
		int mod = -1;

			if(stat%2 != 0){
				mod = (stat-1)-10;
			}else{
				mod = stat-10;
			}
			
		return mod/2;
	}
}
