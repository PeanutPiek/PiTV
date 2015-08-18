/**
 * 
 */
package de.gg.pi.tv.menu;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JButton;

/**
 * @author PeanutPiek
 *
 */
public class MenuButton extends JButton {

	
	
	public static Color DEFAULT_GLOW_COLOR = new Color(1.0f, 0.0f, 0.0f, 0.3f);
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private boolean isGlowing = false;
	
	
	private Color glowColor = DEFAULT_GLOW_COLOR;
	
	
	public MenuButton(String label, Icon icon) {
		super(label, icon);
		this.setFocusPainted(false);
	}
	
	
	public void glow(boolean g) {
		isGlowing = g;
	}

	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		
		if(isGlowing) {
			
			int x = this.getX();
			int y = this.getY();
			
			int width = this.getWidth();
			int height = this.getHeight();
			
			g.setColor(glowColor);
			g.fillRoundRect(x, y, width, height, 15, 15);
		}
		
	}
	
}
