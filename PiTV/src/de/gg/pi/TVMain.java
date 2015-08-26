/**
 * 
 */
package de.gg.pi;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.annotation.Documented;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import de.gg.pi.tv.PiTV;
import de.gg.pi.tv.TVActivity;
import de.gg.pi.tv.Utils;
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
	 * Default Log File.
	 */
	public static final String DEFAULT_LOG = "pitv_default.log";
	
	/**
	 * Default Error Log File.
	 */
	public static final String DEFAULT_ERROR = "pitv_default.err";
	
	
	/**
	 * PiTV Object, which declares the interactable Application Context.
	 */
	private PiTV tv;
	
	
	/**
	 * Constructor of TVMain Class.
	 */
	private TVMain() {
		
		tv = PiTV.newInstance();
		
		
		
	}
	
	/**
	 * 
	 */
	private void setupAndRun() {
		try {
			System.out.print("Loading Configuration... ");
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
		if(!xmlFile.exists()) throw new IllegalAccessError("No default Config File found!");
		try {
			JAXBContext jaxb = JAXBContext.newInstance(PiTVConfig.class);
			Unmarshaller jaxbUnmarshaller = jaxb.createUnmarshaller();
			// Retrieve Config from XML File.
			PiTVConfig config = (PiTVConfig) jaxbUnmarshaller.unmarshal(xmlFile);
			// Inspect every Activity from Activity List.
			for(Activity act : config.getActivityList().getActivities()) {
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
							File f = new File("act/" + exec + ".jar");
							if(f.exists()) {
								URL url = f.toURI().toURL();
								URL[] urls = new URL[] {url};
								cl = new URLClassLoader(urls);
								clazz = cl.loadClass(className);
							}
						}
						if(clazz!=null) {
							// Create a new Instance of the Activity Class.
							// Therefore it is needed to got a default Constructor without any Arguments in the Activity Class.
							Constructor<?> con = clazz.getConstructor();
							if(con!=null) {
								con.setAccessible(true);
								a = (TVActivity) clazz.newInstance();
									
							} else {
								// no default Constructor found.
								System.err.println("Activity " +clazz.getName() + " got no default Constructor!");
							}
							
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
			Color bg = config.getBackground();
			tv.setBackgroundColor(bg);
		}catch(Exception ex) {
			if(DEBUG) {
				ex.printStackTrace();
			} else {
				System.err.println(ex.getMessage());
			}
		}
	}
	
	
	
	
	
	/**
	 * Runs the Installation of the PiTV, which creates all
	 * nesessary Folders and extract all default Files in to
	 * the Installation Directory.
	 * @return	state of Installation is done without Errors.
	 * @throws IOException	Throws an IOException, in case of any Error, creating
	 * 						a Folder or File.
	 */
	private boolean installPiTV() throws IOException {
		
		int chk = 0;
		int err = 0;
		
		File root = new File(TVMain.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		
		// Activity Folder.
		File actFolder = new File(root.getAbsolutePath() + "/act");
		// Create Activity Folder.
		if(actFolder.exists()) chk++;
		else actFolder.mkdirs();
		
		// Extract Default Config File.
		File configFile = new File(root.getAbsolutePath() + "/config.xml");
		String basicConfigContent = Utils.getToolkit().readFile("de/gg/pi/tv/basic/config/config.xml");
		
		if(!configFile.exists()) {
			if(!configFile.createNewFile()) {
				System.err.println("Can not create Default Configuration File!");
				err++;
			} else {
				System.out.println("Default Configuration File created.");
				
				FileWriter fw = new FileWriter(configFile);
				fw.write(Utils.prettyFormat(basicConfigContent, 5));
				fw.flush();
				
				fw.close();
			}
		} else chk++;
		
		
		if(chk==0&&err==0) return true;
		// 
		return false;
	}
	
	
	private void reinstallPiTV() {
		
	}
	
	
	
	
	/**
	 * Entry Point of PiTV Application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		boolean forceInstall = false,
				forceReinstall = false,
				printHelp = false;
		String  logPath = DEFAULT_LOG,
				errPath = DEFAULT_ERROR;
		
		for(String a : args) {
			
			if(a.equals("-i")||a.equals("--install")) {
				forceInstall = true;
			}
			else
			if(a.equals("-r")||a.equals("--reinstall")) {
				forceReinstall = true;
			}
			else
			if(a.equals("-h")||a.equals("--help")) {
				printHelp = true;
			}
		}
		
		try {
			
			if(printHelp) {
				System.out.println(getHelpText());
			}
			else {
				// Main Instance to hold the Application Context.
				TVMain main = new TVMain();		
				if(forceInstall) {
					main.installPiTV();
				}
				
				main.setupAndRun();
				
				// Change Default Output Stream.
				System.setOut(Utils.createPrintStream(TVMain.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/" + logPath));
				// Change Default Error Stream.
				System.setErr(Utils.createPrintStream(TVMain.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/" + errPath));
			
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * Prints the Argument Help Context for this Application.
	 * @return	the Help Context for this Application.
	 */
	private static String getHelpText() {
		String help = "\n";
		help += "---- PiTV Console Help Context ----\n";
//		help += "\n";
		help += "Arg	ArgAlt		Definition\n";
		help += "--------------------------------------\n";
		help += "-i	--install	: Start the Installation of PiTV.\n";
		help += "-r	--reinstall	: Start a Reinstallation. Maybe nesessary for Update.\n";
		help += "-h	--help		: Prints this Help Context.\n";
		help += "\n";
		// Return Help Context.
		return help;
	}

}
