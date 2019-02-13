package Commands;

import net.dv8tion.jda.core.entities.MessageChannel;

import java.util.ArrayList;

import Data.Player;
import Main.GensoRanduul;

public class CharInfo {
	public CharInfo(MessageChannel channel, String content) {
		String command = content.substring(content.indexOf(" ")+1);
		String arg = command.substring(command.indexOf(" ")+1);
		
		System.out.println(command);
		if(command.startsWith("view")) {
			System.out.println("view");
			ArrayList<Player> players = GensoRanduul.getPlayers();
			Data.Character character = null;
			for(int i = 0; i < players.size(); i++) {
				if(players.get(i).getCharacter(arg) != null) {
					character = players.get(i).getCharacter(arg);
					System.out.println(character);
				}
			}
			character.displayCharacter(channel);
		}
	}
}
