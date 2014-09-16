package phr.tools;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import phr.exceptions.ImageHandlerException;

public class ImageHandler {

	public Image getImageFromURL(URL pictureURL) throws ImageHandlerException {
		try {
			Image image = ImageIO.read(pictureURL);
			return toBufferedImage(image);
		} catch (IOException e) {
			throw new ImageHandlerException("Unable to get image from URL", e);
		}
	}

	private BufferedImage toBufferedImage(Image img) {
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

	public String encodedImageToBase64(BufferedImage image)
			throws ImageHandlerException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", baos);
			Base64 base64 = new Base64();
			String encodedImage = new String(base64.encode(baos.toByteArray()));
			return encodedImage;
		} catch (IOException e) {
			throw new ImageHandlerException(
					"Unable to convert image to base64", e);
		}
	}

	public String encodeImageToBase64(Image imageFromPost)
			throws ImageHandlerException {
		BufferedImage image = toBufferedImage(imageFromPost);
		String encodedImage = encodeImageToBase64(image);
		return encodedImage;
	}

	public BufferedImage decodeImage(String encodedImage)
			throws ImageHandlerException {
		try {
			Base64 base64 = new Base64();
			byte[] imageInByte = base64.decode(encodedImage);
			InputStream in = new ByteArrayInputStream(imageInByte);
			BufferedImage image = ImageIO.read(in);
			return image;
		} catch (IOException e) {
			throw new ImageHandlerException("Unable to decode image", e);
		}
	}

	public String saveImage_ReturnFilePath(String encodedImage) {
		String filePath = null;
		return filePath;
	}

	public String getEncodedImageFromFile(String imageSource)
			throws FileNotFoundException, ImageHandlerException {
		String basePath = "D://PHRFiles/images/";
		File folder = new File(basePath);
		System.out.println(folder.exists());
		try {
			BufferedImage bufferedImage = ImageIO.read(Files
					.newInputStream(Paths.get(basePath + imageSource)));
			String encodedImage = encodedImageToBase64(bufferedImage);
			return encodedImage;
		} catch (IOException e) {
			throw new ImageHandlerException("An error occurred", e);
		}

	}
}
