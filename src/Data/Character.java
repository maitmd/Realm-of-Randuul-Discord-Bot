package Data;

import java.util.ArrayList;
import Commands.RollDice;
import net.dv8tion.jda.core.entities.MessageChannel;

public class Character {
	
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
	private int attuedItems;
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
}
