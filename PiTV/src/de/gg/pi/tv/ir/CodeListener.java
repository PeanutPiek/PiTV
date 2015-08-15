/**
 * 
 */
package de.gg.pi.tv.ir;

import org.lirc.LIRCEvent;
import org.lirc.LIRCListener;

import de.gg.pi.tv.ir.IRController.IRCode;

/**
 * @author PeanutPiek
 *
 */
public abstract class CodeListener implements LIRCListener {

	
	
	@Override
	public void received(LIRCEvent arg0) {
		IRCode code = null;
		
		// TODO Auto-generated method stub		
		
		
		codeReceived(code);
	}
		
	public abstract void codeReceived(IRCode code);
	
}
