/**
 * 
 */
package de.gg.pi.tv.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


/**
 * @author PeanutPiek
 *
 */
public abstract class MenuGridBoard<T extends GridValue> extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public static final int HGAP = 15;
	
	
	public static final int VGAP = 15;
	
	
	public static final int OPEN = 2;
	
	
	public static final int CLOSE = 1;
	
	
	public static final int NONE = 0;
	
	
	private int rowCount;
	
	
	private int columnCount;
	
	
	private ArrayList<GridData<T>> valueList;
	
	
	private JPanel gridMenu;
	
	
	public MenuGridBoard(int rows, int cols) {
		rowCount = rows;
		columnCount = cols;
		
		valueList = new ArrayList<GridData<T>>();
		
		gridMenu = new JPanel();
		gridMenu.setLayout(new GridLayout(rows, cols, HGAP, VGAP));
		
		setLayout(new BorderLayout());
		add(gridMenu, BorderLayout.CENTER);
		
		add(new JPanel(), BorderLayout.NORTH);
		add(new JPanel(), BorderLayout.SOUTH);
		add(new JPanel(), BorderLayout.WEST);
		add(new JPanel(), BorderLayout.EAST);
	}
	
	
	public void addValue(T val) {
		int left = -1, top = -1;
		GridData<T> gd = new GridData<T>(val, left, top);
		valueList.add(gd);
	}
	
	
	public void fillGrid(int offset, int length, int elementsPerPage) {
		int i = 0, wx = 0, hx = 0;
		int l = valueList.size();
		if(l>offset&&offset>0) {
			i = offset;
		}
		
		gridMenu.removeAll();
		int w = 0;
		for(int j = 0; j < l - i; ++j) {
			if(j>=length&&length>0) break;
			GridData<T> a = valueList.get(j + i);
			
			ImageIcon img = null;
			if(a.getIcon()!=null) {
				img = new ImageIcon(a.getIcon());
			}
			JButton abtn = new JButton(a.getName(), img);
			abtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					handleMenu(a.getData(), OPEN);
					a.call();
				}
			});
			
			gridMenu.add(abtn);
			w++;
		}
		for(int v = 0; v < elementsPerPage - w; ++v) {
			JPanel dummy = new JPanel();
			dummy.setBorder(BorderFactory.createLineBorder(Color.black));
			gridMenu.add(dummy);
		}
		
		validate();
	}
	
	
	public void clearGrid() {
		
		int i = gridMenu.getComponentCount();
		
		for(int j = 0; j < i - 1; ++j) {
			Component c = gridMenu.getComponent(j);
			if(c instanceof JButton || c instanceof JPanel) {
				gridMenu.remove(c);
			}
		}
		
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		
		
	}
	
	
	protected abstract void handleMenu(T obj, int state);
	
}
