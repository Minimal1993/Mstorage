/*
 * MStorage - storage for notes.
 * 
 * Permission is granted to copy, distribute and/or
 * modify  this  document under  the  terms  of the
 * GNU General Public License
 * 
 * @author: ilya.gulevskiy
 * @email: naveter@gmail.com
 * @date: 2016
 */
package mstorage.events;

import StorageCollection.*;
import mstorage.*;
import mstorage.models.TabsFabric;
import mstorage.components.FileJTab;
import mstorage.utils.Hash;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.Exception;
import java.lang.NoSuchMethodException;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.tree.TreePath;
import mstorage.models.MoveJTree;

/**
 * General handler for actions to events about storage collections: File, Folder
 * and Image.
 *
 */
public class EventsStorageCollectionHandler extends MStorageEventsHandler {

	public EventsStorageCollectionHandler(StorageItem StorageItem) {
		super(StorageItem);
	}

	public void eh_create_folder() {
		if (!this.StorageItem.getType().equals("folder")) {
			return;
		}

		Folder father = (Folder) this.StorageItem;
		try {
			// Create new file
			Folder folder = Folder.create(father, Folder.getNewName(father));
			this.StorageItem = folder;

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void eh_create_file() {
		if (!this.StorageItem.getType().equals("folder")) {
			return;
		}

		Folder folder = (Folder) this.StorageItem;
		try {
			// Create new file
			File file = File.create(folder, File.getNewName(folder));
			this.StorageItem = file;

			// Open new file
			this.eh_open_file();

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	public void eh_delete_folder() {
		if (!this.StorageItem.getType().equals("folder")) {
			return;
		}

		Folder folder = (Folder) this.StorageItem;

		folder.remove();

		// Check in opened tabs whether files exists yet
		MainForm.getInstance().checkOpenedTabsFileExists();
	}

	public void eh_move_folder() {
		Folder file = (Folder) this.StorageItem;
		
		MoveDialog sd = new MoveDialog(MainForm.getInstance(), true);
		sd.pack();
		sd.setLocationRelativeTo(MainForm.getInstance());
		sd.setVisible(true);
		
		// there is place when MoveDialog is living
		
		Folder folderTo = sd.getFolderTo();
		sd.dispose();
		
		if (null == folderTo) {
			return;
		}

		try {
			file.move(folderTo);
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void eh_rename_folder() {
		Folder folder = (Folder) this.StorageItem;

		String s = (String) JOptionPane.showInputDialog(
				MainForm.getInstance(),
				"Enter new folder name:",
				"Rename folder",
				JOptionPane.PLAIN_MESSAGE,
				null,
				null,
				folder.getFileName());

		if ((s != null) && (s.length() > 0)) {
			try {
				folder.rename(s);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			return;
		}
	}

	public void eh_search_in_folder() {
		System.out.println(this.StorageItem.getFileName());
	}

	public void eh_open_file() {
		File file = (File) this.StorageItem;

		// Checking whether this file have opened already or not
		int count = MainForm.getInstance().getTabbedPaneMain().getTabCount();
		for (int i = 0; i < count; i++) {
			FileJTab tab = (FileJTab) MainForm.getInstance().getTabbedPaneMain().getComponent(i);
			if (tab.File.equals(file)) {
				// Select if has opened already
				MainForm.getInstance().getTabbedPaneMain().setSelectedIndex(i);
				return;
			}
		}

		FileJTab newtab = TabsFabric.getTab(file);
		MainForm.getInstance().getTabbedPaneMain().addTab(newtab.File.getFileName(), newtab);

		// Set focus
		count = MainForm.getInstance().getTabbedPaneMain().getTabCount();
		MainForm.getInstance().getTabbedPaneMain().setSelectedIndex(count - 1);

	}

	public void eh_delete_file() {
		if (!this.StorageItem.getType().equals("file")) {
			return;
		}

		File file = (File) this.StorageItem;

		// Check if File is opened in tab
		MainForm.getInstance().checkOpenedFileWhenFileDelete(file);

		// TODO: Is there is mistake somtime occured: deleted file continue live in root Folder object. Investigate it.
		file.remove();
	}

	public void eh_move_file() {
		File file = (File) this.StorageItem;
		
		MoveDialog sd = new MoveDialog(MainForm.getInstance(), true);
		sd.pack();
		sd.setLocationRelativeTo(MainForm.getInstance());
		sd.setVisible(true);
		
		// there is place when MoveDialog is living
		
		Folder folderTo = sd.getFolderTo();
		sd.dispose();
		
		if (null == folderTo) {
			return;
		}

		try {
			file.move(folderTo);
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void eh_add_image() {
		System.out.println(this.StorageItem.getFileName());
	}

	public void eh_save_file() {
		// TODO: Not beautiful decision - next couple lines is performed too in MainForm handlers
		FileJTab tab = (FileJTab) MainForm.getInstance().getTabbedPaneMain().getSelectedComponent();
		int index = MainForm.getInstance().getTabbedPaneMain().indexOfComponent(tab);
		String content = tab.TextAreaDocument.getText();
		tab.savedContentMD5 = Hash.md5(content);
		File file = (File) this.StorageItem;

		try {
			file.save(content);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		// Remove icon 'changed' from title tab
		MainForm.getInstance().getTabbedPaneMain().setIconAt(index, null);
	}

	public void eh_rename_file() {
		File file = (File) this.StorageItem;

		String s = (String) JOptionPane.showInputDialog(
				MainForm.getInstance(),
				"Enter new file name:",
				"Rename file",
				JOptionPane.PLAIN_MESSAGE,
				null,
				null,
				file.getFileName());

		if ((s != null) && (s.length() > 0)) {
			try {
				file.rename(s);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			// Change tab title if file is opened
			int count = MainForm.getInstance().getTabbedPaneMain().getTabCount();
			for (int i = 0; i < count; i++) {
				FileJTab tab = (FileJTab) MainForm.getInstance().getTabbedPaneMain().getComponent(i);
				if (!tab.File.equals(file)) {
					continue;
				}

				MainForm.getInstance().getTabbedPaneMain().setTitleAt(i, file.getFileName());
			}
		}
	}

	public void eh_search_in_file() {
		System.out.println(this.StorageItem.getFileName());
	}

	public void eh_close_this_tab() {
		if (!this.StorageItem.getType().equals("file")) {
			return;
		}

		File file = (File) this.StorageItem;
		FileJTab tab = (FileJTab) MainForm.getInstance().getTabbedPaneMain().getSelectedComponent();
		MainForm.getInstance().getJMenuCloseActiveTab().setSelected(false);

		// Whether document was changed or not
		if (tab.checkTextIsChanged()) {
			int dialogResult = JOptionPane.showConfirmDialog(
					MainForm.getInstance(), "Save document before closing?", "Warning", JOptionPane.YES_NO_OPTION
			);

			if (dialogResult == JOptionPane.YES_OPTION) {
				this.call("save_file");
			}
		}

		// Check if File is opened in tab
		MainForm.getInstance().checkOpenedFileWhenFileDelete(file);
	}

	public void eh_close_other_tabs() {
		if (!this.StorageItem.getType().equals("file")) {
			return;
		}

		File file = (File) this.StorageItem;
		
		int count = MainForm.getInstance().getTabbedPaneMain().getTabCount();
		for (int i = 0; i < count; i++) {
			FileJTab tab = (FileJTab) MainForm.getInstance().getTabbedPaneMain().getComponent(i);
			
			// Dont close current document
			if (tab.File.equals(file)) {
				continue;
			}

			EventsStorageCollectionHandler esch = new EventsStorageCollectionHandler(tab.File);
			esch.call("close_this_tab");
		}
	}

	public void eh_save_file_as() {
		final javax.swing.JFileChooser fc = new javax.swing.JFileChooser();

		//In response to a button click:
		int returnVal = fc.showDialog(MainForm.getInstance(), "Save as...");

		if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
			java.io.File file = fc.getSelectedFile();
			if (file.exists()) {
				return;
			}

			this.eh_save_file();

			Path path = Paths.get(file.getAbsolutePath());

			try {
				((File) this.StorageItem).copy(path);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

		}

	}

	public void eh_view_file_in_directory() {
		System.out.println(this.StorageItem.getFileName());
	}

}