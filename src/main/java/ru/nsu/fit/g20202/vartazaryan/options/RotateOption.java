package ru.nsu.fit.g20202.vartazaryan.options;

import ru.nsu.fit.g20202.vartazaryan.ImagePane;

import javax.swing.*;
import java.awt.*;

public class RotateOption extends JPanel
{
    private ImagePane imagePane;
    private JSlider rotationSlider;

    public RotateOption(ImagePane image)
    {
        imagePane = image;
        setPreferredSize(new Dimension(300, 50));

        rotationSlider = new JSlider(-180, 180);
        rotationSlider.setValue(0);
        rotationSlider.setMajorTickSpacing(1);
        rotationSlider.setPaintTicks(true);

        add(rotationSlider);

        rotationSlider.addChangeListener(e -> {
            imagePane.rotateImage(rotationSlider.getValue());
        });
    }
}
