/**
 * 
 */
package de.gg.pi.tv.menu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;

import de.gg.pi.TVMain;

/**
 * @author PeanutPiek
 *
 */
public class MenuButton extends JButton implements DataHandler, MouseListener {

	
	
	public static final Color DEFAULT_GLOW_COLOR = new Color(.4f, .4f, .4f, 1.0f);
	
	
	public static final Color DEFAULT_BUTTON_COLOR = new Color(238, 238, 238);
	
	
	public static final int HARC = 150;
	
	
	public static final int VARC = 130;
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private boolean isGlowing = false;
	
	
	private Color glowColor = DEFAULT_GLOW_COLOR;
	
	
	
	public MenuButton(String label, Icon icon) {
		super(label, icon);
		this.setFocusPainted(false);
		this.setIgnoreRepaint(true);
		this.setContentAreaFilled(false);
		this.addMouseListener(this);
		
		this.setBorder(new MenuBorder(glowColor, HARC, VARC));
	}

	
	@Override
	protected void paintComponent(Graphics g) {
//		super.paintComponent(g);
		
		int x = getX();
		int y = getY();
		double w = g.getClipBounds().getWidth();
		double h = g.getClipBounds().getHeight();
//		
//		
//		g.fillOval(x, y, w, h);
//		
//		g.dispose();
		
		GradientPaint paint = new GradientPaint(new Point(0, 0), Color.WHITE, new Point(getWidth(), 0), Color.ORANGE.brighter()); 
		
		Graphics2D g2 = (Graphics2D) g.create();
	    g2.setPaint(paint);
	    
	    g2.fillRoundRect(0, 0, getWidth(), getHeight(), HARC, VARC);
	    g2.setPaint(Color.BLACK);
	    g2.drawString(getText(), (int) w / 3 , (int) h / 2);
	    g2.dispose();
		
	}
	
	/**
	 * Set this Button if it should glow or not.
	 * Additional call the repaint Method of the Component.
	 * @param glow	state if should Glow.
	 */
	private void setGlowing(boolean glow) {
		isGlowing = glow;
		repaint();
	}
	
	
	@Override
	protected void paintBorder(Graphics g) {
		if(isGlowing) {
			super.paintBorder(g);
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {}


	@Override
	public void mouseEntered(MouseEvent e) {
		if(TVMain.DEBUG) System.out.println("Mouse entered: " + this);
		setGlowing(true);
	}


	@Override
	public void mouseExited(MouseEvent e) {
		if(TVMain.DEBUG) System.out.println("Mouse exited: " + this);
		setGlowing(false);
	}


	@Override
	public void mousePressed(MouseEvent e) {}


	@Override
	public void mouseReleased(MouseEvent e) {}


	@Override
	public JComponent getObject() {
		return this;
	}

}
