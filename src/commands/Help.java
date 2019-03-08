package commands;

import java.util.ArrayList;

import net.dv8tion.jda.core.entities.MessageChannel;

public class Help extends Command{
	public Help(MessageChannel channel, String content) {
			ArrayList<String> args = new ArrayList<String>();
			try{args = getArgs(content, 1);}catch(StringIndexOutOfBoundsException e){System.out.println("No args for help!");}

			switch(args.get(0)) {
			case "campaign":
				channel.sendMessage("**Campaign Sub-Commands:**" + "\n\n" + "*Note: if any arguments you type have spaces you must containt them with quotes (\" \").*" + "\n\n" 
			+ "**create [next session date] [time zone] [meet time]**" + "\n" + "```" + "[next session date] format \"Year-Month-Day Hour:Minutes\"." + "\n"  
						+ "[time zone] should use GMT + or - with number only (Example: \"-5\")." + "\n" + "[meet time] is just a description of the meet time of the campaign (Example: \"Bi-Weekly, Fridays at 6PM EST\")." 
			+ "\n" + "```"+ "\n" + "**remove**"  + "\n" + "```" +"Removes a campaign from the server forever." + "```" + "\n" + "**characters [view, add, remove] [character name*]**"
						+ "\n" + "```" + "[character name] is only used with the [add, remove] sub-commands." + "\n"  + "```" + "\n" + "**join**" + "\n" + "```" 
						+"Players can only join campaigns they have been invited to." + "```" + "\n" + "**players [view, remove] [@user*]**" + "\n" + "```" 
			+"[view] shows all players with at least one character in the campaign. \n[remove] can only be used by Dungeon Masters and it will remove the [@user] mentioned an all characters they had in the campaign." 
						+ "```" + "\n" + "**nextsession**" + "\n" + "```" +"This command will display the next session for this campaign." + "```" + "\n\n" + "*The following commands can only be used by a Dungeon Master* \n\n" 
			+ "\n" + "**invite [@user]**" + "\n" + "```" +"The Dungeon Master can invite members of the server to a campaign that they have created." + "```" + "\n" +
			"**start**" + "\n" + "```" +"This command will announce to the server that your campaign has started." + "```" + "\n" + "**stop**" 
						+ "\n" + "```" +"This command will announce to the server that your campaign has stopped." + "```" + "\n" + "**schedule [time zone] [quantity] [month, week, day]**" 
			+ "\n" + "```" +"This command will set your next session date to [quantity] [month, week, day]s from today." + "```").queue();
				break;
			case "char":
				channel.sendMessage("**Character Sub-Commands**" + "\n\n" + "*Note: if any arguments you type have spaces you must containt them with quotes (\" \"). Additionally any edits made to character may only be done by that character's owner.*" + "\n\n"
			+ "**view** \n```This command will show your characters sheet which includes: The owner, The campaign they are in if any, class, race, proficiency bonus, ac, hp, speed, con, str, dex, int, wis, cha, number of items attuned, number of spells known, number of items they hold, and and proficiencies they have. ```\n"
						+ "**edit [attribute] [value]**  \n```This command allows the owner of the character to change the [value] of an [attribute] which is level, speed, ac, hp, race, class, or any of the stats denoted as con, str, dex, int, wis, cha. ```\n" + "**create**  \n```This command create a blank character with all values set to 0 or null. ```\n"
			+ "**generate** \n```This command will generate a characters base attributes randomly such as Class, Race, Speed, Con, Str, Dex, Int, Wis, Cha. If the character hasn't been created yet it will create the character as well.``` \n **levelup** \n```This command adds one to the characters level.```\n **remove** \n```This command allows"
						+ " the owner of the character to delete the character forever. ```\n **[un]attune** \n```This command increments the attuned items by one or decrements attuned items by one. ```\n **prof [add/remove]** \n```This command allows you to add a proficieny to your list of proficiencies.```\n"
		    + "**spells [add/remove/view] [spell name*] [spell level*]** \n```This command allows you to add, remove, or view your spells. [spell name] is only used with [add/remove] and [spell level] is only used with [add].```\n **bag [add, remove, view] [item name*]** \n```This command lets you add, remove, and view your bag. [item name] is only used with [add/remove]. ```\n").queue();
				break;
			case "player":
				channel.sendMessage("**Player Sub-Commands** \n\n" + "*Note: if any arguments you type have spaces you must containt them with quotes (\" \").*" + "\n\n"
						+ "**view** \n```This command shows the players server stats such as their discord name, join date, number of characters they own, and number of campaigns they are in.```\n **characters** \n```This command displayers all of this members characters.```\n **campaigns** \n```This command displays all of campaigns this player is "
						+ "a member of. If the campaign has a * next to it they are the Dungeon Master of that campaign. ```").queue();
				break;
			case "roll":
				channel.sendMessage("**Roll Sub-Commands** \n\n" + "*Note: if any arguments you type have spaces you must containt them with quotes (\" \").*" + "\n\n"
						+ "**[{number of dice}d{dice value}/stats]** \n```This command rolls [number of dice] of [dice value]. Examples: 1d20, 2d10, 6d6, etc. [stats] will roll 4d6 and drop the lowest value 6 times.``` \n").queue();
				break;
			default:
				channel.sendMessage("**All Commands:**" + "\n\n"
						+ "*Note: if any arguments you type have spaces you must containt them with quotes (\" \").*" + "\n\n"
						+ "```" + "Campaign: !campaign [campaign name] [create, remove, invite, join, start, stop, nextsession, schedule, players, characters]"  + "```"
						+ "```" +  "Characters: !char [character name] [view, edit, create, levelup, remove, spells, bag, attune, prof]" + "```"
						+ "```" +  "Players: !player [@user name] [view, characters, campaigns]" + "```"
						+ "```" +  "Dice: !roll [{number of dice}d{dice value}, stats]" + "```" + "\n"
						+ "To view more detailed descriptions type !help [inital sub-command]").queue();
				break;
			}
	}
}
