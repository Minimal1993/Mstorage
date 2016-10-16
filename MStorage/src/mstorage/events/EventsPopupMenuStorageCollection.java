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
import mstorage.models.*;
import mstorage.*;
import mstorage.menus.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import javax.swing.JMenuItem;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.Exception;
import java.lang.NoSuchMethodException;

/**
 * Events handlers for popup menu in storage collection tree
 */
public class EventsPopupMenuStorageCollection implements ActionListener {

	protected StorageItem StorageItem;

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		JMenuItem MenuItem = (JMenuItem) e.getSource();
		PopupMenuStorageCollection pmsc = (PopupMenuStorageCollection) MenuItem.getParent();
		this.StorageItem = pmsc.StorageItem;

		EventsStorageCollectionHandler esch = new EventsStorageCollectionHandler(this.StorageItem);
		esch.call(command);
		
		if (command.equals("delete_file")) {
			// TODO: There must be refreshing of tree. But now it dont work correctly
            // TODO: This place not work and is stopping delete
			((javax.swing.tree.DefaultTreeModel)MainForm.getInstance().getTree().getModel()).nodeChanged(
				(javax.swing.tree.TreeNode)this.StorageItem.getFather()
			);
		}
		
	}

	public void itemStateChanged(ItemEvent e) {
		
	}


	
	
}
