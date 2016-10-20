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
public class BrowseFindInDirDialog extends TreeChooseDialog {
	
	public BrowseFindInDirDialog(java.awt.Frame parent, boolean modal){
		super(parent, modal);
	}
	
	@Override
	protected void initMain(){
		this.BigIcon = "/images/folder_explore.32x32.png";
		this.SmallIcon = "/images/folder_explore.24x24.png";
		this.Title = "Choose folder for search";
		this.InviteText = "Please, select folder for search into it:";
		this.ButtonOKText = "Choose";
        
        super.initMain();
	}
	
}
