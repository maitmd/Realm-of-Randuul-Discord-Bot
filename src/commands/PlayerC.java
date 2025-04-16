package commands;

import java.util.ArrayList;
import java.util.List;

import data.Player;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import services.DataHandler;

public class PlayerC extends Command{
	public PlayerC(MessageChannelUnion channel, String content, Member mem, List<Member> mention, Player player){
		super(jda, twitter);
		ArrayList<String> args;
		try{args = getArgs(content, 2);}catch(StringIndexOutOfBoundsException e) {args = getArgs(content, 1);}
		List<Member> mentioned = mention;
		User user = mem.getUser();
		
		//Checks to see if any players were mentioned and if they do makes sure they exist,
		//if not they are added to the bots list.
		if(mention.size() > 0)
			if(DataHandler.getPlayer(mentioned.get(0).getUser().getName()) == null){
				DataHandler.addPlayer(new Player(mentioned.get(0).getUser().getName()));
			}
		
		if(args.size() > 1) {
			switch(args.get(1)){
			case "view":
				view(mentioned, channel, player, user, mem);
				break;
			case "characters":
				characters(mentioned, channel, user, player);
				break;
			case "campaigns":
				player.displayCampaigns(channel);
				break;
			}
		}else {
			switch(args.get(0)){
			case "all":
				String players = "";
				for(Player temp:DataHandler.getPlayers()) {
					players = temp.getName() + ", " + players;
				}
				players = players.substring(0, players.length()-2);
				channel.sendMessage("**Players** ```" + players + "```").queue();
			}
		}
	}
	
	//Displays the member/player's information.
	public void view(List<Member> mentioned, MessageChannelUnion channel, Player player, User user, Member mem){
		Member tempmem = null;
		try{tempmem = mentioned.get(0);}catch(Exception e){};

		if(tempmem != null){
			Player temp = DataHandler.getPlayer(tempmem.getUser().getName());

			temp.display(tempmem, channel);
		}else{
			if(player == null){
				Player newP = new Player(user.getName());
				DataHandler.addPlayer(newP);
				DataHandler.save();
				player = DataHandler.getPlayer(user.getName());;
			}
			player.display(mem, channel);
		}
	}
	
	//Displays the member/player's character names.
	public void characters(List<Member> mentioned, MessageChannelUnion channel, User user, Player player){
		Member tempmem = null;
		try{tempmem = mentioned.get(0);}catch(Exception e){};
		
		if(tempmem != null){
			Player temp = DataHandler.getPlayer(tempmem.getUser().getName());
			if(temp == null){
				DataHandler.addPlayer(new Player(tempmem.getUser().getName()));
				temp = DataHandler.getPlayer(tempmem.getUser().getName().substring(0));
				DataHandler.save();
			}
			
			temp.displayCharacters(channel);
		}else{
			if(player == null){
				Player newP = new Player(user.getName());
				DataHandler.addPlayer(newP);
				DataHandler.save();
				player = DataHandler.getPlayer(user.getName());
			}
			player.displayCharacters(channel);
		}
	}
	
	//Displays the member/player's character names.
		public void campaigns(List<Member> mentioned, MessageChannelUnion channel, User user, Player player){
			Member tempmem = null;
			try{tempmem = mentioned.get(0);}catch(Exception e){};
			
			if(tempmem != null){
				Player temp = DataHandler.getPlayer(tempmem.getUser().getName());
				if(temp == null){
					DataHandler.addPlayer(new Player(tempmem.getUser().getName()));
					temp = DataHandler.getPlayer(tempmem.getUser().getName().substring(0));
					DataHandler.save();
				}
				
				temp.displayCampaigns(channel);
			}else{
				player.displayCampaigns(channel);
			}
		}
}
