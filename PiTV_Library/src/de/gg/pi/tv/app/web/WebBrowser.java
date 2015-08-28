/**
 * 
 */
package de.gg.pi.tv.app.web;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;

import javax.swing.JPanel;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import de.gg.pi.tv.Utils;

/**
 * 
 * 
 * This Class uses JxBrowser (http://www.teamdev.com/jxbrowser) for rendering web content.
 * 
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
	protected Browser browser;
	
	
	private BrowserView bView;
	
	
	protected String pageTemplate;
	
	
	private String preparedTemplate;
	
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
		browser = new Browser();
		bView = new BrowserView(browser);

		setBackground(Color.BLACK);
		
		setLayout(new BorderLayout());
		
		add(bView, BorderLayout.CENTER);
		
		browser.loadHTML(getHTMLContext());
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
			
			browser.loadHTML(html);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public void loadContentTemplate(String template) {
		pageTemplate = template;
	}
	
	
	public void loadContentTemplateByPath(String templatePath) {
		String content = null;
		try {
			content = Utils.getToolkit().readFile(templatePath);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		loadContentTemplate(content);
	}
	
	
	public void prepareContentTemplate(HashMap<String, String> map) {
		
		if(pageTemplate!=null) {
			if(!pageTemplate.isEmpty()) {
				preparedTemplate = pageTemplate;
				for(String key : map.keySet()) {
					preparedTemplate = preparedTemplate.replace(key, map.get(key));
				}
			}
		}
		
	}
	
	
	public void buildContentTemplate() {
		browser.loadHTML(preparedTemplate);
	}
}
