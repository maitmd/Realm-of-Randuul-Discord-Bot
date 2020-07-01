package commands;

import java.util.List;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;

public class Spam {
	public Spam(MessageChannel channel, List<Member> mention, Member mem) {
		
		Boolean admin = false;
		List<Member> men = mention;
		for(int i = 0; i < mem.getRoles().size(); i++) {
			if(mem.getRoles().get(i).toString().contains("Admin")){
				admin = true;
			}
			
			if(men.size() > 0 && admin) {
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
				channel.sendMessage("That soul isn't present or perhaps you're just too weak.").queue();
			}
		}
	}
}
