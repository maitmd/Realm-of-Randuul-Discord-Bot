package bot;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.security.auth.login.LoginException;

import commands.Command;
import data.Campaign;
import data.Player;
import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.signature.TwitterCredentials;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;



public class GensoRanduul{
	private static List<Player> players = new ArrayList<>();
	private static List<ThreadChannel> threadsToRemove = new ArrayList<>();
	
	public static void main(String[] args) throws LoginException, InterruptedException, IOException {
			// Building the JDA, logging the bot in, adding a Command class as a listener, and reading stored member/player data.
		List<String> key = Files.readAllLines(Path.of("./key.env"));
		JDA api = JDABuilder.createDefault((String)key.get(0)).enableIntents(GatewayIntent.MESSAGE_CONTENT).build();
		TwitterClient client = new TwitterClient(TwitterCredentials.builder()
                .accessToken((String) key.get(3))
                .accessTokenSecret((String) key.get(4))
                .apiKey((String) key.get(1))
                .apiSecretKey((String) key.get(2))
                .build());
		
		api.addEventListener(new Command(api, client));
		
		getStoredData();
		writeKey(key);
		startTimeTracker();
	}
	
	//Reads players.txt and retrieves all player and campaign objects stored there.
	public static void getStoredData() {
		try{
			FileInputStream playerFI = new FileInputStream(new File("players.player"));
			FileInputStream threadFI = new FileInputStream(new File("threads.threads"));
			ObjectInputStream oi;
			
			oi = new ObjectInputStream(playerFI);
			players = ((List<Player>)oi.readObject());
			oi = new ObjectInputStream(threadFI);
			threadsToRemove = ((List<ThreadChannel>)oi.readObject());

			oi.close();
			playerFI.close();
			threadFI.close();
		}catch(IOException e) {
			System.err.println("Could not open one of the file streams!");
		}catch(ClassNotFoundException e){
			System.err.println("Could not find the class loaded in!");
		}
	}
	
	//Writes this player object to players.txt
	public static void save() {
		try {
			FileOutputStream playerFS = new FileOutputStream("players.player");
			FileOutputStream threadFS = new FileOutputStream("threads.threads");
			ObjectOutputStream o;
			
			o = new ObjectOutputStream(playerFS);
			o.writeObject(players);
			o = new ObjectOutputStream(threadFS);
			o.writeObject(threadsToRemove);

			o.close();
			playerFS.close();
			threadFS.close();
		}catch(IOException e) {
			System.err.println("Could not open one of the file streams!");
		}
	}
	
	public static void writeKey(List<String> key){
		FileOutputStream fileStream;
		try {
			fileStream = new FileOutputStream("key.ser");
			ObjectOutputStream os = new ObjectOutputStream(fileStream);
			os.writeObject(key);
			os.close();
		} catch (IOException e) {
			System.err.println("Could not open one of the file streams!");
		}
	}

	public static void startTimeTracker(){
		TimeTracker timeTracker = new TimeTracker();
		ScheduledExecutorService timeExec = Executors.newSingleThreadScheduledExecutor();
		timeExec.scheduleAtFixedRate(timeTracker, 0, 30, TimeUnit.SECONDS);
	}

	//Adds a player to the player list
	public static void addPlayer(Player player) {
		players.add(player);
	}

	//Retrieves the entire player list object
	public static List<Player> getPlayers(){
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

	public static void addThreadToRemove(ThreadChannel thread){
		threadsToRemove.add(thread);
	}

	public static List<ThreadChannel> getThreadsToRemove(){
		return threadsToRemove;
	}
}
