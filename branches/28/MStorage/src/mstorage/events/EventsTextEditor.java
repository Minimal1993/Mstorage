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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import javax.swing.JMenuItem;
import mstorage.menus.PopupMenuTextEditor;
import mstorage.storagecollection.File;

/**
 *
 */
public class EventsTextEditor implements ActionListener {
	protected StorageItem StorageItem;

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		JMenuItem MenuItem = (JMenuItem) e.getSource();
		PopupMenuTextEditor pmsc = (PopupMenuTextEditor) MenuItem.getParent();
		this.StorageItem = pmsc.StorageItem;

		// Forbide all operations with text editor when read-only flag is set
		File file = (File) this.StorageItem;
		if (file.getIsReadOnly()) return;
		
		EventsTextEditorHandler esch = new EventsTextEditorHandler(this.StorageItem);
		esch.call(command);
	}

	public void itemStateChanged(ItemEvent e) {
		
	}
}
