/**
 * 
 */
package de.gg.pi.tv.menu;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * @author PeanutPiek
 *
 */
public class Cursor extends JComponent {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private boolean visible = false;
	
	
	private int x;
	
	
	private int y;
	
	
	private int size;
	
	
	private int sizeRef;
	
	
	private boolean reduce = false;
	
	
	private Color color;
	
	
	public Cursor(int size, Color color) {
		this.sizeRef = this.size = size;
		this.color = color;
		
		this.x = 0;
		this.y = 0;
		
		
	}
	
	
	public void move(int x, int y) {
		this.x = x; 
		this.y = y;
	}
	
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	
	public boolean getVisible() {
		return this.visible;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(this.visible) {
			int[] origin = new int[2];
			origin[0] = x;
			origin[1] = y;
			
			if(this.size == this.sizeRef)
				reduce = true;
			if(this.size == 1) reduce = false;
			
			if(reduce) this.size-=1;
			else this.size+=1;
			
			g.setColor(color);
			g.drawLine(x, y + 1, x, y + size);
			g.drawLine(x, y - 1, x, y - size);
			g.drawLine(x + 1, y, x + size, y);
			g.drawLine(x - 1, y, x - size, y);
		}		
	}
	
}
