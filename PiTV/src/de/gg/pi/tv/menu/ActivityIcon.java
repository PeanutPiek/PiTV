/**
 * 
 */
package de.gg.pi.tv.menu;

import java.awt.Image;

import de.gg.pi.tv.TVActivity;
import de.gg.pi.tv.menu.page.MenuObject;

/**
 * @author PeanutPiek
 *
 */
public class ActivityIcon extends MenuObject {

	
	private TVActivity activity;
	
	
	
	
	
	
	public ActivityIcon(TVActivity a) {
		activity = a;
		
		
		
	}


	@Override
	public String getName() {
		return activity.getName();
	}


	@Override
	public Image getIcon() {
		return activity.getIcon();
	}


	@Override
	public void call() {
		activity.call();
	}

	/**
	 * 
	 * @return
	 */
	public TVActivity getActivity() {
		return activity;
	}

}
