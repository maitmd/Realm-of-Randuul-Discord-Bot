package commands;

import java.util.ArrayList;

import net.dv8tion.jda.core.entities.MessageChannel;

public class Search extends Command{
	public Search(MessageChannel channel, String content) {
		super(jda);
		ArrayList<String> args = new ArrayList<String>();
		try{args = getArgs(content, 1);}catch(StringIndexOutOfBoundsException e){return;}
		
		switch(args.get(0)) {
		case "spell":
			try{args = getArgs(content, 3);}catch(Exception e) {args = getArgs(content, 2);}
			
			if(args.size() < 3) {
				channel.sendMessage("https://www.dnd-spells.com/spell/" + args.get(1).replaceAll(" ", "-").toLowerCase()).queue();
			}else {
				channel.sendMessage("https://www.dnd-spells.com/spell/" + args.get(1).replaceAll(" ", "-").toLowerCase() + (args.get(2).equalsIgnoreCase("yes") ? "-ritual" : "")).queue();
			}
			
			break;
		case "item":
			args = getArgs(content, 2);
			channel.sendMessage("https://roll20.net/compendium/dnd5e/Items:" + args.get(1)).queue();
			break;
		case "monster":
			args = getArgs(content, 2);
			channel.sendMessage("http://chisaipete.github.io/bestiary/creatures/" + args.get(1).replaceAll(" ", "-").toLowerCase()).queue();
		}
	}
}
