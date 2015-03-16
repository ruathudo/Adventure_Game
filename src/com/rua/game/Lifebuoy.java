package com.rua.game;

import java.io.Serializable;

public class Lifebuoy extends Thing implements Serializable{
	private static final long serialVersionUID = 1L;
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
