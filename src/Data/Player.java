package Data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;

public class Player implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String name;
	public OffsetDateTime joinDate;
	public ArrayList<Campaign> campaigns;
	public ArrayList<Character> characters;
	private int numCharacters;
	private int numCampaigns;
	
	public Player(String name) {
		this.name = name;
		characters = new ArrayList<Character>();
		campaigns = new ArrayList<Campaign>();
		numCharacters = 0;
		numCampaigns = 0;
	}
	
	public void save() {
		try {
			FileOutputStream f = new FileOutputStream(new File("players.txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);
			
			o.writeObject(this);
			o.close();
		}catch(Exception e) {}
	}
	
	public void getInfo() {
		String info = "";
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void addCharacter(String name) {
		characters.add(new Character(this, name));
		numCharacters++;
	}
	
	public Character getCharacter(String name){
		for(int i = 0; i < getCharacters().size(); i++) {
			if(getCharacters().get(i).getName().equalsIgnoreCase(name)) {
				return getCharacters().get(i);
			}
		}
		
		return null;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Campaign> getCampaigns() {
		return campaigns;
	}
	
	public ArrayList<Character> getCharacters() {
		return characters;
	}
	
	public OffsetDateTime getJoinDate() {
		return joinDate;
	}
}
