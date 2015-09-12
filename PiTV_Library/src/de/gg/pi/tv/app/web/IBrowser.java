/**
 * 
 */
package de.gg.pi.tv.app.web;

/**
 * Web Browser Interface, which 
 * 
 * @author PeanutPiek
 *
 */
public interface IBrowser<T> {

	
	/**
	 * Load Html Content.
	 * @param htmlContent	Html Content to load.
	 */
	public void changeHtmlContent(String htmlContent);
	
	/**
	 * Returns the implemented Browser Component, which is used to render
	 * the Web Browser Functionality.
	 * @return	the implemented Browser Component.
	 */
	public T getBrowserComponent();
}
