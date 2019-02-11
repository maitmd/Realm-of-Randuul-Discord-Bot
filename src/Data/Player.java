package Data;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Player {

	public String name;
	public ZonedDateTime joinDate;
	public ArrayList<Campaign> campaigns;
	public ArrayList<Character> characters;
	
	public Player(ArrayList<Character> chars, ArrayList<Campaign> camp, String name) {
		characters = chars;
		campaigns = camp;
		this.name = name;
	}
	
	public void getInfo() {
		String info = "";
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCampaigns(ArrayList<Campaign> camp) {
		campaigns = camp;
	}
	
	public void setCharacters(ArrayList<Character> chars) {
		characters = chars;
	}
	
	public void setJoinDate(ZonedDateTime join) {
		joinDate = join;
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
	
	public ZonedDateTime getJoinDate() {
		return joinDate;
	}
}
