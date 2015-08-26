/**
 * 
 */
package de.gg.pi.tv.menu;

import java.awt.Image;

import de.gg.pi.tv.IActivity;
import de.gg.pi.tv.TVActivity;
import de.gg.pi.tv.menu.page.MenuObject;

/**
 * @author PeanutPiek
 *
 */
public class ActivityIcon extends MenuObject {

	
	private IActivity activity;
	
	
	
	
	
	
	public ActivityIcon(IActivity a) {
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
	public IActivity getActivity() {
		return activity;
	}

}
