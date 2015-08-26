/**
 * 
 */
package de.gg.pi.tv.app;

import java.awt.Dimension;

import de.gg.pi.tv.TVActivity;

/**
 * @author PeanutPiek
 *
 */
public class TestActivity extends TVActivity {

	
	public TestActivity() {
		super("Test", "");
	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.TVActivity#call()
	 */
	@Override
	public void call() {
		System.out.println("Test Activity called!");
	}

	@Override
	public void close() {
		System.out.println("Test Activity closed!");
	}

	@Override
	public void resize(Dimension size) {}
}
