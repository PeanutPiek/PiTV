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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
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
	
	
	private final JPanel gridMenu = new JPanel();
	
	
	private GridData<T> selectedData;
	
	
	private final JPanel[] placeholder = new JPanel[] {new JPanel(), new JPanel(), new JPanel()};
	
	
	private final JPanel footer = new JPanel();
	
	
	public MenuGridBoard(int rows, int cols) {
		rowCount = rows;
		columnCount = cols;
		
		valueList = new ArrayList<GridData<T>>();
		
//		gridMenu = new JPanel()
		gridMenu.setLayout(new GridLayout(rows, cols, HGAP, VGAP));
		
		setLayout(new BorderLayout());
		add(gridMenu, BorderLayout.CENTER);
		
		add(placeholder[0], BorderLayout.NORTH);
		add(footer, BorderLayout.SOUTH);
		add(placeholder[1], BorderLayout.WEST);
		add(placeholder[2], BorderLayout.EAST);
	}
	
	
	public void setFooterText(String text) {
		JLabel label = new JLabel(text);
		label.setHorizontalAlignment(JLabel.CENTER);
		int co = getBackground().getRGB();
		int ci = (0x00FFFFFF - (co | 0xFF000000)) | (co & 0xFF000000); 
		label.setForeground(new Color(ci));
		footer.add(label);
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
		for(int j = 0; j < Math.min(l - i, elementsPerPage); ++j) {
			if(j>=length&&length>0) break;
			final GridData<T> a = valueList.get(j + i);
			
			ImageIcon img = null;
			if(a.getIcon()!=null) {
				img = new ImageIcon(a.getIcon());
			}
			final MenuButton abtn = new MenuButton(a.getName(), img);
			abtn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					abtn.glow(true);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					abtn.glow(false);
				}
			});
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
//			dummy.setBorder(BorderFactory.createLineBorder(Color.black));
			dummy.setBackground(getBackground());
			gridMenu.add(dummy);
		}
		
		validate();
	}
	
	
	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
		if(gridMenu!=null)
			gridMenu.setBackground(bg);
		if(placeholder!=null) {
			for(JPanel p : placeholder)
				p.setBackground(bg);
		}
		if(footer!=null)
			footer.setBackground(bg);
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
