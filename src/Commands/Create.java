package Commands;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.util.ArrayList;

import Data.Player;
import Main.GensoRanduul;
public class Create extends Command{
	
	public Create(MessageChannel channel, String content, Member member){
		String command = content.substring(content.indexOf(" ")+1);
		String arg = command.substring(command.indexOf(" ")+1);
		
		if(checkArg(arg) || checkCommand(command)){
			channel.sendMessage("I suppose I won't be able to collaborate after all.").queue();
			return;
		}
		
		if(command.startsWith("character")){
			Player temp = GensoRanduul.getPlayer(member);
			
			if(temp != null) {
				temp.addCharacter(arg);
				channel.sendMessage("Ahh my new creation **" + arg + "** is born. Just for you " + member.getEffectiveName() + ".").queue();
			}else {
				temp = new Player(member, member.getEffectiveName());
				GensoRanduul.addPlayer(temp);
				temp.addCharacter(arg);
				channel.sendMessage("Ahh my new creation **" + arg + "** is born. Just for you " + member.getEffectiveName() + ".").queue();
			}
		}else if(command.startsWith("campaign")) {
			
		}
	}
	
	public boolean checkCommand(String temp) {
		if(temp.contains("!create")) {
			return true;
		}
		return false;
	}
	
	public boolean checkArg(String temp) {
		if(temp.contains("character") || temp.contains("campaign")) {
			return true;
		}
		return false;
	}

}
