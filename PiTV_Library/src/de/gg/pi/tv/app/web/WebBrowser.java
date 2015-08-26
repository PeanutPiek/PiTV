/**
 * 
 */
package de.gg.pi.tv.app.web;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;


import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

import de.gg.pi.tv.Utils;

/**
 * 
 * @author PeanutPiek
 * @version 26.08.2015
 *
 */
public class WebBrowser extends JPanel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	protected JWebBrowser browser;
	
	/**
	 * 
	 */
	public WebBrowser() {

		
		initialize();
	}
	
	/**
	 * 
	 */
	private void initialize() {
		browser = new JWebBrowser();
		browser.setBarsVisible(false);
		browser.setButtonBarVisible(false);
		browser.setLocationBarVisible(false);
		browser.setMenuBarVisible(false);
		browser.setStatusBarVisible(false);
		browser.setBackground(Color.BLACK);
		browser.getNativeComponent().setBackground(Color.BLACK);
		browser.setFocusable(false);
		browser.setJavascriptEnabled(true);
		
		browser.setHTMLContent(getHTMLContext());
		
		setBackground(Color.BLACK);
		
		setLayout(new BorderLayout());
		
		add(browser, BorderLayout.CENTER);
	}
	
	/**
	 * 
	 * @return
	 */
	protected String getHTMLContext() {
		String html = "";
		
		html += "<html>";
		html += "<head><title>Default Webbrowser Page</title></head>";
		html += "<body>";
		html += "<h2 align=\"center\">It works!</h2>";
		html += "</body>";
		html += "</head>";
		
		return html;
	}
	
	/**
	 * 
	 * @param htmlFilePath
	 */
	public void changeHtmlContext(String htmlFilePath) {
		String html = getHTMLContext();
		try {
			html = Utils.getToolkit().readFile(htmlFilePath);
			
			browser.setHTMLContent(html);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
