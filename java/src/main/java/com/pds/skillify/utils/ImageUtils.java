package com.pds.skillify.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageUtils {
	/**
	 * Extrae la ruta de archivo almacenada en la descripción del {@link ImageIcon}.
	 * Si la descripción es nula o vacía, devuelve {@code null}.
	 *
	 * @param icon El {@link ImageIcon} del cual extraer la ruta.
	 * @return La ruta original de la imagen, o {@code null} si no está definida.
	 */
	public static String imageIconToPath(ImageIcon icon) {
	    if (icon == null)
	        return null;

	    String descripcion = icon.getDescription();
	    return (descripcion != null && !descripcion.trim().isEmpty()) ? descripcion : null;
	}


	/**
	 * Crea un objeto {@link ImageIcon} a partir de una ruta de archivo.
	 * Si la ruta es inválida, el archivo no existe o ocurre un error de lectura, se devuelve {@code null}.
	 *
	 * @param imagePath Ruta al archivo de imagen (por ejemplo, "imagenes/usuarios/user123.png").
	 * @return Un objeto {@link ImageIcon} creado a partir de la imagen de la ruta proporcionada,
	 *         o {@code null} si ocurre un error o si la ruta es inválida.
	 */
	public static ImageIcon pathToImageIcon(String imagePath) {
	    if (imagePath == null || imagePath.trim().isEmpty())
	        return null;

	    try {
	        File file = new File(imagePath);
	        if (!file.exists())
	            return null;

	        BufferedImage image = ImageIO.read(file);
	        return new ImageIcon(image);
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

}
