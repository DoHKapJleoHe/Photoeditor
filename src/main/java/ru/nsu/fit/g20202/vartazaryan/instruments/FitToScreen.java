package ru.nsu.fit.g20202.vartazaryan.instruments;

import lombok.Setter;
import ru.nsu.fit.g20202.vartazaryan.MainFrame;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class FitToScreen implements Instrument
{
    @Setter
    private int type = 2;
    @Setter
    private int width;
    @Setter
    private int height;

    @Override
    public BufferedImage apply(BufferedImage image)
    {
        System.out.println("Width= "+width+" Height= "+height);
        double coef = Math.min(width / image.getWidth(), height / image.getHeight());

        BufferedImage newImage = new BufferedImage((int)(image.getWidth() * coef), (int)(image.getHeight() * coef), image.getType());
        AffineTransform fit = AffineTransform.getScaleInstance(coef, coef);
        AffineTransformOp fitOp = new AffineTransformOp(fit, AffineTransformOp.TYPE_BILINEAR);
        fitOp.filter(image, newImage);

        return newImage;
    }
}
