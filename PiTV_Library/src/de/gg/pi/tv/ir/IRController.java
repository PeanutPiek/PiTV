/**
 * 
 */
package de.gg.pi.tv.ir;

import org.lirc.LIRCClient;
import org.lirc.LIRCException;

/**
 * This Class declares a Controller Interface for an IR Receiver to read IR Signals.
 *  
 * @author PeanutPiek
 * @version 28.07.2015
 *
 */
public class IRController {
	/**
	 * This Enumaration contains all nesessary Button Codes from a basic Key Pad.
	 * With this Values, it is able to process with every Type of Controller and
	 * get an platform independet use of IR Devices. 
	 * 
	 * @author PeanutPiek
	 * @version 28.07.2015
	 *
	 */
	public enum IRCode {
		/**
		 * Number Button, aka. 0
		 */
		NUM_PAD_0,
		/**
		 * Number Button, aka. 1
		 */
		NUM_PAD_1,
		/**
		 * Number Button, aka. 2
		 */
		NUM_PAD_2,
		/**
		 * Number Button, aka. 3
		 */
		NUM_PAD_3,
		/**
		 * Number Button, aka. 4
		 */
		NUM_PAD_4,
		/**
		 * Number Button, aka. 5
		 */
		NUM_PAD_5,
		/**
		 * Number Button, aka. 6
		 */
		NUM_PAD_6,
		/**
		 * Number Button, aka. 7
		 */
		NUM_PAD_7,
		/**
		 * Number Button, aka. 8
		 */
		NUM_PAD_8,
		/**
		 * Number Button, aka. 9
		 */
		NUM_PAD_9,
		/**
		 * Menu Button, also known as Submit.
		 */
		MENU_OK,
		/**
		 * Menu Button Next.
		 */
		MENU_NEXT,
		/**
		 * Menu Button Previous.
		 */
		MENU_PREV,
		/**
		 * Menu Button Upwards.
		 */
		MENU_UP,
		/**
		 * Menu Button Downwards.
		 */
		MENU_DOWN,
		/**
		 * Menu Button Page Next.
		 */
		MENU_PAGE_NEXT,
		/**
		 * Menu Button Page Previous.
		 */
		MENU_PAGE_PREV,
		/**
		 * Menu Button Back.
		 */
		MENU_BACK
	};
		
	
	private LIRCClient receiver;
	
	
	
	/**
	 * This Method creates a new IRController, which uses the Codesets from the selected Model by
	 * the selected Vendor. The IRController Key Codes are loaded by Lirc API.
	 * 
	 * @return			A new IRController to read Signals from the selected IR Controll Device.
	 */
	public static IRController createNewController() {
		System.out.print("\nCreate new IRController... ");
		IRController controller = null;
		
		try {
			controller = new IRController();
			
			
			
			System.out.println("Done.");
		} catch(Exception ex) {
			System.out.println("");
			ex.printStackTrace();
		}
		
		return controller;
	}
	
	/**
	 * Adds a new Code Listener to the IRController, which is listen for any Key Input.
	 * @param listener	the CodeListener to add.
	 */
	public void addCodeListener(CodeListener listener) {
		receiver.addLIRCListener(listener);
	}
	
	/**
	 * Hidden Default IRController Constructor.
	 * @throws LIRCException 
	 */
	private IRController() throws LIRCException {
		
		receiver = new LIRCClient();
		
		
	}
	
	
	public void dispose() {
		receiver.stopListening();
	}
	
	

	private IRCode translate(String code) {
		
		IRCode result = null;
		
		
		
		
		
		return result;
	}
	
	

}
