package ru.nsu.fit.g20202.vartazaryan.filters;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class WaterColorFilter implements IFilter
{
    private int[][] kernel = {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}};

    private int median(BufferedImage image, int curX, int curY)
    {
        int[] redArr = new int[25];
        int[] greenArr = new int[25];
        int[] blueArr = new int[25];

        int i = 0;
        for(int x = curX-2; x <= curX+2; x++)
        {
            for(int y = curY-2; y <= curY+2; y++)
            {
                if(x > 0 && y > 0 && x < image.getWidth() && y < image.getHeight())
                {
                    int color = image.getRGB(x, y);
                    int red = (color >> 16) & 0xFF;
                    int green = (color >> 8) & 0xFF;
                    int blue = color & 0xFF;

                    redArr[i] = red;
                    greenArr[i] = green;
                    blueArr[i] = blue;

                    i++;
                }
            }
        }

        Arrays.sort(redArr);
        Arrays.sort(greenArr);
        Arrays.sort(blueArr);

        int medianRed = redArr[12];
        int medianGreen = greenArr[12];
        int medianBlue = blueArr[12];
        int res = 255 << 24 | medianRed << 16 | medianGreen << 8 | medianBlue;

        return res;
    }

    @Override
    public BufferedImage applyFilter(BufferedImage image)
    {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage(image, null, 0, 0);

        for(int x = 0; x < image.getWidth(); x++)
        {
            for (int y = 0; y < image.getHeight(); y++)
            {
                newImage.setRGB(x, y, median(newImage, x, y));
            }
        }

        BufferedImage newImage1 = new BufferedImage(newImage.getWidth(), newImage.getHeight(), newImage.getType());
        g2d = newImage1.createGraphics();
        g2d.drawImage(newImage, null, 0, 0);

        for(int x = 0; x < image.getWidth(); x++)
        {
            for (int y = 0; y < image.getHeight(); y++)
            {
                int resR = 0, resG = 0, resB = 0;
                for(int i = -1; i <= 1; i++)
                {
                    for(int j = -1; j <= 1; j++)
                    {
                        int curColor = 0;

                        if(x+i > 0 && y+j > 0 && x+i < image.getWidth() && y+j < image.getHeight())
                        {
                            curColor = newImage.getRGB(x + i, y + j);
                        }
                        else
                        {
                            curColor = newImage.getRGB(x, y);
                        }
                        int red = (curColor >> 16) & 0xFF;
                        int green = (curColor >> 8) & 0xFF;
                        int blue = curColor & 0xFF;

                        resR += red * kernel[i+1][j+1];
                        resG += green * kernel[i+1][j+1];
                        resB += blue * kernel[i+1][j+1];
                    }
                }

                resR = Math.max(Math.min(resR, 255), 0);
                resG = Math.max(Math.min(resG, 255), 0);
                resB = Math.max(Math.min(resB, 255), 0);

                int res = 255 << 24 | resR << 16 | resG << 8 | resB;
                newImage1.setRGB(x, y, res);
            }
        }

        return newImage1;
    }
}
