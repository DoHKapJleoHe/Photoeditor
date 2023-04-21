package ru.nsu.fit.g20202.vartazaryan.instruments;

import lombok.Setter;

import java.awt.*;
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

        int newH = (int) (originalImage.getWidth()*sin + originalImage.getHeight()*cos);
        int newW = (int) (originalImage.getWidth()*cos + originalImage.getHeight()*sin);

        BufferedImage newImage = new BufferedImage(newW, newH, originalImage.getType());
        Graphics2D g2d = newImage.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, newW, newH);

        for(int x = 0; x < newW; x++)
        {
            for(int y = 0; y < newH; y++)
            {
                int newX = (int) ((x - newW/2)*cos - (y - newH/2)*sin) + originalImage.getWidth() / 2;
                int newY = (int) ((x - newW/2)*sin + (y - newH/2)*cos) + originalImage.getHeight() / 2;

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
