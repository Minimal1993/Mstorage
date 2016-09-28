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
import mstorage.events.EventsStorageCollectionHandler;

import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Create a context menu for ImageItem
 */
public class PopupMenuImageItem extends JPopupMenu implements ActionListener {

	public Image Image;

	public PopupMenuImageItem(Image item) {
		super();

		this.Image = item;

		this.initMenu();
	}

	private void initMenu() {
		JMenuItem m1 = new JMenuItem("Delete image");
		m1.addActionListener(this);
		m1.setActionCommand("delete_image");
		m1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/photo_delete.16x16.png")));
		this.add(m1);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		EventsStorageCollectionHandler esch = new EventsStorageCollectionHandler(this.Image);
		esch.call(command);
	}

}
