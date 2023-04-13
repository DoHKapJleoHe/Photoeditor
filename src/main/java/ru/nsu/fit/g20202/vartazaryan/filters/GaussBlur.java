package ru.nsu.fit.g20202.vartazaryan.filters;

import lombok.Setter;

import java.awt.image.BufferedImage;

public class GaussBlur implements IFilter
{
    @Setter
    private double sigma;
    @Setter
    private int kernelSize = 7; //
    private double[][] kernel;

    private void createKernel()
    {
        kernel = new double[kernelSize][kernelSize];
        double sum = 0.0;

        for(int x = -kernelSize/2; x <= kernelSize/2; x++)
        {
            for(int y = - kernelSize/2; y <= kernelSize/2; y++)
            {
                kernel[x + kernelSize/2][y + kernelSize/2] = (1 / (2 * Math.PI * sigma * sigma)) * Math.exp(-(x*x + y*y)/(2*sigma*sigma));
                sum += kernel[x + kernelSize/2][y + kernelSize/2];
            }
        }

        // normalizing Gaussian kernel
        for(int x  = 0; x < kernelSize; x++)
        {
            for(int y = 0; y < kernelSize; y++)
            {
                kernel[x][y] /= sum;
            }
        }

        System.out.println(kernel);

        System.out.println("Kernel created!");
    }

    @Override
    public BufferedImage applyFilter(BufferedImage image) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        createKernel();

        for(int x = 0; x < image.getWidth(); x++)
        {
            for(int y = 0; y < image.getHeight(); y++)
            {
                double resR = 0, resG = 0, resB = 0;
                int color = 0;

                int a = 0, b = 0;
                for(int i = x - kernelSize/2; i <= x + kernelSize/2; i++)
                {
                    for(int j = y - kernelSize/2; j <= y + kernelSize/2; j++)
                    {
                        if(i > 0 && j > 0 && i < image.getWidth() && j < image.getHeight())
                        {
                            color = image.getRGB(i, j);
                        }
                        else
                        {
                            color = image.getRGB(x, y);
                        }

                        int red = (color >> 16) & 0xFF;
                        int green = (color >> 8) & 0xFF;
                        int blue = color & 0xFF;

                        resR += (double) red * kernel[a][b];
                        resG += (double) green * kernel[a][b];
                        resB += (double) blue * kernel[a][b];

                        b = (b+1)%kernel[0].length;
                    }
                    a = (a+1)%kernel[0].length;
                }

                //resR = Math.max(Math.min(resR, 255), 0);
                //resG = Math.max(Math.min(resG, 255), 0);
                //resB = Math.max(Math.min(resB, 255), 0);

                int res = 255 << 24 | (int)resR << 16 | (int)resG << 8 | (int)resB;
                newImage.setRGB(x, y, res);
            }
        }
        return newImage;
    }
}
