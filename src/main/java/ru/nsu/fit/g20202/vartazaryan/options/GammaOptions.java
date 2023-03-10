package ru.nsu.fit.g20202.vartazaryan.options;

import javax.swing.*;
import java.awt.*;

public class GammaOptions extends JPanel
{
    private JSlider gammaSlider;
    private JSpinner gammaSpinner;

    public GammaOptions()
    {
        setPreferredSize(new Dimension(300, 100));

        gammaSlider = new JSlider(0, 20);
        gammaSlider.setMinimum(0);
        gammaSlider.setMaximum(20);
        gammaSlider.setMajorTickSpacing(1);
        gammaSlider.setValue(10);

        add(gammaSlider);

        SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(10, 0, 20, 1);
        gammaSpinner = new JSpinner(spinnerNumberModel);
        add(gammaSpinner);

        gammaSlider.addChangeListener(e -> {
            gammaSpinner.setValue(gammaSlider.getValue());
        });

        gammaSpinner.addChangeListener(e -> {
            if((int)gammaSpinner.getValue() > 20)
            {
                gammaSpinner.setValue(20);
            }
            gammaSlider.setValue((int)gammaSpinner.getValue());
        });
    }
    public int getGammaValue()
    {
        return gammaSlider.getValue();
    }
}
