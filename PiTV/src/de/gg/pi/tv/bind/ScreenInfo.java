/**
 * 
 */
package de.gg.pi.tv.bind;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author PeanutPiek
 *
 */
@XmlRootElement(name="ScreenRendering")
public class ScreenInfo {

	public enum ScreenEngine {
		Swing,
		SWT,
		OGL
	}
	
	
	
	String engineIdentifier;
	
	/**
	 * 
	 */
	String background;
	
	
	public String getEngineIdentifier() {
		return engineIdentifier;
	}
	
	@XmlElement(name="engine")
	public void setEngineIdentifier(String engine) {
		engineIdentifier = engine;
	}
	
	
	public String getBackground() {
		return background;
	}
	
	@XmlElement(name="bgdata")
	public void setBackground(String background) {
		this.background = background;
	}
	
	
	public ScreenEngine getScreenEngine() {
		return ScreenEngine.valueOf(engineIdentifier);
	}
}
