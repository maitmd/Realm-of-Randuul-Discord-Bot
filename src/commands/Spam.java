package commands;

import java.util.List;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

public class Spam {
	public Spam(MessageChannelUnion channel, List<Member> mention, Member mem) {
		
		Boolean admin = false;
		List<Member> men = mention;
		String spamTarg;
		String spamMessage;
		
		for(int i = 0; i < mem.getRoles().size(); i++) {
			if(mem.getRoles().get(i).toString().contains("Admin")){
				admin = true;
			}
		}
		
		if(men.size() > 0 && admin) {
			
			spamTarg = men.get(0).getUser().getId();
			if(men.size() > 0 && men.get(0).getUser().getId().equals("549758973369253919")){
				spamTarg = mem.getUser().getId();
			}
			
			spamMessage = "Hey <@" + spamTarg + "> get on!";

			channel.sendMessage(spamMessage).queue();
			channel.sendMessage(spamMessage).queue();
			channel.sendMessage(spamMessage).queue();
			channel.sendMessage(spamMessage).queue();
			channel.sendMessage(spamMessage).queue();
			channel.sendMessage(spamMessage).queue();
			channel.sendMessage(spamMessage).queue();
			channel.sendMessage(spamMessage).queue();
			channel.sendMessage(spamMessage).queue();
			channel.sendMessage(spamMessage).queue();
			channel.sendMessage(spamMessage).queue();
			channel.sendMessage(spamMessage).queue();
			channel.sendMessage(spamMessage).queue();
			channel.sendMessage(spamMessage).queue();
			channel.sendMessage(spamMessage).queue();
			channel.sendMessage(spamMessage).queue();
			channel.sendMessage(spamMessage).queue();
			channel.sendMessage(spamMessage).queue();
			channel.sendMessage(spamMessage).queue();
			channel.sendMessage(spamMessage).queue();
			
			}else {
				channel.sendMessage("That soul isn't present or perhaps you're just too weak.").queue();
			}
	}
}
