/**
 * 
 */
package de.gg.pi.tv.menu.page;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;

/**
 * @author PeanutPiek
 *
 */
public class MenuBorder extends AbstractBorder {

	
	
	
	private Color color;
	
	
	private int harc;
	
	
	private int varc;
	
	
	
	
	
	public MenuBorder(Color color, int harc, int varc) {
		super();
		this.color = color;
		this.harc = harc;
		this.varc = varc;
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see javax.swing.border.Border#getBorderInsets(java.awt.Component)
	 */
	@Override
	public Insets getBorderInsets(Component arg0) {
		return (getBorderInsets(arg0, new Insets(harc, varc, harc, varc)));
	}
	
	
	public Insets getBorderInsets(Component arg0, Insets insets) {
		insets.left = insets.top = harc;
		insets.right = insets.bottom = varc;
		return insets;
	}

	/* (non-Javadoc)
	 * @see javax.swing.border.Border#isBorderOpaque()
	 */
	@Override
	public boolean isBorderOpaque() {
		return true;
	}

	/* (non-Javadoc)
	 * @see javax.swing.border.Border#paintBorder(java.awt.Component, java.awt.Graphics, int, int, int, int)
	 */
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		super.paintBorder(c, g, x, y, width, height);
		
		Graphics2D g2 = (Graphics2D) g.create();
		
		g2.setColor(color);
		g2.setStroke(new BasicStroke(3));
		g2.drawOval(x, y, width, height);
		g2.dispose();
	}

}
