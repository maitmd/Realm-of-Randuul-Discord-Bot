package commands;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.ArrayList;

import bot.GensoRanduul;
import data.Player;

public class Char extends Command{
	
	public Char(MessageChannel channel, String content, Member member) {
		ArrayList<String> args = new ArrayList<String>();
		try{args = getArgs(content, 2);}catch(StringIndexOutOfBoundsException e){return;}
		User user = member.getUser();
		Player player = GensoRanduul.getPlayer(user.getName());
		data.Character character = getCharacter(args.get(0));
		
		//Checking to see if the member has been added into the bot as a player. If not add them.
		if(player == null){
			GensoRanduul.addPlayer(new Player(user.getName()));
			player = GensoRanduul.getPlayer(user.getName());
		}
		
		//!char [character] [sub-command] Find which sub-command was issued.
		switch(args.get(1)){
			case "view":
				//Displays the characters information
				break;
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
				try{character.setLevel(character.getLevel()+1); channel.sendMessage("Grow child.").queue();}catch(NullPointerException e){channel.sendMessage("This destiny has yet to be created.").queue(); return;}
				break;
			case "remove":
				//Removes the character from the players list.
				player.removeCharacter(args.get(0));
				channel.sendMessage("You remain in my memories").queue();
				break;
			case "spells":
				//!char [character] spells [sub-command] Find which sub-command of spells was issued.
				args = getArgs(content, 3);
				switch(args.get(2)){
					case "add":
						//Adds a String of the spells name and an integer to the characters spells list.
						args = getArgs(content,5);
						character.addSpell(args.get(3), Integer.parseInt(args.get(4)));
						break;
					case "remove":
						//Removes a String of the spells name from the characters spell list.
						args = getArgs(content,4);
						character.removeSpell(args.get(3));
						break;
					case "view":
						//Displays the characters spells by level.
						character.displayeSpells(channel);
						break;
				}
			case "bag":
				//!char [character] bag [sub-command] Find which sub-command was issued
				args = getArgs(content, 3);
				switch(args.get(2)){
					case "add":
						//Adds a String with the item's name to the characters bag list.
						args = getArgs(content,4);
						character.addItem(args.get(3));
						break;
					case "remove": 
						//Removes a String with the item's name from the characters bag list.
						args = getArgs(content, 4);
						character.removeItem(args.get(3));
						break;
					case "view":
						//Displays the characters bag list.
						character.displayBag(channel);
						break;
				}
			case "attune":
				//Adds 1 to the characters attune counter if that character exists
				try{character.attune(channel);}catch(NullPointerException e){channel.sendMessage("This destiny has yet to be created.").queue(); return;}
				break;
			
		}
		
		//Adds the player to players.txt
		player.save();
		view(channel, content, args, character);
	}
	
	//Creates a new Character object and adds it to the player's Character list.
	public void create(User member, MessageChannel channel, String content, Player player, ArrayList<String> args){
		String name = null;
		try{name = args.get(0);}catch(Exception e) {
			channel.sendMessage("I suppose I won't be able to collaborate after all.").queue(); 
			return;
		}
		
		player.addCharacter(name);
		channel.sendMessage("Ahh my new creation **" + name + "** is born. Just for you " + member.getName() + ".").queue();
	}
	
	//Displays the stats of the character.
	public void view(MessageChannel channel, String content, ArrayList<String> args, data.Character character){
		if(character == null){
			character = getCharacter(args.get(0));
		}
		try{character.display(channel);}catch(Exception e) {channel.sendMessage("That destiny has yet to be created.").queue();}
	}
	
	//Replaces the characters current stat that was provided with the value given.
	public void edit(MessageChannel channel, data.Character character, String content, ArrayList<String> args, User user){
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
		}else channel.sendMessage("Hands to your own creations!").queue();
	}
	
	//Randomizes all of the character's stats. If the character doesn't exist it creates the character first.
	public void generate(MessageChannel channel, ArrayList<String> args, data.Character character, User user, String content, Player player){
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
	}
}
