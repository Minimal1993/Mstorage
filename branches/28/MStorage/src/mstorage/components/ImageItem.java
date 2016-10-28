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
package mstorage.components;

import java.awt.Cursor;
import mstorage.storagecollection.Image;
import net.coobird.thumbnailator.Thumbnails;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import mstorage.MainForm;
import mstorage.PreviewJFrame;
import mstorage.menus.PopupMenuImageItem;

/**
 * Item of Image for inserting to ImageCarousel
 *
 * @author ilya.gulevskiy
 */
public class ImageItem extends javax.swing.JPanel {

	protected Image Image = null;
	protected Integer VerticalSize = 100;
	protected Integer GorizontalSize = 100;
	public static String NotFoundPic = "/images/not_found.png";
	public PreviewJFrame PreviewJFrame = null;

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

		this.addMouseListener(this.getMouseListener());
	}

	protected MouseListener getMouseListener() {
		return new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				final ImageItem item = (ImageItem) e.getSource();
				int x = e.getX();
				int y = e.getY();

				// If it was right click. 
				// Use getButton because isPopupTrigger() diffrently work in Linux and Windows 
				if (3 == e.getButton()) {
					// Close PreviewJFrame window
					if (null != item.PreviewJFrame) {
						item.PreviewJFrame.setVisible(false);
						item.PreviewJFrame.dispose();
						item.PreviewJFrame = null;
					}

					PopupMenuImageItem popup = new PopupMenuImageItem(item.Image);
					popup.show(item, x, y);

					return;
				}

				// Left mouse button, open preview window
				// Permit to only one window and if Image is real
				if (null == item.Image) {
					return;
				}

				item.PreviewJFrame = new PreviewJFrame(item.Image);
				item.PreviewJFrame.pack();
				item.PreviewJFrame.setVisible(true);

				item.PreviewJFrame.addWindowListener(new WindowAdapter() {
					public void windowClosed(WindowEvent e) {
						PreviewJFrame w = (PreviewJFrame) e.getWindow();
						item.PreviewJFrame = null;
					}

					public void windowClosing(WindowEvent e) {
						//				MainForm.getInstance().HowToUseDialogIsOpened = false;
					}
				});
			} // END mouseReleased
			
			public void mousePressed(MouseEvent e){
				int ii = 0;
				
				if (e.isPopupTrigger()){
					int r = 8;
				}
			}
			
			public void mouseEntered(MouseEvent e) {
				ImageItem item = (ImageItem) e.getSource();
				item.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			public void mouseExited(MouseEvent e) {
				ImageItem item = (ImageItem) e.getSource();
				item.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		};
	}

}