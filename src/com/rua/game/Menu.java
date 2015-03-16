package com.rua.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class Menu {
	private boolean openHelp;
	
	public Menu() {
		openHelp = false;
	}
	
	public void setOpenHelp(boolean b) {
		openHelp = b;
	}
	
	public void draw(Graphics g) {
		
	    
		if( openHelp ) {
			g.setFont(new Font("Arial", Font.BOLD, 14));
		    g.setColor(Color.WHITE);
		    g.drawString("- You have to go through the map", 10, 50);
			g.drawString("to collect enough stuffs to win the game", 10, 70);
			g.drawString("There is a countdown clock till the bombs", 10, 90);
			g.drawString("exploded. If time's up and you haven't", 10, 110);
			g.drawString("got enough stuffs, you will loose the game.", 10, 130);
			g.drawString("Collect battery will increase 10% of battery", 10, 150);
			g.drawString("Collect clock will increase 30s of time", 10, 170);
			
			g.drawString("- To controll player: ← ↑ ↓ →", 10, 200);
			g.drawString("- Use Flashlight to show the trees: SPACE", 10, 220);
			g.drawString("- Use Lifebuoy to swim (1 time only)", 10, 240);
			g.drawString("- To go back to the menu: ESC", 10, 270);
			
		} else {
			g.setFont(new Font("Arial", Font.BOLD, 20));
		    g.setColor(Color.WHITE);
			g.drawString("ADVENTURE GAME", 70, 80);
			g.drawString("Press:", 70, 130);
			g.drawString("1 To Play", 100, 160);
			g.drawString("2 To SAVE", 100, 190);
			g.drawString("3 To LOAD", 100, 220);
			g.drawString("4 To HELP", 100, 250);
			g.drawString("ESC To BACK", 100, 280);
		}
		
		
	    
	}
}
