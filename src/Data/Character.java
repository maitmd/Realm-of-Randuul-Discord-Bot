package Data;

public class Character {
	
	public int level;
	public String race;
	public String classChar;
	public Player player;
	public Campaign campaign;
	public String location;
	
	public Character(Player player, Campaign campaign) {
		this.player = player;
		this.campaign = campaign;
	}
}
