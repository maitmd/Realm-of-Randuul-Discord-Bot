package Data;

import java.util.ArrayList;
import Commands.RollDice;
import net.dv8tion.jda.core.entities.MessageChannel;

public class Character {
	
	private final String[] RACES = {"Dragonborn", "Dwarf", "Elf", 
									"Gnome", "Half-Elf", "Halfling", "Half-Orc", "Human", 
									"Tiefling", "Aarakocra", "Genasi", "Golitah", "Dwarf", 
									"Aasimar", "Gobling", "Firbolg", "Kenku", "Kobold", 
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
	private ArrayList<String> spells;
	private ArrayList<String> feats;
	private ArrayList<String> proficienies;
	private int attunedItems;
	private int hitDice;
	private int hdAvailable;
	
	public Character(Player player, String name) {
		this.player = player;
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void displayCharacter(MessageChannel channel) {
		channel.sendMessage("`" + name + "`\r\n" + 
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
				"Con: " + con + "\r\n" + 
				"Str: " + str + "\r\n" + 
				"Dex: " + dex + "\r\n" + 
				"Int: " + inte + "\r\n" + 
				"Wis: " + wis + "\r\n" + 
				"Cha: " + cha + "```").queue();
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
		setAC((int)(Math.random()*10)+10);
		setHP((int)(Math.random()*9)+10);
		setLevel(1);
	}
	
	public void levelUp() {
		setLevel(level++);
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
}
