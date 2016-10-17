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

package mstorage.dialogs;

import mstorage.storagecollection.File;

/**
 *
 */
public class CryptPasswordChildDialog extends CryptPasswordDialog {
	
	public CryptPasswordChildDialog(java.awt.Frame parent, boolean modal, File file) {
		super(parent, modal, file);
	}

}
