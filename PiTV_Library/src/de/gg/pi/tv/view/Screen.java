/**
 * 
 */
package de.gg.pi.tv.view;

import de.gg.pi.tv.ICursor;
import de.gg.pi.tv.MenuBoard;

/**
 * @author PeanutPiek
 *
 */
public interface Screen {

	
	
	public float getAspectRatio();
	
	
	public void setPage(MenuBoard board);
	
	
	public void setCursor(ICursor cursor);
	
	
	public boolean hasNextPage();
	
	
	public boolean hasPrevPage();
	
	
	public void moveNextPage();
	
	
	public void movePrevPage();
	
	
	public void movePage(int index);
}
