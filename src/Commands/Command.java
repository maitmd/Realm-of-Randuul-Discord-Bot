package Commands;
import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Command extends ListenerAdapter{
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {	
		Message msg = event.getMessage();
		String content = msg.getContentDisplay();
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
			case "char":
				new Char(channel, content, mem);
				break;
			}
		}
		
	}
	
	public ArrayList<String> getArgs(String msg, int args){
		String content = msg.substring(msg.indexOf(" ")+1);
		String split;
		ArrayList<String> argList = new ArrayList<String>();
		
		if(args == 1) {
			argList.add(content.substring(content.indexOf(content), content.indexOf(" ")));
		}else {
			for(int i = 0; i < args-1; i++) {
				argList.add(content.substring(content.indexOf(content), content.indexOf(" ")));
				content = content.substring(content.indexOf(" ")+1);
			}
		}
		argList.add(content.substring(content.indexOf(" ")+1));
		
		return argList;
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
