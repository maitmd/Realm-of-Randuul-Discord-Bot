package bot;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import commands.Command;
import data.Campaign;
import data.Player;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;



public class GensoRanduul{
	private static ArrayList<Player> players = new ArrayList<Player>();
	
	public static void main(String[] args) throws LoginException, InterruptedException, ClassNotFoundException, IOException {
			// Building the JDA, logging the bot in, adding a Command class as a listener, and reading stored member/player data.
		
		JDA api = new JDABuilder(AccountType.BOT).setToken("").build();
		Command listener = new Command(api);
		api.addEventListener(listener);
		getStoredData();
		
	}
	
	//Reads players.txt and retrieves all player and campaign objects stored there.
	@SuppressWarnings("unchecked")
	public static void getStoredData() {
		try {
			FileInputStream fi = new FileInputStream(new File("players.player"));
			ObjectInputStream oi = new ObjectInputStream(fi);
			
			players = ((ArrayList<Player>)oi.readObject());
			
			oi.close();
			fi.close();
		}catch(Exception e) {
			System.out.println("Error!");
		}
	}
	
	//Writes this player object to players.txt
	public static void save() {
		try {
			FileOutputStream f = new FileOutputStream("players.player");
			ObjectOutputStream o = new ObjectOutputStream(f);
			
			o.writeObject(players);
			
			o.close();
			f.close();
		}catch(Exception e) {}
	}
	
	//Adds a player to the player list
	public static void addPlayer(Player player) {
		players.add(player);
	}

	//Retrieves the entire player list object
	public static ArrayList<Player> getPlayers(){
		return players;
	}
	
	//Retrieves a specific player from the player list
	public static Player getPlayer(String mem) {
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).getName().equals(mem)) return players.get(i);
		}
		
		return null;
	}
	
	//Runs through all players and looks through each of their characters to see if
	//they have a character matching the name provided.
	public static data.Character getCharacter(String name) {
		data.Character character = null;
		
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).getCharacter(name) != null) {
				character = players.get(i).getCharacter(name);
			}
		}
		
		return character;
	}
	
	public static Campaign getCampaign(String name) {
		Campaign campaign = null;
		
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).getCampaign(name) != null) {
				campaign = players.get(i).getCampaign(name);
			}
		}
		return campaign;
	}
}
