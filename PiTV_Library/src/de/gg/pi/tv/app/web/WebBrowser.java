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
 * This Class uses JxBrowser (http://www.teamdev.com/jxbrowser) for rendering web content.
 * 
 * Disclaimer: JxBrowser library is not part of PiTV, but it is a proprietary software. The use of 
 * JxBrowser is governed by JxBrowser Product Licence Agreement. If you would like to use JxBrowser
 * in your development, please contact TeamDev team: teamdev.com/jxbrowser
 * 
 * @author PeanutPiek
 * @version 26.08.2015
 *
 */
public class WebBrowser extends JPanel implements IBrowser<Browser> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Browser Logic Component.
	 */
	protected Browser browser;
	
	/**
	 * Browser View Component.
	 */
	private BrowserView bView;
	
	/**
	 * Web Template.
	 */
	protected String pageTemplate;
	
	/**
	 * Replaced Template.
	 */
	private String preparedTemplate;
	
	/**
	 * Default Constructor.
	 */
	public WebBrowser() {

		initialize();
	}
	
	/**
	 * Initialize Browser Component.
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
	 * Returns default Html Content.
	 * @return	default Html Content.
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
	 * Replace Html Context with a Html File Content.
	 * @param htmlFilePath	File Path of Html File to load.
	 */
	public void changeHtmlContext(String htmlFilePath) {
		String html = getHTMLContext();
		try {
			html = Utils.getToolkit().readFile(htmlFilePath);
			changeHtmlContent(html);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void changeHtmlContent(String htmlContent) {
		browser.loadHTML(htmlContent);
	}
	
	/**
	 * Loads a template for WebBrowser.
	 * @param template	the html Template Content, to load.
	 */
	public void loadContentTemplate(String template) {
		if(template!=null)
			pageTemplate = template;
	}
	
	/**
	 * Loads a template for WebBrowser from the given Filepath.
	 * @param templatePath	The Filepath, to load a Html Template.
	 */
	public void loadContentTemplateByPath(String templatePath) {
		try {
			String content = null;
			content = Utils.getToolkit().readFile(templatePath);
			loadContentTemplate(content);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Prepares the loaded Template with the given Replacement Map.
	 * @param map	Map of Replacements, which will used in the loaded Template.
	 */
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
	
	/**
	 * Loads the Content Template into the Browser Context.
	 */
	public void buildContentTemplate() {
		changeHtmlContent(preparedTemplate);
	}
	
	/**
	 * Returns JxBrowser Component of this WebBrowser.
	 * @return	The JxBrowser Component.
	 */
	public Browser getJxBrowser() {
		return browser;
	}

	@Override
	public Browser getBrowserComponent() {
		return getJxBrowser();
	}
	
	
}
