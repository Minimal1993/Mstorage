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
import javax.swing.JPanel;
import mstorage.MainForm;

/**
 * Fabric for create new tabs for Files and make these appearance.
 */
public class TabsFabric {

	public static FileJTab getTab(File file) {
		final FileJTab PanelTemplate = new FileJTab(file);
		PanelTemplate.ScrollPaneDocumentText = new javax.swing.JScrollPane();
		PanelTemplate.JPanelDocumentPictures = new javax.swing.JPanel();
		PanelTemplate.TextAreaDocument = new javax.swing.JTextArea();

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
			PanelTemplate.TextAreaDocument.setText(file.getContent());
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
