package com.greenlend.greenlend.utils;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {

    public static int THUMBNAIL_HEIGHT = 300;

    public static String createThumbnail(String hires) {
        return getImageMetaData(hires) + DatatypeConverter.printBase64Binary(createThumbnailBytes(hires));
    }

    public static String getImageMetaData(String hires) {
        return hires.substring(0, hires.indexOf(',')+1);
    }

    public static byte[] createThumbnailBytes(String hires) {
        String metadata = hires.substring(0, hires.indexOf(',')+1);
        String imagedata = hires.substring(hires.indexOf(',')+1);
        //convert the image data String to a byte[]
        byte[] dta = DatatypeConverter.parseBase64Binary(imagedata);

        try (InputStream in = new ByteArrayInputStream(dta)) {
            BufferedImage fullSize = ImageIO.read(in);

            double proportion = (double)fullSize.getHeight() / (double)THUMBNAIL_HEIGHT;
            int THUMBNAIL_WIDTH = (int)Math.round((double)fullSize.getWidth() / proportion);

            BufferedImage resized = new BufferedImage(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, BufferedImage.SCALE_REPLICATE);

            Graphics2D g2 = (Graphics2D) resized.getGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

            //draw fullsize image to resized image
            g2.drawImage(fullSize, 0, 0, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, null);

            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                // data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAoAAAAKAC
                String imageType = metadata.substring(11, hires.indexOf(';'));
                ImageIO.write( resized, imageType, baos );
                baos.flush();
                return baos.toByteArray();

            }


        } catch (IOException e) {
            System.err.println("error resizing screenshot" + e.toString());
            return null;
        }
    }
}
