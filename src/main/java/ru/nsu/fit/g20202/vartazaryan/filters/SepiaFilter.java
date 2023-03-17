package ru.nsu.fit.g20202.vartazaryan.filters;

import java.awt.image.BufferedImage;

public class SepiaFilter implements IFilter
{
    @Override
    public BufferedImage applyFilter(BufferedImage image) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for(int x = 0; x < image.getWidth(); x++)
        {
            for(int y = 0; y < image.getHeight(); y++)
            {
                int curColor = image.getRGB(x, y);

                int red = (curColor >> 16) & 0xFF;
                int green = (curColor >> 8) & 0xFF;
                int blue = curColor & 0xFF;

                int tr = (int) (0.393*red + 0.769*green + 0.189*blue);
                int tg = (int) (0.349*red + 0.686*green + 0.168*blue);
                int tb = (int) (0.272*red + 0.534*green + 0.131*blue);

                if(tr > 255){
                    red = 255;
                }else{
                    red = tr;
                }

                if(tg > 255){
                    green = 255;
                }else{
                    green = tg;
                }

                if(tb > 255){
                    blue = 255;
                }else{
                    blue = tb;
                }

                int p = (red<<16) | (green<<8) | blue;
                newImage.setRGB(x, y, p);
            }
        }

        return newImage;
    }
}
