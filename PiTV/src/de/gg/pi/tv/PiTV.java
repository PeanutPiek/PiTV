package de.gg.pi.tv;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.gg.pi.TVMain;
import de.gg.pi.tv.bind.Activity;
import de.gg.pi.tv.ir.CodeListener;
import de.gg.pi.tv.ir.IRController;
import de.gg.pi.tv.ir.IRController.IRCode;
import de.gg.pi.tv.menu.ActivityIcon;
import de.gg.pi.tv.menu.Cursor;
import de.gg.pi.tv.menu.GridData;
import de.gg.pi.tv.menu.MenuGridBoard;

import javax.swing.JButton;
import javax.swing.BoxLayout;


/**
 * This Class is the Main Object of the PiTV Application, which supports an Application based
 * Context Menu, to use on a Television Device. This Application extends the Television Device
 * with a full Screen Menu to run different Applications from the Television with Controll Support.
 * 
 * 
 * @author PeanutPiek
 * @version 28.07.2015
 *
 */
public class PiTV implements Runnable {

	/**
	 * 
	 */
	public static final Color DEFAULT_BACKGROUND_COLOR = Color.BLACK;
	
	/**
	 * 
	 */
	private static final long RENDERING_INTERVAL = 15;

	/**
	 * Instance of the PiTV Object, to hold this Class as Singleton.
	 */
	private static PiTV _instance = null;
	
	/**
	 * Full Screen Application Window.
	 */
	private JFrame screen;
	
	/**
	 * Sub Window, which contains the Content of a Single Page of Application Icons.
	 */
	private JPanel page;
	
	/**
	 * Count of Rows for Activities in the Screen Content.
	 */
	private int rows = 4;
	
	/**
	 * Count of Coulumns for Activities in the Screen Content.
	 */
	private int cols = 6;
	
	/**
	 * Current Page Number.
	 */
	private int pageNum = 0;
	
	/**
	 * Total Count of Pages.
	 */
	private int pageCount = 0;
	
	/**
	 * Total Count of Activities per Page.
	 */
	private int elementsPerPage = rows * cols;
	
	/**
	 * Aspect Ratio of the Screen Device.
	 */
	private float aspect_ratio;
	
	/**
	 * Background Color of Main Panel.
	 */
	private Color backgroundColor = Color.WHITE;
	
	/**
	 * Rendering Thread, which paints the page.
	 */
	private Thread renderingThread;
	
	/**
	 * TVActivity List, with available Activities.
	 */
	private final ArrayList<TVActivity> activities = new ArrayList<TVActivity>();
	
	/**
	 * IRController, to read IR Input from TV Controll Device.
	 */
	private IRController ir;
	
	
	private boolean activ = false;
	
	
	private ItemCursor cursor;
	
	/**
	 * 
	 * @return
	 */
	public static PiTV getInstance() {
		if(_instance==null) {
			return PiTV.newInstance();
		}
		return _instance;
	}
	
	/**
	 * 
	 * @return
	 */
	public static PiTV newInstance() {
		if(_instance==null) {
			System.out.print("Initializing new PiTV... ");
			_instance = new PiTV();
			System.out.println("Done.");
		}
		return _instance;
	}
	
	/**
	 * 
	 */
	private PiTV() {
		
		Dimension screen_dimension = Toolkit.getDefaultToolkit().getScreenSize();
		if(TVMain.DEBUG) {
			screen_dimension = new Dimension(800, 600);
		}
		screen = new JFrame("PiTV");
		screen.setSize(screen_dimension.width, screen_dimension.height);
		screen.setUndecorated(true);
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screen.getContentPane().setBackground(DEFAULT_BACKGROUND_COLOR);
		
		aspect_ratio = screen.getWidth() / screen.getHeight();
		
		screen.getContentPane().setLayout(new BorderLayout());		
		
		final Dimension d = screen_dimension;
		page = new MenuGridBoard<ActivityIcon>(rows, cols) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void handleMenu(ActivityIcon obj, int state) {
				// Get involved Activity.
				TVActivity a = obj.getActivity();
				// Visibillity of Screen Context.
				boolean visible = true;
				if(state==OPEN) {
					// Activity is opened.
					switch(a.getType()) {
					case FULLSCREEN:
						// Application should opened in Fullscreen mode.
						visible = false;
						break;
					case NO_SCREEN:
						// Application should opened without Screen.
						visible = false;
						break;
					case LAYERED_SCREEN:
						// Application should opened in Layered Screen.
						visible = true;
						break;
					default:
						break;
					}
				}
				if(state==CLOSE) {
					// Activity is closed.
					visible = true;
				}
				// Show/Hide Screen Content.
				screen.setVisible(visible);
				

			}
		};
		String footText = "PiTV [" + TVMain.VERSION + ((TVMain.DEBUG)?"/Debug":"") + "] IR_Enabled: " + TVMain.IR_ENABLED;
		((MenuGridBoard<ActivityIcon>)page).setFooterText(footText);
		cursor = new ItemCursor(Color.WHITE) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
		};
//		page.add(cursor);
		
		screen.getContentPane().add(page, BorderLayout.CENTER);
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
		
		screen.getContentPane().add(btnNext, BorderLayout.EAST);
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
		screen.getContentPane().add(btnPrev, BorderLayout.WEST);
		
		if(TVMain.IR_ENABLED) {
			ir = IRController.createNewController();
			if(ir==null) {
				System.err.println("No IRController created!");
			} else {
				ir.addCodeListener(new CodeListener() {
					
					@Override
					public void codeReceived(IRCode code) {
						switch(code) {
						case MENU_PAGE_NEXT:
							nextPage();
							break;
						case MENU_PAGE_PREV:
							prevPage();
							break;
						default:
							System.out.println("Unknown IRCode Received!");
							break;
						}
					}
				});
			}
		}
	}
	
	
	public void start() {
		activ = true;
		screen.setVisible(activ);
		screen.repaint();
		
		fillGrid();
		
		renderingThread = new Thread(this);
		renderingThread.start();
	}
	
	
	protected boolean hasPrevPage() {
		return (activities.size()>(elementsPerPage)&&pageCount>0&&pageNum>0);
	}
	
	
	protected boolean hasNextPage() {
		return (activities.size()>(elementsPerPage*(pageNum+1))&&pageCount>0);
	}
	
	/**
	 * 
	 * @return
	 */
	private ActionListener nextPage() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(hasNextPage()) {
					int w = elementsPerPage * ++pageNum;
					fillGrid(w, activities.size() - w);
				}
			}
		};
	}
	
	/**
	 * 
	 * @return
	 */
	private ActionListener prevPage() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(hasPrevPage()) {
					int w = elementsPerPage * --pageNum;
					fillGrid(w, activities.size() - w);
				}
			}
		};
	}
	
	/**
	 * 
	 */
	public void fillGrid() {
		fillGrid(0, elementsPerPage);
	}
	
	/**
	 * 
	 * @param offset
	 */
	public void fillGrid(int offset) {
		fillGrid(offset, elementsPerPage);
	}
	
	/**
	 * 
	 * @param offset
	 * @param length
	 */
	private void fillGrid(int offset, int length) {
		System.out.println("Fill Activity Grid...");
		int w = length - offset;
//		((MenuGridBoard<ActivityIcon>) page).clearGrid();
		((MenuGridBoard<ActivityIcon>) page).fillGrid(offset, length, rows, cols);
//		((MenuGridBoard<ActivityIcon>) page).repaint();
		screen.repaint();
		System.out.println("Done.");
	}
	
	/**
	 * Returns the Aspect Ratio of the Screen Device.
	 * @return Aspect Ratio of the Screen Device.
	 */
	public float getAspectRatio() {
		return aspect_ratio;
	}
	
	/**
	 * 
	 * @param activity
	 * @return
	 */
	public boolean addActivity(TVActivity activity) {
		if(!activities.contains(activity)) {
			activities.add(activity);
			((MenuGridBoard<ActivityIcon>)page).addValue(new ActivityIcon(activity));
			if(activities.size()>pageCount*elementsPerPage) {
				pageCount++;
			}
//			fillGrid(elementsPerPage*pageNum, elementsPerPage);
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param activity
	 */
	public void removeActivity(TVActivity activity) {
		if(activities.contains(activity)) {
			activities.remove(activity);
			if(activities.size()<pageCount*elementsPerPage) {
				pageCount--;
			}
//			fillGrid(elementsPerPage*pageNum, elementsPerPage);
		}
	}
	
	
	public void setBackgroundColor(Color background) {
		this.backgroundColor = background;
		screen.getContentPane().setBackground(backgroundColor);
		page.setBackground(backgroundColor);
		page.invalidate();
		page.validate();
		page.repaint();
	}

	@Override
	public void run() {
		while(activ) {
//			((MenuGridBoard<ActivityIcon>) page).fillGrid(0, activities.size(), elementsPerPage);
			GridData<?> sd = ((MenuGridBoard<ActivityIcon>) page).getSelectedData();
			if(cursor!=null) {
				if(sd!=null) {
					cursor.selectComponent(sd.getHandler().getObject());
				} else {
					cursor.selectComponent(null);
				}
			}
			screen.repaint();
			try {
				Thread.sleep(RENDERING_INTERVAL);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	
	
}
