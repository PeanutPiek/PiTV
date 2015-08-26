/**
 * 
 */
package de.gg.pi.tv;

import java.awt.Dimension;
import java.awt.Image;

/**
 * @author PeanutPiek
 *
 */
public final class DefaultActivity implements IActivity {

	
	private static IActivity focus = null;
	
	
	
	private DefaultActivity() {}
	
	
	
	/* (non-Javadoc)
	 * @see de.gg.pi.tv.Activity#call()
	 */
	@Override
	public void call() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.Activity#close()
	 */
	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.Activity#getName()
	 */
	@Override
	public String getName() { return null; }

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.Activity#getIcon()
	 */
	@Override
	public Image getIcon() { return null; }
	
	
	@Override
	public void resize(Dimension size) {}
	
	
	public static IActivity getFocusedActivity() {
		return focus;
	}
	
	
	public static void setFocusedActivity(IActivity activity) {
		focus = activity;
	}

	
}
