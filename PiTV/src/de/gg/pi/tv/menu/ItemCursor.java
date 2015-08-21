/**
 * 
 */
package de.gg.pi.tv.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

/**
 * @author PeanutPiek
 *
 */
public abstract class ItemCursor {
	
	
	protected Color mainColor;
	
	
	public ItemCursor(Color color) {
		super();
		mainColor = color;
		
	}


	public abstract void paint(Graphics g, int x, int y, int w, int h);
	
}
