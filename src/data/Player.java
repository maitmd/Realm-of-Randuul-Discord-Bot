package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

public class Player implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private List<Campaign> campaigns;
	private List<Character> characters;
	
	public Player(String name) {
		this.name = name;
		characters = new ArrayList<>();
		campaigns = new ArrayList<>();
	}
	
	//Displays this players information
	public void display(Member mem, MessageChannelUnion channel) {
		channel.sendMessage("**" + mem.getEffectiveName() + "**"
				+ "```\nDiscord Name: " + mem.getUser().getName()
				+ "\nJoin Date: " + mem.getTimeJoined().toString().substring(0, mem.getTimeJoined().toString().indexOf(("T")))
				+ "\nNumber of Characters: " + getCharacters().size()
				+ "\nNumber of Campaigns: " + getCampaigns().size() + "```").queue();
	}
	
	//Displays the campaigns this player is in
	public void displayCampaigns(MessageChannelUnion channel) {
		String campaign = "";
		
		if(campaigns.isEmpty()){
			channel.sendMessage("There is only void.").queue();
			return;
		}
		
		for(int i = 0; i < campaigns.size(); i++){
			campaign = campaign + (campaigns.get(i).getDm() == this ?  campaigns.get(i).getName() + "*" :  campaigns.get(i).getName()) + ", ";
		}
		
		campaign = campaign.substring(0, campaign.length()-2);
		channel.sendMessage("```" + campaign + "```").queue();
	}
	
	//Displays this player's characters
	public void displayCharacters(MessageChannelUnion channel){
		String chars = "";
		
		if(getCharacters().isEmpty()){
			channel.sendMessage("They hold no souls.").queue();
			return;
		}
		
		for(int i = 0; i < getCharacters().size(); i++){
			chars = chars + getCharacters().get(i).getName() + ", ";
		}
		
		chars = chars.substring(0, chars.length()-2);
		channel.sendMessage("**" + getName() + "'s Characters: **\n" + chars).queue();
	}
	
	//Sets this players name
	public void setName(String name) {
		this.name = name;
	}
	
	//Removes a character from this player's character list
	public void removeCharacter(String name){
		for(int i = getCharacters().size(); i > 0 ; i--) {
			if(getCharacters().get(i).getName().equalsIgnoreCase(name)) {
				getCharacters().remove(i);
			}
		}
	}
	
	//Adds a character to this player's character list
	public void addCharacter(String name) {
		characters.add(new Character(this, name));
	}
	
	//Adds a campaign to this character
	public void addCampaign(String name) {
		campaigns.add(new Campaign(name, this));
	}
	
	//Removes a character from this character
	public void removeCampaign(MessageChannelUnion channel, String name) {
		for(Campaign temp : campaigns) {
			if(temp.getName().equals(name) && temp.getDm() == this){
				campaigns.remove(temp);
				channel.sendMessage("Return to the void").queue();
				return;
			}
		}
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
	
	//Returns a character matching the name given
		public Campaign getCampaign(String name){
			for(int i = 0; i < campaigns.size(); i++) {
				if(campaigns.get(i).getName().equalsIgnoreCase(name)) {
					return getCampaigns().get(i);
				}
			}
			
			return null;
		}
		
	//Returns the players name
	public String getName() {
		return name;
	}
	
	//Returns the list of campaigns this player is in
	public List<Campaign> getCampaigns() {
		return campaigns;
	}
	
	//Returns the list of characters this player has
	public List<Character> getCharacters() {
		return characters;
	}
}
