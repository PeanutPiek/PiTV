/**
 * 
 */
package de.gg.pi.tv.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.gg.pi.tv.DefaultActivity;
import de.gg.pi.tv.IActivity;
import de.gg.pi.tv.ICursor;
import de.gg.pi.tv.MenuBoard;
import de.gg.pi.tv.MenuObject;
import de.gg.pi.tv.menu.ActivityIcon;
import de.gg.pi.tv.view.Screen;
import de.gg.pi.tv.view.View;
import de.gg.pi.tv.view.ViewMenuAdapter;

/**
 * @author PeanutPiek
 *
 */
public final class SwingScreen extends JFrame implements Screen {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	private SwingView view;
	
	
//	/**
//	 * Sub Window, which contains the Content of a Single Page of Application Icons.
//	 */
//	private MenuBoard<MenuObject> page;
	
	/**
	 * 
	 */
	private ICursor cursor;
	
	
	
	public SwingScreen(String title, Dimension size, SwingView view) {
		super(title);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setSize(size);
		
		setContentPane(new JPanel());
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(Color.RED);
		
		this.view = view;
		if(this.view!=null) {
			
			getContentPane().add(view, BorderLayout.CENTER);
			
			view.addMenuHandler(new ViewMenuAdapter() {
				
				@Override
				public void activityFocused(MenuObject obj) {
					IActivity a = ((ActivityIcon)obj).getActivity();
					
					IActivity def = DefaultActivity.getFocusedActivity();
					if(def != null) {
						def.resize(new Dimension(getWidth(), getHeight()));
					}
					
					setVisible(false);
				}
				
				@Override
				public void activityCloseRequested() {
					DefaultActivity.setFocusedActivity(null);
					
					setVisible(true);
				}
			});
		}
	}	
	
	@Override
	public float getAspectRatio() {
		return getWidth() / getHeight();
	}
	
	@Override
	public void setCursor(ICursor cursor) {
		this.cursor = cursor;
	}
	
	@Override
	public View getView() {
		return view;
	}
	
	@Override
	public void setView(View view) {
		if(view instanceof SwingView) {
			this.view = (SwingView)view;
		}
		else System.err.println("The View have to be type of SwingView, in case of SwingScreen is used.");
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		
		
	}

	@Override
	public Thread getRenderingThread() {
		return null;
	}
}
