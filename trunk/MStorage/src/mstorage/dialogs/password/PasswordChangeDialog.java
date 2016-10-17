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

import mstorage.components.CryptComp;
import mstorage.storagecollection.File;

/**
 * Form for change password
 */
public class PasswordChangeDialog extends PasswordDialog {
	protected String BigIcon = "/images/lock_edit.32x32.png";
	protected String SmallIcon = "/images/lock_edit.24x24.png";
	protected String Title = "Encrypted file. Change password.";

	public PasswordChangeDialog(javax.swing.JFrame parent, boolean modal, File file){
		super(parent, modal, file);
	}
	
	@Override
	protected boolean isOKActionPerformed(java.awt.event.ActionEvent evt){
		if (!this.isPasswordsEquals()) {
			this.jLabelErrorText.setVisible(true);
			return false;
		}
		
		String oldPass = new String(this.jPasswordFieldOldPassword.getPassword());
		if (oldPass.isEmpty() || !oldPass.equals(this.File.getPassword()) ) {
			this.jLabelErrorText.setVisible(true);
			this.jLabelErrorText.setText("Old password is not correct");
			return false;
		}
		
		return true;
	}
	
	@Override
	protected void initMain(){
		super.initMain();
		
		this.jLabelInvitation.setText("Please, confirm current password and enter a new:");
		this.jLabelOldPassword.setVisible(true);
		this.jPasswordFieldOldPassword.setVisible(true);
		this.jLabelPassword.setText("New password:");
		this.jButtonOK.setText("Change password");
	}
	
}
