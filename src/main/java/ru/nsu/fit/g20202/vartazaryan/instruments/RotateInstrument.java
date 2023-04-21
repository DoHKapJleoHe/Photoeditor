package ru.nsu.fit.g20202.vartazaryan.instruments;

import lombok.Setter;

import java.awt.image.BufferedImage;

public class RotateInstrument implements Instrument
{
    private int degree = 20;

    public void setDegree(int deg)
    {
        this.degree = deg;
    }

    @Override
    public BufferedImage apply(BufferedImage originalImage)
    {
        System.out.println(degree);
        double angle = Math.toRadians(degree);
        double sin = Math.abs(Math.sin(angle));
        double cos = Math.abs(Math.cos(angle));

        int newSize = originalImage.getWidth()*originalImage.getWidth() + originalImage.getHeight()*originalImage.getHeight();
        newSize = (int) Math.sqrt(newSize);

        BufferedImage newImage = new BufferedImage(newSize, newSize, originalImage.getType());

        for(int x = 0; x < originalImage.getWidth(); x++)
        {
            for(int y = 0; y < originalImage.getHeight(); y++)
            {
                int newX = (int) ((x - originalImage.getWidth()/2)*cos - (y - originalImage.getHeight()/2)*sin) + originalImage.getWidth() / 2;
                int newY = (int) ((x - originalImage.getWidth()/2)*sin + (y - originalImage.getHeight()/2)*cos) + originalImage.getHeight() / 2;

                int color = 0;
                if(newX > 0 && newY > 0 && newX < originalImage.getWidth() && newY < originalImage.getHeight())
                    color = originalImage.getRGB(newX, newY);
                else
                    color = -1;

                newImage.setRGB(x, y, color);
            }
        }

        return newImage;
    }
}
