package data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;

public class Player implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private OffsetDateTime joinDate;
	private ArrayList<Campaign> campaigns;
	private ArrayList<Character> characters;
	
	public Player(String name) {
		this.name = name;
		characters = new ArrayList<Character>();
		campaigns = new ArrayList<Campaign>();
	}
	
	//Writes this player object to players.txt
	public void save() {
		try {
			FileOutputStream f = new FileOutputStream(new File("players.txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);
			
			o.writeObject(this);
			o.close();
		}catch(Exception e) {}
	}
	
	//Displays this players information
	public void display(Member mem, MessageChannel channel) {
		channel.sendMessage("**" + mem.getEffectiveName() + "**"
				+ "```\nDiscord Name: " + mem.getUser().getName()
				+ "\nJoin Date: " + mem.getJoinDate().toString().substring(0, mem.getJoinDate().toString().indexOf(("T")))
				+ "\nNumber of Characters: " + getCharacters().size()
				+ "\nNumber of Campaigns: " + getCampaigns().size() + "```").queue();
	}
	
	//Displays this player's characters
	public void displayCharacters(MessageChannel channel){
		String chars = "";
		
		if(getCharacters().size() == 0){
			channel.sendMessage("They hold no souls.").queue();
			return;
		}
		
		for(int i = 0; i < getCharacters().size(); i++){
			chars = chars + getCharacters().get(i).getName() + ", ";
			if(i == getCharacters().size()-1){
				chars = chars.substring(chars.indexOf(chars), chars.length()-2);
			}
		}
		
		channel.sendMessage("**" + getName() + "'s Characters: **\n" + chars).queue();
	}
	
	//Sets this players name
	public void setName(String name) {
		this.name = name;
	}
	
	//Removes a character from this player's character list
	public void removeCharacter(String name){
		for(int i = 0; i < getCharacters().size(); i++) {
			if(getCharacters().get(i).getName().equalsIgnoreCase(name)) {
				getCharacters().remove(i);
			}
		}
	}
	
	//Adds a character to this player's character list
	public void addCharacter(String name) {
		characters.add(new Character(this, name));
	}
	
	//Returns a character matching the name given
	public Character getCharacter(String name){
		for(int i = 0; i < getCharacters().size(); i++) {
			if(getCharacters().get(i).getName().equalsIgnoreCase(name)) {
				return getCharacters().get(i);
			}
		}
		
		return null;
	}
	
	//Returns the players name
	public String getName() {
		return name;
	}
	
	//Returns the list of campaigns this player is in
	public ArrayList<Campaign> getCampaigns() {
		return campaigns;
	}
	
	//Returns the list of characters this player has
	public ArrayList<Character> getCharacters() {
		return characters;
	}
	
	//Returns the dates this player joined the server
	public OffsetDateTime getJoinDate(){
		return joinDate;
	}
}
