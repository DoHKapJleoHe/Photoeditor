package ru.nsu.fit.g20202.vartazaryan.instruments;

import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Zoom implements Instrument
{
    @Setter
    private int zoomCoef = 100;

    @Override
    public BufferedImage apply(BufferedImage image)
    {
        int newWidth = (image.getWidth() * zoomCoef) / 100;
        int newHeight = (image.getHeight() * zoomCoef) / 100;

        //System.out.println("New width = "+newWidth+" "+"New height = "+" "+newHeight);

        BufferedImage newImage = new BufferedImage(newWidth, newHeight, image.getType());
        Graphics2D g = newImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(image, 0, 0, newWidth, newHeight, 0, 0, image.getWidth(), image.getHeight(), null);

        return newImage;
    }
}
