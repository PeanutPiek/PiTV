/**
 * 
 */
package de.gg.pi.tv.bind;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author PeanutPiek
 *
 */
@XmlRootElement(name="Activity")
public class Activity {

	
	String name;
	
	
	String iconImagePath;
	
	
	String activityClass;
	
	
	String executable;

	public String getName() {
		return name;
	}

	public String getIconImagePath() {
		return iconImagePath;
	}

	public String getActivityClass() {
		return activityClass;
	}

	public String getExecutable() {
		return executable;
	}

	@XmlAttribute(name="name")
	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name="IconImagePath")
	public void setIconImagePath(String iconImagePath) {
		this.iconImagePath = iconImagePath;
	}

	@XmlElement(name="ActivityClass")
	public void setActivityClass(String activityClass) {
		this.activityClass = activityClass;
	}

	@XmlElement(name="Executable")
	public void setExecutable(String executable) {
		this.executable = executable;
	}
	
}
