package ru.nsu.fit.g20202.vartazaryan.filters;

import java.awt.image.BufferedImage;

public class SharpnessFilter implements IFilter
{
    private int[][] kernel = {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}};

    @Override
    public BufferedImage applyFilter(BufferedImage image)
    {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for(int x = 0; x < image.getWidth(); x++)
        {
            for(int y = 0; y < image.getHeight(); y++)
            {
                int resR = 0, resG = 0, resB = 0;

                for(int i = -1; i <= 1; i++)
                {
                    for(int j = -1; j <= 1; j++)
                    {
                        int curColor = 0;

                        if(x+i > 0 && y+j > 0 && x+i < image.getWidth() && y+j < image.getHeight())
                        {
                            curColor = image.getRGB(x + i, y + j);
                        }
                        else
                        {
                            curColor = image.getRGB(x, y);
                        }

                        int red = (curColor >> 16) & 0xFF;
                        int green = (curColor >> 8) & 0xFF;
                        int blue = curColor & 0xFF;

                        resR += red * kernel[i + 1][j + 1];
                        resG += green * kernel[i + 1][j + 1];
                        resB += blue * kernel[i + 1][j + 1];
                    }
                }

                resR = Math.max(Math.min(resR, 255), 0);
                resG = Math.max(Math.min(resG, 255), 0);
                resB = Math.max(Math.min(resB, 255), 0);

                int res = 255 << 24 | resR << 16 | resG << 8 | resB;
                newImage.setRGB(x, y, res);
            }
        }

        return newImage;
    }
}
