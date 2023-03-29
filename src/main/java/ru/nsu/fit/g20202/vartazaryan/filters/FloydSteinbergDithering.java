package ru.nsu.fit.g20202.vartazaryan.filters;

import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FloydSteinbergDithering implements IFilter
{
    private BufferedImage oldImage;
    @Setter
    private int redQuantizationNum = 8;
    @Setter
    private int greenQuantizationNum = 8;
    @Setter
    private int blueQuantizationNum = 8;

    private int[] rPalette;
    private int[] gPalette;
    private int[] bPalette;

    private void fillPalette(int[] palette, int interval, int quantNum)
    {
        int color = 0;
        for(int i = 0; i < quantNum; i++)
        {
            palette[i] = color;
            color += interval;
        }
    }

    private void createPalette()
    {
        rPalette = new int[redQuantizationNum];
        gPalette = new int[greenQuantizationNum];
        bPalette = new int[blueQuantizationNum];

        /* This is the step between colors in palette.
        *  Interval depends on quantization number.
        *  Quantization number is the number of colors in palette.
        */
        int interval = (int) (256 / redQuantizationNum);
        fillPalette(rPalette, interval, redQuantizationNum);

        interval = (int) (256 / greenQuantizationNum);
        fillPalette(gPalette, interval, greenQuantizationNum);

        interval = (int) (256 / blueQuantizationNum);
        fillPalette(bPalette, interval, blueQuantizationNum);
    }

    private int findColorInPalette(int[] palette, int color)
    {
        int min = 1000;
        int pos = 0;
        for(int i = 0; i < palette.length; i++)
        {
            if(Math.abs(palette[i] - color) < min)
            {
                min = Math.abs(palette[i] - color);
                pos = i;
            }
        }

        return palette[pos];
    }

    private int neighboursColor(int color, int errR, int errG, int errB, double coef)
    {
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        int newR = (int) (red + errR * coef);
        int newG = (int) (green + errG * coef);
        int newB = (int) (blue + errB * coef);

        newR = Math.max(Math.min(newR, 255), 0);
        newG = Math.max(Math.min(newG, 255), 0);
        newB = Math.max(Math.min(newB, 255), 0);

        /*int rgb = 0xFF;
        rgb = (rgb << 8) | newR;
        rgb = (rgb << 8) | newG;
        rgb = (rgb << 8) | newB;*/

        return new Color(newR, newG, newB).getRGB();
    }

    @Override
    public BufferedImage applyFilter(BufferedImage image)
    {
        var cm = image.getColorModel();
        var raster = image.getRaster();
        BufferedImage newImage = new BufferedImage(cm, raster, cm.isAlphaPremultiplied(), null);

        createPalette();

        for(int x = 0; x < newImage.getWidth(); x++)
        {
            for(int y = 0; y < newImage.getHeight(); y++)
            {
                int curColor = newImage.getRGB(x, y);
                int red = (curColor >> 16) & 0xFF;
                int green = (curColor >> 8) & 0xFF;
                int blue = curColor & 0xFF;

                int newR = findColorInPalette(rPalette, red);
                int newG = findColorInPalette(gPalette, green);
                int newB = findColorInPalette(bPalette, blue);

                int newColor = 0xFF;
                newColor = (newColor << 8) | newR;
                newColor = (newColor << 8) | newG;
                newColor = (newColor << 8) | newB;

                newImage.setRGB(x, y, newColor);

                int err = Math.abs(curColor - newColor);

                /*int errR = red - newR;
                int errG = green - newG;
                int errB = blue - newB;*/

                int neighbour;
                int color;

                if(x < newImage.getWidth() - 1)
                {
                    neighbour = newImage.getRGB(x + 1, y);

                    //color = neighboursColor(neighbour, errR, errG, errB, 7.0/16);
                    newImage.setRGB(x + 1, y, (int) (neighbour + err*7.0/16));
                }
                if(x > 0 && y < newImage.getHeight() - 1)
                {
                    neighbour = newImage.getRGB(x - 1, y + 1);

                    //color = neighboursColor(neighbour, errR, errG, errB, 3.0/16);
                    newImage.setRGB(x - 1, y + 1, (int) (neighbour + err*3.0/16));
                }
                if(y < newImage.getHeight() - 1)
                {
                    neighbour = newImage.getRGB(x, y + 1);

                    //color = neighboursColor(neighbour, errR, errG, errB, 5.0/16);
                    newImage.setRGB(x, y + 1, (int) (neighbour + err*5.0/16));
                }
                if(x < newImage.getWidth() - 1 && y < newImage.getHeight() - 1)
                {
                    neighbour = newImage.getRGB(x + 1, y + 1);

                    //color = neighboursColor(neighbour, errR, errG, errB, 1.0/16);
                    newImage.setRGB(x + 1, y + 1, (int) (neighbour + err*1.0/16));
                }
            }
        }

        return newImage;
    }

}
