package commands;

import java.util.ArrayList;
import java.util.List;

import bot.GensoRanduul;
import data.Player;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class PlayerC extends Command{
	public PlayerC(MessageChannel channel, String content, Member mem, List<Member> mention){
		ArrayList<String> args;
		try{args = getArgs(content, 2);}catch(StringIndexOutOfBoundsException e) {return;}
		List<Member> mentioned = mention;
		User user = mem.getUser();
		Player player = GensoRanduul.getPlayer(user.getName());
		
		//Checks to see if the player exists, if not it adds the player to the bots list.
		if(player == null){
			GensoRanduul.addPlayer(new Player(user.getName()));
			player = GensoRanduul.getPlayer(user.getName());
			GensoRanduul.save();
		}
		
		//Checks to see if any players were mentioned and if they do makes sure they exist,
		//if not they are added to the bots list.
		if(GensoRanduul.getPlayer(mentioned.get(0).getUser().getName()) == null){
			GensoRanduul.addPlayer(new Player(mentioned.get(0).getUser().getName()));
		}
		
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
	}
	
	//Displays the member/player's information.
	public void view(List<Member> mentioned, MessageChannel channel, Player player, User user, Member mem){
		Member tempmem = null;
		try{tempmem = mentioned.get(0);}catch(Exception e){};

		if(tempmem != null){
			Player temp = GensoRanduul.getPlayer(tempmem.getUser().getName());

			temp.display(tempmem, channel);
		}else{
			if(player == null){
				Player newP = new Player(user.getName());
				GensoRanduul.addPlayer(newP);
				GensoRanduul.save();
				player = GensoRanduul.getPlayer(user.getName());;
			}
			player.display(mem, channel);
		}
	}
	
	//Displays the member/player's character names.
	public void characters(List<Member> mentioned, MessageChannel channel, User user, Player player){
		Member tempmem = null;
		try{tempmem = mentioned.get(0);}catch(Exception e){};
		
		if(tempmem != null){
			Player temp = GensoRanduul.getPlayer(tempmem.getUser().getName());
			if(temp == null){
				GensoRanduul.addPlayer(new Player(tempmem.getUser().getName()));
				temp = GensoRanduul.getPlayer(tempmem.getUser().getName().substring(0));
				GensoRanduul.save();
			}
			
			temp.displayCharacters(channel);
		}else{
			if(player == null){
				Player newP = new Player(user.getName());
				GensoRanduul.addPlayer(newP);
				GensoRanduul.save();
				player = GensoRanduul.getPlayer(user.getName());
			}
			player.displayCharacters(channel);
		}
	}
	
	//Displays the member/player's character names.
		public void campaigns(List<Member> mentioned, MessageChannel channel, User user, Player player){
			Member tempmem = null;
			try{tempmem = mentioned.get(0);}catch(Exception e){};
			
			if(tempmem != null){
				Player temp = GensoRanduul.getPlayer(tempmem.getUser().getName());
				if(temp == null){
					GensoRanduul.addPlayer(new Player(tempmem.getUser().getName()));
					temp = GensoRanduul.getPlayer(tempmem.getUser().getName().substring(0));
					GensoRanduul.save();
				}
				
				temp.displayCampaigns(channel);
			}else{
				player.displayCampaigns(channel);
			}
		}
}
