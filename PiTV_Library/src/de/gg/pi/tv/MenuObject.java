/**
 * 
 */
package de.gg.pi.tv;

import java.awt.Image;

/**
 * @author PeanutPiek
 *
 */
public abstract class MenuObject {

	
	/**
	 * 
	 * @return
	 */
	public abstract String getName();
	
	/**
	 * 
	 * @return
	 */
	public abstract Image getIcon();
	
	/**
	 * 
	 */
	public abstract void call();
	
}
