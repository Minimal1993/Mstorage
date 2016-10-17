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

import java.awt.Toolkit;
import mstorage.components.CryptComp;
import mstorage.storagecollection.File;
import mstorage.MainForm;

/**
 * Dialog for create a new password
 */
public class PasswordCreateDialog extends PasswordDialog {
	protected String BigIcon = "/images/lock_add.32x32.png";
	protected String SmallIcon = "/images/lock_add.24x24.png";
	protected String Title = "Encrypt file. Set password.";

	public PasswordCreateDialog(javax.swing.JFrame parent, boolean modal, File file){
		super(parent, modal, file);
		
		this.setTitle(this.Title);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(this.SmallIcon)));
		this.jLabelBigIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource(this.BigIcon)));
		
		int r = 9;
	}
	
	/**
	 * Override parent's method because there is not need crypt file
	 * 
	 * @return 
	 */
	@Override
	protected boolean checksBeforeWork(){
		if (CryptComp.isCryptedFile(this.File.getPath())) return false;
		
		return true;
	}	
	
	@Override
	protected boolean isOKActionPerformed(java.awt.event.ActionEvent evt){
		if (!this.isPasswordsEquals()) {
			this.jLabelErrorText.setVisible(true);
			return false;
		}
		
		return true;
	}
	
//	@Override
	protected void initMain(){
		super.initMain();
		

		
		this.jLabelInvitation.setText("Please, enter passphrase for encryption:");
		this.jLabelOldPassword.setVisible(false);
		this.jPasswordFieldOldPassword.setVisible(false);
		this.jButtonOK.setText("Encrypt file");
	}
	
}
