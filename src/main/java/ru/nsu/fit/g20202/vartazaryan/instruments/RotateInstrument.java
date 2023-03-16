package ru.nsu.fit.g20202.vartazaryan.instruments;

import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RotateInstrument implements Instrument
{
    @Setter
    private int degree;

    @Override
    public BufferedImage apply(BufferedImage originalImage)
    {
        double sin = Math.abs(Math.sin(Math.toRadians(degree)));
        double cos = Math.abs(Math.cos(Math.toRadians(degree)));

        // using rotation matrix
        int newWidth = (int) Math.floor((double)originalImage.getWidth() * cos + (double)originalImage.getHeight() * sin);
        int newHeight = (int) Math.floor((double)originalImage.getHeight() * cos + (double)originalImage.getWidth() * sin);

        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = rotatedImage.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, newWidth, newHeight);
        g2d.translate(newWidth/2, newHeight/2);
        g2d.rotate(Math.toRadians(degree));
        g2d.translate(-originalImage.getWidth()/2, -originalImage.getHeight()/2);
        g2d.drawImage(originalImage, 0, 0, null);

        return rotatedImage;
    }
}
