/**
 * 
 */
package de.gg.pi.tv.view;

import de.gg.pi.tv.ICursor;
import de.gg.pi.tv.MenuObject;

/**
 * @author PeanutPiek
 *
 */
public interface View {

	
	void addMenuHandler(ViewMenuHandler handler);

	
	void setCursor(ICursor cursor);


	void addElement(MenuObject object);
	
	
	boolean hasNextPage();
	
	
	boolean hasPrevPage();
	
	
	void moveNextPage();
	
	
	void movePrevPage();
	
	
	void loadPage();
	

}
