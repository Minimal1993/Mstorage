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
package mstorage.components;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import net.coobird.thumbnailator.Thumbnails;
import java.awt.image.BufferedImage;

import StorageCollection.*;
import mstorage.MainForm;
import mstorage.PreviewJFrame;

/**
 * Item of Image for inserting to ImageCarousel
 *
 * @author ilya.gulevskiy
 */
public class ImageItem extends javax.swing.JPanel {

	protected Image Image = null;
	public boolean PreviewIsOpened = false;
	protected Integer VerticalSize = 100;
	protected Integer GorizontalSize = 100;
	public static String NotFoundPic = "/images/not_found.png";

	public Image getImage() {
		return Image;
	}

	public Integer getVerticalSize() {
		return VerticalSize;
	}

	public Integer getGorizontalSize() {
		return GorizontalSize;
	}

	/**
	 * Creates new form ImageItem
	 */
	public ImageItem(Image image) {
		this.Image = image;

		initComponents();

		this.initMain();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelImage = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(100, 100));

        jLabelImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelImageMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelImage, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelImage, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jLabelImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelImageMouseClicked
		// Permit to only one window and if Image is real
		if (true == this.PreviewIsOpened || null == this.Image) {
			return;
		}
		
		final javax.swing.JFrame sd = new PreviewJFrame(this, this.Image);
		sd.pack();
		sd.setLocationRelativeTo(this);
		sd.setVisible(true);
		this.PreviewIsOpened = true;

		sd.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				PreviewJFrame w = (PreviewJFrame) e.getWindow();
				w.ImageItem.PreviewIsOpened = false;
			}

			public void windowClosing(WindowEvent e) {
//				MainForm.getInstance().HowToUseDialogIsOpened = false;
			}
		});
    }//GEN-LAST:event_jLabelImageMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelImage;
    // End of variables declaration//GEN-END:variables

	private void initMain() {
		java.io.File picture;
		try {
			picture = new java.io.File(this.Image.getPath().toAbsolutePath().toString());
		} catch (java.lang.NullPointerException e) {
			picture = new java.io.File(getClass().getResource(this.NotFoundPic).getPath());
		}

		try {
			BufferedImage originalImage = javax.imageio.ImageIO.read(picture);

			BufferedImage thumbnail = Thumbnails.of(originalImage)
					.size(this.GorizontalSize, this.VerticalSize)
					.asBufferedImage();

			this.jLabelImage.setIcon(new javax.swing.ImageIcon(thumbnail));

		} catch (java.io.IOException e) {
			return;
		}
	}
	
	
	
	
}
