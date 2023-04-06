package ru.nsu.fit.g20202.vartazaryan.filters;

import lombok.Setter;

import java.awt.image.BufferedImage;

public class GammaCorrection implements IFilter
{
    @Setter
    private double gamma = 1.0;

    @Override
    public BufferedImage applyFilter(BufferedImage image)
    {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for(int x = 0; x < image.getWidth(); x++)
        {
            for (int y = 0; y < image.getHeight(); y++)
            {
                int curColor = image.getRGB(x, y);

                int red = (curColor >> 16) & 0xFF;
                int green = (curColor >> 8) & 0xFF;
                int blue = curColor & 0xFF;

                int correctedRed = (int)(255 * Math.pow((double)red / 255.0, gamma));
                int correctedGreen = (int)(255 * Math.pow((double)green / 255.0, gamma));
                int correctedBlue = (int)(255 * Math.pow((double)blue / 255.0, gamma));

                int color = 255 << 24 | (correctedRed << 16) | (correctedGreen << 8) | correctedBlue;

                //(int) (Math.pow((double) ColorUtils.parseColorRed(color) / MAX_COLOR, gamma) * MAX_COLOR);

                newImage.setRGB(x, y, color);
            }
        }

        return newImage;
    }
}
