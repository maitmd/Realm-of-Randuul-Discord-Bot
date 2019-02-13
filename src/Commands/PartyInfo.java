package Commands;

import net.dv8tion.jda.core.entities.MessageChannel;

public class PartyInfo extends Command{
	 
	private int partyLevel = 11;
	public PartyInfo(MessageChannel channel) {
		
		channel.sendMessage("The party is level " + partyLevel).queue();
	}
}
