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
package mstorage.dialogs;

import javax.swing.UIManager;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.BorderFactory;

import java.net.URL;
import java.io.IOException;

import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.TreeMap;
import javax.swing.tree.DefaultTreeCellRenderer;
import mstorage.classes.Enums;
import mstorage.classes.Settings;
import mstorage.utils.Log;


/**
 * How to use dialog
 *
 * @author ilya.gulevskiy
 */
public class HowToUseDialog extends javax.swing.JDialog implements TreeSelectionListener {

	private JTree tree;

	private URL helpURL;

	/**
	 * Creates new form HowToUseDialog
	 */
	public HowToUseDialog() {
//		super(parent, modal);
		initComponents();

		DefaultMutableTreeNode top = new DefaultMutableTreeNode("How to use");
		this.createNodes(top);
		this.tree = new JTree(top);

		this.tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.tree.setRootVisible(false);
		this.tree.setShowsRootHandles(false);
		this.tree.setToggleClickCount(1);
		this.tree.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		this.tree.addTreeSelectionListener(this);
		
		
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();

		// Set icons for tree from settings preferencess
		TreeMap<String, String> icons = Enums.getIcons().get( Settings.getInstance().getProperty("Icons") );
		
		renderer.setLeafIcon(new javax.swing.ImageIcon(getClass().getResource("/images/" + icons.get("tree_leaf"))));
		renderer.setClosedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/" + icons.get("tree_closed"))));
		renderer.setOpenIcon(new javax.swing.ImageIcon(getClass().getResource("/images/" + icons.get("tree_open"))));

		this.tree.setCellRenderer(renderer);
		

		this.jScrollPaneLeft.setViewportView(tree);

		this.setIcon();
		
		this.initHelp();
	}

	public void valueChanged(TreeSelectionEvent e) {
		//Returns the last path element of the selection.
		//This method is useful only when the selection model allows a single selection.
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.tree.getLastSelectedPathComponent();

		if (node == null) {
			return;
		}

		Object nodeInfo = node.getUserObject();
		if (node.isLeaf()) {
			BookInfo book = (BookInfo) nodeInfo;
			this.displayURL(book.bookURL);
		} else {
			this.displayURL(helpURL);
		}
	}

	private void initHelp() {
		String s = "/html/intro.html";
		helpURL = getClass().getResource(s);
		if (helpURL == null) {
			Log.info("Couldn't open help file: " + s);
		}

		this.displayURL(helpURL);
	}

	private void displayURL(URL url) {
		try {
			if (url != null) {
				this.jEditorPane2HTML.setPage(url);
			} else { //null url
				this.jEditorPane2HTML.setText("File Not Found");
			}
		} catch (IOException e) {
			Log.info("Attempted to read a bad URL: " + url);
		}
	}

	private class BookInfo {
		public String bookName;
		public URL bookURL;

		public BookInfo(String book, String filename) {
			bookName = book;
			bookURL = getClass().getResource("/html/" + filename);
			if (bookURL == null) {
				Log.info("Couldn't find file: " + filename);
			}
		}

		public String toString() {
			return bookName;
		}
	}

	private void createNodes(DefaultMutableTreeNode top) {
		DefaultMutableTreeNode category = null;
		DefaultMutableTreeNode book = null;

		book = new DefaultMutableTreeNode(new BookInfo("Overview", "intro.html"));
		top.add(book);
		
		book = new DefaultMutableTreeNode(new BookInfo("Getting start", "getting_start.html"));
		top.add(book);
		
		book = new DefaultMutableTreeNode(new BookInfo("Encryption files", "encryption.html"));
		top.add(book);

		book = new DefaultMutableTreeNode(new BookInfo("Search, readonly and filter", "search.html"));
		top.add(book);

		book = new DefaultMutableTreeNode(new BookInfo("Encoding problem", "encoding.html"));
		top.add(book);        
        
		book = new DefaultMutableTreeNode(new BookInfo("Update control", "update.html"));
		top.add(book);
		
		book = new DefaultMutableTreeNode(new BookInfo("Additional features", "additional_features.html"));
		top.add(book);
		
		book = new DefaultMutableTreeNode(new BookInfo("Run many application", "many_app.html"));
		top.add(book);
        
		book = new DefaultMutableTreeNode(new BookInfo("Keys shortcuts", "shortcuts.html"));
		top.add(book);
        
		book = new DefaultMutableTreeNode(new BookInfo("Thanks to", "thanksto.html"));
		top.add(book);
        
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelTop = new javax.swing.JPanel();
        jScrollPaneLeft = new javax.swing.JScrollPane();
        jScrollPaneRight = new javax.swing.JScrollPane();
        jEditorPane2HTML = new javax.swing.JEditorPane();
        jPanelBottom = new javax.swing.JPanel();
        jButtonClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("How to use");
        setName("HowToUseDialog"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1018, 800));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                doClose(evt);
            }
        });

        jScrollPaneLeft.setViewportBorder(javax.swing.BorderFactory.createEtchedBorder());

        jScrollPaneRight.setViewportBorder(javax.swing.BorderFactory.createEtchedBorder());

        jEditorPane2HTML.setEditable(false);
        jScrollPaneRight.setViewportView(jEditorPane2HTML);

        javax.swing.GroupLayout jPanelTopLayout = new javax.swing.GroupLayout(jPanelTop);
        jPanelTop.setLayout(jPanelTopLayout);
        jPanelTopLayout.setHorizontalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopLayout.createSequentialGroup()
                .addComponent(jScrollPaneLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneRight, javax.swing.GroupLayout.DEFAULT_SIZE, 855, Short.MAX_VALUE))
        );
        jPanelTopLayout.setVerticalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneLeft)
            .addComponent(jScrollPaneRight, javax.swing.GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
        );

        jButtonClose.setText("Close");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelBottomLayout = new javax.swing.GroupLayout(jPanelBottom);
        jPanelBottom.setLayout(jPanelBottomLayout);
        jPanelBottomLayout.setHorizontalGroup(
            jPanelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBottomLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonClose)
                .addContainerGap())
        );
        jPanelBottomLayout.setVerticalGroup(
            jPanelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBottomLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonClose)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
		this.doClose();
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void doClose(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_doClose
		this.doClose();
    }//GEN-LAST:event_doClose

	private void doClose() {
		this.setVisible(false);
		this.dispose();
	}
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(HowToUseDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(HowToUseDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(HowToUseDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(HowToUseDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				HowToUseDialog dialog = new HowToUseDialog();
				dialog.addWindowListener(new java.awt.event.WindowAdapter() {
					@Override
					public void windowClosing(java.awt.event.WindowEvent e) {
						System.exit(0);
					}
				});
				dialog.setVisible(true);
			}
		});
	}

	private void setIcon() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/help.32x32.png")));
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JEditorPane jEditorPane2HTML;
    private javax.swing.JPanel jPanelBottom;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JScrollPane jScrollPaneLeft;
    private javax.swing.JScrollPane jScrollPaneRight;
    // End of variables declaration//GEN-END:variables
}
