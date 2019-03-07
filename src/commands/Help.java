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
			+ "- **create [next session date] [time zone] [meet time]**" + "\n" + "```" + "- [next session date] format \"Year-Month-Day\"" + "\n"  
						+ "- [time zone] should use GMT + or - with number only (Example: \"-5\")" + "\n" + "- [meet time] is just a description of the meet time of the campaign (Example: \"Bi-Weekly, Fridays at 6PM EST\")" 
			+ "\n" + "```"+ "\n" + "- **remove**"  + "\n" + "```" +"- Removes a campaign from the server." + "```" + "\n" + "- **characters [view, add, remove] [character name*]**"
						+ "\n" + "```" + "- [character name] is only used with the [add, remove] sub-commands." + "\n"  + "```" + "\n" + "- **invite [@user]**" + "\n" 
			+ "```" +"- The Dungeon Master can invite members of the server to a campaign that they have created." + "```" + "\n" + "- **join**" + "\n" + "```" 
						+"- Players can only join servers they have been invited to." + "```" + "\n" + "- **players [view, remove] [@user*]**" + "\n" + "```" 
			+"- [view] shows all players with at least one character in the campaign. \n- [remove] can only be used by Dungeon Masters and it will remove the user mentioned an all characters they had in the campaign." 
						+ "```" + "\n" + "- **nextsession**" + "\n" + "```" +"- This command will display the next session for this campaign." + "```" + "\n\n" + "*The following commands can only be used by a Dungeon Master*" 
			+ "\n\n" + "- **start**" + "\n" + "```" +"- This command will announce to the server that your campaign has started." + "```" + "\n" + "- **stop**" 
						+ "\n" + "```" +"- This command will announce to the server that your campaign has stopped." + "```" + "\n" + "- **schedule [time zone] [quantity] [month, week, day]**" 
			+ "\n" + "```" +"- This command will set your next session date to [quantity] [month, week, day]s from today." + "```").queue();
				break;
			case "char":
				break;
			case "player":
				break;
			case "roll":
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
