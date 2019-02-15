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
		Player player = GensoRanduul.getPlayer(member.getEffectiveName());
		
		//View
		if(args.get(0).equals("view")) {
			try{args = getArgs(content, 2);}catch(Exception e) {channel.sendMessage("That destiny has yet to be created.").queue(); return;}
			Data.Character character = getCharacter(args.get(1));
			try{character.displayCharacter(channel);}catch(Exception e) {channel.sendMessage("That destiny has yet to be created.").queue(); return;}
		
		//Edit
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
			if(character.getPlayer().getName().equals(member.getEffectiveName())){
			
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
			}else{
				channel.sendMessage("Hands to your own creations!").queue();
			}
			}catch(Exception e) {
				channel.sendMessage("That destiny has yet to be created.").queue(); 
				return;
			}
		
		//Create
		}else if(args.get(0).equals("create")) {
			args = getArgs(content, 2);
			String name = null;
			try{name = args.get(1);}catch(Exception e) 
			{channel.sendMessage("I suppose I won't be able to collaborate after all.").queue(); 
			return;
			}
			if(player != null) {
				player.addCharacter(name);
				channel.sendMessage("Ahh my new creation **" + name + "** is born. Just for you " + member.getEffectiveName() + ".").queue();
			}else {
				player = new Player(member.getEffectiveName());
				GensoRanduul.addPlayer(player);
				player.addCharacter(name);
				channel.sendMessage("Ahh my new creation **" + name + "** is born. Just for you " + member.getEffectiveName() + ".").queue();
			}
			
		//Generate
		}else if(args.get(0).equals("generate")) {
			try{args = getArgs(content,2);}catch(Exception e) {
				channel.sendMessage("This destiny hasn't been created yet..").queue();
				return;
			}
			
			Data.Character character = getCharacter(args.get(1));
			
				if(character.getPlayer().getName().equals(member.getEffectiveName())){
					character.generate();
					channel.sendMessage("A new soul is born.").queue();
				}else{
					channel.sendMessage("Hands to your own creations!").queue();
				}
		
		//Level Up
		}else if(args.get(0).equals("levelup")) {
			try{args = getArgs(content,2);}catch(Exception e) {
				channel.sendMessage("This destiny hasn't been created yet..").queue();
				return;
			}
			
			Data.Character character = getCharacter(args.get(1));
			character.levelUp();
			channel.sendMessage("Grow child.").queue();
			
		}else if(args.get(0).equals("remove")) {
			
		}
		
		player.save();
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
