package commands;

import java.util.ArrayList;
import java.util.List;

import data.Campaign;
import data.Player;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import services.DataHandler;
public class Camp extends Command{
	public Camp(MessageChannelUnion channel, String content, Member member, List<Member> mention, Player messageOwner) {
		super(jda, twitter);
		ArrayList<String> args;
		args = getArgs(content, 2);

		Campaign campaign = DataHandler.getCampaign(args.get(1));

		if(!mention.isEmpty()) {
			for(Member mem : mention) {
				if(DataHandler.getPlayer(mem.getUser().getName()) == null) {
					DataHandler.addPlayer(new Player(mem.getUser().getName()));
				}
			}
		}
		
		switch(args.get(1)) {
			case "create":
				campaign = new Campaign(args.get(1), messageOwner);
				messageOwner.addCampaign(campaign);
				campaign.display(channel);
			    break;
			case "remove":
				if (campaign == null) {
					channel.sendMessage("That universe does not exist").queue();
					return;
				}
				DataHandler.getAllCampaigns().remove(campaign);
			    break;
			case "characters":
				if (campaign == null) {
					channel.sendMessage("That universe does not exist").queue();
					return;
				}
				args = getArgs(content, 4);
				switch(args.get(2)) {
					case "view":
						campaign.displayCharacters(channel);
						break;
					case "add":
						data.Character character = DataHandler.getCharacter(args.get(3));
						campaign.addCharacter(channel, character);
						try{character.addCampaign(campaign);}catch(NullPointerException e) {channel.sendMessage("That destiny has yet to be created.").queue(); return;}
						break;
					case "remove":
						campaign.removeCharacter(channel, args.get(3), messageOwner);
						break;
				}
			    break;
			case "players":
				if (campaign == null) {
					channel.sendMessage("That universe does not exist").queue();
					return;
				}
				args = getArgs(content, 3);
				switch(args.get(2)) {
				case "view":
					campaign.displayPlayers(channel);
					break;
				case "remove":
					campaign.removePlayer(channel, DataHandler.getPlayer(mention.get(0).getUser().getName()), messageOwner);
					break;
				}
			case "invite":
				if (campaign == null) {
					channel.sendMessage("That universe does not exist").queue();
					return;
				}
				if(!mention.isEmpty()){
					for(Member temp : mention) {
						campaign.invitePlayer(channel, DataHandler.getPlayer(member.getUser().getName()), DataHandler.getPlayer(temp.getUser().getName()));
					}
				}
			    break;
			case "view": 
				if (campaign == null) {
					channel.sendMessage("That universe does not exist").queue();
					return;
				}
				campaign.display(channel);
				break;
			case "join":
				if (campaign == null) {
					channel.sendMessage("That universe does not exist").queue();
					return;
				}
				campaign.addPlayer(channel, DataHandler.getPlayer(member.getUser().getName()));
				break;
			case "nextsession":
				if(campaign.getDm() != DataHandler.getPlayer(member.getUser().getName())) {
					channel.sendMessage("This is not your universe..").queue();
					return;
				}
				
				args = getArgs(content, 5);
				campaign.nextSession(args.get(2), Integer.parseInt(args.get(3)), args.get(4));
				channel.sendMessage("Until next time..").queue();
				break;
			case "schedule":
				if(campaign.getDm() != DataHandler.getPlayer(member.getUser().getName())) {
					channel.sendMessage("This is not your universe..").queue();
					return;
				}
				
				args = getArgs(content, 5);
				campaign.setNextSession(args.get(2) + " " + args.get(3), args.get(4));
				channel.sendMessage("Your time of communion has been noted.").queue();
				break;
			case "meettime":
				if(campaign.getDm() != DataHandler.getPlayer(member.getUser().getName())) {
					channel.sendMessage("This is not your universe..").queue();
					return;
				}
				
				args = getArgs(content, 3);
				campaign.setMeetTime(args.get(2));
				channel.sendMessage("See you then.").queue();
				break;
		}
		
		DataHandler.save();
	}
	
	public void create(MessageChannelUnion channel, String name, Player dm) {
		
	}
}
