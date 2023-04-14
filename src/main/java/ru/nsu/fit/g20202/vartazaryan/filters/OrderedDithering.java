package ru.nsu.fit.g20202.vartazaryan.filters;

import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OrderedDithering implements IFilter
{
    @Setter
    private int redQuantizationNum = 4;
    @Setter
    private int greenQuantizationNum = 4;
    @Setter
    private int blueQuantizationNum = 4;

    private int[] redPalette;
    private int[] greenPalette;
    private int[] bluePalette;
    private double[][] thresholdMap;
    private int matrixSize = 4;

    private double[][] firstKernel = {{0, 2}, {3, 1}};

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

    private void fillPalette(int[] palette, int interval, int quantNum)
    {
        int color = 0;
        for(int i = 0; i < quantNum; i++)
        {
            palette[i] = color;
            color += interval;
            if(color > 255)
                color = 255;
        }
    }

    private void createPalette()
    {
        redPalette = new int[redQuantizationNum];
        greenPalette = new int[greenQuantizationNum];
        bluePalette = new int[blueQuantizationNum];

        int interval = (int) (256 / (redQuantizationNum - 1));
        fillPalette(redPalette, interval, redQuantizationNum);

        interval = (int) (256 / (greenQuantizationNum - 1));
        fillPalette(greenPalette, interval, greenQuantizationNum);

        interval = (int) (256 / (blueQuantizationNum - 1));
        fillPalette(bluePalette, interval, blueQuantizationNum);
    }

    private double[][] createThresholdMap(int size)
    {
        double[][] oldKernel;
        if(size == 2)
            return firstKernel;

        oldKernel = createThresholdMap(size/2);
        int length = oldKernel[0].length;
        double[][] newMatrix = new double[2*length][2*length];

        for(int x = 0; x < length; x++)
        {
            for(int y = 0; y < length; y++)
            {
                newMatrix[x][y] = 4 * oldKernel[x % length][y % length];
            }
        }

        for(int x = length; x < 2*length; x++)
        {
            for(int y = 0; y < length; y++)
            {
                newMatrix[x][y] = 4 * oldKernel[x % length][y % length] + 2;
            }
        }

        for(int x = 0; x < length; x++)
        {
            for(int y = length; y < 2*length; y++)
            {
                newMatrix[x][y] = 4 * oldKernel[x % length][y % length] + 3;
            }

        }

        for(int x = length; x < 2*length; x++)
        {
            for(int y = length; y < 2*length; y++)
            {
                newMatrix[x][y] = 4 * oldKernel[x % length][y % length] + 1;
            }
        }

        return newMatrix;
    }

    private void normalize()
    {
        for (int x = 0; x < matrixSize; x++)
        {
            for(int y = 0; y < matrixSize; y++)
            {
                thresholdMap[x][y] = (thresholdMap[x][y] + 1) / (matrixSize*matrixSize);
            }
        }
    }

    private int findMatrixSize()
    {
        double imgSizeR = (double) 256 / redQuantizationNum;
        double imgSizeG = (double) 256 / greenQuantizationNum;
        double imgSizeB = (double) 256 / blueQuantizationNum;
        double maxSize = Math.max(imgSizeR, imgSizeG);
        maxSize = Math.max(maxSize, imgSizeB);

        int[] sizes = {2, 4, 8, 16};
        int finalSize = 0;
        for(int i : sizes)
        {
            if (maxSize <= i*i)
            {
                finalSize = i;
                break;
            }
        }
        return finalSize;
    }

    @Override
    public BufferedImage applyFilter(BufferedImage image)
    {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage(image, null, 0, 0);
        matrixSize = findMatrixSize();
        createPalette();
        thresholdMap = createThresholdMap(matrixSize);
        normalize();

        double stepR = (double) 256 / redPalette.length;
        double stepG = (double) 256 / greenPalette.length;
        double stepB = (double) 256 / bluePalette.length;

        int red = 0, green = 0, blue = 0;
        int color;
        int newR = 0, newG = 0, newB = 0;
        for(int x = 0 ; x < image.getWidth(); x++)
        {
            for(int y = 0; y < image.getHeight(); y++)
            {
                color = image.getRGB(x, y);
                red = (color >> 16) & 0xFF;
                green = (color >> 8) & 0xFF;
                blue = color & 0xFF;

                newR = findColorInPalette(redPalette, (int) (red + stepR*thresholdMap[y % matrixSize][x % matrixSize]));
                newG = findColorInPalette(greenPalette, (int) (green + stepG*thresholdMap[y % matrixSize][x % matrixSize]));
                newB = findColorInPalette(bluePalette, (int) (blue + stepB*thresholdMap[y % matrixSize][x % matrixSize]));

                int res = 255 << 24 | newR << 16 | newG << 8 | newB;
                newImage.setRGB(x, y, res);
            }
        }

        return newImage;
    }
}
