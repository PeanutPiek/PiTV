/**
 * 
 */
package de.gg.pi.tv;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import de.gg.pi.tv.menu.page.MenuButton;
import de.gg.pi.tv.menu.page.grid.GridData;

/**
 * @author PeanutPiek
 *
 */
public class GridView extends SwingView {

	
	
	private int rows;
	
	
	private int cols;
	
	
	private int elementsPerPage;
	
	
	private int pageCount;
	
	
	private int pageNum;
	
	
	private boolean isValid = false;
	
	
	public GridView(int rows, int cols) {
		super();
		// TODO Auto-generated constructor stub
		
		this.rows = rows;
		this.cols = cols;
		
		this.elementsPerPage = rows * cols;
		this.pageCount = 0;
		this.pageNum = 0;
		
		
		setLayout(new BorderLayout());
		page.setLayout(null);
		
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
		add(btnNext, BorderLayout.EAST);
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
		add(btnPrev, BorderLayout.WEST);
		add(page, BorderLayout.CENTER);
		
	}
	
	
	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		
	}


	@Override
	public void addElement(MenuObject object) {
		super.addElement(object);
		this.pageCount = (int) Math.round((this.elements.size() / elementsPerPage) + 0.5d);
		isValid = false;
	}
	

	@Override
	public boolean hasNextPage() {
		return (elements.size()>(elementsPerPage*(pageNum+1))&&pageCount>0);
	}


	@Override
	public boolean hasPrevPage() {
		return (elements.size()>(elementsPerPage)&&pageCount>0&&pageNum>0);
	}

	
	@Override
	public void moveNextPage() {
		if(hasNextPage()) {
			int w = elementsPerPage * ++pageNum;
			fillGrid(w, elements.size() - w);
		}
	}
	
	
	@Override
	public void movePrevPage() {
		if(hasPrevPage()) {
			int w = elementsPerPage * --pageNum;
			fillGrid(w, elements.size() - w);
		}
	}
	
	@Override
	public void loadPage() {
		fillGrid();
	}
	
	@Override
	public void paintComponents(Graphics g) {
		super.paintComponents(g);
	}

	
	private ActionListener nextPage() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveNextPage();
			}
		};
	}
	
	
	private ActionListener prevPage() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				movePrevPage();
			}
		};
	}
	
	
	private void fillGrid() {
		if(elements.size()>0&&isVisible()) {
			int offset = pageNum*elementsPerPage;
			fillGrid(offset, elements.size() - offset);
		}
	}
	
	
	private void fillGrid(int offset, int length) {
		
		int i = 0;
		int l = elements.size();
		if(l>offset&&offset>0) {
			i = offset;
		}
		int posOffset = 0;
		int hgap = 5;
		int vgap = 15;
		int w = 0;
		int width = page.getWidth() - hgap;
		int height = page.getHeight() - vgap;
		int awi = width / cols;
		int ahi = height / rows;
		
		page.removeAll();
		
		for(int k = 0; k < rows; ++k) {
			for(int j = 0; j < cols; ++j) {
				int ii = k*cols+j;
				if(ii>=length&&length>0) break;
				MenuObject mo = elements.get(ii + i);
				final GridData<MenuObject> a = new GridData<MenuObject>(mo, k, j);
				
				ImageIcon img = null;
				if(a.getIcon()!=null) {
					img = new ImageIcon(a.getIcon());
				}
				final MenuButton abtn = new MenuButton(a.getName(), img) {
//					/**
//					 * 
//					 */
//					private static final long serialVersionUID = 1L;
//					@Override
//					public void mouseEntered(MouseEvent e) {
//						super.mouseEntered(e);
//						selectedData = a;
//					}
//					@Override
//					public void mouseExited(MouseEvent arg0) {
//						super.mouseExited(arg0);
//						selectedData = null;
//					}
				};
//				abtn.setItemCursor(itemCursor);
				a.setDataHandler(abtn);
				
				int ax = ((posOffset + j) * awi + hgap) + j * hgap;
				int ay = ((posOffset + k) * ahi + vgap) + k * vgap;
				int aw = (width / cols) - (cols * hgap) - hgap;
				int ah = (height / rows) - (rows * hgap) - vgap;
				
				abtn.setBounds(ax, ay, aw, ah);
				abtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
//						handleMenu(a.getData(), OPEN);
						a.call();
					}
				});
				
				page.add(abtn);
				page.validate();
				w++;
			}
		}
		// Fill the rest of the Board with Dummy Patches.
		for(int v = 0; v < elementsPerPage - w; ++v) {
			JPanel dummy = new JPanel();
//			dummy.setBorder(BorderFactory.createLineBorder(Color.black));
			dummy.setBackground(getBackground());
			dummy.setBounds(posOffset + v * width, posOffset + v * height, width, height);
			page.add(dummy);
		}
		
		page.validate();
		validate();
	}



}
