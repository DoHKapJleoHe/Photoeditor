package ru.nsu.fit.g20202.vartazaryan.filters;

import java.awt.image.BufferedImage;

public class ContouringFilter implements IFilter
{
    private int treshold  = 20;

    private BufferedImage oldImage;

    /*Robert's contouring kernels*/
    private final double[][] Rkernel1 = {{1, 0}, {0, -1}};
    private final double[][] Rkernel2 = {{0, 1}, {-1, 0}};

    private int status = 0; // 0 - Sobel's operator 1 - Robert's operator

    private void sobel(BufferedImage newImage)
    {
        double[][] Skernel1 = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
        double[][] Skernel2 = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};

        for(int  x = 1; x < oldImage.getWidth() - 1; x++)
        {
            for(int y = 1; y < oldImage.getHeight() - 1; y++)
            {
                int gxRed = 0, gyRed = 0;
                int gxGreen = 0, gyGreen = 0;
                int gxBlue = 0, gyBlue = 0;

                for(int i = -1; i <=1; i++)
                {
                    for(int j = -1; j <= 1; j++)
                    {
                        int curColor = oldImage.getRGB(x + i, y + j);

                        int red = (curColor >> 16) & 0xFF;
                        int green = (curColor >> 8) & 0xFF;
                        int blue = curColor & 0xFF;

                        int grayRed = (int) (0.299 * red + 0.587 * 0 + 0.114 * 0);
                        int grayGreen = (int) (0.299 * 0 + 0.587 * green + 0.114 * 0);
                        int grayBlue = (int) (0.299 * 0 + 0.587 * 0 + 0.114 * blue);

                        gxRed += Skernel1[i + 1][j + 1] * grayRed;
                        gyRed += Skernel2[i + 1][j + 1] * grayRed;
                        gxGreen += Skernel1[i + 1][j + 1] * grayGreen;
                        gyGreen += Skernel2[i + 1][j + 1] * grayGreen;
                        gxBlue += Skernel1[i + 1][j + 1] * grayBlue;
                        gyBlue += Skernel2[i + 1][j + 1] * grayBlue;
                    }
                }

                int magnitudeRed = (int) Math.sqrt(gxRed * gxRed + gyRed * gyRed);
                int magnitudeGreen = (int) Math.sqrt(gxGreen * gxGreen + gyGreen * gyGreen);
                int magnitudeBlue = (int) Math.sqrt(gxBlue * gxBlue + gyBlue * gyBlue);

                int intensity = (magnitudeRed + magnitudeGreen + magnitudeBlue) / 3;
                int gray = (intensity << 16) + (intensity << 8) + intensity;

                newImage.setRGB(x, y, gray);
            }
        }
    }

    private void robert()
    {

    }


    @Override
    public BufferedImage applyFilter(BufferedImage image) {
        this.oldImage = image;

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        if (status == 0)
        {
            sobel(newImage);
        }

        return newImage;
    }
}
