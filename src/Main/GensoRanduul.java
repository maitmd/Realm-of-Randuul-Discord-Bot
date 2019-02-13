package Main;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import Commands.Command;
import Data.Campaign;
import Data.Player;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Member;



public class GensoRanduul {
	
	static ArrayList<Campaign> campaigns = new ArrayList<Campaign>();
	static ArrayList<Player> players = new ArrayList<Player>();
	
	public static void main(String[] args) throws LoginException, InterruptedException {

			JDA api = new JDABuilder("NTQzMjkwMDE5MDM3NTc3MjI2.Dz6rRg.PF4ieCwenWr9zo7iEYpFFUcaO_g").build();
			Command listener = new Command();
			api.addEventListener(listener);
	}
	
	public static void addPlayer(Player player) {
		players.add(player);
	}
	
	public static void addCampaign(Campaign campaign) {
		campaigns.add(campaign);
	}
	
	public static ArrayList<Campaign> getCampaigns() {
		return campaigns;
	}
	
	public static ArrayList<Player> getPlayers(){
		return players;
	}
	
	public static Campaign getCampaign(String name) {
		for(int i = 0; i < campaigns.size(); i++) {
			//if(campaigns.get(i).getName().equals(name)) return campaigns.get(i);
		}
		
		return null;
	}
	
	public static Player getPlayer(Member mem) {
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).getMember() == mem) return players.get(i);
		}
		
		return null;
	}
}
