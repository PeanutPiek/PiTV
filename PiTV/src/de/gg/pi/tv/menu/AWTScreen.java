/**
 * 
 */
package de.gg.pi.tv.menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import de.gg.pi.tv.ICursor;
import de.gg.pi.tv.MenuBoard;
import de.gg.pi.tv.menu.page.grid.MenuGridBoard;
import de.gg.pi.tv.view.Screen;

/**
 * @author PeanutPiek
 *
 */
public final class AWTScreen extends JFrame implements Screen {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Sub Window, which contains the Content of a Single Page of Application Icons.
	 */
	private MenuBoard page;
	
	/**
	 * 
	 */
	private ICursor cursor;
	
	
	
	public AWTScreen(String title, Dimension size) {
		super(title);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setSize(size);
		
		getContentPane().setLayout(new BorderLayout());
		
		// right Button to go to next Page.
		JButton btnNext = new JButton() {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isVisible() {
				// Show this Button only if a next Page is available.
				if(hasNextPage()) {
					return super.isVisible();
				}
				return false;
			}
		};
		btnNext.addActionListener(nextPage());
		
		getContentPane().add(btnNext, BorderLayout.EAST);
		// left Button to go to previouse Page.
		JButton btnPrev = new JButton() {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isVisible() {
				// Show this Button only if a previous Page is available.
				if(hasPrevPage()) {
					return super.isVisible();
				}
				return false;
			};
		};
		btnPrev.addActionListener(prevPage());
		getContentPane().add(btnPrev, BorderLayout.WEST);
		
	}
	
	
	public ActionListener nextPage() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveNextPage();
			}
		};
	}
	
	
	public ActionListener prevPage() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				movePrevPage();
			}
		};
	}
	
	
	@Override
	public float getAspectRatio() {
		return getWidth() / getHeight();
	}


	@Override
	public void setPage(MenuBoard board) {
		// TODO Auto-generated method stub
		page = board;
		if(page!=null) {
			getContentPane().remove(page);
		}
		getContentPane().add(page, BorderLayout.CENTER);
	}
	
	
	@Override
	public void setCursor(ICursor cursor) {
		this.cursor = cursor;
		if(page!=null) {
			page.setCursor(cursor);
		}
	}
	
	
	@Override
	public boolean hasNextPage() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@Override
	public boolean hasPrevPage() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@Override
	public void moveNextPage() {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void movePrevPage() {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		
		
	}
}
