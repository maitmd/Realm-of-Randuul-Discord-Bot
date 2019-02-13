package Commands;
import java.util.List;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Command extends ListenerAdapter{
	private JDA jda;
	
	public Command(JDA jda) {
		this.jda = jda;
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {	
		String content = event.getMessage().getContentDisplay();
		Message msg = event.getMessage();
		MessageChannel channel = event.getChannel();
		List<Member> mem = msg.getMentionedMembers();
		
		if(content.startsWith("!")) {
			switch(getCommand(content)){
			case "partyinfo":
				new PartyInfo(channel, jda);
				break;
			case "roll":
				new RollDice(channel, content);
				break;
			case "nextsession":
				break;
			case "countdown":
				break;
			case "collapse":
				jda.shutdownNow();
				break;
			case "user":
				Member test = mem.get(0);
				channel.sendMessage(test.getEffectiveName() + " " + test.getNickname() + " " + test.getRoles() + " " + test.getJoinDate()).complete();
		    	break;
			}
		}
		
	}
	
	public String getCommand(String msg) {
		String command;
		if(msg.contains(" ")) {
			command = msg.substring(1, msg.indexOf(" "));
		}else {
			command = msg.substring(1);
		}
		
		return command;
	}
}
