/**
 * 
 */
package de.gg.pi.tv;

import java.lang.reflect.Constructor;

/**
 * @author PeanutPiek
 *
 */
public class ActivityWrapper {
	
	private TVActivity activity; 
	
	
	private Class<?> activityClass;
	
	
	
	public ActivityWrapper(Class<?> sourceClass) {
		activityClass = sourceClass;

	}
	
	
	public TVActivity getActivity() {
		if(activity==null) {
			try {
				activity = ActivityWrapper.newActivityInstance(activityClass, this);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			
		}
		return activity;
	}
	
	/**
	 * 
	 * @param sourceClass
	 * @return
	 * @throws Exception
	 */
	private static TVActivity newActivityInstance(Class<?> sourceClass, ActivityWrapper wrapper) throws Exception {
		Constructor<?> con = sourceClass.getConstructor();
		if(con!=null) {
			con.setAccessible(true);
			TVActivity a = null;
			a = (TVActivity) con.newInstance();
			a.setWrapper(wrapper);
			Thread.sleep(300);
			return a;
		} else {
			// no default Constructor found.
			throw new Exception("Activity " + sourceClass.getName() + " got no default Constructor!");
		}		
	}


	public boolean isValidActivity() {
		return Utils.isTypeOf(activityClass, TVActivity.class);
	}


	public void setActivityIcon(String iconImagePath) {
		if(isValidActivity()) {
			getActivity().setIcon(iconImagePath);
		}
	}

}
