package commands;
import java.util.ArrayList;
import java.util.List;

import bot.GensoRanduul;
import data.Player;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Command extends ListenerAdapter{
	
	//Listens for messages that are sent in the server if they contain ! in them then the command is processed and
	//the corresponding child of this class is called.
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {	
		Message msg = event.getMessage();
		String content = msg.getContentDisplay();
		MessageChannel channel = event.getChannel();
		Member mem = event.getMember();
		List<Member> men = msg.getMentionedMembers();
		
		if(content.startsWith("!")) {
			switch(getCommand(content)){
			case "partyinfo":
				break;
			case "roll":
				new RollDice(channel, content);
				break;
			case "nextsession":
				break;
			case "countdown":
				break;
			case "player":
				new PlayerC(channel, content, mem, men);
		    	break;
			case "char":
				new Char(channel, content, mem);
				break;
			}
		}
		
	}
	
	//Splits up the whole command string into specific arguments as needed. Quotes("") override normal
	//procedures and read in between quotes.
	public ArrayList<String> getArgs(String msg, int args){
		String content = msg.substring(msg.indexOf(" ")+1) + " ";
		ArrayList<String> argList = new ArrayList<String>();
		
		for(int i = 0; i < args-1; i++) {
			if(content.charAt(0) == ('\"')){
				content = content.substring(1);			
				argList.add(content.substring(0, content.indexOf('\"')));
				content = content.substring(content.indexOf('\"')+2);
			}else{
				argList.add(content.substring(content.indexOf(content), content.indexOf(" ")));
				content = content.substring(content.indexOf(" ")+1);
			}
		}
		
		argList.add(content.charAt(0) == ('\"') ? content.substring(1, content.indexOf('\"')) : content.substring(content.indexOf(content), content.indexOf(" ")));
		
		return argList;
	}
	
	//Retrieves the command that was issued.
	public String getCommand(String msg) {
		String command;
		if(msg.contains(" ")) {
			command = msg.substring(1, msg.indexOf(" "));
		}else {
			command = msg.substring(1);
		}
		
		return command;
	}
	
	//Runs through all players and looks through each of their characters to see if
	//they have a character matching the name provided.
	public data.Character getCharacter(String name) {
		ArrayList<Player> players = GensoRanduul.getPlayers();
		data.Character character = null;
		
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).getCharacter(name) != null) {
				character = players.get(i).getCharacter(name);
			}
		}
		return character;
	}
}
