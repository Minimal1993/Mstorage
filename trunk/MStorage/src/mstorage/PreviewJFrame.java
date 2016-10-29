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

import mstorage.storagecollection.Image;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import mstorage.components.ImageItem;
import mstorage.storagecollection.File;
import mstorage.utils.Log;
import net.coobird.thumbnailator.Thumbnails;

/**
 *
 * @author ilya.gulevskiy
 */
public class PreviewJFrame extends javax.swing.JFrame {

	protected Image Image;
		
    private JLabel imageContainer;
	private JPanel jPanelLeft;
	private JPanel jPanelRight;
    private JLabel jLabelLeft;
	private JLabel jLabelRight;
	private String jLabelLeftIcon = "/images/resultset_previous.17x32.png";
    private String jLabelTransIcon = "/images/transparent.17x32.png";
	private String jLabelRightIcon = "/images/resultset_next.17x32.png";
	
	/**
	 * Default size for preview window
	 */
	protected Dimension Dimension = new Dimension(600,600);

	public Image getImage() {
		return Image;
	}
	
	/**
	 * Creates new form PreviewJFrame
	 */
	public PreviewJFrame(Image image) {
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

        jLayeredPaneContainer = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLayeredPaneContainer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLayeredPaneContainer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLayeredPaneContainerMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jLayeredPaneContainerLayout = new javax.swing.GroupLayout(jLayeredPaneContainer);
        jLayeredPaneContainer.setLayout(jLayeredPaneContainerLayout);
        jLayeredPaneContainerLayout.setHorizontalGroup(
            jLayeredPaneContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        jLayeredPaneContainerLayout.setVerticalGroup(
            jLayeredPaneContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPaneContainer)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPaneContainer)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLayeredPaneContainerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLayeredPaneContainerMouseClicked
        this.goNextHandler(evt);
    }//GEN-LAST:event_jLayeredPaneContainerMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane jLayeredPaneContainer;
    // End of variables declaration//GEN-END:variables

	private void initMain() {
		this.jLayeredPaneContainer.removeAll();
		this.imageContainer = new JLabel();
		this.jPanelLeft = new javax.swing.JPanel();
		this.jPanelRight = new javax.swing.JPanel();
		this.jLabelLeft = new javax.swing.JLabel();;
		this.jLabelRight = new javax.swing.JLabel();
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/picture.32x32.png")));
		this.setTitle(this.Image.getOrigName());
		
		java.io.File picture;
		try {
			picture = new java.io.File(this.Image.getPath().toAbsolutePath().toString());
		} catch (java.lang.NullPointerException e) {
			picture = new java.io.File(getClass().getResource(ImageItem.NotFoundPic).getPath());
		}		
		
		try {
			BufferedImage originalImage = javax.imageio.ImageIO.read(picture);
			
			// Calc how size will be at window
			int origWidth = originalImage.getWidth();
			int origHeight = originalImage.getHeight();
			Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
			int scrWidth = (int)screenSize.getWidth() - 150;
			int scrHeight = (int) screenSize.getHeight() - 150;
			
			if (origWidth < scrWidth && origHeight < scrHeight) {
				this.Dimension = new Dimension(origWidth, origHeight);
			}
			else {
				this.Dimension = new Dimension(scrWidth, scrHeight);
			}

			BufferedImage thumbnail = Thumbnails.of(originalImage)
					.size((int)this.Dimension.getWidth(), (int)this.Dimension.getHeight())
					.asBufferedImage();

			// TODO: There increment to Dimension tested only in Win7
			Dimension winSize = new Dimension(thumbnail.getWidth() + 5, thumbnail.getHeight() + 25);
			this.setPreferredSize(winSize);
			
			javax.swing.ImageIcon imageIcon = new javax.swing.ImageIcon(thumbnail);
			this.imageContainer.setIcon(null);
			this.imageContainer.setIcon(imageIcon);
			this.jLayeredPaneContainer.add(this.imageContainer, new Integer(50));
			
			// BEGIN: Next and Prev panels
			jPanelLeft.setBackground(new java.awt.Color(204, 204, 204));
			jPanelLeft.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
			jPanelLeft.setOpaque(false);
            java.awt.event.MouseAdapter maLeft = new java.awt.event.MouseAdapter(){
				public void mouseClicked(java.awt.event.MouseEvent evt) {
					goPrevHandler(evt);
				}
				public void mouseEntered(java.awt.event.MouseEvent evt) {
					jLabelLeft.setIcon(new javax.swing.ImageIcon(getClass().getResource(jLabelLeftIcon)));
				}
				public void mouseExited(java.awt.event.MouseEvent evt) {
					jLabelLeft.setIcon(new javax.swing.ImageIcon(getClass().getResource(jLabelTransIcon)));
				}
            };
			jPanelLeft.addMouseListener(maLeft);

			jLabelLeft.setIcon(new javax.swing.ImageIcon(getClass().getResource(this.jLabelTransIcon))); 
			jLabelLeft.addMouseListener(maLeft);

			javax.swing.GroupLayout jPanelLeftLayout = new javax.swing.GroupLayout(jPanelLeft);
			jPanelLeft.setLayout(jPanelLeftLayout);
			jPanelLeftLayout.setHorizontalGroup(
				jPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLeftLayout.createSequentialGroup()
					.addGap(0, 0, Short.MAX_VALUE)
					.addComponent(jLabelLeft))
			);
			jPanelLeftLayout.setVerticalGroup(
				jPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanelLeftLayout.createSequentialGroup()
					.addGap((int)imageIcon.getIconHeight()/2 - 25)
					.addComponent(jLabelLeft)
					.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);

			jPanelRight.setBackground(new java.awt.Color(204, 204, 204));
			jPanelRight.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
			jPanelRight.setOpaque(false);
            java.awt.event.MouseAdapter maRight = new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent evt) {
					goNextHandler(evt);
				}
				public void mouseEntered(java.awt.event.MouseEvent evt) {
					jLabelRight.setIcon(new javax.swing.ImageIcon(getClass().getResource(jLabelRightIcon)));
				}
				public void mouseExited(java.awt.event.MouseEvent evt) {
					jLabelRight.setIcon(new javax.swing.ImageIcon(getClass().getResource(jLabelTransIcon)));
				}
			};
			jPanelRight.addMouseListener(maRight);

			jLabelRight.setIcon(new javax.swing.ImageIcon(getClass().getResource(this.jLabelTransIcon))); 
			jLabelRight.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
			jLabelRight.addMouseListener(maRight);

			javax.swing.GroupLayout jPanelRightLayout = new javax.swing.GroupLayout(jPanelRight);
			jPanelRight.setLayout(jPanelRightLayout);
			jPanelRightLayout.setHorizontalGroup(
				jPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jLabelRight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			);
			jPanelRightLayout.setVerticalGroup(
				jPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanelRightLayout.createSequentialGroup()
					.addGap((int)imageIcon.getIconHeight()/2 - 25)
					.addComponent(jLabelRight)
					.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);

			this.jLayeredPaneContainer.setLayer(jPanelLeft, new Integer(100));
			this.jLayeredPaneContainer.setLayer(jPanelRight, new Integer(100));

			javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(this.jLayeredPaneContainer);
			this.jLayeredPaneContainer.setLayout(jLayeredPane1Layout);
			jLayeredPane1Layout.setHorizontalGroup(
				jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jLayeredPane1Layout.createSequentialGroup()
					.addComponent(jPanelLeft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 450, Short.MAX_VALUE)
					.addComponent(jPanelRight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
			);
			jLayeredPane1Layout.setVerticalGroup(
				jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jPanelLeft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(jPanelRight, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			);
			// END: Next and Prev panels
			
			this.imageContainer.setBounds( 0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight() ); 			

		} catch (java.io.IOException e) {
			Log.info(e.getMessage());
			return;
		}
	
		pack();
            
                // This is magic trick - Linux dont want resize window without this delay
                try {
                    Thread.sleep(50);
                } catch(Exception e){
                    Log.info(e.getMessage());
                }
                
                this.setLocationRelativeTo(MainForm.getInstance());
		
        
	}
    
    /**
     * Go to previous picture
     */
    public void goPrevHandler(java.awt.event.MouseEvent evt){
        File file = (File)this.Image.getFather();
        Image prev = null;

        Set<Map.Entry<String, Image>> imgSet = file.Images.entrySet();
        for (Map.Entry<String, Image> img : imgSet) {
            if(img.getValue().getFileName().equals( this.Image.getFileName()) ) {
                break;
            }
            prev = img.getValue();
        }

        if (null != prev) {
            this.Image = prev;
            this.initMain();
        }
    }
	
    /**
     * Go to next picture
     */
    public void goNextHandler(java.awt.event.MouseEvent evt){
        File file = (File)this.Image.getFather();
        Image prev = null;
        Image next = null;

        Set<Map.Entry<String, Image>> imgSet = file.Images.entrySet();
        for (Map.Entry<String, Image> img : imgSet) {
            if(null != prev && prev.getFileName().equals( this.Image.getFileName()) ) {
                next = img.getValue();
                break;
            }
            
            prev = img.getValue();
        }

        if (null != next) {
            this.Image = next;
            this.initMain();
        }
    }
    
}
