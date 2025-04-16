package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import data.Campaign;
import data.Player;
import data.Server;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;

public class DataHandler {

    private static List<Player> players = new ArrayList<>();
    private static List<Server> servers = new ArrayList<>();
	private static List<ThreadChannel> threadsToRemove = new ArrayList<>();

    private static String basePath = "";
	private static String twitterApiKey = "";
	private static String twitterApiSecret = "";
	private static String twitterOauthKey = "";
	private static String twitterOauthSecret = "";
	private static String twitterBearerToken = "";
    private static String discordToken = "";

    private DataHandler(){

    };

    //Reads players.txt and retrieves all player and campaign objects stored there.
	@SuppressWarnings("unchecked")
	public static void getStoredData() {
		try (FileInputStream playerFI = new FileInputStream(new File("data/players.player"));
		FileInputStream threadFI = new FileInputStream(new File("data/threads.thread"));
		FileInputStream serverFI = new FileInputStream(new File("data/servers.server"));){
			
			
			ObjectInputStream oi;
			
			oi = new ObjectInputStream(playerFI);
			players = ((List<Player>)oi.readObject());
			oi = new ObjectInputStream(threadFI);
			threadsToRemove = ((List<ThreadChannel>)oi.readObject());
			oi = new ObjectInputStream(serverFI);
			servers = ((List<Server>)oi.readObject());

			oi.close();
		}catch(IOException e) {
			System.err.println("Could not open one of the file streams! " + e);
		}catch(ClassNotFoundException e){
			System.err.println("Could not find the class loaded in!");
		}
	}
	
	//Writes this player object to players.txt
	public static void save() {
		try (FileOutputStream playerFS = new FileOutputStream("data/players.player");
		FileOutputStream threadFS = new FileOutputStream("data/threads.thread");
		FileOutputStream serverFS = new FileOutputStream("data/servers.server");) {

			ObjectOutputStream o;
			
			o = new ObjectOutputStream(playerFS);
			o.writeObject(players);
			o = new ObjectOutputStream(threadFS);
			o.writeObject(threadsToRemove);
			o = new ObjectOutputStream(serverFS);
			o.writeObject(servers);

			o.close();
		}catch(IOException e) {
			System.err.println("Could not write " + e);
		}
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

	public static void addServer(Server server){
		servers.add(server);
	}

    public static boolean removeServer(String serverName){
		for (Server server : servers) {
            if (server.getServerName().equals(serverName)) {
                return servers.remove(server);
            }
        }

        return false;
	}

    public static boolean removeServer(Server server){
        return servers.remove(server);
	}

    public static Server getServer(String serverName) {
        for (Server server : servers) {
            if (server.getServerName().equals(serverName)) {
                return server;
            }
        }

        return null;
    }

    public static List<Server> getAllServers() {
        return servers;
    }

	public static void loadEnv() {
		Dotenv dotenv = Dotenv.load();

		twitterApiKey = dotenv.get("TWITTER_KEY");
        twitterApiSecret = dotenv.get("TWITTER_SECRET");
        twitterOauthKey = dotenv.get("TWITTER_OAUTH");
        twitterOauthSecret = dotenv.get("TWITTER_OAUTH_SECRET");
        twitterBearerToken = dotenv.get("BEARER_TOKEN");
        discordToken = dotenv.get("DISCORD_TOKEN");
        basePath = dotenv.get("BASE_SERVER_PATH");
	}

    public static String getTwitterKey() {
        return twitterApiKey;
    }

    public static String getTwitterSecret() {
        return twitterApiSecret;
    }

    public static String getTwitterOauth() {
        return twitterOauthKey;
    }

    public static String getTwitterOauthSecret() {
        return twitterOauthSecret;
    }

    public static String getTwitterBearerToken() {
        return twitterBearerToken;
    }

    public static String getBaseServerPath() {
        return basePath;
    }

    public static String getDiscordToken() {
        return discordToken;
    }
}
