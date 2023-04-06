package ru.nsu.fit.g20202.vartazaryan.options;

import ru.nsu.fit.g20202.vartazaryan.filters.FloydSteinbergDithering;

import javax.swing.*;
import java.awt.*;

public class FloydDitheringOptions extends JPanel implements Option
{
    private FloydSteinbergDithering filter;

    public FloydDitheringOptions(FloydSteinbergDithering filter)
    {
        this.filter = filter;
        setPreferredSize(new Dimension(290, 150));

        JLabel red = new JLabel("R");
        add(red);

        JSlider redPalette = new JSlider(2, 128);
        setSlider(redPalette);

        SpinnerNumberModel redSpinnerModel = new SpinnerNumberModel(2, 2, 128, 1);
        JSpinner redSpinner = new JSpinner(redSpinnerModel);
        add(redSpinner);

        JLabel green = new JLabel("G");
        add(green);

        JSlider greenPalette = new JSlider(2, 128);
        setSlider(greenPalette);

        SpinnerNumberModel greenSpinnerModel = new SpinnerNumberModel(2, 2, 128, 1);
        JSpinner greenSpinner = new JSpinner(greenSpinnerModel);
        add(greenSpinner);

        JLabel blue = new JLabel("B");
        add(blue);

        JSlider bluePalette = new JSlider(2, 128);
        setSlider(bluePalette);

        SpinnerNumberModel blueSpinnerModel = new SpinnerNumberModel(2, 2, 128, 1);
        JSpinner blueSpinner = new JSpinner(blueSpinnerModel);
        add(blueSpinner);

        redPalette.addChangeListener(e -> {
            int val = redPalette.getValue();
            redSpinner.setValue(val);
            filter.setRedQuantizationNum(val);
        });

        greenPalette.addChangeListener(e -> {
            int val = greenPalette.getValue();
            greenSpinner.setValue(val);
            filter.setGreenQuantizationNum(val);
        });

        bluePalette.addChangeListener(e -> {
            int val = bluePalette.getValue();
            blueSpinner.setValue(val);
            filter.setBlueQuantizationNum(val);
        });

        redSpinner.addChangeListener(e -> {
            if((int)redSpinner.getValue() > 128)
                redSpinner.setValue(128);
            if((int)redSpinner.getValue() < 2)
                redSpinner.setValue(2);

            int val = (int)redSpinner.getValue();
            redPalette.setValue(val);
        });

        greenSpinner.addChangeListener(e -> {
            if((int)greenSpinner.getValue() > 128)
                greenSpinner.setValue(128);
            if((int)greenSpinner.getValue() < 2)
                greenSpinner.setValue(2);

            int val = (int)greenSpinner.getValue();
            greenPalette.setValue(val);
        });

        blueSpinner.addChangeListener(e -> {
            if((int)blueSpinner.getValue() > 128)
                blueSpinner.setValue(128);
            if((int)blueSpinner.getValue() < 2)
                blueSpinner.setValue(2);

            int val = (int)blueSpinner.getValue();
            bluePalette.setValue(val);
        });
    }

    private void setSlider(JSlider slider)
    {
        slider.setValue(2);
        slider.setMajorTickSpacing(1);
        add(slider);
    }
}
