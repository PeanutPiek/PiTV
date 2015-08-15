/**
 * 
 */
package de.gg.pi;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import de.gg.pi.tv.PiTV;
import de.gg.pi.tv.TVActivity;
import de.gg.pi.tv.bind.Activity;
import de.gg.pi.tv.bind.PiTVConfig;

/**
 * TVMain Class declares the Entry Point for the PiTV Application, which runs a Smart TV like Application
 * on a Computer bound to the Television Device.
 * Primary, this Application is designed to use on a Raspberry PI any Type, which is connected to a
 * Television Device with HDMI Connector. The optional Use of an IR Receiver, connected to the GPIO
 * of the Raspberry PI, is recommended for complete Functionallity of this Application. 
 * 
 * 
 * @author PeanutPiek
 * @version 28.07.2015
 *
 */
public class TVMain {

	/**
	 * Debug State Identifier.
	 */
	public static final boolean DEBUG = true;
	
	/**
	 * Application Version.
	 */
	public static final String VERSION = "dev";

	/**
	 * State if IR is enabled.
	 */
	public static final boolean IR_ENABLED = false;
	
	/**
	 * PiTV Object, which declares the interactable Application Context.
	 */
	private PiTV tv;
	
	
	/**
	 * Constructor of TVMain Class.
	 */
	private TVMain() {
		
		tv = PiTV.newInstance();
		
		
		
		try {
			System.out.print("Loading Activities... ");
			loadConfig();
			System.out.println("\nDone.");
			tv.start();
		} catch(Exception ex) {
			System.out.println("Failed!");
			if(DEBUG) {
				ex.printStackTrace();
			} else {
				System.err.println(ex.getMessage());
			}
		}
		
	}
	
	/**
	 * Reads used Activity List from predefined XML File.
	 *  
	 * @throws IllegalAccessError	Activity List is needed for Functionallity of this Application,
	 * 								so there is an Error thrown if the Activity List File is not found.
	 */
	private void loadConfig() throws IllegalAccessError {
		// Get default XML File Activity List.
		File xmlFile = new File("res/config.xml");
		// If the XML File does not exists, exit with Error.
		if(!xmlFile.exists()) throw new IllegalAccessError("No Activity List File found!");
		try {
			JAXBContext jaxb = JAXBContext.newInstance(PiTVConfig.class);
			Unmarshaller jaxbUnmarshaller = jaxb.createUnmarshaller();
			// Retrieve Activity List from XML File.
			PiTVConfig list = (PiTVConfig) jaxbUnmarshaller.unmarshal(xmlFile);
			// Inspect every Activity from Activity List.
			for(Activity act : list.getActivities().getActivities()) {
				// Get Information about selected Activity.
				String iconImagePath = act.getIconImagePath();
				String className = act.getActivityClass();
				String exec = act.getExecutable();
				try {
					// Dummy Activity.
					TVActivity a = null;
					if(!className.isEmpty()) {
						ClassLoader cl;
						// If ActivityClass is defined, search for the needed Class.
						Class<?> clazz = null;
						if(exec.isEmpty()) {
							clazz = Class.forName(className);
						} else {
							File f = new File(exec + ".jar");
							if(f.exists()) {
								URL url = f.toURI().toURL();
								URL[] urls = new URL[] {url};
								cl = new URLClassLoader(urls);
								clazz = cl.loadClass(className);
							}
						}
						if(clazz!=null) {
							// Create a new Instance of the Activity Class.
							// Therefor it is needed to got a default Constructor without any Arguments in the Activity Class.
							a = (TVActivity) clazz.newInstance();
						}
						if(a!=null) {
							// Set IconImage from Activity Definition.
							a.setIcon(iconImagePath);
						}
					}
					// If there is an Activity yet, add this to the Activity List.
					if(a!=null) {
						tv.addActivity(a);
						if(DEBUG)
							System.out.print("\n" + a + " succesfully loaded.");
					}
				} catch(Exception e) {
					if(DEBUG&&!(e instanceof java.lang.ClassNotFoundException)) {
						e.printStackTrace();
					} else {
						System.out.print("\nUnable to load TVActivity " + className + ".\n");
					}
				}
			}
			
			tv.setBackgroundColor(list.getBackground());
		}catch(Exception ex) {
			if(DEBUG) {
				ex.printStackTrace();
			} else {
				System.err.println(ex.getMessage());
			}
		}
	}
	
	
	
	/**
	 * Entry Point of PiTV Application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		// Main Instance to hold the Application Context.
		TVMain main = new TVMain();		
		
		
	}

}
