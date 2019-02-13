package Commands;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class PartyInfo extends Command{
	 
	private int partyLevel = 11;
	public PartyInfo(MessageChannel channel,JDA jda) {
		super(jda);
		channel.sendMessage("The party is level " + partyLevel).queue();
	}
}
