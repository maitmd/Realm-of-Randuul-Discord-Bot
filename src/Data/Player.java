package Data;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import net.dv8tion.jda.core.entities.Member;

public class Player {

	public String name;
	public OffsetDateTime joinDate;
	public ArrayList<Campaign> campaigns;
	public ArrayList<Character> characters;
	private Member member;
	private int numCharacters;
	private int numCampaigns;
	
	public Player(Member member, String name) {
		this.member = member;
		this.name = name;
		characters = new ArrayList<Character>();
		campaigns = new ArrayList<Campaign>();
		joinDate = member.getJoinDate();
	}
	
	public void getInfo() {
		String info = "";
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void addCharacter(String name) {
		characters.add(new Character(this, name));
	}
	
	public Character getCharacter(String name){
		for(int i = 0; i < getCharacters().size(); i++) {
			if(getCharacters().get(i).getName().equalsIgnoreCase(name)) {
				return getCharacters().get(i);
			}
		}
		
		return null;
	}
	
	public Member getMember() {
		return member;
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
