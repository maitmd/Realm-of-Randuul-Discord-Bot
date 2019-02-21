package data;

import java.io.Serializable;
import java.util.ArrayList;

import commands.RollDice;
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
		
		channel.sendMessage("\n\n" + "**" + name + "**\r\n" + 
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
				"Carrying " + bag.size() + " Items" + "\r\n" +
				"```").queue();
	}
	
	public void displayeSpells(MessageChannel channel){
		String cantrip = ""; String one = ""; String two = ""; String three = ""; String four = ""; 
		String five = ""; String six = ""; String seven = ""; String eight = ""; String nine = "";
		
		for(int i = 0; i < spells.size(); i++){
			if(spells.get(i).getLevel() == 0){
				cantrip = cantrip + spells.get(i).getName() + ", ";
			}else if(spells.get(i).getLevel() == 1){
				one = one + spells.get(i).getName() + ", ";
			}else if(spells.get(i).getLevel() == 2){
				two = two + spells.get(i).getName() + ", ";
			}else if(spells.get(i).getLevel() == 3){
				three = three + spells.get(i).getName() + ", ";
			}else if(spells.get(i).getLevel() == 4){
				four = four + spells.get(i).getName() + ", ";
			}else if(spells.get(i).getLevel() == 5){
				five = five + spells.get(i).getName() + ", ";
			}else if(spells.get(i).getLevel() == 6){
				six = six + spells.get(i).getName() + ", ";
			}else if(spells.get(i).getLevel() == 7){
				seven = seven + spells.get(i).getName() + ", ";
			}else if(spells.get(i).getLevel() == 8){
				eight = eight + spells.get(i).getName() + ", ";
			}else if(spells.get(i).getLevel() == 9){
				nine = nine + spells.get(i).getName() + ", ";
			}
		}
		
		cantrip = cantrip.contains(",") ? cantrip.substring(0, cantrip.length()-2) : "-";
		one = one.contains(",") ? one.substring(0, one.length()-2) : "-";
		two = two.contains(",") ? two.substring(0, two.length()-2) : "-";
		three = three.contains(",") ? three.substring(0, three.length()-2) : "-";
		four = four.contains(",") ? four.substring(0, four.length()-2) : "-";
		five = five.contains(",") ? five.substring(0, five.length()-2) : "-";
		six = six.contains(",") ? six.substring(0, six.length()-2) : "-";
		seven = seven.contains(",") ? seven.substring(0, seven.length()-2) : "-";
		eight = eight.contains(",") ? eight.substring(0, eight.length()-2) : "-";
		nine = nine.contains(",") ? nine.substring(0, nine.length()-2) : "-";
		
		channel.sendMessage("**Cantrips:** \n```" + cantrip + "```**\nLevel 1:** \n```" + one + "```\n**Level 2:** \n```" + two 
				+ "```\n**Level 3:** \n```" + three + "```\n**Level 4:** \n```" + four + "```\n**Level 5:** \n```" + five 
				+ "```\n**Level 6:** \n```" + six + "```\n**Level 7:** \n```" + seven + "```\n**Level 8:** \n```"  
				+ eight + "```\n**Level 9:** \n```" + nine + "```").queue();
	}
	
	public void displayBag(MessageChannel channel){
		String items = "";
		
		for(int i = 0; i < bag.size(); i++){
			items = items + bag.get(i) + ", ";
		}
		
		items = items.contains(",") ? items.substring(0, items.length()-2) : "-";
		channel.sendMessage("```" + items + "```").queue();;
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
		setLevel(level+1);
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
	
	public void removeSpell(String name){
		for(int i = 0; i < spells.size(); i++){
			if(spells.get(i).getName().equals(name)){
				spells.remove(i);
			}
		}
	}
	public void addSpell(String name, int level){
		spells.add(new Spell(name, level));
	}
	
	public void addItem(String name){
		bag.add(name);
	}
	
	public void removeItem(String name){
		for(int i = 0; i < bag.size(); i++){
			if(bag.get(i).equals(name)){
				bag.remove(i);
			}
		}
	}
	
	public Player getPlayer(){
		return player;
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
