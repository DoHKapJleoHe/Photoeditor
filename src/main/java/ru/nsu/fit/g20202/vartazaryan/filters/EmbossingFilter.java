package ru.nsu.fit.g20202.vartazaryan.filters;

import java.awt.image.BufferedImage;

public class EmbossingFilter implements IFilter
{
    private final int[][] embossingMatrix = {{0, 1, 0}, {-1, 0, 1}, {0, -1, 0}};

    @Override
    public BufferedImage applyFilter(BufferedImage oldImage) //78648
    {
        BufferedImage newImage = new BufferedImage(oldImage.getWidth(), oldImage.getHeight(), oldImage.getType());

        for(int x = 1; x < oldImage.getWidth() - 1; x++)
        {
            for (int y = 1; y < oldImage.getHeight() - 1; y++)
            {
                int sum = 0;
                int resR = 0, resG = 0, resB = 0;

                for(int i = -1; i <= 1; i++)
                {
                    for(int j = -1; j <= 1; j++)
                    {
                        int curColor = oldImage.getRGB(x + i, y + j);
                        int red = (curColor >> 16) & 0xFF;
                        int green = (curColor >> 8) & 0xFF;
                        int blue = curColor & 0xFF;

                        resR += red * embossingMatrix[i+1][j+1];
                        resG += green * embossingMatrix[i+1][j+1];
                        resB += blue * embossingMatrix[i+1][j+1];
                    }
                }

                // making gray each color
                resR += 128;
                resG += 128;
                resB += 128;

                resR = Math.max(Math.min(resR, 255), 0);
                resG = Math.max(Math.min(resG, 255), 0);
                resB = Math.max(Math.min(resB, 255), 0);

                sum = 255 << 24 |  resR << 16 | resG << 8 | resB;


                newImage.setRGB(x, y, sum);
            }
        }

        return newImage;
    }
}
