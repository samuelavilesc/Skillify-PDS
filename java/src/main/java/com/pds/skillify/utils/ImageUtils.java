package com.pds.skillify.utils;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageUtils {

	/**
	 * Convierte un objeto {@link ImageIcon} en un arreglo de bytes en formato PNG.
	 * Si el {@link ImageIcon} proporcionado es {@code null}, la función retornará {@code null}.
	 *
	 * @param icon El objeto {@link ImageIcon} que se desea convertir a bytes.
	 * @return Un arreglo de bytes que representa la imagen en formato PNG, o {@code null} si ocurre un error
	 *         o si el parámetro {@code icon} es {@code null}.
	 * @throws IOException Si ocurre un error durante la conversión de la imagen a bytes.
	 */
	public static byte[] imageIconToBytes(ImageIcon icon) {
		if (icon == null)
			return null;

		try {
			// Convertir ImageIcon a BufferedImage
			Image image = icon.getImage();
			java.awt.image.BufferedImage bufferedImage = new java.awt.image.BufferedImage(image.getWidth(null),
					image.getHeight(null), java.awt.image.BufferedImage.TYPE_INT_ARGB);
			java.awt.Graphics2D g2 = bufferedImage.createGraphics();
			g2.drawImage(image, 0, 0, null);
			g2.dispose();

			// Convertir BufferedImage a byte[]
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "png", baos); // Guardamos como PNG
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Convierte un arreglo de bytes que representa una imagen en un objeto {@link ImageIcon}.
	 * Si el arreglo de bytes proporcionado es {@code null} o está vacío, la función retornará {@code null}.
	 *
	 * @param imageData El arreglo de bytes que representa la imagen.
	 * @return Un objeto {@link ImageIcon} creado a partir de los bytes proporcionados, o {@code null} si ocurre un error
	 *         o si el parámetro {@code imageData} es {@code null} o está vacío.
	 * @throws IOException Si ocurre un error durante la lectura de los bytes o la creación de la imagen.
	 */
	public static ImageIcon bytesToImageIcon(byte[] imageData) {
		if (imageData == null || imageData.length == 0)
			return null;

		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
			Image image = ImageIO.read(bais);
			return new ImageIcon(image);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
