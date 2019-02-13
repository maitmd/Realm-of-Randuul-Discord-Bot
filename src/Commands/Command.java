package Commands;
import java.util.List;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Command extends ListenerAdapter{
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {	
		String content = event.getMessage().getContentDisplay();
		Message msg = event.getMessage();
		MessageChannel channel = event.getChannel();
		Member mem = event.getMember();
		
		if(content.startsWith("!")) {
			switch(getCommand(content)){
			case "partyinfo":
				new PartyInfo(channel);
				break;
			case "roll":
				new RollDice(channel, content);
				break;
			case "nextsession":
				break;
			case "countdown":
				break;
			case "user":
		    	break;
			case "create":
				new Create(channel, content, mem);
				break;
			case "charinfo":
				new CharInfo(channel, content);
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
