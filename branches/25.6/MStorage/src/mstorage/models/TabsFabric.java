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
                
                // TODO: Have to use password input
                String s = (String) JOptionPane.showInputDialog(
                        MainForm.getInstance(),
                        "Please, enter a password:",
                        "Crypted file",
                        JOptionPane.PLAIN_MESSAGE,
                        new javax.swing.ImageIcon(
                            MainForm.getInstance().getClass().getResource("/images/lock.16x16.png")
                        ),
                        null,
                        null);

                if ((s != null) && (s.length() > 0)) {
                    file.setPassword(s);
                    try {
                        AESEncrypter encrypter = new AESEncrypter(file.getPassword());
                        String code = file.getContent();
                        String content = encrypter.decrypt( code );
                        PanelTemplate.TextAreaDocument.setText( content );
                    }
                    catch (Exception e) {
                        MainForm.showError(e.getMessage());
                        return null;
                    }
                }
            }
            else {
                PanelTemplate.TextAreaDocument.setText(file.getContent());
            }
		} catch (IOException e) {
			MainForm.showError(e.getMessage());
		}
        

		PanelTemplate.TextAreaDocument.setWrapStyleWord(true);
		PanelTemplate.ScrollPaneDocumentText.setViewportView(PanelTemplate.TextAreaDocument);

		javax.swing.GroupLayout PanelTemplateLayout = new javax.swing.GroupLayout(PanelTemplate);
		PanelTemplate.setLayout(PanelTemplateLayout);
		PanelTemplateLayout.setHorizontalGroup(PanelTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(PanelTemplate.ScrollPaneDocumentText, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
				.addComponent(PanelTemplate.JPanelDocumentPictures)
		);
		PanelTemplateLayout.setVerticalGroup(PanelTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(PanelTemplateLayout.createSequentialGroup()
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