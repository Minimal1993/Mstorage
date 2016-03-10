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

/**
 * Fabric for create new tabs for Files and make these appearance.
 */
public class TabsFabric {

	public static FileJTab getTab(File file) {
		final FileJTab PanelTemplate = new FileJTab(file);
		JScrollPane ScrollPaneDocumentText = new javax.swing.JScrollPane();
		JPanel JPanelDocumentPictures = new javax.swing.JPanel();
		JTextArea TextAreaDocument = new javax.swing.JTextArea();
		PanelTemplate.TextAreaDocument = TextAreaDocument;

		PanelTemplate.setPreferredSize(new java.awt.Dimension(300, 300));

		TextAreaDocument.setColumns(20);
		TextAreaDocument.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
		TextAreaDocument.setLineWrap(true);
		TextAreaDocument.setRows(5);
        TextAreaDocument.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                PanelTemplate.TextAreaDocumentKeyReleased(evt);
            }
        });

		try {
			TextAreaDocument.setText(file.getContent());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		TextAreaDocument.setWrapStyleWord(true);
		ScrollPaneDocumentText.setViewportView(TextAreaDocument);

		javax.swing.GroupLayout PanelTemplateLayout = new javax.swing.GroupLayout(PanelTemplate);
		PanelTemplate.setLayout(PanelTemplateLayout);
		PanelTemplateLayout.setHorizontalGroup(PanelTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(ScrollPaneDocumentText, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
				.addComponent(JPanelDocumentPictures)
		);
		PanelTemplateLayout.setVerticalGroup(PanelTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(PanelTemplateLayout.createSequentialGroup()
						.addComponent(ScrollPaneDocumentText, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(JPanelDocumentPictures, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
		);
		
		// Create ImageCarousel
		ImageCarousel ImageCarousel = new ImageCarousel(file);

		JPanelDocumentPictures.setLayout(new java.awt.BorderLayout());
		JPanelDocumentPictures.add(ImageCarousel, java.awt.BorderLayout.NORTH);

		return PanelTemplate;
	}

}
