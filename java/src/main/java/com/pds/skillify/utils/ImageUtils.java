package com.pds.skillify.utils;


import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageUtils {

    /**
     * Convierte un ImageIcon a un array de bytes.
     */
    public static byte[] imageIconToBytes(ImageIcon icon) {
        if (icon == null) return null;
        
        try {
            // Convertir ImageIcon a BufferedImage
            Image image = icon.getImage();
            java.awt.image.BufferedImage bufferedImage = new java.awt.image.BufferedImage(
                    image.getWidth(null), image.getHeight(null), java.awt.image.BufferedImage.TYPE_INT_ARGB);
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
     * Convierte un array de bytes a ImageIcon.
     */
    public static ImageIcon bytesToImageIcon(byte[] imageData) {
        if (imageData == null || imageData.length == 0) return null;

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
