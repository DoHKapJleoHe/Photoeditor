package ru.nsu.fit.g20202.vartazaryan.options;

import ru.nsu.fit.g20202.vartazaryan.filters.GaussBlur;

import javax.swing.*;
import java.awt.*;

public class GaussBlurOptions extends JPanel implements Option
{
    private GaussBlur gaussBlur;

    public GaussBlurOptions(GaussBlur gaussBlur)
    {
        this.gaussBlur = gaussBlur;
        setPreferredSize(new Dimension(340, 200));

        JLabel label = new JLabel("Sigma/10");
        add(label);
        JSlider sigmaSlider = new JSlider(1, 50, 1);
        sigmaSlider.setMajorTickSpacing(1);
        add(sigmaSlider);

        SpinnerNumberModel sigmaNumberModel = new SpinnerNumberModel(1, 1, 50, 1);
        JSpinner sigmaSpinner = new JSpinner(sigmaNumberModel);
        add(sigmaSpinner);

        JLabel label1 = new JLabel("Matrix Size");
        add(label1);
        JSlider matrixSizeSlider = new JSlider(3, 11, 3);
        matrixSizeSlider.setMajorTickSpacing(2);
        add(matrixSizeSlider);

        SpinnerNumberModel matrixNumberModel = new SpinnerNumberModel(3, 3, 11, 2);
        JSpinner matrixSpinner = new JSpinner(matrixNumberModel);
        add(matrixSpinner);

        matrixSizeSlider.addChangeListener(e -> {
            int val = matrixSizeSlider.getValue();
            matrixSpinner.setValue(val);
            gaussBlur.setKernelSize(val);
        });

        matrixSpinner.addChangeListener(e -> {
            if((int)matrixSpinner.getValue() > 11)
                sigmaSpinner.setValue(11);

            matrixSizeSlider.setValue((Integer) matrixSpinner.getValue());
        });

        sigmaSlider.addChangeListener(e -> {
            int val = sigmaSlider.getValue();
            sigmaSpinner.setValue(val);
            gaussBlur.setSigma((double)val/10);
        });

        sigmaSpinner.addChangeListener(e -> {
            if((int)sigmaSpinner.getValue() > 50)
                sigmaSpinner.setValue(50);

            sigmaSlider.setValue((int)sigmaSpinner.getValue());
        });
    }
}
