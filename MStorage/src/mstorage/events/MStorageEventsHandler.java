/*
 * MStorage - storage for notes.
 * 
 * Permission is granted to copy, distribute and/or
 * modify  this  document under  the  terms  of the
 * GNU General Public License
 * 
 * @author: ilya.gulevskiy
 * @email: mstorage.project@gmail.com
 * @date: 2016
 */

package mstorage.events;

import StorageCollection.StorageItem;
import java.lang.reflect.Method;
import mstorage.MainForm;

/**
 * base functional for Events Handlers
 */
public abstract class MStorageEventsHandler {
	
	protected StorageItem StorageItem;

	public MStorageEventsHandler(StorageItem StorageItem) {
		this.StorageItem = StorageItem;
	}
	
	/**
	 * Call a handler for menu command.
	 *
	 * @param String command
	 */
	public void call(Object... command) {
		if (0 == command.length) {
			return;
		}
		
		try {
			String method = "eh_" + command[0].toString();

			Object[] mainArgs = java.util.Arrays.copyOfRange(command, 1, command.length);
			Class[] argTypes = new Class[mainArgs.length];
			for(int i = 0; i < argTypes.length; i++) {
				argTypes[i] = Object.class;
			}

			Method m = this.getClass().getMethod(method, argTypes);
			m.setAccessible(true);
			Object ret = m.invoke(this, mainArgs);
			
		} catch (Exception e) {
			MainForm.showError(Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + e.getMessage());
		}

		// Update tree
		MainForm.getInstance().getTree().updateUI();

		// Check toolbar
		MainForm.getInstance().checkButtonCloseCurrentDocument();
		
		this.doAfterCall();

	}
	
	/**
	 * Actions for implement after call performed
	 */
	public void doAfterCall(){
		
	}
	
}
