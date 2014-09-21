package phr.tools;

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
import sun.awt.image.ToolkitImage;

public class ImageHandler {

	public static Image getImageFromURL(URL pictureURL)
			throws ImageHandlerException {
		try {
			Image image = ImageIO.read(pictureURL);
			return toBufferedImage(image);
		} catch (IOException e) {
			throw new ImageHandlerException("Unable to get image from URL", e);
		}
	}

	private static BufferedImage toBufferedImage(Image img) {
		if (img.getClass().equals(BufferedImage.class))
			return (BufferedImage) img;
		BufferedImage buffered = ((ToolkitImage) img).getBufferedImage();
		return buffered;
	}

	public static String encodeBufferedImage(BufferedImage image)
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

	public static String encodeImageToBase64(Image imageFromPost)
			throws ImageHandlerException {
		BufferedImage image = toBufferedImage(imageFromPost);
		String encodedImage = encodeBufferedImage(image);
		return encodedImage;
	}

	public static BufferedImage decodeImage(String encodedImage)
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

	public static String saveImage_ReturnFilePath(String encodedImage) {
		long time = TimestampHandler.getCurrentTimestamp().getTime();
		String uniqueString = UUIDGenerator.generateUniqueString();

		String fileName = time + uniqueString + ".jpg";
		// save
		System.out.println(fileName);

		return fileName;
	}

	public static String getEncodedImageFromFile(String fileName)
			throws FileNotFoundException, ImageHandlerException {
		String basePath = "D://PHRFiles/images/";
		File folder = new File(basePath);
		if (!folder.exists())
			folder.mkdir();
		try {
			BufferedImage bufferedImage = ImageIO.read(Files
					.newInputStream(Paths.get(basePath + fileName)));
			String encodedImage = encodeBufferedImage((bufferedImage));
			return encodedImage;
		} catch (IOException e) {
			throw new ImageHandlerException("An error occurred", e);
		}

	}
}
