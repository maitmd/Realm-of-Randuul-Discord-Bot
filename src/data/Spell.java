package data;

import java.io.Serializable;

public class Spell implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int level;
	private boolean ritual;
	
	public Spell(String name, int level, boolean ritual){
		this.name = name;
		this.level = level;
		this.ritual = ritual;
	}
	
	//Returns this spells level
	public int getLevel(){
		return level;
	}
	
	//Returns this spells name
	public String getName(){		
		return name;
	}
	
	public boolean getRitual() {
		return ritual;
	}
}
