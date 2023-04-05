package ru.nsu.fit.g20202.vartazaryan.options;

import ru.nsu.fit.g20202.vartazaryan.ImagePane;
import ru.nsu.fit.g20202.vartazaryan.filters.GammaCorrection;

import javax.swing.*;
import java.awt.*;

public class GammaOptions extends JPanel
{
    private JSlider gammaSlider;
    private JSpinner gammaSpinner;

    private GammaCorrection gammaCorrection;

    public GammaOptions(GammaCorrection gammaCorrection)
    {
        setPreferredSize(new Dimension(300, 100));

        this.gammaCorrection = gammaCorrection;

        gammaSlider = new JSlider(0, 20);
        gammaSlider.setMajorTickSpacing(1);
        gammaSlider.setValue(10);

        add(gammaSlider);

        SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(10, 0, 20, 1);
        gammaSpinner = new JSpinner(spinnerNumberModel);
        add(gammaSpinner);

        gammaSlider.addChangeListener(e -> {
            int gamma = gammaSlider.getValue();
            gammaSpinner.setValue(gamma);
            gammaCorrection.setGamma(gamma/10);
        });

        gammaSpinner.addChangeListener(e -> {
            if((int)gammaSpinner.getValue() > 20)
            {
                gammaSpinner.setValue(20);
            }
            gammaSlider.setValue((int)gammaSpinner.getValue());
        });
    }
}
