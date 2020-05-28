/**
 * 
 */
package de.gg.pi.tv.graphics;

import org.eclipse.swt.widgets.Composite;

import de.gg.pi.tv.ICursor;
import de.gg.pi.tv.MenuObject;
import de.gg.pi.tv.view.View;
import de.gg.pi.tv.view.ViewMenuHandler;

/**
 * @author PeanutPiek
 *
 */
public class SWTView extends Composite implements View {

	public SWTView() {
		super(null, 0);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.View#addMenuHandler(de.gg.pi.tv.view.ViewMenuHandler)
	 */
	@Override
	public void addMenuHandler(ViewMenuHandler handler) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.View#setCursor(de.gg.pi.tv.ICursor)
	 */
	@Override
	public void setCursor(ICursor cursor) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.View#addElement(de.gg.pi.tv.MenuObject)
	 */
	@Override
	public void addElement(MenuObject object) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.View#hasNextPage()
	 */
	@Override
	public boolean hasNextPage() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.View#hasPrevPage()
	 */
	@Override
	public boolean hasPrevPage() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.View#moveNextPage()
	 */
	@Override
	public void moveNextPage() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.View#movePrevPage()
	 */
	@Override
	public void movePrevPage() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.View#loadPage()
	 */
	@Override
	public void loadPage() {
		// TODO Auto-generated method stub

	}

}
