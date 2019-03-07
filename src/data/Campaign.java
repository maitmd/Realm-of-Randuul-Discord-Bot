package data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import bot.GensoRanduul;
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
	
	public Campaign(String time, String zone, String name, Player dm, String meet) {
		this.dm = dm;
		this.name = name;
		meetTime = meet;
		playing = false;
		invited = new ArrayList<Player>();
		players = new ArrayList<Player>();
		characters = new ArrayList<Character>();
		nextSession = getDate(time, zone);
		players.add(dm);
	}
	
	public void display(MessageChannel channel) {
		channel.sendMessage("**" + name + "**" + "\n" +
				"```Dungeon Master: " + dm.getName() + "\n" +
				"Number of Players: " + players.size() + "\n" +
				"Number of Characters: " + characters.size() + "\n" +
				"Meet Time: " + meetTime + "\n" +
				"Next Session: " +
				nextSession.getYear() + "-" + nextSession.getDayOfMonth() + "-" + nextSession.getMonthValue() 
				+ " " + (nextSession.getHour() > 12 ? nextSession.getHour()-12 : nextSession.getHour()) +
				":" + (nextSession.getMinute() < 10 ? "0" + nextSession.getMinute() : nextSession.getMinute()) + "\n" +
				"In Session: " + (playing ? "Yes" : "No") + "\n" +
				"```").queue();
	}
	
	public void displayCharacters(MessageChannel channel) {
		String chars = "";
		
		try{characters.get(0);}catch(Exception e) {channel.sendMessage("No heros walk in this universe").queue(); return;}
		
		for(Character temp : characters) {
			chars = temp.getName() + ", " + chars ;
		}
		
		chars = chars.substring(0, chars.length()-2);
		channel.sendMessage("**Characters** ```" + chars + "```").queue();
	}
	
	public void displayPlayers(MessageChannel channel) {
		String plays = "";
		for(Player temp : players) {
			plays = temp.getName() + plays + ", ";
		}
		
		plays = plays.substring(0, plays.length()-2);
		channel.sendMessage("**Players** ```" + plays + "```").queue();
	}
	
	public void addCharacter(MessageChannel channel, Character character){
		try{
			for(Player temp : players){
				if(temp == character.getPlayer() && !characters.contains(character)){
					characters.add(character);
					channel.sendMessage("Your soul has found a home, " + character.getName()).queue();
					return;
				}
			}
		}catch(NullPointerException e) {return;}
		
		channel.sendMessage("Go back to your own universe.").queue();
	}
	
	public void addPlayer(MessageChannel channel, Player player){
		for(Player temp : invited){
			if(player == temp && !players.contains(player)){
				players.add(player);
				invited.remove(player);
				channel.sendMessage("Welcome to the universe.").queue();
				return;
			}
		}
		
		channel.sendMessage("You aren't on the list.").queue();
	}
	
	public void invitePlayer(MessageChannel channel, Player dm, Player player) {
		if(dm == this.dm) {
			invited.add(player);
			channel.sendMessage(player.getName() + " have you ever created a soul?").queue();
		}
	}
	
	public void removeCharacter(MessageChannel channel, String name, Player dm) {
		if(dm == this.dm) {
			for(Character temp : characters) {
				if(temp == GensoRanduul.getCharacter(name)) {
					characters.remove(temp);
					channel.sendMessage("To dust.").queue();
					return;
				}
			}
		}
	}
	
	public void removePlayer(MessageChannel channel, String name, Player dm) {
		ArrayList<Character> remove;
		//Make sure this is the dm removing the player
		if(dm == this.dm) {
			//Find the player
			for(Player temp : players) {
				if(temp.getName().equals(name)) {
					//Remove all of their characters from the campaign
					remove = temp.getCharacters();
					for(int i = 0; i < (remove.size() < characters.size() ? remove.size()-1 : characters.size()-1); i++){
						if(remove.get(i) == characters.get(i)) {
							removeCharacter(channel, remove.get(i).getName(), dm);
						}
					}
					players.remove(temp);
				}
				channel.sendMessage("Farewell.").queue();
			}
		}
	}
	
	public ZonedDateTime getDate(String time, String zone) {
		DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime next = LocalDateTime.parse(time, parser);
		ZonedDateTime zoned = ZonedDateTime.of(next, ZoneId.of(zone));
		
		return zoned;
	}
	
	public void setNextSession(String zone, int jump, String unit) {
		ZonedDateTime next = ZonedDateTime.of(now, ZoneId.of(zone));
		switch(unit) {
		case "week":
			next = next.plusWeeks(jump);
			break;
		case "month":
			next = next.plusMonths(jump);
			break;
		case "day":
			next = next.plusDays(jump);
			break;
		}
		
		nextSession = next;
	}
	
	public ZonedDateTime getNextSession() {
		return nextSession;
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
	
	public void play(Boolean play) {
		playing = play;
	}
}
