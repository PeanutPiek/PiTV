/**
 * 
 */
package de.gg.pi.tv.menu;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import de.gg.pi.tv.ICursor;
import de.gg.pi.tv.OGLView;
import de.gg.pi.tv.view.Screen;
import de.gg.pi.tv.view.View;

/**
 * @author PeanutPiek
 *
 */
public class OGLScreen extends Frame implements Screen, Runnable {

	
	
	public static final int FREQ = 60;
	
	
	private boolean closeRequested = false;
	
	
	private Thread renderingThread;
	
	
	public OGLScreen(String title, Dimension size, OGLView view) {
		super(title);
		
		setLayout(null);
		
		renderingThread = new Thread(this);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				dispose();
			}
		});
		
		
		try {
			Display.setParent(view);
			Display.setVSyncEnabled(true);
			
			pack();
			Display.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public boolean isCloseRequested() {
		return closeRequested;
	}
	
	
	
	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.Screen#getAspectRatio()
	 */
	@Override
	public float getAspectRatio() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.Screen#setCursor(de.gg.pi.tv.ICursor)
	 */
	@Override
	public void setCursor(ICursor cursor) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.Screen#getWidth()
	 */
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.Screen#getHeight()
	 */
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.Screen#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean visible) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.Screen#repaint()
	 */
	@Override
	public void repaint() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.Screen#getView()
	 */
	@Override
	public View getView() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.Screen#setView(de.gg.pi.tv.view.View)
	 */
	@Override
	public void setView(View view) {
		// TODO Auto-generated method stub

	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!(closeRequested=Display.isCloseRequested())) {
			
			
			Display.sync(FREQ);
			
			Display.update();
		}
	}


	@Override
	public Thread getRenderingThread() {
		return renderingThread;
	}

}
