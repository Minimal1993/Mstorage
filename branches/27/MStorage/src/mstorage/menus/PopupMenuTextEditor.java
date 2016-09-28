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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JMenuItem;
import javax.swing.JTree;
import javax.swing.JPopupMenu;
import javax.swing.text.DefaultEditorKit.*;
import java.awt.event.KeyEvent;

import StorageCollection.*;
import javax.swing.text.DefaultEditorKit;
import mstorage.MainForm;
import mstorage.components.FileJTab;
import mstorage.events.EventsTextEditor;

/**
 * Context menu for text editor
 */
public class PopupMenuTextEditor extends JPopupMenu {

	public StorageItem StorageItem;
	public EventsTextEditor EventsHandler = new EventsTextEditor();
	protected javax.swing.JTextArea TextAreaDocument;

	public PopupMenuTextEditor(StorageItem item) {
		super();

		this.StorageItem = item;
		
		FileJTab tab = (FileJTab) MainForm.getInstance().getTabbedPaneMain().getSelectedComponent();
		this.TextAreaDocument = tab.TextAreaDocument;

		this.initMenu();
	}

	public void show(JTree jtree, int x, int y) {
		super.show(jtree, x, y);
	}

	private void initMenu() {		
		JMenuItem m1 = new JMenuItem("Cut");
		m1.addActionListener(EventsHandler);
		m1.addActionListener(new DefaultEditorKit.CutAction());
		m1.setActionCommand("cut");
		m1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
		m1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cut_red.16x16.png")));
		this.add(m1);

		JMenuItem m2 = new JMenuItem("Copy");
		m2.addActionListener(new DefaultEditorKit.CopyAction());
		m2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
		m2.setActionCommand("copy");
		m2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clipboard_sign_out.16x16.png")));
		this.add(m2);
		
		JMenuItem m3 = new JMenuItem("Paste");
		m3.addActionListener(EventsHandler);
		m3.addActionListener(new DefaultEditorKit.PasteAction());
		m3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
		m3.setActionCommand("paste");
		m3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/paste_plain.16x16.png")));
		this.add(m3);
		
		JMenuItem m4 = new JMenuItem("Delete");
		m4.addActionListener(EventsHandler);
		m4.setActionCommand("delete");
		m4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
		m4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/font_delete.16x16.png")));
		this.add(m4);
		
		JMenuItem m5 = new JMenuItem("Select all");
		m5.addActionListener(EventsHandler);
		m5.setActionCommand("select_all");
		m5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/layer_select.16x16.png")));
		this.add(m5);

		this.addSeparator();

		JMenuItem m6 = new JMenuItem("UPPERCASE");
		m6.addActionListener(EventsHandler);
		m6.setActionCommand("uppercase");
		m6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/text_uppercase.16x16.png")));
		this.add(m6);
		
		JMenuItem m7 = new JMenuItem("lowercase");
		m7.addActionListener(EventsHandler);
		m7.setActionCommand("lowercase");
		m7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/text_lowercase.16x16.png")));
		this.add(m7);
		
		// hide-show items when there is not exists selected text
		if(null == this.TextAreaDocument.getSelectedText()) {
			m1.setEnabled(false);
			m2.setEnabled(false);
			m4.setEnabled(false);
			m6.setEnabled(false);
			m7.setEnabled(false);
		}
		
	}

	public static MouseListener initMouseListener() {
		MouseListener ml = new MouseAdapter() {
			private void myPopupEvent(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				FileJTab tab = (FileJTab) MainForm.getInstance().getTabbedPaneMain().getSelectedComponent();

				PopupMenuTextEditor popup = new PopupMenuTextEditor(tab.File);
				popup.show((javax.swing.JTextArea)e.getSource(), x, y);
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
