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
package mstorage.menus;

import StorageCollection.*;
import mstorage.events.EventsPopupMenuTabbedPaneMain;

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
import javax.swing.tree.TreePath;
import mstorage.components.FileJTab;
import mstorage.events.EventsStorageCollectionHandler;

/**
 * Create a context menu for tabs titles, call by right mouse button click
 */
public class PopupMenuTabbedPaneMain extends JPopupMenu {

	public StorageItem StorageItem;
	EventsPopupMenuTabbedPaneMain EventsHandler = new EventsPopupMenuTabbedPaneMain();

	public PopupMenuTabbedPaneMain(StorageItem item) {
		super();

		this.StorageItem = item;

		this.initMenu();
	}

	public void show(JTree jtree, int x, int y) {
		super.show(jtree, x, y);
	}

	private void initMenu() {
		JMenuItem m1 = new JMenuItem("Close this tab");
		m1.addActionListener(EventsHandler);
		m1.setActionCommand("close_this_tab");
		m1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tab_delete.16x16.png")));
		this.add(m1);

		JMenuItem m2 = new JMenuItem("Close all other tabs");
		m2.addActionListener(EventsHandler);
		m2.setActionCommand("close_other_tabs");
		m2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tab_go.16x16.png")));
		this.add(m2);

		this.addSeparator();

		JMenuItem m22 = new JMenuItem("Save this file");
		m22.addActionListener(EventsHandler);
		m22.setActionCommand("save_file");
		m22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/disk.16x16.png")));
		this.add(m22);

		JMenuItem m23 = new JMenuItem("Save this file as...");
		m23.addActionListener(EventsHandler);
		m23.setActionCommand("save_file_as");
		m23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save_as.16x16.png")));
		m23.setEnabled(true);
		this.add(m23);

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

		JMenuItem m7 = new JMenuItem("Delete a file");
		m7.addActionListener(EventsHandler);
		m7.setActionCommand("delete_file");
		m7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/page_white_delete.16x16.png")));
		this.add(m7);

		this.addSeparator();

		JMenuItem m8 = new JMenuItem("View file in directory");
		m8.addActionListener(EventsHandler);
		m8.setActionCommand("view_file_in_directory");
		m8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/folder_go.16x16.png")));
		m8.setEnabled(false);
		this.add(m8);

	}

	public static MouseListener initMouseListener() {
		MouseListener ml = new MouseAdapter() {
			private void myPopupEvent(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				javax.swing.JTabbedPane tabbedPane = (javax.swing.JTabbedPane) e.getSource();

				final int index = tabbedPane.getUI().tabForCoordinate(tabbedPane, e.getX(), e.getY());
				int selectedIndex = tabbedPane.getSelectedIndex();

				// Show popup only of click was on tab title and on current selected tabs
				if (index != -1 && selectedIndex == index) {
					FileJTab tab = (FileJTab) tabbedPane.getComponent(index);

					PopupMenuTabbedPaneMain popup = new PopupMenuTabbedPaneMain(tab.File);
					popup.show(tabbedPane, x, y);

				}
			}

			public void mousePressed(MouseEvent e) {
				javax.swing.JTabbedPane tabbedPane = (javax.swing.JTabbedPane) e.getSource();

				final int index = tabbedPane.getUI().tabForCoordinate(tabbedPane, e.getX(), e.getY());
				int selectedIndex = tabbedPane.getSelectedIndex();

				// Work if click was on tab title and on current selected tabs
				if (index != -1 && selectedIndex == index) {
					FileJTab tab = (FileJTab) tabbedPane.getComponent(index);

					if (2 == e.getClickCount()) {
						EventsStorageCollectionHandler esch = new EventsStorageCollectionHandler(tab.File);
						esch.call("rename_file");
					}

				}
			}

			public void mouseReleased(MouseEvent e) {
				if (3 == e.getButton()) {
					myPopupEvent(e);
				}
			}
		};

		return ml;
	}

}
