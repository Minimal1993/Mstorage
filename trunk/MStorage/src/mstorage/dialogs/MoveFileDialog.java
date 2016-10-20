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
public class MoveFileDialog extends TreeChooseDialog {
	
	public MoveFileDialog(java.awt.Frame parent, boolean modal){
		super(parent, modal);
	}
	
	@Override
	protected void initMain(){
		this.BigIcon = "/images/document_move.32x32.png";
		this.SmallIcon = "/images/document_move.24x24.png";
		this.Title = "Move a file";
		this.InviteText = "Please, select destination folder to move a file:";
		this.ButtonOKText = "Move file";
        
        super.initMain();
	}
	
}
