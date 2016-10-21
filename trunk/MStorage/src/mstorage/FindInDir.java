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
package mstorage;

import java.awt.Color;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileView;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import mstorage.classes.Settings;
import mstorage.components.FileJTab;
import mstorage.dialogs.BrowseFindInDirDialog;
import mstorage.events.EventsStorageCollectionHandler;
import mstorage.findreplace.FindInput;
import mstorage.findreplace.FindReplace;
import mstorage.findreplace.FindResult;
import mstorage.findreplace.FindResultItem;
import mstorage.storagecollection.File;
import mstorage.storagecollection.Folder;

/**
 * Window for search in directory. Singleton.
 */
public class FindInDir extends javax.swing.JFrame 
        implements TreeSelectionListener {
	
	protected static FindInDir FindInDir = null;
	protected Folder Folder = null;
    public JTree jTreeResults = null;
	
	// Singleton
	public static FindInDir getInstance(){
		if (null == FindInDir.FindInDir) {
			FindInDir.FindInDir = new FindInDir();
		}

		return FindInDir.FindInDir;
	}

	/**
	 * Creates new form FindInDir
	 */
	private FindInDir() {
		this.initComponents();
		
		this.initMain();
	}
	
	public void init(Folder folder){
		this.Folder = folder;
		this.jTextFieldFolder.setText(this.Folder.getPath().toAbsolutePath().toString());		
	}
	
	public void setSelectionAndFocus(){
		// Ask focus and select all text
		this.jTextFieldText.requestFocus();
		this.jTextFieldText.select(0,this.jTextFieldText.getText().length());
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelSettings = new javax.swing.JPanel();
        jLabelFolder = new javax.swing.JLabel();
        jLabelPattern = new javax.swing.JLabel();
        jTextFieldFolder = new javax.swing.JTextField();
        jTextFieldText = new javax.swing.JTextField();
        jButtonBrowseFolder = new javax.swing.JButton();
        jCheckBoxMatchCase = new javax.swing.JCheckBox();
        jButtonSearchInDir = new javax.swing.JButton();
        jButtonClose = new javax.swing.JButton();
        jButtonClear = new javax.swing.JButton();
        jScrollPaneResults = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("JFrameFindInDir"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabelFolder.setText("Folder:");

        jLabelPattern.setText("Text:");

        jTextFieldFolder.setEditable(false);

        jButtonBrowseFolder.setText("Browse...");
        jButtonBrowseFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBrowseFolderActionPerformed(evt);
            }
        });

        jCheckBoxMatchCase.setText("Match case");

        jButtonSearchInDir.setText("Search in folder");
        jButtonSearchInDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchInDirActionPerformed(evt);
            }
        });

        jButtonClose.setText("Close");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        jButtonClear.setText("Clear");
        jButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelSettingsLayout = new javax.swing.GroupLayout(jPanelSettings);
        jPanelSettings.setLayout(jPanelSettingsLayout);
        jPanelSettingsLayout.setHorizontalGroup(
            jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelSettingsLayout.createSequentialGroup()
                        .addComponent(jButtonClose)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonClear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonSearchInDir))
                    .addGroup(jPanelSettingsLayout.createSequentialGroup()
                        .addComponent(jLabelFolder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonBrowseFolder))
                    .addGroup(jPanelSettingsLayout.createSequentialGroup()
                        .addComponent(jLabelPattern)
                        .addGap(18, 18, 18)
                        .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBoxMatchCase)
                            .addComponent(jTextFieldText, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelSettingsLayout.setVerticalGroup(
            jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelFolder)
                    .addComponent(jTextFieldFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonBrowseFolder))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPattern)
                    .addComponent(jTextFieldText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBoxMatchCase)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSearchInDir)
                    .addComponent(jButtonClose)
                    .addComponent(jButtonClear))
                .addContainerGap())
        );

        jScrollPaneResults.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPaneResults)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneResults, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.deselectAllTabs();
    }//GEN-LAST:event_formWindowClosing

    private void jButtonBrowseFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBrowseFolderActionPerformed
		BrowseFindInDirDialog sd = new BrowseFindInDirDialog(this, true);
		sd.pack();
		sd.setLocationRelativeTo(this);
		sd.setVisible(true);

		// there is place when TreeChooseDialog is living
		Folder folderTo = sd.getFolderTo();
		sd.dispose();

		if (null == folderTo) return;
				
		this.Folder = folderTo;
		this.jTextFieldFolder.setText(folderTo.getPath().toAbsolutePath().toString());
		
    }//GEN-LAST:event_jButtonBrowseFolderActionPerformed

    private void jButtonSearchInDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchInDirActionPerformed
		this.deselectAllTabs();
		
		String findText = this.jTextFieldText.getText().trim();
        if (findText.isEmpty()) return;
		
		if(findText.length() < 3) {
			MainForm.showError("Too short text for search");
			return;
		}
        
        FindInput fi = new FindInput(this.Folder.getPath(), findText);

        if (this.jCheckBoxMatchCase.isSelected()){
            fi.setAccordingToCase(true);
        }
        
        FindReplace fr = new FindReplace(fi);
        ArrayList<FindResult> findResult = fr.find();
        
		int matches = 0;
		for(FindResult res : findResult){
			matches += res.getCollection().size();
		}
		
		// Generate tree data
		String rootString = "<html>Found " + matches + " matches of <b>" + findText + "</b> in " 
			+ findResult.size() + " files.</html>";
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(rootString);
		
		if (0 != findResult.size()) {
			for(FindResult res : findResult){
				ArrayList<FindResultItem> friCollection = res.getCollection();
				if (friCollection.isEmpty()) continue;

				DefaultMutableTreeNode category = new DefaultMutableTreeNode();
				category.setUserObject(res);
				top.add(category);

				for (FindResultItem fri : friCollection ) {
					DefaultMutableTreeNode book = new DefaultMutableTreeNode();
					book.setUserObject(fri);
					category.add(book);
				}
			}
		}
        
		// Build tree
        this.jTreeResults = new JTree(top);
		
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setLeafIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bullet_arrow_right.16x16.png")));
		renderer.setClosedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/page_white.16x16.png")));
		renderer.setOpenIcon(new javax.swing.ImageIcon(getClass().getResource("/images/page.16x16.png")));
		this.jTreeResults.setCellRenderer(renderer);
		
        this.jTreeResults.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.jTreeResults.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.jTreeResults.addTreeSelectionListener(this);
        this.jTreeResults.setRootVisible(true);
		this.jTreeResults.setShowsRootHandles(true);
		this.jTreeResults.setToggleClickCount(2);
        this.jScrollPaneResults.setViewportView(jTreeResults);
    
    }//GEN-LAST:event_jButtonSearchInDirActionPerformed

    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) 
            this.jTreeResults.getLastSelectedPathComponent();
 
        if (node == null || !node.isLeaf()) return;
		
		FindResultItem findResultItem = (FindResultItem) node.getUserObject();
		Folder rootFolder = (Folder) MainForm.getInstance().getTree().getTreeModel().getRoot();
		File existsFile = null;

		try{
			existsFile = rootFolder.findFile(findResultItem.getFileName());
		}
		catch(Exception exc){}

		if (null == existsFile) return;
		
		// Open file if it not opened yet, set active if opened already
		EventsStorageCollectionHandler esch = new EventsStorageCollectionHandler(existsFile);
		esch.call("open_file");
 
		FileJTab tab = (FileJTab) MainForm.getInstance().getTabbedPaneMain().getSelectedComponent();
		
		Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.pink);
		int p0 = findResultItem.getGlobalCharNumber();
		int p1 = p0 + findResultItem.getResult().length();

		try {
			tab.Highlighter.addHighlight(p0, p1, painter );
		}
		catch ( BadLocationException exc){}
		
    }
    
    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
		this.deselectAllTabs();
		this.setVisible(false);
		this.dispose();
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearActionPerformed
		this.jTextFieldText.setText("");
		this.jTreeResults = null; 
		this.jScrollPaneResults.setViewportView(this.jTreeResults);
		this.deselectAllTabs();
    }//GEN-LAST:event_jButtonClearActionPerformed
	
	/**
	 * Deselect tex in all opened tabs
	 */
	protected void deselectAllTabs(){
		int count = MainForm.getInstance().getTabbedPaneMain().getTabCount();
		for (int i = 0; i < count; i++) {
			FileJTab tab = (FileJTab) MainForm.getInstance().getTabbedPaneMain().getComponent(i);
			tab.Highlighter.removeAllHighlights();
		}
	}
	
	private void initMain(){
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/magnifier.24x24.png")));
		this.setTitle("Search in folder");
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBrowseFolder;
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonSearchInDir;
    private javax.swing.JCheckBox jCheckBoxMatchCase;
    private javax.swing.JLabel jLabelFolder;
    private javax.swing.JLabel jLabelPattern;
    private javax.swing.JPanel jPanelSettings;
    private javax.swing.JScrollPane jScrollPaneResults;
    private javax.swing.JTextField jTextFieldFolder;
    private javax.swing.JTextField jTextFieldText;
    // End of variables declaration//GEN-END:variables
}
