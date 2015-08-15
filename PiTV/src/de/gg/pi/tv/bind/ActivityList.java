/**
 * 
 */
package de.gg.pi.tv.bind;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author PeanutPiek
 *
 */
@XmlRootElement(name="Activities")
public class ActivityList {

	
	
	List<Activity> activities;

	
	public List<Activity> getActivities() {
		return activities;
	}

	@XmlElement(name="Activity")
	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	
	
	
}
