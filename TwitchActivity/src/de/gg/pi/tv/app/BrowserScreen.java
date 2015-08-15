/**
 * 
 */
package de.gg.pi.tv.app;

import java.awt.BorderLayout;
import java.awt.image.BufferedImageFilter;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import chrriis.dj.nativeswing.swtimpl.components.JFlashPlayer;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import de.gg.pi.tv.Utils;
import de.gg.pi.tv.app.js.IJavascriptExecution;
import de.gg.pi.tv.app.js.JavascriptExecutionHandler;
import de.gg.pi.tv.view.Screen;

/**
 * @author PeanutPiek
 *
 */
public class BrowserScreen extends Screen {

	
	public enum ContentType {
		HTML, FLASH, JS;
	}
	
	
	private JFlashPlayer flashPlayer;
	
	
	private JWebBrowser webBrowser;
	
	
	private Browser browser;
	
	
	private BrowserView bView;
	
	
	private String htmlTemplate;
	
	
	public BrowserScreen(String title) {
		super();
		
		TitledBorder border = BorderFactory.createTitledBorder(title);
		border.setTitleJustification(TitledBorder.CENTER);
		setBorder(border);
		setLayout(new BorderLayout());
		
		browser = new Browser();
		bView = new BrowserView(browser);
		
		flashPlayer = new JFlashPlayer();
		
		webBrowser = new JWebBrowser();
		webBrowser.setBarsVisible(false);
		
		add(bView, BorderLayout.CENTER);
		
	}

	
	public void loadTemplate(String fn) throws FileNotFoundException, IOException, IllegalArgumentException {
		if(!fn.contains(".")) fn += ".html";
		System.out.println(fn);
		String content = Utils.getToolkit().readFile(fn);
		if(content.isEmpty()) throw new IllegalArgumentException("Template seems to be Empty!");

		htmlTemplate = content;
	}

	
	
	public void prepareTemplate(Map<String, String> matches) throws IllegalStateException {
		
		IllegalStateException notLoaded = new IllegalStateException("Template not loaded!");
		if(htmlTemplate==null) throw notLoaded;
		if(htmlTemplate.isEmpty()) throw notLoaded;
		
		Iterator<String> iter = matches.keySet().iterator();
		while(iter.hasNext()) {
			String key = iter.next();
			String value = matches.get(key);
			htmlTemplate = htmlTemplate.replace(key, value);
		}
		
	}
	
	
	public void openTemplate() throws IllegalStateException {
		
		IllegalStateException notLoaded = new IllegalStateException("Template not loaded!");
		if(htmlTemplate==null) throw notLoaded;
		if(htmlTemplate.isEmpty()) throw notLoaded;
		
//		flashPlayer.load(getClass(), "resources/templates/player_template.swf");
		webBrowser.setHTMLContent(htmlTemplate);
		
		System.out.println(htmlTemplate);
	}
	

	
	
}
