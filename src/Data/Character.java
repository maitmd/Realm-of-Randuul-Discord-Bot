package Data;

import java.util.ArrayList;
import Commands.RollDice;

public class Character {
	
	private int level;
	private String race;
	private String classChar;
	private Player player;
	private Campaign campaign;
	private String location;
	private int AC;
	private int HP;
	private int Speed;
	private int Int;
	private int Str;
	private int Dex;
	private int Con;
	private int Wis;
	private int Char;
	private ArrayList<String> bag;
	private ArrayList<String> spells;
	private ArrayList<String> feats;
	private ArrayList<String> proficienies;
	private int attuedItems;
	private int HitDice;
	private int HDAvailable;
	private int profBonus;
	
	public Character(Player player, Campaign campaign) {
		this.player = player;
		this.campaign = campaign;
	}
}
