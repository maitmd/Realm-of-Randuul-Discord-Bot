package data;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Campaign {
	private ArrayList<Character> characters;
	private ZonedDateTime nextSession;
	private ZonedDateTime thisSession;
	private String meetTime;
	private ArrayList<Player> players;
	private ArrayList<Player> invited;
	private String name;
	private Player dm;
	private boolean playing;
	
	public Campaign(OffsetDateTime time, String name, Player dm) {
		this.dm = dm;
		this.name = name;
		thisSession = time.toZonedDateTime();
		playing = false;
		invited = new ArrayList<Player>();
		players = new ArrayList<Player>();
		characters = new ArrayList<Character>();
	}
	
	public void addCharacter(Character character){
		for(Player temp : players){
			if(temp == character.getPlayer()){
				characters.add(character);
			}
		}
	}
	
	public void addPlayers(Player player){
		for(Player temp : invited){
			if(player == temp){
				players.add(player);
			}
		}
	}
	
	public ZonedDateTime getNextSession(){
		return null;
	}
}
