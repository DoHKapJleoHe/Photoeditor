package ru.nsu.fit.g20202.vartazaryan.options;

import ru.nsu.fit.g20202.vartazaryan.ImagePane;
import ru.nsu.fit.g20202.vartazaryan.instruments.RotateInstrument;

import javax.swing.*;
import java.awt.*;

public class RotateOption extends JPanel implements Option
{
    private RotateInstrument rotateInstrument;
    private JSlider rotationSlider;

    public RotateOption(RotateInstrument rotationInstrument)
    {
        this.rotateInstrument = rotationInstrument;
        setPreferredSize(new Dimension(300, 50));

        rotationSlider = new JSlider(-180, 180);
        rotationSlider.setValue(0);
        rotationSlider.setMajorTickSpacing(1);
        add(rotationSlider);

        SpinnerNumberModel rotateNumberModel = new SpinnerNumberModel(0, -180, 180, 1);
        JSpinner rotateSpinner = new JSpinner(rotateNumberModel);
        add(rotateSpinner);

        rotationSlider.addChangeListener(e -> {
            int deg = rotationSlider.getValue();
            rotateInstrument.setDegree(deg);
            rotateSpinner.setValue(deg);
        });

        rotateSpinner.addChangeListener(e -> {
            if((int)rotateSpinner.getValue() > 180)
                rotateSpinner.setValue(180);

            rotationSlider.setValue((Integer) rotateSpinner.getValue());
        });
    }
}
