package com.rua.game;

public class Lifebuoy extends Thing{
	private boolean used; // how many times this be used
	private boolean using;
	public Lifebuoy() {
		super("Lifebuoy");
		used = false;
		using = false;
	}
	
	public void setUsed() { used = true; }
	public void setUsing() { using = true; }
	
	public boolean isUsed() { return used;}
	public boolean isUsing() { return using;}

}
