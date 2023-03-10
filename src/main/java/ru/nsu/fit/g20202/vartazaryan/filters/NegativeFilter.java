package ru.nsu.fit.g20202.vartazaryan.filters;

import java.awt.image.BufferedImage;

public class NegativeFilter implements IFilter
{
    @Override
    public BufferedImage applyFilter(BufferedImage curImage)
    {
        BufferedImage image = new BufferedImage(curImage.getWidth(), curImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        for(int x = 0; x < curImage.getWidth(); x++)
        {
            for (int y = 0; y < curImage.getHeight(); y++)
            {
                int curColor = curImage.getRGB(x, y);

                int red = (curColor >> 16) & 0xFF;
                int green = (curColor >> 8) & 0xFF;
                int blue = curColor & 0xFF;

                int negatedRed = 255 - red;
                int negatedGreen = 255 - green;
                int negatedBlue = 255 - blue;

                int negative = (negatedRed << 16) | (negatedGreen << 8) | negatedBlue;

                image.setRGB(x, y, negative);
            }
        }

        return image;
    }
}
