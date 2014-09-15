package phr.tools;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

public class ImageHandler {

	public static Image getImageFromURL(URL pictureURL) throws IOException {
		Image image = ImageIO.read(pictureURL);
		return image;
	}

	private static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null),
				img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}

	public static String encodeImageToBase64(Image imageFromPost)
			throws IOException {
		BufferedImage image = toBufferedImage(imageFromPost);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "png", baos);
		Base64 base64 = new Base64();
		String encodedImage = new String(base64.encode(baos.toByteArray()));
		return encodedImage;
	}
}
