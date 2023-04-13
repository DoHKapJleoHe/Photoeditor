package ru.nsu.fit.g20202.vartazaryan.filters;

import java.awt.image.BufferedImage;

public class GlitchFilter implements IFilter
{
    @Override
    public BufferedImage applyFilter(BufferedImage image)
    {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for(int x = 0; x < image.getWidth(); x++)
        {
            for (int y = 0; y < image.getHeight(); y++)
            {
                int color = image.getRGB(x, y);
                int rand = (int) (Math.random() * 100);
                if(rand < 10)
                {
                    int red = (color >> 16) & 0xFF;
                    int green = (color >> 8) & 0xFF;
                    int blue = color & 0xFF;
                    int newColor = 255 << 24 | green << 16 | blue << 8 | red;
                    newImage.setRGB(x, y, newColor);
                }
                else
                    newImage.setRGB(x, y, color);
            }
        }

        return newImage;
    }
}
