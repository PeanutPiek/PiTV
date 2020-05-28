/**
 * 
 */
package de.gg.pi.tv.graphics;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import de.gg.pi.TVMain;
import de.gg.pi.tv.ICursor;
import de.gg.pi.tv.view.Screen;
import de.gg.pi.tv.view.View;

/**
 * OpenGL Screen Component.
 * 
 * @author PeanutPiek
 *
 */
public class OGLScreen extends Frame implements Screen, Runnable {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public static final int FREQ = 90;
	
	
	private boolean closeRequested = false;
	
	
	private Thread renderingThread;
	
	
	private OGLView view;
	
	
	private Dimension size;
	
	
	public OGLScreen(String title, Dimension size, OGLView view) {
		super(title);
		this.size = size;
		
		setLayout(null);
		setSize(size);
		setUndecorated(true);
		
		renderingThread = new Thread(this);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				dispose();
			}
		});
		
		setView(view);
		
	}
	
	
	public boolean isCloseRequested() {
		return closeRequested;
	}
	
	
	
	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.Screen#getAspectRatio()
	 */
	@Override
	public float getAspectRatio() {
		return getSize().width / getSize().height;
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
		return getSize().width;
	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.Screen#getHeight()
	 */
	@Override
	public int getHeight() {
		return getSize().height;
	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.Screen#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if(visible) {
			pack();
			setSize(size);
		}
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
		return view;
	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.Screen#setView(de.gg.pi.tv.view.View)
	 */
	@Override
	public void setView(View view) {
		if(view!=null&&view instanceof Canvas) {
			try {
				setVisible(true);
				Display.setParent((Canvas) view);
				Display.setVSyncEnabled(true);
				
//				pack();
				setVisible(true);
				Display.create();
				this.view = (OGLView) view;
				renderingThread.start();
			} catch (LWJGLException e) {
				if(TVMain.DEBUG)
					e.printStackTrace();
				else
					System.err.println(e.getMessage());
			}
		}
	}
	
	
	private void handleInput() {
		// call IR Listener?
		// or what Input?
		
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!(closeRequested=Display.isCloseRequested())) {
			
			handleInput();
			
			
			Display.sync(FREQ);
			Display.update();
		}
	}

	@Override
	public Thread getRenderingThread() {
		return renderingThread;
	}

}
