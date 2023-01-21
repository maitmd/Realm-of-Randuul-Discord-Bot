package commands;

import java.util.ArrayList;
import java.util.List;

import bot.GensoRanduul;
import data.Campaign;
import data.Player;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
public class Camp extends Command{
	public Camp(MessageChannelUnion channel, String content, Member member, List<Member> mention, Player player) {
		super(jda, twitter);
		ArrayList<String> args = new ArrayList<String>();
		args = getArgs(content, 2);
		Campaign campaign = GensoRanduul.getCampaign(args.get(0));
		
		if(mention.size() > 0) {
			for(int i = 0; i < mention.size(); i++) {
				if(GensoRanduul.getPlayer(mention.get(0).getUser().getName()) == null) {
					GensoRanduul.addPlayer(new Player(mention.get(0).getUser().getName()));
				}
			}
		}
		
		switch(args.get(1)) {
			case "create":
				args = getArgs(content, 2);
				player.addCampaign(args.get(0));
				campaign = GensoRanduul.getCampaign(args.get(0));
				campaign.display(channel);
			    break;
			case "remove":
				player.removeCampaign(channel, campaign.getName());
			    break;
			case "characters":
				args = getArgs(content, 4);
				switch(args.get(2)) {
					case "view":
						campaign.displayCharacters(channel);
						break;
					case "add":
						data.Character chara = player.getCharacter(args.get(3));
						campaign.addCharacter(channel, chara);
						try{chara.addCampaign(campaign);}catch(NullPointerException e) {channel.sendMessage("That destiny has yet to be created.").queue(); return;}
						break;
					case "remove":
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
					campaign.removePlayer(channel, GensoRanduul.getPlayer(mention.get(0).getUser().getName()), player);
					break;
				}
			case "invite":
				if(mention.size() > 0){
					for(Member temp : mention) campaign.invitePlayer(channel, GensoRanduul.getPlayer(member.getUser().getName()), GensoRanduul.getPlayer(temp.getUser().getName()));
				}
			    break;
			case "view": 
				campaign.display(channel);
				break;
			case "join":
				campaign.addPlayer(channel, GensoRanduul.getPlayer(member.getUser().getName()));
				break;
			case "nextsession":
				if(campaign.getDm() != GensoRanduul.getPlayer(member.getUser().getName())) {
					channel.sendMessage("This is not your universe..").queue();
					return;
				}
				
				args = getArgs(content, 5);
				campaign.nextSession(args.get(2), Integer.parseInt(args.get(3)), args.get(4));
				channel.sendMessage("Until next time..").queue();
				break;
			case "schedule":
				if(campaign.getDm() != GensoRanduul.getPlayer(member.getUser().getName())) {
					channel.sendMessage("This is not your universe..").queue();
					return;
				}
				
				args = getArgs(content, 5);
				campaign.setNextSession(args.get(2) + " " + args.get(3), args.get(4));
				channel.sendMessage("Your time of communion has been noted.").queue();
				break;
			case "meettime":
				if(campaign.getDm() != GensoRanduul.getPlayer(member.getUser().getName())) {
					channel.sendMessage("This is not your universe..").queue();
					return;
				}
				
				args = getArgs(content, 3);
				campaign.setMeetTime(args.get(2));
				channel.sendMessage("See you then.").queue();
				break;
		}
		
		GensoRanduul.save();
	}
	
	public void create(MessageChannelUnion channel, String name, Player dm) {
		
	}
}
