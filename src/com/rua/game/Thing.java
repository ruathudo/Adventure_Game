package com.rua.game;

import java.io.Serializable;

public class Thing implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	
	public Thing(String n) {
		this.name = n;
	}
	
	public String getName() {
		return this.name;
	}
	
}
