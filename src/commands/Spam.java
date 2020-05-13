package commands;

import java.util.List;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;

public class Spam {
	public Spam(MessageChannel channel, List<Member> mention) {
		List<Member> men = mention;
		
		if(men.size() > 0) {
			channel.sendMessage("Hey <@" + men.get(0).getUser().getId() + "> get on!").queue();
			channel.sendMessage("Hey <@" + men.get(0).getUser().getId() + "> get on!").queue();
			channel.sendMessage("Hey <@" + men.get(0).getUser().getId() + "> get on!").queue();
			channel.sendMessage("Hey <@" + men.get(0).getUser().getId() + "> get on!").queue();
			channel.sendMessage("Hey <@" + men.get(0).getUser().getId() + "> get on!").queue();
			channel.sendMessage("Hey <@" + men.get(0).getUser().getId() + "> get on!").queue();
			channel.sendMessage("Hey <@" + men.get(0).getUser().getId() + "> get on!").queue();
			channel.sendMessage("Hey <@" + men.get(0).getUser().getId() + "> get on!").queue();
			channel.sendMessage("Hey <@" + men.get(0).getUser().getId() + "> get on!").queue();
		}else {
			channel.sendMessage("That person doesn't exist :(").queue();
		}
		
		
	}
}
