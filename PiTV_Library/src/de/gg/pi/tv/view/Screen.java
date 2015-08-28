/**
 * 
 */
package de.gg.pi.tv.view;

import de.gg.pi.tv.ICursor;
import de.gg.pi.tv.MenuBoard;
import de.gg.pi.tv.MenuObject;

/**
 * @author PeanutPiek
 *
 */
public interface Screen {

	
	
	public float getAspectRatio();

	
	
	public void setCursor(ICursor cursor);
	
	
	public int getWidth();
	
	
	public int getHeight();
	
	
	public void setVisible(boolean visible);
	
	
	public void repaint();

	
	public View getView();
	
	
	public void setView(View view);

}
