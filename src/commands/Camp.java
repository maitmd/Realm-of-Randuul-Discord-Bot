package commands;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import bot.GensoRanduul;
import data.Campaign;
import data.Player;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
public class Camp extends Command{
	public Camp(MessageChannel channel, String content, Member member, List<Member> mention, Player player) {
		super(jda);
		ArrayList<String> args = new ArrayList<String>();
		try{args = getArgs(content, 2);}catch(StringIndexOutOfBoundsException e){return;}
		Campaign campaign = GensoRanduul.getCampaign(args.get(0));
		
		if(mention.size() > 0) {
			if(GensoRanduul.getPlayer(mention.get(0).getUser().getName()) == null) {
				GensoRanduul.addPlayer(new Player(mention.get(0).getUser().getName()));
			}
		}
		
		switch(args.get(1)) {
			case "create":
				args = getArgs(content, 2);
				player.addCampaign(args.get(1));
				campaign = GensoRanduul.getCampaign(args.get(0));
				campaign.display(channel);
			    break;
			case "remove":
				args = getArgs(content, 2);
				player.removeCampaign(channel, campaign.getName());
			    break;
			case "characters":
				args = getArgs(content, 3);
				switch(args.get(2)) {
					case "view":
						campaign.displayCharacters(channel);
						break;
					case "add":
						args = getArgs(content, 4);
						data.Character chara = player.getCharacter(args.get(3));
						campaign.addCharacter(channel, chara);
						try{chara.addCampaign(campaign);}catch(NullPointerException e) {channel.sendMessage("That destiny has yet to be created.").queue(); return;}
						break;
					case "remove":
						args = getArgs(content, 4);
						campaign.removeCharacter(channel, args.get(3), player);
						break;
				}
			    break;
			case "players":
				args = getArgs(content, 3);
				switch(args.get(2)) {
				case "view":
					campaign.displayPlayers(channel);
					break;
				case "remove":
					campaign.removePlayer(channel, GensoRanduul.getPlayer(mention.get(0).getEffectiveName()), player);
					break;
				case "add":
					campaign.addPlayer(channel, player);
					break;
				}
			    break;
			default: 
				campaign.display(channel);
				return;
		}
		
		GensoRanduul.save();
	}
	
	public void create(MessageChannel channel, String name, Player dm) {
		
	}
}
