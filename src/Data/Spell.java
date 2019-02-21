package Data;

import java.io.Serializable;

public class Spell implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int level;
	
	public Spell(String name, int level){
		this.name = name;
		this.level = level;
	}
	
	public int getLevel(){
		return level;
	}
	
	public String getName(){		
		return name;
	}
}
