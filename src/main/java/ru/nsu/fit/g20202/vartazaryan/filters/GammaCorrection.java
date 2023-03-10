package ru.nsu.fit.g20202.vartazaryan.filters;

import lombok.Setter;

import java.awt.image.BufferedImage;

public class GammaCorrection implements IFilter
{
    @Setter
    private double gamma = 1.0;

    @Override
    public BufferedImage applyFilter(BufferedImage curImage)
    {
        BufferedImage image = new BufferedImage(curImage.getWidth(), curImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        /*
        *   To make gamma correction i have to get each component of color of pixel and then
        *   apply gamma correction to each component.
        */

        for(int x = 0; x < curImage.getWidth(); x++)
        {
            for (int y = 0; y < curImage.getHeight(); y++)
            {
                int curColor = curImage.getRGB(x, y);

                int red = (curColor >> 16) & 0xFF;
                int green = (curColor >> 8) & 0xFF;
                int blue = curColor & 0xFF;

                /*
                *   While gamma correcting we use numbers in range [0, 1], so firstly
                *   I convert previous color to the [0, 1] range, then apply gamma correction and then
                *   convert back to [0, 255] range
                */
                int correctedRed = (int)(255 * Math.pow(red / 255.0, gamma));
                int correctedGreen = (int)(255 * Math.pow(green / 255.0, gamma));
                int correctedBlue = (int)(255 * Math.pow(blue / 255.0, gamma));

                int color = (correctedRed << 16) | (correctedGreen << 8) | correctedBlue;

                image.setRGB(x, y, color);
            }
        }

        return image;
    }
}
