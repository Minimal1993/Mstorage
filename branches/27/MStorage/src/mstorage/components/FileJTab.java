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

import StorageCollection.File;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTextArea;
import mstorage.MainForm;
import mstorage.utils.Hash;

/**
 * This is wrapper for JPanel component with data about current File,
 * representing in panel
 */
public class FileJTab extends javax.swing.JPanel {

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
	
	public static String IsChangedIcon = "/images/bullet_red.9x10.png";

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
		
		// If md5 not calculated yet, calculate and save it
		if (this.savedContentMD5.isEmpty()) {
			try {
				String content = this.File.getContent();
				this.savedContentMD5 = Hash.md5(content);
			}
			catch (Exception e) {
				MainForm.showError(e.getMessage());
			}
		}

		if (Hash.md5(text).equals(this.savedContentMD5)){
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
}
