/**
 * 
 */
package de.gg.pi.tv.bind;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author PeanutPiek
 *
 */
@XmlRootElement(name="IRControl")
public class IRConfig {

	
	String type;
	
	
	int gpio;


	public String getType() {
		return type;
	}


	public int getGpio() {
		return gpio;
	}

	@XmlAttribute(name="type")
	public void setType(String type) {
		this.type = type;
	}

	@XmlAttribute(name="gpio")
	public void setGpio(int gpio) {
		this.gpio = gpio;
	}
	
}
