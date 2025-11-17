package robotgohome;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/*
 * * Called by all JLabel objects to resize the image based on gridsize
 * @author Cameron Abanes
 * @version 5/6/25
 */
public class ImageResize {
	
	/**
	 * resizes image with given size
	 * @param picName picture to be resized
	 * @param size the desired size
	 * @return newIcon the resized imageIcon
	 */
	public static ImageIcon resizeImage(String picName, int size) {
		ImageIcon originalIcon = new ImageIcon(picName);
		Image originalImage = originalIcon.getImage();

		Image resized = originalImage.getScaledInstance(size, size, Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(resized);
		return newIcon;
	}
}
