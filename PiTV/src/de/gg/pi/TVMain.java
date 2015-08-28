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
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import de.gg.pi.tv.ActivityWrapper;
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
	
	
	
	public static final String DEFAULT_CONFIG_FILE = "tv/basic/config/config.xml";
	

	/**
	 * Debug State Identifier.
	 */
	public static final boolean DEBUG = false;
	
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
	 * 
	 */
	private PiTVConfig config;
	
	/**
	 * Constructor of TVMain Class.
	 */
	private TVMain() {
		this(DEFAULT_CONFIG_FILE);
	}
	
	
	/**
	 * 
	 * @param configFilePath
	 */
	private TVMain(String configFilePath) {
		System.out.println("PiTV started...");
		try {
			List<ActivityWrapper> list = null;
			// try to load configuration.
			System.out.println("Try to load PiTV Configuration...");
			config = loadConfig(configFilePath);
			if(config!=null) {
				System.out.println("Done.");
				System.out.println("Load Activity List...");
				list = loadActivityList();
				if(list!=null) {
					System.out.println("Done.");
				} else {
					System.out.println("Failed.");
				}
			} else {
				System.out.println("Failed.");
			}
			// Create new PiTV Instance.
			System.out.println("Try to get PiTV Instance...");
			tv = PiTV.newInstance();
			System.out.println("Done.");
			// Add loaded Activities into PiTV.
			System.out.println("Load Activities into PiTV...");
			for(ActivityWrapper a : list) {
				System.out.println("Add " + a);
				TVActivity act = a.getActivity();
				if(act!=null)
					tv.addActivity(act);
				else System.out.println("Unable to add " + act);
			}
			System.out.println("Done.");
			// Run PiTV.
			System.out.println("Start PiTV Application...");
			boolean started = tv.start();
			if(started) {
				System.out.println("Done.");
			} else {
				throw new Exception("Unable to start PiTV!");
			}
			// Initialized.
			System.out.println("PiTV started and running well.");
		}catch(Exception ex) {
			System.out.println("Failed.");
			ex.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param configFile
	 * @return
	 * @throws IllegalAccessError
	 * @throws JAXBException
	 */
	private PiTVConfig loadConfig(String configFile) throws IllegalAccessError, JAXBException {
		PiTVConfig config = null;
		File configXmlFile = new File(TVMain.class.getResource(configFile).getFile());
		// Check if Config File exists.
		if(!configXmlFile.exists()) throw new IllegalAccessError("No default Config File found!");
		// Load Config File via JAXB.
		JAXBContext jaxb = JAXBContext.newInstance(PiTVConfig.class);
		Unmarshaller jaxbUnmarshaller = jaxb.createUnmarshaller();
		config = (PiTVConfig) jaxbUnmarshaller.unmarshal(configXmlFile);
		// return loaded Config.
		return config;
	}
	
	/**
	 * 
	 * @return
	 */
	private List<ActivityWrapper> loadActivityList() {
		String className = null;
		String exec = null;
		String iconImagePath = null;
		List<ActivityWrapper> list = new ArrayList<ActivityWrapper>();
		try {
		// For each Activity in configuration Activity List.
			for(Activity act : config.getActivityList().getActivities()) {
				// Get Information about current Activity.
				iconImagePath = act.getIconImagePath();
				exec = act.getExecutable();
				className = act.getActivityClass();
				// Dummy Activity.
				ActivityWrapper a = null;
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
						System.out.println("Build ActivityWrapper for " + clazz.getName());
						a = createActivityWrapper(clazz);
						if(a!=null) {
							// Set IconImage from Activity Definition.
							a.setActivityIcon(iconImagePath);
							
							list.add(a);
							if(DEBUG)
								System.out.print("\n" + a + " succesfully loaded.");
						} else System.err.println("Unresolved Problem, during Building Activity.");
					}
				}				
			}
			return list;
		} catch(Exception e) {
			if(!(e instanceof java.lang.ClassNotFoundException)) {
				e.printStackTrace();
			} else {
				System.out.print("\nUnable to load TVActivity " + className + ".\n");
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param source
	 * @return
	 * @throws Exception
	 */
	private ActivityWrapper createActivityWrapper(Class<?> source) throws Exception {
		ActivityWrapper aw = new ActivityWrapper(source);
		if(aw.isValidActivity()) {
			return aw;
		}
		return null;
	}
		
	/**
	 * Runs the Installation of the PiTV, which creates all
	 * necessary Folders and extract all default Files in to
	 * the Installation Directory.
	 * @return	state of Installation is done without Errors.
	 * @throws IOException	Throws an IOException, in case of any Error, creating
	 * 						a Folder or File.
	 */
	private static boolean installPiTV() throws IOException {
		
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
	
	
	private static boolean reinstallPiTV() {
		// TODO 
		
		return false;
	}
	
	
	private static boolean isInstalled() {
		// TODO
		
		return true;
	}
	
	
	/**
	 * Entry Point of PiTV Application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		boolean forceInstall = false,
				alreadyInstalled = false,
				printHelp = false;
		String  logPath = DEFAULT_LOG,
				errPath = DEFAULT_ERROR;
		
		for(String a : args) {
			
			if(a.equals("-i")||a.equals("--install")) {
				forceInstall = true;
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
				alreadyInstalled = isInstalled();
				if(forceInstall||!alreadyInstalled) {
					if(installPiTV()) {
						System.out.println("PiTV successful installed.");
					}
				} else if(forceInstall) {
					if(reinstallPiTV()) {
						System.out.println("PiTV successful reinstalled.");
					}
				}
				
				// Main Instance to hold the Application Context.
				TVMain main = new TVMain();		
				
				
				
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
		help += "-h	--help		: Prints this Help Context.\n";
		help += "\n";
		// Return Help Context.
		return help;
	}

}
