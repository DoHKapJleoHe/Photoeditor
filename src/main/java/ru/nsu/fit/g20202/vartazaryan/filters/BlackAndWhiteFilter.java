package ru.nsu.fit.g20202.vartazaryan.filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BlackAndWhiteFilter implements IFilter
{
    private static final double RED_COEFICIENT = 0.299;
    private static final double GREEN_COEFICIENT = 0.587;
    private static final double BLUE_COEFICIENT = 0.114;

    /**
     *  This method returns color in pixel in TYPE_INT_ARGB. Color is 32-bit number:
     *
     *  <br>bits 0 - 7 : blue chanel
     *  <br>bits 8 - 15 : green chanel
     *  <br>bits 16 - 23 : red chanel
     *  <br>bits 24 - 31 : alpha chanel (transparency)
     */
    @Override
    public BufferedImage applyFilter(BufferedImage curImage)
    {
        BufferedImage image = new BufferedImage(curImage.getWidth(), curImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        for(int x = 0; x < curImage.getWidth(); x++)
        {
            for(int y = 0; y < curImage.getHeight(); y++)
            {
                int curColor = curImage.getRGB(x, y);

                int red = (curColor >> 16) & 0xFF;
                int green = (curColor >> 8) & 0xFF;
                int blue = curColor & 0xFF;

                int brightness = (int) (red * RED_COEFICIENT + green * GREEN_COEFICIENT + blue * BLUE_COEFICIENT);

                int gray = (brightness << 16) | (brightness << 8) | brightness;
                image.setRGB(x, y, gray);
            }
        }

        return image;
    }
}
