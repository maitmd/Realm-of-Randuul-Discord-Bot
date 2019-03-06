package data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import net.dv8tion.jda.core.entities.MessageChannel;

public class Campaign implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static LocalDateTime now = LocalDateTime.now();
	private ArrayList<Character> characters;
	private String meetTime;
	private ZonedDateTime nextSession;
	private ArrayList<Player> players;
	private ArrayList<Player> invited;
	private String name;
	private Player dm;
	private boolean playing;
	
	public Campaign(String time, String name, Player dm, String meet) {
		this.dm = dm;
		this.name = name;
		meetTime = meet;
		playing = false;
		invited = new ArrayList<Player>();
		players = new ArrayList<Player>();
		characters = new ArrayList<Character>();
		nextSession = convertDate("2019-03-08 06:00", "-5");
		System.out.println(nextSession);
	}
	
	public void addCharacter(MessageChannel channel, Character character){
		for(Player temp : players){
			if(temp == character.getPlayer()){
				characters.add(character);
				return;
			}
		}
		
		channel.sendMessage("Go back to your own universe.");
	}
	
	public void addPlayers(MessageChannel channel, Player player){
		for(Player temp : invited){
			if(player == temp){
				players.add(player);
				return;
			}
		}
		channel.sendMessage("You aren't on the list. :alright:");
	}
	
	public ZonedDateTime convertDate(String time, String zone) {
		DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime next = LocalDateTime.parse(time, parser);
		ZonedDateTime zoned = ZonedDateTime.of(next, ZoneId.of(zone));
		
		return zoned;
	}
	
	public ZonedDateTime getDiffTimeZone(String zone) {
		ZonedDateTime time = ZonedDateTime.of(nextSession.toLocalDateTime(), ZoneId.of(zone));
		return time;
	}
	
	public String getMeetTime() {
		return meetTime;
	}
	
	public String getName() {
		return name;
	}
	
	public Player getDm() {
		return dm;
	}
	
	public boolean isPlaying() {
		return playing;
	}
	
	public void setMeetTime(String meet) {
		meetTime = meet;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDm(Player player) {
		dm = player;
	}
	
	public void play() {
		playing = !playing;
	}
}
