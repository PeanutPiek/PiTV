/**
 * 
 */
package de.gg.pi.tv;

import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import de.gg.pi.tv.app.ActivityHandler;

/**
 * @author PeanutPiek
 *
 */
public final class DefaultActivity implements IActivity {

	
	private static DefaultActivity _instance = null;
	
	
	private static IActivity focus = null;
	
	
	private List<ActivityHandler> handlers = new ArrayList<ActivityHandler>();
	
	
	private DefaultActivity() {}

	
	public List<ActivityHandler> getHandlers() {
		return handlers;
	}
	
	
	public void addHandler(ActivityHandler handler) {
		handlers.add(handler);
	}
	
	
	public void removeHandler(ActivityHandler handler) {
		handlers.remove(handler);
	}
	
	
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

	
	public static DefaultActivity getInstance() {
		if(_instance==null) {
			_instance = new DefaultActivity();
		}
		return _instance;
	}
	
	
	public static IActivity getFocusedActivity() {
		return focus;
	}
	
	
	public static void setFocusedActivity(IActivity activity) {
		focus = activity;
		if(focus!=null) {
			for(ActivityHandler handler : DefaultActivity.getInstance().getHandlers()) {
				handler.activityFocused(activity);
			}
		}
	}

	
}
