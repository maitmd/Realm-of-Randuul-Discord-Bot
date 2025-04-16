package commands;
import java.util.ArrayList;
import java.util.List;

import data.Player;
import io.github.redouane59.twitter.TwitterClient;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import services.DataHandler;

public class Command extends ListenerAdapter{
	boolean enabled = true;
	//Listens for messages that are sent in the server if they contain ! in them then the command is processed and
	//the corresponding child of this class is called.

	public static JDA jda;
	public static TwitterClient twitter;
	
	public Command(JDA jda, TwitterClient twitter) {
		Command.jda = jda;
		Command.twitter = twitter;
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {	
		Message msg = event.getMessage();
		String content = msg.getContentDisplay();
		MessageChannelUnion channel = event.getChannel();
		Member mem = event.getMember();
		List<Member> men = msg.getMentions().getMembers();
		Player player = DataHandler.getPlayer(mem.getUser().getName());
		
		//Checking to see if the member has been added into the bot as a player. If not add them.
		if(player == null){
			DataHandler.addPlayer(new Player(mem.getUser().getName()));
			player = DataHandler.getPlayer(mem.getUser().getName());
		}
		
		if(content.startsWith("!")) {
			switch(getCommand(content)){
			case "roll":
				new RollDice(channel, content);
				break;
			case "campaign":
				new Camp(channel, content, mem, men, player);
				break;
			case "player":
				new PlayerC(channel, content, mem, men, player);
		    	break;
			case "char":
				new Char(channel, content, mem, player);
				break;
			case "help":
				new Help(channel, content);
				break;
			case "collapse":
				if(mem.getId().equals("132603306382983169")){
					channel.deleteMessageById(event.getMessageId()).queue();
					jda.shutdown();
				}
				break;
			case "search":
				new Search(channel, content);
				break;
			case "diag":
				ArrayList<String> len = getArgs(content, 2);
				int arg1 = Integer.parseInt(len.get(0));
				int arg2 = Integer.parseInt(len.get(1));
				int diag = (int) Math.sqrt(((arg1*arg1)+(arg2*arg2)));
				
				channel.sendMessage("That will be moving **" + diag + "** feet with those numbers.").queue();
				break;
			case "spam":
				//channel.sendMessage("This feature has been disabled sorry").queue();
				new Spam(channel, men, mem);
				break;
			case "tweet":
				new SendTweet(channel, msg, twitter, mem);
				break;
			case "server":
				new ServerC(channel, content);
				break;
			default:
				channel.sendMessage("I can't do anything like that..").queue();
				break;
			}
		} else if((content.contains("https://twitter.com") || content.contains("https://x.com")) && !content.contains("https://fxtwitter.com")) {
			//convertTwitterLink(content, event);
		}	
	}
	
	//Splits up the whole command string into specific arguments as needed. Quotes("") override normal
	//procedures and read in between quotes.
	public ArrayList<String> getArgs(String msg, int args){
		ArrayList<String> argList = new ArrayList<>();
		if(!msg.contains(" ")) return argList;
		String content = msg.substring(msg.indexOf(" ")+1) + " ";
		for(int i = 0; i < args; i++) {
			if(content.charAt(0) == ('\"')){
				content = content.substring(1);			
				argList.add(content.substring(0, content.indexOf('\"')));
				content = content.substring(content.indexOf('\"')+2);
			}else{
				argList.add(content.substring(0, content.indexOf(" ")));
				content = content.substring(content.indexOf(" ")+1);
			}
		}
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

	public Boolean isAuthorized(Member mem, String groupID){
		Boolean authorized = false;
		for(Role role : mem.getRoles()){
			if(role.getId().equals(groupID)) authorized = true;
		}

		return authorized;
	}

	public void convertTwitterLink(String content, MessageReceivedEvent event) {
		ThreadChannel thread = event.getMessage().createThreadChannel("fx").complete();
		String url = content.contains("https://twitter.com") ? "https://twitter.com" : "https://x.com";

		//If the message contains no spaces
		if(!content.contains(" ")) {
			thread.sendMessage("https://fxtwitter.com" + content.substring(content.indexOf(url) + url.length())).queue();
		
		//If the meessage has spaces after the link
		}else if(!content.substring(content.indexOf("https")).contains(" ")) {
			thread.sendMessage("https://fxtwitter.com" + content.substring(content.indexOf(url) + url.length())).queue();
		
		//If the message has content before and after the link
		}else {
			thread.sendMessage("https://fxtwitter.com" + content.substring(content.indexOf(url) + url.length())).queue();
		}

		DataHandler.addThreadToRemove(thread);
		DataHandler.save();
	}
}
