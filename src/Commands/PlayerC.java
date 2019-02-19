package Commands;

import java.util.ArrayList;
import java.util.List;

import Data.Player;
import Main.GensoRanduul;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class PlayerC extends Command{
	public PlayerC(MessageChannel channel, String content, Member mem, List<Member> mention){
		ArrayList<String> args = getArgs(content, 1);
		List<Member> mentioned = mention;
		User user = mem.getUser();
		Player player = GensoRanduul.getPlayer(user.getName());
		if(args.get(0).equals("view")){
			Member tempmem = null;
			try{tempmem = mentioned.get(0);}catch(Exception e){};

			if(tempmem != null){
				Player temp = GensoRanduul.getPlayer(tempmem.getUser().getName());
				if(temp == null){
					GensoRanduul.addPlayer(new Player(tempmem.getUser().getName()));
					temp = GensoRanduul.getPlayer(tempmem.getUser().getName().substring(0));
					temp.save();
				}
				temp.display(tempmem, channel);
			}else{
				if(player == null){
					Player newP = new Player(user.getName());
					GensoRanduul.addPlayer(newP);
					newP.save();
					player = GensoRanduul.getPlayer(user.getName());;
				}
				player.display(mem, channel);
			}
		}else if(args.get(0).equals("characters")){
			Member tempmem = null;
			try{tempmem = mentioned.get(0);}catch(Exception e){};
			
			if(tempmem != null){
				Player temp = GensoRanduul.getPlayer(tempmem.getUser().getName());
				if(temp == null){
					GensoRanduul.addPlayer(new Player(tempmem.getUser().getName()));
					temp = GensoRanduul.getPlayer(tempmem.getUser().getName().substring(0));
					temp.save();
				}
				
				temp.displayCharacters(channel);
			}else{
				if(player == null){
					Player newP = new Player(user.getName());
					GensoRanduul.addPlayer(newP);
					newP.save();
					player = GensoRanduul.getPlayer(user.getName());
				}
				player.displayCharacters(channel);
			}
		}
			
	}
}
