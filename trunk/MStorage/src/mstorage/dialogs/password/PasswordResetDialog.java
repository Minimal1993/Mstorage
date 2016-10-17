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

package mstorage.dialogs.password;

import mstorage.storagecollection.File;

/**
 * Dialog for reset password
 */
public class PasswordResetDialog extends PasswordDialog {
	
	public PasswordResetDialog(javax.swing.JFrame parent, boolean modal, File file){
		super(parent, modal, file);
	}
	
	@Override
	protected void initMain(){
        this.BigIcon = "/images/lock_delete.32x32.png";
        this.SmallIcon = "/images/lock_delete.24x24.png";
        this.Title = "Reset encryption. Confirm a password.";
		
		this.jLabelInvitation.setText("Please, enter passphrase for reset:");
		this.jLabelOldPassword.setVisible(false);
		this.jPasswordFieldOldPassword.setVisible(false);
		this.jLabelConfirm.setVisible(false);
		this.jPasswordFieldPass2.setVisible(false);
		this.jButtonOK.setText("Reset encryption");
        
        super.initMain();
	}
	
}
