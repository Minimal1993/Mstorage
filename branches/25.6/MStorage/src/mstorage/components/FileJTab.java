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
package mstorage.components;

import mstorage.storagecollection.File;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTextArea;
import mstorage.MainForm;
import mstorage.utils.Hash;

/**
 * This is wrapper for JPanel component with data about current File,
 * representing in panel
 */
public class FileJTab extends javax.swing.JPanel implements FocusListener {

	public File File;
	public JTextArea TextAreaDocument;
	public JScrollPane ScrollPaneDocumentText;
	public JPanel JPanelDocumentPictures;
	
	/**
	 * Save last action according to visibility of image carousel
	 */
	public boolean VisibilityForJPanelDocumentPictures = true;
	
	
	/**
	 * For economy purposes. When file will save, his content will get md5 summa of it.
	 */
	public String savedContentMD5 = "";
	
	/**
	 * To understand whether file on the disc was changed or not
	 */
	public long lastModified = 0;
	
	public static String IsChangedIcon = "/images/bullet_red.9x10.png";
	public static String IsReadOnlyIcon = "/images/bullet_black.9x10.png";

	public FileJTab(File file) {
		this.File = file;
	}

	/**
	 * Performs when text in textarea was changed, exactly key was released
	 *
	 * @param evt
	 */
	public void TextAreaDocumentKeyReleased(java.awt.event.KeyEvent evt) {
//		JTextArea jta = (JTextArea) evt.getSource();
		this.checkTextIsChanged();
	}
	
	/**
	 * Whether current document is changed or not
	 * @return 
	 *        true - document was changed, false - document was not changed
	 */
	public boolean checkTextIsChanged() {
		String text = this.TextAreaDocument.getText();
		int index = MainForm.getInstance().getTabbedPaneMain().getSelectedIndex();
		
		// If md5 or lastChanged not calculated yet, calculate and save it
		if (this.savedContentMD5.isEmpty() || 0 == this.lastModified) {
			try {
				String content = this.File.getContent();
				this.savedContentMD5 = Hash.md5(content);
				
				java.io.File iofile = new java.io.File(this.File.getPath().toAbsolutePath().toString());
				this.lastModified = iofile.lastModified();
			}
			catch (Exception e) {
				MainForm.showError(e.getMessage());
			}
		}

		// If enabled regime read-only
		if (this.checkReadOnly()) {
			
			return false;
		}
		// If content the same and was not changed
		else if (Hash.md5(text).equals(this.savedContentMD5)){
			// Remove icon 'changed' from title tab
			MainForm.getInstance().getTabbedPaneMain().setIconAt(index, null);
			return false;
		}
		
		// Set icon 'changed' to title tab
		MainForm.getInstance().getTabbedPaneMain().setIconAt(
			index, new javax.swing.ImageIcon(getClass().getResource(FileJTab.IsChangedIcon))
		);
		
		return true;
	}
	
	/**
	 * Hide or show JPanel with images carousel
	 * 
	 * @param value 
	 *			   - true or false
	 */
	public void setVisibilityForJPanelDocumentPictures(boolean value) {
		this.VisibilityForJPanelDocumentPictures = value;
		this.JPanelDocumentPictures.setVisible(this.VisibilityForJPanelDocumentPictures);
	}
	
	/**
	 * Checks after panel was created
	 */
	public void checksAfterCreating(){
		
		// Hide images carousel when File dont has any files
		if (0 == this.File.Images.size()) {
			this.setVisibilityForJPanelDocumentPictures(false);
		}
	}
	
	/**
	 * Clean and create panel with pictures.
	 * Can use for creating and refreshing.
	 */
	public void createJPanelDocumentPictures(){
		this.JPanelDocumentPictures.removeAll();
		ImageCarousel ImageCarousel = new ImageCarousel(this.File);

		this.JPanelDocumentPictures.setLayout(new java.awt.BorderLayout());
		this.JPanelDocumentPictures.add(ImageCarousel, java.awt.BorderLayout.NORTH);
	}
	
	/**
	 * OnFocus on the TextArea
	 * @param e 
	 */
	@Override
	public void focusGained(FocusEvent e) {
		boolean isChanged = this.checkTextIsChanged();
		
		// Have to check whether file on disk has changed
		java.io.File iofile = new java.io.File(this.File.getPath().toAbsolutePath().toString());
		if (this.lastModified == iofile.lastModified()) return;

		String text = this.File.getPath().toAbsolutePath().toString() 
			+ "\nThis file has been modified by another program. Do you want to reload it?";
		if (isChanged) text = text + "\nYou will lost all current changes.";

		int dialogResult = JOptionPane.showConfirmDialog(
				MainForm.getInstance(),
				text,
				"File was changed",
				JOptionPane.YES_NO_OPTION
		);

		String content = null;
		try{
			content = this.File.getContent();
			this.savedContentMD5 = Hash.md5(content);
			this.lastModified = iofile.lastModified();
		}
		catch(IOException ex){
			MainForm.showError(ex);
		}
		
		// Load new content to textArea
		if (dialogResult == JOptionPane.YES_OPTION) {
			this.TextAreaDocument.setText(content);
		}
		
		this.checkTextIsChanged();		
    }

	@Override
    public void focusLost(FocusEvent e) {
		
    }
	
	/**
	 * Enable-disable attribute ReadOnly
	 */
	public boolean checkReadOnly(){
		int count = MainForm.getInstance().getTabbedPaneMain().getTabCount();
		int index = 0;
		for (int i = 0; i < count; i++) {
			FileJTab tab = (FileJTab) MainForm.getInstance().getTabbedPaneMain().getComponent(i);
			if (!tab.File.equals(this.File)) {
				continue;
			}
			
			index = i;
			break;
		}
		
		// Is read-only
		if (this.File.getIsReadOnly()){
			// Set icon 'readonly' to title tab
			MainForm.getInstance().getTabbedPaneMain().setIconAt(
				index, new javax.swing.ImageIcon(getClass().getResource(FileJTab.IsReadOnlyIcon))
			);
			this.TextAreaDocument.setEditable(false);
			
			return true;
		}
		
		// Usual condition		
		MainForm.getInstance().getTabbedPaneMain().setIconAt(index, null);
		this.TextAreaDocument.setEditable(true);
		
		return false;
	}
	
	
	
}
