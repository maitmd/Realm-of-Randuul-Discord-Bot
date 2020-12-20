package data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import bot.GensoRanduul;
import net.dv8tion.jda.api.entities.MessageChannel;

public class Campaign implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Character> characters;
	private String meetTime;
	private ZonedDateTime nextSession;
	private ArrayList<Player> players;
	private ArrayList<Player> invited;
	private String name;
	private Player dm;
	private boolean playing;
	
	public Campaign(String name, Player dm) {
		this.dm = dm;
		this.name = name;
		playing = false;
		invited = new ArrayList<Player>();
		players = new ArrayList<Player>();
		characters = new ArrayList<Character>();
		players.add(dm);
		nextSession = getDate("0001-01-01 00:00", "-0");
	}
	
	public void display(MessageChannel channel) {
		channel.sendMessage("**" + name + "**" + "\n" +
				"```Dungeon Master: " + dm.getName() + "\n" +
				"Number of Players: " + players.size() + "\n" +
				"Number of Characters: " + characters.size() + "\n" +
				"Pending Invites: " + invited.size() + "\n" +
				"Meet Time: " + meetTime + "\n" +
				"Next Session: " + (nextSession.getMonthValue() < 10 ? "0" + nextSession.getMonthValue() : nextSession.getMonthValue()) + "-" 
				+ nextSession.getDayOfMonth() + "-" +
				nextSession.getYear() + " " + (nextSession.getHour() > 12 ? nextSession.getHour()-12 : nextSession.getHour()) +
				":" + (nextSession.getMinute() < 10 ? "0" + nextSession.getMinute() : nextSession.getMinute()) + (nextSession.getHour() > 12 ? "PM" : "AM") +"\n" +
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
			plays = temp.getName() + ", " + plays;
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
			if(players.contains(player)) {
				channel.sendMessage("Your soul walks the land already.");
				return;
			}else if(player == temp){
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
			channel.sendMessage(player.getName() + " have you ever created a soul? (!campaign [campaign name] join)").queue();
		}else {
			channel.sendMessage("This is not your world..").queue();
		}
	}
	
	public void removeCharacter(MessageChannel channel, String name, Player dm) {
		data.Character chara = GensoRanduul.getCharacter(name);
		if(dm == this.dm || dm == chara.getPlayer()) {
			for(Character temp : characters) {
				if(temp == chara) {
					characters.remove(temp);
					channel.sendMessage("To dust.").queue();
					return;
				}
			}
		}
	}
	
	public void removePlayer(MessageChannel channel, Player name, Player dm) {
		ArrayList<Character> remove;
		//Make sure this is the dm removing the player
		if(dm == this.dm) {
			//Find the player
			for(Player temp : players) {
				if(temp.equals(name)){
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
	
	public void nextSession(String zone, int jump, String unit) {
		ZonedDateTime next = ZonedDateTime.of(nextSession.toLocalDateTime(), ZoneId.of(zone));
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
	
	public void play(Boolean play, Player dm) {
		if(dm == this.dm)
		playing = play;
	}
	
	public void setNextSession(String session, String zone) {
		nextSession = getDate(session, zone);
	}
}
