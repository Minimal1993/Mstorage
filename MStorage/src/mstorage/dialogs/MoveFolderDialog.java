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
 * Dialog for choose folder to move File
 */
public class MoveFolderDialog extends TreeChooseDialog {
	
	public MoveFolderDialog(java.awt.Frame parent, boolean modal){
		super(parent, modal);
	}
	
	@Override
	protected void initMain(){
		this.BigIcon = "/images/document_move.32x32.png";
		this.SmallIcon = "/images/document_move.24x24.png";
		this.Title = "Move a folder";
		this.InviteText = "Please, select destination folder to move a folder:";
		this.ButtonOKText = "Move folder";
        
        super.initMain();
	}
	
}
