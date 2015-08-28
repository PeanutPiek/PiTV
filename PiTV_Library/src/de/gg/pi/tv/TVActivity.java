package de.gg.pi.tv;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import de.gg.pi.Library;

/**
 * This Class provides the basic Object of a PiTV Activity.
 * An Activity is any On-Screen Routine which is callable from the App Menu.
 * An On-Screen Routine describes any Subprogram, which is called as Application on 
 * the PiTV. Application really means Application, because only .JAR Files are called.
 * 
 * @author PeanutPiek
 *
 */
public abstract class TVActivity implements IActivity {
	
	
	/**
	 * Number of Activities were created since Start of this Program.
	 */
	private static int ACTIVITY_COUNT = 0;
	
	
	/**
	 * The unique Identification Number of this Activity.
	 */
	private int id;
	
	/**
	 * Name of the Activity.
	 */
	protected String name = null;
	
	/**
	 * Icon of the Activity.
	 */
	protected Image icon = null;
	
	/**
	 * 
	 */
	protected JFrame screen;

	
	private ActivityWrapper wrapperReference;
	
	
	/**
	 * 
	 * @param name
	 * @param iconUrl
	 */
	protected TVActivity(String name, String iconUrl) {
		this.id = ACTIVITY_COUNT++;
		this.name = name;
		
		if(!iconUrl.isEmpty())
			setIcon(iconUrl);
		
		initializeScreen();
	}
	
	/**
	 * 
	 * @param name
	 * @param icon
	 */
	protected TVActivity(String name, Image icon) {
		this(name);
		
		this.icon = icon;
	}
	
	/**
	 * 
	 * @param name
	 */
	protected TVActivity(String name) {
		this(name, "");
	}
	
	
	private void initializeScreen() {
		screen = new JFrame(this.name);
		screen.setUndecorated(true);
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screen.setLayout(null);
		
	}
	
	
	/**
	 * 
	 * @param iconImagePath
	 */
	public void setIcon(String iconImagePath) {
		File iconFile = new File(iconImagePath);
		loadIcon(iconFile);
	}
	
	/**
	 * 
	 * @param iconFile
	 */
	private void loadIcon(File iconFile) {
		if(iconFile.exists()) {
			try {
				icon = ImageIO.read(iconFile);
			} catch (IOException e) {
				if(Library.DEBUG)
					e.printStackTrace();
				else
					System.err.println(e.getMessage());
			}
		}
	}
	
	/**
	 * Returns the unique Identification Number of this Activity.
	 * @return Identification Number of this Activity.
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Returns the Icon of this Activity.
	 * @return the Icon of this Activity.
	 */
	public Image getIcon() {
		return icon;
	}
	
	/**
	 * Returns the Name of this Activity.
	 * @return the Name of this Activity.
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * This Method will called if the TVActivity is clicked.
	 * Use this Method to run a sub Application.
	 */
	public abstract void call();
	
	/**
	 * This Method will called if the TVActivity is closed.
	 * Use this Method to close the sub Application and free all resources.
	 */
	public abstract void close();
	
	
	public void resize(Dimension size) {
		if(screen!=null) {
			screen.setSize(size);
			screen.invalidate();
			screen.validate();
		}
	}
	
	
	@Override
	public String toString() {
		return "TVActivity@" + id + " [" + name + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof TVActivity) {
			return (name.equals(((TVActivity) obj).getName()) && (id==((TVActivity)obj).getID()));
		}
		return super.equals(obj);
	}

	public void setWrapper(ActivityWrapper wrapper) {
		wrapperReference = wrapper;
	}

}
