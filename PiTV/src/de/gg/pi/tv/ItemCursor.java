/**
 * 
 */
package de.gg.pi.tv;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * @author PeanutPiek
 *
 */
public abstract class ItemCursor extends JPanel {

	
	protected JComponent selectedComponent;
	
	
	protected Color mainColor;
	
	
	public ItemCursor(Color color) {
		super();
		mainColor = color;
		setOpaque(true);
		setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
		
	}
	
	
	public void selectComponent(JComponent comp) {
		selectedComponent = comp;
	}
	
	
	public JComponent getSelectedComponent() {
		return selectedComponent;
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
//		super.paintComponent(g);
		
		if(selectedComponent!=null) {
			int w = selectedComponent.getWidth();
			int h = selectedComponent.getHeight();
			
			int x = selectedComponent.getX();
			int y = selectedComponent.getY();
			
			double p = 0.15;
			double lw = w * p;
			double lh = h * p;
			
			Graphics2D g2 = (Graphics2D)g.create();
			
			g2.setComposite(AlphaComposite.Clear);
			g2.setColor(mainColor);
			g2.fillRect(0, 0, (int) lw, 3);
			g2.fillRect(0, 0, 3, (int) lh);
			
			g2.dispose();
		}
	}
	
}
