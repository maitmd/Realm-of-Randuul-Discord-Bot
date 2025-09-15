package commands;

import java.util.ArrayList;

import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

public class Help extends Command {
	public Help(MessageChannelUnion channel, String content) {
		super(jda, twitter);
		ArrayList<String> args = new ArrayList<String>();
		try {
			args = getArgs(content, 1);
		} catch (StringIndexOutOfBoundsException e) {
			System.out.println("No args for help!");
		}

		if (args.isEmpty()) {
			channel.sendMessage("**All Commands:**" + "\n\n"
					+ "*Note: if any arguments you type have spaces you must containt them with quotes (\" \") [Excluding mentions].*"
					+ "\n\n"
					+ "```"
					+ "Campaign: !campaign [campaign name] [create, remove, join, , players, characters] \n\n(DM Only Sub-Commands) \n[invite, nextsession, schedule, meettime]"
					+ "```"
					+ "```"
					+ "Characters: !char [character name] [view, edit, create, levelup, remove, spells, bag, attune, prof]"
					+ "```"
					+ "```" + "Players: !player [@user name] [view, characters, campaigns]" + "```"
					+ "```" + "Dice: !roll [{number of dice}d{dice value}, stats]" + "```"
					+ "```" + "Search: !search [item, monster, spell], [search query]" + "```" + "\n"
					+ "```" + "Tweet (Aether only): !tweet [message] [image (optional)]" + "```" + "\n"
					+ "```" + "Diag: !diag [A-Length] [B-Length]" + "```" + "\n"
					+ "```" + "Spam: !spam [@User's name]" + "```" + "\n"
					+ "```" + "Server Controls: !server list or !server [add/delete/list/status/crashreport/log/start/restart] [Server Name] " + "```" + "\n"
					+ "To view more detailed descriptions type !help [inital sub-command]").queue();

		} else {
			switch (args.get(0)) {
				case "campaign":
					channel.sendMessage("**Campaign Sub-Commands:**" + "\n\n"
							+ "*Note: if any arguments you type have spaces you must containt them with quotes (\" \").*"
							+ "\n\n"
							+ "**create**" + "\n" + "```"
							+ "Creates and displays a blank campaign. You can set the next session date, description of the meet time, and invite players with the \"edit\" command and \"invite\" command respectively."
							+ "\n" + "```" + "\n" + "**remove**" + "\n" + "```"
							+ "Removes a campaign from the server forever." + "```" + "\n"
							+ "**characters [view, add, remove] [character name*]**"
							+ "\n" + "```"
							+ "[character name] is only used with the [add, remove] sub-commands. If you wish to view individual characters you can use \"!char [character name] view\""
							+ "\n" + "```" + "\n" + "**join**" + "\n" + "```"
							+ "Players can only join campaigns they have been invited to." + "```" + "\n"
							+ "**players [view, remove] [@user*]**" + "\n" + "```"
							+ "[view] shows all players with at least one character in the campaign. \n[remove] can only be used by Dungeon Masters and it will remove the [@user] mentioned an all characters they had in the campaign. If you wish to view individual players you can use \"!player @[user] view\""
							+
							"``` \n\n *The following commands can only be used by the Dungeon Master of the campaign*")
							.queue();
					channel.sendMessage("_ _").queue();
					channel.sendMessage("_ _").queue();
					channel.sendMessage("\n" + "**invite [@user]**" + "\n" + "```"
							+ "The Dungeon Master can invite members of the server to a campaign that they have created. If you mention more than one member then they will all be invited!"
							+ "```" + "\n" +
							"**nextsession [GMT Timezone] [Number] [Day, Week, Month]**" + "\n" + "```"
							+ "Move the current \"Next Session\" date by [Number] [Day, Week, Month]s. For [GMT Timezone] only include the number do not put GMT in front of it! \n\nExample: !campaign [campaign name] nextsession -5 1 Week"
							+
							"```" + "\n" + "**schedule [Year-Month-Day] [Hour:Minute] [GMT Timezone]**" + "\n" + "```"
							+ "Set the current \"Next Session\" to a specific date. For [GMT Timezone] only include the number do not put GMT in front of it! For months and days that are single digit they must include a 0 in front of them. "
							+ "The time set must be in military time. \n\nExample: !campaign [campaign name] 2020-06-24 16:00"
							+ "```" + "\n" + "**meettime \"[Text]\"**" + "\n" + "```"
							+ "Sets the current \"Meet Time\" text. The text must be surrounded by \" \" or it will not be displayed properly."
							+ "```").queue();
					break;
				case "char":
					channel.sendMessage("**Character Sub-Commands**" + "\n\n"
							+ "*Note: if any arguments you type have spaces you must containt them with quotes (\" \"). Additionally any edits made to character may only be done by that character's owner.*"
							+ "\n\n"
							+ "**view** \n```This command will show your characters sheet which includes: The owner, The campaign they are in if any, class, race, proficiency bonus, ac, hp, speed, con, str, dex, int, wis, cha, number of items attuned, number of spells known, number of items they hold, and and proficiencies they have. ```\n"
							+ "**edit [attribute] [value]**  \n```This command allows the owner of the character to change the [value] of an [attribute] which is level, speed, ac, hp, race, class, or any of the stats denoted as con, str, dex, int, wis, cha. ```\n"
							+ "**create**  \n```This command create a blank character with all values set to 0 or null. ```\n"
							+ "**generate** \n```This command will generate a characters base attributes randomly such as Class, Race, Speed, Con, Str, Dex, Int, Wis, Cha. If the character hasn't been created yet it will create the character as well.``` \n **levelup** \n```This command adds one to the characters level.```\n **remove** \n```This command allows"
							+ " the owner of the character to delete the character forever. ```\n **[un]attune** \n```This command increments the attuned items by one or decrements attuned items by one. ```\n **prof [add/remove]** \n```This command allows you to add a proficieny to your list of proficiencies.```\n"
							+ "**spells [add/remove/view] [spell name*] [spell level*]** \n```This command allows you to add, remove, or view your spells. [spell name] is only used with [add/remove], but is optional with view if you want more detailed descriptions, and [spell level] is only used with [add].```\n **bag [add, remove, view] [item name*]** \n```This command lets you add, remove, and view your bag. [item name] is only used with [add/remove]. ```\n")
							.queue();
					break;
				case "player":
					channel.sendMessage("**Player Sub-Commands** \n\n"
							+ "*Note: if any arguments you type have spaces you must containt them with quotes (\" \").*"
							+ "\n\n"
							+ "**view** \n```This command shows the players server stats such as their discord name, join date, number of characters they own, and number of campaigns they are in.```\n **characters** \n```This command displayers all of this members characters.```\n **campaigns** \n```This command displays all of campaigns this player is "
							+ "a member of. If the campaign has a * next to it they are the Dungeon Master of that campaign. ```")
							.queue();
					break;
				case "roll":
					channel.sendMessage("**Roll Sub-Commands** \n\n"
							+ "*Note: if any arguments you type have spaces you must containt them with quotes (\" \").*"
							+ "\n\n"
							+ "**[{number of dice}d{dice value}/stats]** \n```This command rolls [number of dice] of [dice value]. Examples: 1d20, 2d10, 6d6, etc. [stats] will roll 4d6 and drop the lowest value 6 times.``` \n")
							.queue();
					break;
			}
		}
	}
}
