package it.spaghettisource.navaltrader.ui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ImageIconFactory {

	private static Log log = LogFactory.getLog(ImageIconFactory.class.getName());
	
	public  static final int ICON_SIZE_BUTTON = 30;
	public  static final int ICON_SIZE_TAB = 25;
	public  static final int ICON_SIZE_FRAME = 20;		

	private ImageIconFactory(){
	}


	public static BufferedImage getScaledBufferedImage(BufferedImage srcImg, int w, int h){
		Image tmp = srcImg.getScaledInstance(w, h, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}
	
	private static Image getImageByNameAndSize(String name,int size) {

		try {
			BufferedImage bufferedImage;
			bufferedImage = ImageIO.read(ImageIconFactory.class.getResourceAsStream(name));
			return bufferedImage.getScaledInstance(size, size,  java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e) {
			String message = "error loading the immange "+name+" for:"+e.getMessage();
			log.error(message,e );
			throw new RuntimeException(message,e);
		}

	}
	
	private static ImageIcon getImageIconByNameAndSize(String name,int size) {
		return new ImageIcon(getImageByNameAndSize(name,size));
	}

	public static ImageIcon getForButton(String name) {
		return getImageIconByNameAndSize(name, ICON_SIZE_BUTTON);
	}

	public static ImageIcon getForTab(String name) {
		return getImageIconByNameAndSize(name, ICON_SIZE_TAB);
	}

	public static ImageIcon getForFrame(String name) {
		return getImageIconByNameAndSize(name, ICON_SIZE_FRAME);
	}
	
	public static Image getAppImage() {
		try {
			return ImageIO.read(ImageIconFactory.class.getResourceAsStream("/icon/ship.png"));
		} catch (IOException e) {
			log.error("error loading the app image for:"+e.getMessage(),e );
			throw new RuntimeException(e);
		}

	}

}