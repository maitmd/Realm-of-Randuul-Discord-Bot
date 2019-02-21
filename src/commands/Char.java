package commands;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.ArrayList;

import bot.GensoRanduul;
import data.Player;

public class Char extends Command{
	
	public Char(MessageChannel channel, String content, Member member) {
		ArrayList<String> args = getArgs(content, 2);
		User user = member.getUser();
		Player player = GensoRanduul.getPlayer(user.getName());
		data.Character character = getCharacter(args.get(0));
		
		if(player == null){
			GensoRanduul.addPlayer(new Player(user.getName()));
		}
		
		//View
		if(args.get(1).equals("view")) {
			
		//Edit
		}else if(args.get(1).equals("edit")) {
			try{args = getArgs(content,4);}catch(Exception e) {
				channel.sendMessage("What don't you like about this one..").queue();
				return;
			}
			
			String stat = args.get(2);
			String value = args.get(3);
			
			if(character == null){
				channel.sendMessage("That destiny has yet to be created.").queue();
				return;
			}
			
			if(character.getPlayer().getName().equals(user.getName())){

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
			
		//Create
		}else if(args.get(1).equals("create")) {
			create(user, channel, content, player, args);
			
		//Generate
		}else if(args.get(1).equals("generate")) {
			
			try{args = getArgs(content,2);}catch(Exception e) {
				channel.sendMessage("This destiny hasn't been created yet..").queue();
				return;
			}
			
			if(character == null){
				create(user, channel, content, player, args);
				character = getCharacter(args.get(0));
			}
			
			if(character.getPlayer().getName().equals(user.getName())){
				character.generate();
				channel.sendMessage("A new soul is born.").queue();
			}else{
				channel.sendMessage("Hands to your own creations!").queue();
			}
			
		//Level Up
		}else if(args.get(1).equals("levelup")) {

			try{character.levelUp(); channel.sendMessage("Grow child.").queue();}catch(NullPointerException e){channel.sendMessage("This destiny has yet to be created.").queue(); return;}
			
		//Remove
		}else if(args.get(1).equals("remove")) {
			player.removeCharacter(args.get(0));
			channel.sendMessage("You remain in the memories").queue();
			return;
			
		//Attune
		}else if(args.get(1).equals("attune")){
			try{character.attune(channel);}catch(NullPointerException e){channel.sendMessage("This destiny has yet to be created.").queue(); return;}
		
		//Spells
		}else if(args.get(1).equals("spells")){
			args = getArgs(content, 3);
			if(args.get(2).equals("add")){
				args = getArgs(content,5);
				character.addSpell(args.get(3), Integer.parseInt(args.get(4)));
			}else if(args.get(2).equals("remove")){
				args = getArgs(content,4);
				character.removeSpell(args.get(3));
			}else if(args.get(2).equals("view")){
				character.displayeSpells(channel);
				return;
			}
		}else if(args.get(1).equals("bag")){
			args = getArgs(content, 3);
			if(args.get(2).equals("add")){
				args = getArgs(content,4);
				character.addItem(args.get(3));
			}else if(args.get(2).equals("remove")){
				args = getArgs(content, 4);
				character.removeItem(args.get(3));
			}else if(args.get(2).equals("view")){
				character.displayBag(channel);
				return;
			}
		}
		
		player.save();
		view(channel, content, args, character);
	}
	
	public void create(User member, MessageChannel channel, String content, Player player, ArrayList<String> args){
		String name = null;
		try{name = args.get(0);}catch(Exception e) {
			channel.sendMessage("I suppose I won't be able to collaborate after all.").queue(); 
			return;
		}
		
		player.addCharacter(name);
		channel.sendMessage("Ahh my new creation **" + name + "** is born. Just for you " + member.getName() + ".").queue();
	}
	
	public void view(MessageChannel channel, String content, ArrayList<String> args, data.Character character){
		if(character == null){
			character = getCharacter(args.get(0));
		}
		try{character.display(channel);}catch(Exception e) {channel.sendMessage("That destiny has yet to be created.").queue();}
	}
		
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
