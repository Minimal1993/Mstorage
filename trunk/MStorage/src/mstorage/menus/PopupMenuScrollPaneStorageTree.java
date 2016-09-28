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
import mstorage.events.EventsPopupMenuScrollPaneStorageTree;

import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import mstorage.MainForm;
import mstorage.components.StorageCollectionJTree;

/**
 * Create a popup menu in scrollPaneStorageTree. This give a approach for create
 * a new folders and files in root directory in storage tree
 */
public class PopupMenuScrollPaneStorageTree extends JPopupMenu {

	public StorageItem StorageItem;
	EventsPopupMenuScrollPaneStorageTree EventsHandler = new EventsPopupMenuScrollPaneStorageTree();

	/**
	 *
	 * @param item - root Folder
	 */
	public PopupMenuScrollPaneStorageTree(StorageItem item) {
		super();

		this.StorageItem = item;

		this.initPopup();
	}

	public void show(JTree jtree, int x, int y) {
		super.show(jtree, x, y);
	}

	private void initPopup() {
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

	}
	
	
}
