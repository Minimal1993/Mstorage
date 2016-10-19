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
package mstorage.models;

import mstorage.storagecollection.File;
import mstorage.components.FileJTab;
import mstorage.components.ImageCarousel;

import java.awt.*;
import javax.swing.tree.*;
import javax.swing.JTree;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import mstorage.MainForm;
import mstorage.classes.AESEncrypter;
import mstorage.components.CryptComp;
import mstorage.dialogs.password.PasswordAskDialog;

/**
 * Fabric for create new tabs for Files and make these appearance.
 */
public class TabsFabric {

	public static FileJTab getTab(File file) {
		final FileJTab PanelTemplate = new FileJTab(file);
		PanelTemplate.ScrollPaneDocumentText = new javax.swing.JScrollPane();
		PanelTemplate.JPanelDocumentPictures = new javax.swing.JPanel();
		PanelTemplate.TextAreaDocument = new javax.swing.JTextArea();
		PanelTemplate.TextAreaDocument.addFocusListener(PanelTemplate);
        
        // Elements search in file
        PanelTemplate.jPanelSearchInFile = new javax.swing.JPanel();
        PanelTemplate.jButtonCloseSearchInFilePanel = new javax.swing.JButton();
        PanelTemplate.jTextFieldSearchInFile = new javax.swing.JTextField();
        PanelTemplate.jButtonSearchInFile = new javax.swing.JButton();
        PanelTemplate.jCheckBoxFindInFileUseCase = new javax.swing.JCheckBox();

		PanelTemplate.setPreferredSize(new java.awt.Dimension(300, 300));

		PanelTemplate.TextAreaDocument.setColumns(20);
		PanelTemplate.TextAreaDocument.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
		PanelTemplate.TextAreaDocument.setLineWrap(true);
		PanelTemplate.TextAreaDocument.setRows(5);
        PanelTemplate.TextAreaDocument.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                PanelTemplate.TextAreaDocumentKeyReleased(evt);
            }
        });

		try {
            
            // Check if file is crypted
            if (CryptComp.isCryptedFile(file.getPath())){
                
				PasswordAskDialog sd = new PasswordAskDialog(MainForm.getInstance(), true, file);
				sd.pack();
				sd.setLocationRelativeTo(MainForm.getInstance());
				sd.setVisible(true);

				// When dialog isclosed
				String newPassword = sd.getPassword();
				boolean isCancel = sd.getIsCancel();
				sd.dispose();

				if (null == newPassword || isCancel) return null;

				file.setPassword(newPassword);
				AESEncrypter encrypter = new AESEncrypter(file.getPassword());
				String code = file.getContent();
				String content = encrypter.decrypt( code );
				PanelTemplate.TextAreaDocument.setText( content );
            }
            else {
                PanelTemplate.TextAreaDocument.setText(file.getContent());
            }
		} catch (Exception e) {
			MainForm.showError(e.getMessage());
			return null;
		}
        
		PanelTemplate.TextAreaDocument.setWrapStyleWord(true);
		PanelTemplate.ScrollPaneDocumentText.setViewportView(PanelTemplate.TextAreaDocument);
        
        
        // Elements search in file
        PanelTemplate.jPanelSearchInFile.setPreferredSize(new java.awt.Dimension(0, 30));

        PanelTemplate.jButtonCloseSearchInFilePanel.setIcon(
            new javax.swing.ImageIcon(
                MainForm.getInstance().getClass().getResource("/images/cross.16x16.png")
            )
        ); // NOI18N
        PanelTemplate.jButtonCloseSearchInFilePanel.setContentAreaFilled(false);
        PanelTemplate.jButtonCloseSearchInFilePanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PanelTemplate.jPanelSearchInFile.setVisible(false);
                MainForm.getInstance().getjButtonSearchInFile().setSelected(false);
            }
        });

        PanelTemplate.jButtonSearchInFile.setText("Find");
        PanelTemplate.jButtonSearchInFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.out.println("jButtonSearchInFile");
            }
        });

        PanelTemplate.jCheckBoxFindInFileUseCase.setText("Use case");
        
        javax.swing.GroupLayout jPanelSearchInFileLayout = new javax.swing.GroupLayout(PanelTemplate.jPanelSearchInFile);
        PanelTemplate.jPanelSearchInFile.setLayout(jPanelSearchInFileLayout);
        jPanelSearchInFileLayout.setHorizontalGroup(
            jPanelSearchInFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSearchInFileLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(PanelTemplate.jTextFieldSearchInFile, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PanelTemplate.jButtonSearchInFile)
                .addGap(18, 18, 18)
                .addComponent(PanelTemplate.jCheckBoxFindInFileUseCase)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                .addComponent(PanelTemplate.jButtonCloseSearchInFilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelSearchInFileLayout.setVerticalGroup(
            jPanelSearchInFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelTemplate.jButtonCloseSearchInFilePanel)
            .addGroup(jPanelSearchInFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(PanelTemplate.jTextFieldSearchInFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(PanelTemplate.jButtonSearchInFile)
                .addComponent(PanelTemplate.jCheckBoxFindInFileUseCase))
        );
        PanelTemplate.jPanelSearchInFile.setVisible(false);

        
		javax.swing.GroupLayout PanelTemplateLayout = new javax.swing.GroupLayout(PanelTemplate);
		PanelTemplate.setLayout(PanelTemplateLayout);
		PanelTemplateLayout.setHorizontalGroup(PanelTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(PanelTemplate.ScrollPaneDocumentText, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
				.addComponent(PanelTemplate.JPanelDocumentPictures)
                .addComponent(PanelTemplate.jPanelSearchInFile, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
		);
		PanelTemplateLayout.setVerticalGroup(PanelTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(PanelTemplateLayout.createSequentialGroup()
                        .addComponent(PanelTemplate.jPanelSearchInFile, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(PanelTemplate.ScrollPaneDocumentText, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(PanelTemplate.JPanelDocumentPictures, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
		);
		
		// Create ImageCarousel
		PanelTemplate.createJPanelDocumentPictures();
		
		PanelTemplate.checksAfterCreating();

		return PanelTemplate;
	}
    


}
