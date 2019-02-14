package Commands;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;

import Data.Character;
import Data.Player;
import Main.GensoRanduul;

public class Char extends Command{
	
	public Char(MessageChannel channel, String content, Member member) {
		ArrayList<String> args = getArgs(content, 1);
		
		if(args.get(0).equals("view")) {
			try{args = getArgs(content, 2);}catch(Exception e) {channel.sendMessage("That destiny has yet to be created.").queue(); return;}
			Data.Character character = getCharacter(args.get(1));
			try{character.displayCharacter(channel);}catch(Exception e) {channel.sendMessage("That destiny has yet to be created.").queue(); return;}
		}else if(args.get(0).equals("edit")) {
			try{args = getArgs(content,4);}catch(Exception e) {
				channel.sendMessage("What don't you like about this one..").queue();
				return;
			}
			
			String name = args.get(1);
			String stat = args.get(2);
			String value = args.get(3);
			Data.Character character;
			
			try{
				character = getCharacter(name);
			
			
				switch(stat) {
					case "level":
						character.setLevel(Integer.parseInt(value));
						break;
					case "speed":
						character.setSpeed(Integer.parseInt(value));
						break;
					case "ac":
						character.setAC(Integer.parseInt(value));
						break;
					case "hp":
						character.setHP(Integer.parseInt(value));
						break;
					case "race":
						character.setRace(value);
						break;
					case "class":
						character.setClass(value);
						break;
					default:
						character.setStat(stat, Integer.parseInt(value));
						break;
					}
			}catch(Exception e) {
				channel.sendMessage("That destiny has yet to be created.").queue(); 
				return;
			}
		}else if(args.get(0).equals("create")) {
			Player temp = GensoRanduul.getPlayer(member);
			args = getArgs(content, 2);
			String name = null;
			try{name = args.get(1);}catch(Exception e) 
			{channel.sendMessage("I suppose I won't be able to collaborate after all.").queue(); 
			return;
			}
			if(temp != null) {
				temp.addCharacter(name);
				channel.sendMessage("Ahh my new creation **" + name + "** is born. Just for you " + member.getEffectiveName() + ".").queue();
			}else {
				temp = new Player(member, member.getEffectiveName());
				GensoRanduul.addPlayer(temp);
				temp.addCharacter(name);
				channel.sendMessage("Ahh my new creation **" + name + "** is born. Just for you " + member.getEffectiveName() + ".").queue();
			}
		}else if(args.get(0).equals("generate")) {
			try{args = getArgs(content,2);}catch(Exception e) {
				channel.sendMessage("This destiny hasn't been created yet..").queue();
				return;
			}
			
			Data.Character character = getCharacter(args.get(1));
			character.generate();
			channel.sendMessage("A new soul is born.").queue();
			
		}else if(args.get(0).equals("levelup")) {
			try{args = getArgs(content,2);}catch(Exception e) {
				channel.sendMessage("This destiny hasn't been created yet..").queue();
				return;
			}
			
			Data.Character character = getCharacter(args.get(1));
			character.levelUp();
			channel.sendMessage("Grow child.").queue();
			
		}
	}
	
	public Data.Character getCharacter(String name) {
		ArrayList<Player> players = GensoRanduul.getPlayers();
		Data.Character character = null;
		
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).getCharacter(name) != null) {
				character = players.get(i).getCharacter(name);
			}
		}
		return character;
	}
}
