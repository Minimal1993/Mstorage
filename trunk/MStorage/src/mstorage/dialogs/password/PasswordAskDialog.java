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
 * Dialog for ask password to decrypt a file
 */
public class PasswordAskDialog extends PasswordDialog {
	protected String BigIcon = "/images/lock.32x32.png";
	protected String SmallIcon = "/images/lock.24x24.png";
	protected String Title = "Encrypted file. Enter a password.";
	
	public PasswordAskDialog(javax.swing.JFrame parent, boolean modal, File file){
		super(parent, modal, file);
	}
	
	@Override
	protected void initMain(){
		super.initMain();
		
		this.jLabelInvitation.setText("Please, enter passphrase for decryption:");
		this.jLabelOldPassword.setVisible(false);
		this.jPasswordFieldOldPassword.setVisible(false);
		this.jLabelConfirm.setVisible(false);
		this.jPasswordFieldPass2.setVisible(false);
		this.jButtonOK.setText("Decrypt file");
	}
	
}
