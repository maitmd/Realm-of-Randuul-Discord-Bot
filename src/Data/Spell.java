package Data;

public class Spell {
	
	private String name;
	private int level;
	private boolean prepared;
	
	public Spell(String name, int level){
		this.name = name;
		this.level = level;
	}
	
	public void prepare(){
		prepared = true;
	}
	
	public boolean isPrepared(){
		return prepared;
	}
}
