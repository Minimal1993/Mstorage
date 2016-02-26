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
import mstorage.MainForm;
import mstorage.classes.*;
import mstorage.models.StorageCollectionJTree;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.event.*;
import java.util.TreeMap;


/**
 *
 */
public class MoveJTree extends StorageCollectionJTree {

	public MoveJTree(Folder graphNode){
		super(graphNode, false);
		
//		this.setModel(treeModel);
	}
	
	@Override
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


					return;
				}

				tree.setSelectionPath(path);

				// Create a popup menu for item
				StorageItem obj = (StorageItem) path.getLastPathComponent();

				int r = 9;
				
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
