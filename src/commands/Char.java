package commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import services.DataHandler;

import java.util.ArrayList;

import data.Player;

public class Char extends Command{
	
	public Char(MessageChannelUnion channel, String content, Member member, Player player) {
		super(jda, twitter);
		ArrayList<String> args = new ArrayList<String>();
		try{args = getArgs(content, 2);}catch(StringIndexOutOfBoundsException e){return;}
		User user = member.getUser();
		data.Character character = DataHandler.getCharacter(args.get(0));
		
		//!char [character] [sub-command] Find which sub-command was issued.
		switch(args.get(1)){
			case "edit":
				//Edits the stat of a character.
				edit(channel, character, content, args, user);
				break;
			case "create":
				//Creates a new character object and adds it to the player.
				create(user, channel, content, player, args);
				break;
			case "generate":
				//Generates random stats for the character.
				generate(channel, args, character, user, content, player);
				break;
			case "levelup":
				//Adds 1 to the characters level if they exist.
				if(character.getPlayer().getName().equals(user.getName())){args = getArgs(content, 3);
					try{character.setLevel(character.getLevel()+1); channel.sendMessage("Grow child.").queue();}catch(NullPointerException e){channel.sendMessage("This destiny has yet to be created.").queue(); return;}
				}else{channel.sendMessage("Hands to your own creations!").queue();}
				break;
			case "remove":
				//Removes the character from the players list.
				player.removeCharacter(args.get(0));
				channel.sendMessage("You remain in my memories").queue();
				return;
			case "spells":
				//!char [character] spells [sub-command] Find which sub-command of spells was issued.
				
				if(character.getPlayer().getName().equals(user.getName())){
					args = getArgs(content, 4);
					switch(args.get(2)){
						case "add":
							//Adds a String of the spells name and an integer to the characters spells list.
							try{args = getArgs(content,6);}catch(Exception e) {args = getArgs(content,5);}
							
							if(args.size() < 6) {
								character.addSpell(args.get(3), Integer.parseInt(args.get(4)), false);
							}else {
								character.addSpell(args.get(3), Integer.parseInt(args.get(4)), args.get(5).equalsIgnoreCase("yes") ? true : false);
							}
							
							break;
						case "remove":
							//Removes a String of the spells name from the characters spell list.
							character.removeSpell(args.get(3));
							break;
						case "view":
							//Displays the characters spells by level.
							if(args.size() < 4) {
								character.displayeSpells(channel);
							}else {
								character.displaySpell(channel, args.get(3));
							}
					}
					break;
				}else{channel.sendMessage("Hands to your own creations!").queue();}
			case "bag":
				//!char [character] bag [sub-command] Find which sub-command was issued
				args = getArgs(content, 4);
				if(character.getPlayer().getName().equals(user.getName())){
					switch(args.get(2)){
						case "add":
							//Adds a String with the item's name to the characters bag list.
							character.addItem(args.get(3));
							break;
						case "remove": 
							//Removes a String with the item's name from the characters bag list.
							character.removeItem(args.get(3));
							break;
						case "view":
							//Displays the characters bag list.
							character.displayBag(channel);
							break;
					}
				}else{channel.sendMessage("Hands to your own creations!").queue();}
			case "attune":
				//Adds 1 to the characters attune counter if that character exists
				if(character.getPlayer().getName().equals(user.getName())){
					try{character.attune(channel);}catch(NullPointerException e){channel.sendMessage("This destiny has yet to be created.").queue(); return;}
				}else{
					channel.sendMessage("Hands to your own creations!").queue();}
				break;
				case "unattune":
				//Removes 1 to the characters attune counter if that character exists
				if(character.getPlayer().getName().equals(user.getName())){
					try{character.unattune(channel);}catch(NullPointerException e){channel.sendMessage("This destiny has yet to be created.").queue(); return;}
				}else{channel.sendMessage("Hands to your own creations!").queue();}
				break;
			case "prof":
				if(character.getPlayer().getName().equals(user.getName())){
					args = getArgs(content, 4);
					switch(args.get(2)) {
					case "add":
						character.addProf(args.get(3));
						break;
					case "remove":
						character.removeProf(args.get(3));
						break;
					}	
				}else{channel.sendMessage("Hands to your own creations!").queue();
			}
			default:
				view(channel, content, args, character);
				return;
		}
		
		//Adds the player to players.txt
		DataHandler.save();
	}
	
	//Creates a new Character object and adds it to the player's Character list.
	public void create(User member, MessageChannelUnion channel, String content, Player player, ArrayList<String> args){
		String name = null;
		try{name = args.get(0);}catch(Exception e) {
			channel.sendMessage("I suppose I won't be able to collaborate after all.").queue(); 
			return;
		}
		
		player.addCharacter(name);
		channel.sendMessage("Ahh my new creation **" + name + "** is born. Just for you " + member.getName() + ".").queue();
	}
	
	//Displays the stats of the character.
	public void view(MessageChannelUnion channel, String content, ArrayList<String> args, data.Character character){
		if(character == null){
			character = DataHandler.getCharacter(args.get(0));
		}
		
		try{character.display(channel);}catch(Exception e) {channel.sendMessage("That destiny has yet to be created.").queue();}
	}
	
	//Replaces the characters current stat that was provided with the value given.
	public void edit(MessageChannelUnion channel, data.Character character, String content, ArrayList<String> args, User user){
		try{args = getArgs(content,4);}catch(Exception e) {
			channel.sendMessage("What do you want to change about them? (!char [name] edit [stat] [value])").queue();
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
		}else channel.sendMessage("Hands to your own creations!").queue();
	}
	
	//Randomizes all of the character's stats. If the character doesn't exist it creates the character first.
	public void generate(MessageChannelUnion channel, ArrayList<String> args, data.Character character, User user, String content, Player player){
		try{args = getArgs(content,2);}catch(Exception e) {
			channel.sendMessage("This destiny hasn't been created yet..").queue();
			return;
		}
		
		if(character == null){
			create(user, channel, content, player, args);
			character = DataHandler.getCharacter(args.get(0));
		}
		
		if(character.getPlayer().getName().equals(user.getName())){
			character.generate();
			channel.sendMessage("A brand new soul for " + character.getName()).queue();
		}else{
			channel.sendMessage("Hands to your own creations!").queue();
		}
	}
}
