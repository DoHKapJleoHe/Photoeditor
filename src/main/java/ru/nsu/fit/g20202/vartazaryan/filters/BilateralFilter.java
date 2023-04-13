package ru.nsu.fit.g20202.vartazaryan.filters;

import java.awt.image.BufferedImage;

public class BilateralFilter implements IFilter
{
    private int sigma = 2;

    private double gaussian(double x, double y)
    {
        return Math.exp(-(x * x + y * y) / (2 * sigma * sigma));
    }

    private double luminance(int color)
    {
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;
        double lum =  0.299 * red + 0.587 * green + 0.114 * blue;

        return lum;
    }


    // not done
    @Override
    public BufferedImage applyFilter(BufferedImage image)
    {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        int size = 4;
        int radius = size / 2;
        double[] weights = new double[size * size];
        double sumWeights;

        for(int x = 0; x < image.getWidth(); x++)
        {
            for (int y = 0; y < image.getHeight(); y++)
            {
                for (int kx = -radius; kx <= radius; kx++)
                {

                    int centreLum = (int) luminance(image.getRGB(x, y));

                    for (int ky = -radius; ky <= radius; ky++)
                    {
                        if(x+kx > 0 && y+ky > 0 && x+kx < image.getWidth() && y+ky < image.getHeight())
                        {
                            int px = Math.min(Math.max(x + kx, 0), image.getWidth() - 1);
                            int py = Math.min(Math.max(y + ky, 0), image.getHeight() - 1);

                            int color = image.getRGB(px, py);
                            double pixelLum = luminance(color);
                            double weight = gaussian(kx, ky) ;
                        }
                    }
                }
            }
        }
        return null;
    }
}
