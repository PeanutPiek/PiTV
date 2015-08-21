/**
 * 
 */
package de.gg.pi.tv.app;

import de.gg.pi.tv.ActivityType;
import de.gg.pi.tv.TVActivity;

/**
 * @author PeanutPiek
 *
 */
public class EmptyActivity extends TVActivity {

	
	
	
	
	public EmptyActivity() {
		super("", "res/icon/empty-icon.png");
		
	}

	@Override
	public void call() {
		System.out.println("Empty Activity called!");
	}

	@Override
	public void close() {
		System.out.println("Empty Activity is closed!");
	}

}
