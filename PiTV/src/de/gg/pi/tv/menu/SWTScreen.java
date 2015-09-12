/**
 * 
 */
package de.gg.pi.tv.menu;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.gg.pi.tv.ICursor;
import de.gg.pi.tv.view.Screen;
import de.gg.pi.tv.view.View;

/**
 * @author PeanutPiek
 *
 */
public class SWTScreen extends Shell implements Screen, Runnable {

	
	public static final Display display = new Display();
	
	
	private Thread renderingThread;
	
	
	public SWTScreen(String title, int width, int height) {
		super(display, SWT.NO_TRIM | SWT.ON_TOP);
		
		setSize(width, height);
		
		
		renderingThread = new Thread(this);
	}
	
	@Override
	public Thread getRenderingThread() {
		return renderingThread;
	}
	
	
	@Override
	public float getAspectRatio() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCursor(ICursor cursor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void repaint() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public View getView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setView(View view) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!isDisposed()) {
			if(!display.readAndDispatch()) {
				
				
				display.sleep();
			}
		}
		
		display.dispose();
	}

}
