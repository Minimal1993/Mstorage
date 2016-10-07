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
package mstorage.events;

import mstorage.storagecollection.StorageItem;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import java.awt.event.KeyEvent;

import mstorage.MainForm;
import mstorage.components.FileJTab;
import mstorage.utils.Hash;

/**
 * Event handlers for popup menu in text editor. Include standard copy-past-cut
 * functions.
 */
public class EventsTextEditorHandler extends MStorageEventsHandler {
	
	/**
	 * TextArea object by current selected tab. 
	 * All actions will happened there.
	 */
	protected javax.swing.JTextArea TextAreaDocument;
	
	protected FileJTab FileJTab;

	public EventsTextEditorHandler(StorageItem StorageItem) {
		super(StorageItem);
		
		this.FileJTab = (FileJTab) MainForm.getInstance().getTabbedPaneMain().getSelectedComponent();
		this.TextAreaDocument = this.FileJTab.TextAreaDocument;
	}
	
	@Override
	public void doAfterCall(){
		this.FileJTab.checkTextIsChanged();
	}
	
	public void eh_cut() {
		
	}
	
	public void eh_paste() {
		
	}

	public void eh_delete() {
		this.TextAreaDocument.setText(this.TextAreaDocument.getText().replace(this.TextAreaDocument.getSelectedText(),""));
	}

	public void eh_select_all() {
		this.TextAreaDocument.setSelectionStart(0);
		this.TextAreaDocument.setSelectionEnd(this.TextAreaDocument.getText().length());
	}

	public void eh_uppercase() {
		this.TextAreaDocument.setText(
			this.TextAreaDocument.getText().replace(
				this.TextAreaDocument.getSelectedText(),
				StringUtils.upperCase(this.TextAreaDocument.getSelectedText())
			)
		);
	}

	public void eh_lowercase() {
		this.TextAreaDocument.setText(
			this.TextAreaDocument.getText().replace(
				this.TextAreaDocument.getSelectedText(),
				StringUtils.lowerCase(this.TextAreaDocument.getSelectedText())
			)
		);
	}

}
