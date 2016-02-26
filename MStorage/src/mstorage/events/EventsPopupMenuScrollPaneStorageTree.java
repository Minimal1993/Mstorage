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
public class EventsPopupMenuScrollPaneStorageTree implements ActionListener {

	protected StorageItem StorageItem;

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		JMenuItem MenuItem = (JMenuItem) e.getSource();
		PopupMenuScrollPaneStorageTree pmsc = (PopupMenuScrollPaneStorageTree) MenuItem.getParent();
		this.StorageItem = pmsc.StorageItem;

		EventsStorageCollectionHandler esch = new EventsStorageCollectionHandler(this.StorageItem);
		esch.call(command);
	}

	public void itemStateChanged(ItemEvent e) {
		
	}


	
	
}
