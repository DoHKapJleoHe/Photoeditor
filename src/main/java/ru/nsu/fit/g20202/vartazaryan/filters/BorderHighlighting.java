package ru.nsu.fit.g20202.vartazaryan.filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BorderHighlighting implements IFilter
{
    private int treshold;

    private int[][] Rkernel1 = {{1, 0}, {0, -1}};
    private int[][] Rkernel2 = {{0, 1}, {-1, 0}};



    @Override
    public BufferedImage applyFilter(BufferedImage image)
    {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for(int x = 0; x < width - 1; x++)
        {
            for(int y = 0; y < height - 1; y++)
            {
                int gx = 0;
                int gy = 0;


            }
        }

        return null;
    }
}
