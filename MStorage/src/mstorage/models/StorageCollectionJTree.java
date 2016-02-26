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
package mstorage.models;

import StorageCollection.*;
import mstorage.menus.PopupMenuStorageCollection;
import mstorage.menus.PopupMenuScrollPaneStorageTree;
import mstorage.events.EventsStorageCollectionHandler;
import mstorage.classes.*;
import mstorage.MainForm;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.event.*;
import java.util.TreeMap;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;


/**
 * TODO: this is not model but component. Move to mstorage.components folder
 */
public class StorageCollectionJTree extends JTree {

	public StorageCollectionJTree(Folder graphNode, boolean showFiles) {
		super(new StorageCollectionModel(graphNode, showFiles));
		getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();

		// Set icons for tree from settings preferencess
		TreeMap<String, String> icons = Enums.getIcons().get( Settings.getInstance().getProperty("Icons") );
		
		renderer.setLeafIcon(new javax.swing.ImageIcon(getClass().getResource("/images/" + icons.get("tree_leaf"))));
		renderer.setClosedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/" + icons.get("tree_closed"))));
		renderer.setOpenIcon(new javax.swing.ImageIcon(getClass().getResource("/images/" + icons.get("tree_open"))));

		setCellRenderer(renderer);

		// Mouse listener
		this.addMouseListener(this.getMouseListener());
	}

	public StorageCollectionModel getTreeModel() {
		return (StorageCollectionModel) this.treeModel;
	}
	
	protected MouseListener getMouseListener() {
		return new MouseAdapter() {
			private void myPopupEvent(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				StorageCollectionJTree tree = (StorageCollectionJTree) e.getSource();
				TreePath path = tree.getPathForLocation(x, y);

				// If path is null, it mean click was not in tree item.
				// Show menu for root folder
				if (path == null) {
					Folder root = (Folder) tree.getTreeModel().getFolder();

					PopupMenuScrollPaneStorageTree popup = new PopupMenuScrollPaneStorageTree(root);
					popup.show(tree, x, y);

					return;
				}

				tree.setSelectionPath(path);

				// Create a popup menu for item
				StorageItem obj = (StorageItem) path.getLastPathComponent();
				PopupMenuStorageCollection popup = new PopupMenuStorageCollection(obj);
				popup.show(tree, x, y);
			}

			public void mousePressed(MouseEvent e) {
				int selRow = getRowForLocation(e.getX(), e.getY());
				TreePath path = getPathForLocation(e.getX(), e.getY());

				if (selRow != -1 && path != null) {
					if (e.getClickCount() == 1) {
						System.out.println("Single click");

					} else if (e.getClickCount() == 2) {
						System.out.println("Double click");

						StorageItem obj = (StorageItem) path.getLastPathComponent();

						// Double click on file will open it
						EventsStorageCollectionHandler esch = new EventsStorageCollectionHandler(obj);
						esch.call("open_file");
					}
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					myPopupEvent(e);
				}
			}
		};
	}
	
	
}
