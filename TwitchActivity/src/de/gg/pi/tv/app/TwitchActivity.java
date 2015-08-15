/**
 * 
 */
package de.gg.pi.tv.app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import de.gg.pi.tv.TVActivity;
import de.gg.pi.tv.Utils;
import de.gg.pi.tv.app.BrowserScreen.ContentType;
import de.gg.pi.tv.app.js.JavascriptExecutionHandler;
import de.gg.pi.tv.view.Screen;

/**
 * @author PeanutPiek
 *
 */
public class TwitchActivity extends TVActivity {

	
	public static final boolean DEBUG = true;
	
	
	public static final String TEMPLATE_PATH = "resources/templates/";
	
	
	private BrowserScreen browser;
	
	
	private List<String> broadcasters;
	
	
	private JFrame screen;
	
	
	private Dimension screenSize = new Dimension(800, 600);

	
	public TwitchActivity() {
		super("Twitch", "");
		// TODO Auto-generated constructor stub

		broadcasters = new ArrayList<String>();
		
		screen = new JFrame();
		screen.setUndecorated(true);
		screen.setLayout(new BorderLayout());
		
		if(!DEBUG) {
			screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		}
		
		screen.setSize(screenSize);
		
		
		
		browser = new BrowserScreen("Twitch Activity");
		screen.add(browser, BorderLayout.CENTER);
		
		
		screen.setVisible(true);
		
	}



	@Override
	public void call() {
		// TODO Auto-generated method stub
		System.out.println("Twitch Activity called.");
		
		try {
			browser.loadTemplate(TEMPLATE_PATH + "player_template");
			
			HashMap<String, String> matches = new HashMap<String, String>();
			matches.put("${WIDTH}", (int)screenSize.getWidth() + "");
			matches.put("${HEIGHT}", (int)screenSize.getHeight() + "");
			matches.put("${CHANNEL}", "peanutflake");
			matches.put("${VOLUME}", "10");
			matches.put("${AUTOPLAY}", "true");
			
			browser.prepareTemplate(matches);
			browser.openTemplate();
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		System.out.println("Twitch Activity closed.");
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UIUtils.setPreferredLookAndFeel();
		NativeInterface.open();
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				TVActivity activity = new TwitchActivity();
				activity.call();
			}

		});

		
		NativeInterface.runEventPump();
		
	}
}
