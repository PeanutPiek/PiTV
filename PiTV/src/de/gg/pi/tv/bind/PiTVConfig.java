/**
 * 
 */
package de.gg.pi.tv.bind;

import java.awt.Color;
import java.util.List;

import javax.xml.bind.annotation.*;

import de.gg.pi.TVMain;

/**
 * @author PeanutPiek
 *
 */
@XmlRootElement(name="PiTVConfig")
public class PiTVConfig {

	
	String backgroundColor;
	
	
	ActivityList activities;
	
	
	List<IRConfig> irConfig;

	
	public List<IRConfig> getIrConfig() {
		return irConfig;
	}

	@XmlElement(name="IRControl")
	public void setIrConfig(List<IRConfig> irConfig) {
		this.irConfig = irConfig;
	}

	public ActivityList getActivities() {
		return activities;
	}

	@XmlElement(name="Activities")
	public void setActivities(ActivityList activities) {
		this.activities = activities;
	}
	
	
	public String getBackgroundColor() {
		return backgroundColor;
	}
	
	@XmlElement(name="backgroundcolor")
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	/**
	 * 
	 * @return
	 */
	public Color getBackground() {
		Color c = null;
		String cs = this.backgroundColor;
		try {
			// Hex Color Code.
			if(cs.startsWith("#")) {
				String cn = cs.substring(1);
				if(cn.length()==6||cn.length()==8) {
					
					String cr = cn.substring(0, 2);
					String cg = cn.substring(2, 4);
					String cb = cn.substring(4, 6);
					String ca = "ff";
					if(cn.length()==8)
						ca = cn.substring(6, 8);
					
					int r = Integer.parseInt(cr, 16);
					int g = Integer.parseInt(cg, 16);
					int b = Integer.parseInt(cb, 16);
					int a = Integer.parseInt(ca, 16);
					
					c = new Color(r, g, b, a);
					
				} else throw new NumberFormatException("Hex Color Code unsupported Format!");
			}
			else
			// Int Color Code.
			if(cs.contains(",")) {
				String[] tmp = cs.split(",");
				int r = 255, g = 255, b = 255, a = 255;
				
				if(tmp.length==3||tmp.length==4) {
					String cr = tmp[0];
					String cg = tmp[1];
					String cb = tmp[2];
					
					r = Integer.parseInt(cr);
					g = Integer.parseInt(cg);
					b = Integer.parseInt(cb);
					
				} else throw new NumberFormatException("Int Color Code unsupported Format!");
				if(tmp.length==4) {
					String ca = tmp[3];
					
					a = Integer.parseInt(ca);
				}
				
				c = new Color(r, g, b, a);
			}
			else {
				// String Color and everything, which could awt.Color decode.
				c = Color.getColor(cs);
			}
			if(c == null) throw new IllegalArgumentException("BackgroundColor: Unsupported Argument!");
		} catch(Exception ex) {
			if(TVMain.DEBUG)
				ex.printStackTrace();
			else
				System.err.println(ex.getMessage());
		}
		return c;
	}
	
	
}
