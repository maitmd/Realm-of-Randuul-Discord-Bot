package commands;

import java.util.List;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

public class Spam extends Command{
	public Spam(MessageChannelUnion channel, List<Member> mention, Member member) {
		super(jda, twitter);
		List<Member> men = mention;
		String spamTarg;
		String spamMessage;
		final int SPAM_NUM = 8;

		for (int i = 0; i < member.getRoles().size(); i++) {
			if (!member.getRoles().get(i).toString().contains("Admin") || !member.getRoles().get(i).toString().contains("Dungeon Master")) {
				return;
			}
		}
		
		if (men.size() > 0) {
			
			spamTarg = men.get(0).getUser().getId();
			if(men.size() > 0 && men.get(0).getUser().getId().equals("549758973369253919")){
				spamTarg = member.getUser().getId();
			}
			
			spamMessage = "Hey <@" + spamTarg + "> get on!";

			for (int i = 0; i < SPAM_NUM; i++) {
				channel.sendMessage(spamMessage).queue();
			}
			
			}else {
				channel.sendMessage("That soul isn't present or perhaps you're just too weak.").queue();
			}
	}

}
