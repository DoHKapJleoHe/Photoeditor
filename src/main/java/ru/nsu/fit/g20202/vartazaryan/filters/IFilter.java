package ru.nsu.fit.g20202.vartazaryan.filters;

import java.awt.image.BufferedImage;

public interface IFilter
{
    default BufferedImage applyFilter(BufferedImage image)
    {
        return null;
    }
}
