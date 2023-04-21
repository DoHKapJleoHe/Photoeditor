package ru.nsu.fit.g20202.vartazaryan.options;

import ru.nsu.fit.g20202.vartazaryan.filters.ContouringFilter;
import ru.nsu.fit.g20202.vartazaryan.filters.IFilter;

import javax.swing.*;
import java.util.Map;

public class ContouringOptions extends JPanel implements Option
{
    ContouringFilter filter;

    public ContouringOptions(ContouringFilter filter)
    {
        this.filter = filter;

        ButtonGroup operators = new ButtonGroup();
        JRadioButton sobel = new JRadioButton("Sobel");
        sobel.setSelected(true);
        JRadioButton robert = new JRadioButton("Robert");
        add(sobel);
        add(robert);
        operators.add(sobel);
        operators.add(robert);

        sobel.addActionListener(e -> {
            filter.setStatus(0);
        });
        robert.addActionListener(e -> {
            filter.setStatus(1);
        });

        JSlider thresholdSlider = new JSlider(0, 100);
        thresholdSlider.setMajorTickSpacing(1);
        thresholdSlider.setValue(20);
        add(thresholdSlider);

        SpinnerNumberModel thresholdSpinnerModel = new SpinnerNumberModel(20,0, 100, 1);
        JSpinner thresholdSpinner = new JSpinner(thresholdSpinnerModel);
        add(thresholdSpinner);

        thresholdSlider.addChangeListener(e -> {
            int threshold = thresholdSlider.getValue();
            thresholdSpinner.setValue(threshold);
            filter.setThreshold(threshold);
        });

        thresholdSpinner.addChangeListener(e -> {
            if((int)thresholdSpinner.getValue() > 100)
            {
                thresholdSpinner.setValue(100);
            }

            int threshold = (int) thresholdSpinner.getValue();
            thresholdSlider.setValue(threshold);
        });
    }
}
