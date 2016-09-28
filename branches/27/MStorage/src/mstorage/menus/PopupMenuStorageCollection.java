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
package mstorage.menus;

import StorageCollection.*;
import mstorage.events.EventsPopupMenuStorageCollection;

import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

/**
 * Create a context menu form storage collection items, call by right mouse
 * button click
 */
public class PopupMenuStorageCollection extends JPopupMenu  {

	public StorageItem StorageItem;
	EventsPopupMenuStorageCollection EventsHandler = new EventsPopupMenuStorageCollection();

	public PopupMenuStorageCollection(StorageItem item) {
		super();

		this.StorageItem = item;

		if ("file".equals(item.getType())) {
			this.menuForFile();
		} else if ("folder".equals(item.getType())) {
			this.menuForFolder();
		}
	}

	/**
	 * Generate menu items for type Folder
	 */
	protected void menuForFolder() {
		JMenuItem m1 = new JMenuItem("Create a new folder");
		m1.addActionListener(EventsHandler);
		m1.setActionCommand("create_folder");
		m1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/folder_open-add2.16x16.png")));
		this.add(m1); 

		JMenuItem m2 = new JMenuItem("Create a new file");
		m2.addActionListener(EventsHandler);
		m2.setActionCommand("create_file");
		m2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/page_white_add.16x16.png")));
		this.add(m2);

		JMenuItem m3 = new JMenuItem("Delete this folder");
		m3.addActionListener(EventsHandler);
		m3.setActionCommand("delete_folder");
		m3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/folder_delete.16x16.png")));
		this.add(m3);

		JMenuItem m4 = new JMenuItem("Move this folder");
		m4.addActionListener(EventsHandler);
		m4.setActionCommand("move_folder");
		m4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/document_move.16x16.png")));
		this.add(m4);
		
		JMenuItem m5 = new JMenuItem("Rename this folder");
		m5.addActionListener(EventsHandler); 
		m5.setActionCommand("rename_folder");
		m5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/folder_vertical_rename.16x16.png")));
		this.add(m5);
		
		JMenuItem m6 = new JMenuItem("Search in this folder");
		m6.addActionListener(EventsHandler);
		m6.setActionCommand("search_in_folder");
		m6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/magnifier.16x16.png")));
		m6.setEnabled(false);
		this.add(m6);
		
	}

	/**
	 * Generate menu items for type File
	 */
	protected void menuForFile() {
		JMenuItem m1 = new JMenuItem("Open a file");
		m1.addActionListener(EventsHandler);
		m1.setActionCommand("open_file");
		m1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/page_white.16x16.png")));
		this.add(m1); 

		JMenuItem m2 = new JMenuItem("Delete a file");
		m2.addActionListener(EventsHandler);
		m2.setActionCommand("delete_file");
		m2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/page_white_delete.16x16.png")));
		this.add(m2);

		JMenuItem m3 = new JMenuItem("Move this file");
		m3.addActionListener(EventsHandler);
		m3.setActionCommand("move_file");
		m3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/document_move.16x16.png")));
		this.add(m3);

		JMenuItem m4 = new JMenuItem("Add picture to this file");
		m4.addActionListener(EventsHandler);
		m4.setActionCommand("add_image");
		m4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/picture_add.16x16.png")));
		this.add(m4);
		
		JMenuItem m5 = new JMenuItem("Rename this file");
		m5.addActionListener(EventsHandler); 
		m5.setActionCommand("rename_file");
		m5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/textfield.16x16.png")));
		this.add(m5);
		
		JMenuItem m6 = new JMenuItem("Search in this file");
		m6.addActionListener(EventsHandler);
		m6.setActionCommand("search_in_file");
		m6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/page_white_magnify.16x16.png")));
		m6.setEnabled(false);
		this.add(m6);
	}

	public void show(JTree jtree, int x, int y) {
		super.show(jtree, x, y);
	}


}
