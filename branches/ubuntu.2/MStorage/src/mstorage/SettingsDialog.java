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
package mstorage;

import java.awt.Toolkit;

import mstorage.classes.Settings;

/**
 *
 * @author ilya.gulevskiy
 */
public class SettingsDialog extends javax.swing.JDialog {

	/**
	 * Creates new form SettingsDialog
	 */
	public SettingsDialog(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	
		this.setIcon();
		
		// 
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPaneAppearance = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        SettingsTabbedPane = new javax.swing.JTabbedPane();
        jPanelCommon = new javax.swing.JPanel();
        jPanelCommonIn = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldStorageDirectory = new javax.swing.JTextField();
        jButtonBrowse = new javax.swing.JButton();
        jPanelAppearance = new javax.swing.JPanel();
        jPanelAppearanceIn = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxStyleOfStorageTree = new javax.swing.JComboBox<>();
        jButtonOK = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 390, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 208, Short.MAX_VALUE)
        );

        jTabbedPaneAppearance.addTab("tab1", jPanel3);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Settings");
        setModal(true);
        setName("dialogSettings"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        javax.swing.GroupLayout jPanelCommonInLayout = new javax.swing.GroupLayout(jPanelCommonIn);
        jPanelCommonIn.setLayout(jPanelCommonInLayout);
        jPanelCommonInLayout.setHorizontalGroup(
            jPanelCommonInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelCommonInLayout.setVerticalGroup(
            jPanelCommonInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 214, Short.MAX_VALUE)
        );

        jLabel1.setText("Select storage directory:");

        jTextFieldStorageDirectory.setText(Settings.getInstance().getProperty("StorageDirectory"));

        jButtonBrowse.setText("Browse...");
        jButtonBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBrowseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelCommonLayout = new javax.swing.GroupLayout(jPanelCommon);
        jPanelCommon.setLayout(jPanelCommonLayout);
        jPanelCommonLayout.setHorizontalGroup(
            jPanelCommonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCommonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCommonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldStorageDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButtonBrowse)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelCommonIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(95, 95, 95))
        );
        jPanelCommonLayout.setVerticalGroup(
            jPanelCommonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCommonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCommonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCommonLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelCommonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldStorageDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonBrowse))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanelCommonIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        SettingsTabbedPane.addTab("Common", jPanelCommon);

        jLabel2.setText("Choose style of storage tree:");

        jComboBoxStyleOfStorageTree.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Delta", "Vega" }));
        jComboBoxStyleOfStorageTree.setSelectedItem(Settings.getInstance().getProperty("Icons"));
        jComboBoxStyleOfStorageTree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxStyleOfStorageTreeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelAppearanceInLayout = new javax.swing.GroupLayout(jPanelAppearanceIn);
        jPanelAppearanceIn.setLayout(jPanelAppearanceInLayout);
        jPanelAppearanceInLayout.setHorizontalGroup(
            jPanelAppearanceInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAppearanceInLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addComponent(jComboBoxStyleOfStorageTree, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelAppearanceInLayout.setVerticalGroup(
            jPanelAppearanceInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAppearanceInLayout.createSequentialGroup()
                .addGroup(jPanelAppearanceInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(jComboBoxStyleOfStorageTree, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 194, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelAppearanceLayout = new javax.swing.GroupLayout(jPanelAppearance);
        jPanelAppearance.setLayout(jPanelAppearanceLayout);
        jPanelAppearanceLayout.setHorizontalGroup(
            jPanelAppearanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAppearanceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelAppearanceIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelAppearanceLayout.setVerticalGroup(
            jPanelAppearanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAppearanceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelAppearanceIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        SettingsTabbedPane.addTab("Appearance", jPanelAppearance);

        jButtonOK.setText("OK");
        jButtonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(SettingsTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonOK)
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(SettingsTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonOK)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        MainForm.getInstance().windowSettingClosed();
		System.out.println("formWindowClosed");
    }//GEN-LAST:event_formWindowClosed

    private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOKActionPerformed
        this.setVisible(false);
		this.dispose();
    }//GEN-LAST:event_jButtonOKActionPerformed

    private void jButtonBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBrowseActionPerformed
        final javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
		fc.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showOpenDialog(this);
		
        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File file = fc.getSelectedFile();
			
			if (file.exists() && file.isDirectory()) {
				Settings.getInstance().setProperty("StorageDirectory", file.getAbsolutePath().toString());
				
				this.jTextFieldStorageDirectory.setText(Settings.getInstance().getProperty("StorageDirectory"));
				
				// TODO: May be need apply change by special command of button or do it when settings window will closed
				MainForm.getInstance().initTree();
				MainForm.getInstance().checkOpenedTabsFileExists();
				MainForm.getInstance().checkButtonCloseCurrentDocument();
			}
        } 
    }//GEN-LAST:event_jButtonBrowseActionPerformed

    private void jComboBoxStyleOfStorageTreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxStyleOfStorageTreeActionPerformed
		if(Settings.getInstance().getProperty("Icons").equals( this.jComboBoxStyleOfStorageTree.getSelectedItem().toString() )){
			return;
		}
		
		Settings.getInstance().setProperty("Icons", this.jComboBoxStyleOfStorageTree.getSelectedItem().toString());
		MainForm.getInstance().initTree();
    }//GEN-LAST:event_jComboBoxStyleOfStorageTreeActionPerformed

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
			java.util.logging.Logger.getLogger(SettingsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(SettingsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(SettingsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(SettingsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				SettingsDialog dialog = new SettingsDialog(new javax.swing.JFrame(), true);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane SettingsTabbedPane;
    private javax.swing.JButton jButtonBrowse;
    private javax.swing.JButton jButtonOK;
    private javax.swing.JComboBox<String> jComboBoxStyleOfStorageTree;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelAppearance;
    private javax.swing.JPanel jPanelAppearanceIn;
    private javax.swing.JPanel jPanelCommon;
    private javax.swing.JPanel jPanelCommonIn;
    private javax.swing.JTabbedPane jTabbedPaneAppearance;
    private javax.swing.JTextField jTextFieldStorageDirectory;
    // End of variables declaration//GEN-END:variables

	private void setIcon() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/cog.32x32.png")));
	}

}
