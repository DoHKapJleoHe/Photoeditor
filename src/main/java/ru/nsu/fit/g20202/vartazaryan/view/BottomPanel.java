package ru.nsu.fit.g20202.vartazaryan.view;

import ru.nsu.fit.g20202.vartazaryan.ImagePane;
import ru.nsu.fit.g20202.vartazaryan.instruments.FitToScreen;
import ru.nsu.fit.g20202.vartazaryan.instruments.Instrument;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class BottomPanel extends JPanel
{
    private Map<String, Instrument> instruments;
    private ImagePane imagePane;
    private JButton fit;
    private JButton full;

    public BottomPanel(ImagePane image, Map<String, Instrument> instruments)
    {
        this.instruments = instruments;
        this.imagePane = image;

        setLayout(new FlowLayout(FlowLayout.LEFT));

        SpinnerNumberModel scaleModel = new SpinnerNumberModel(100, 0, 200, 1);
        JSpinner scaleSpinner = new JSpinner(scaleModel);
        add(scaleSpinner);

        fit = new JButton("Fit");
        add(fit);

        full = new JButton("Full Size");
        add(full);

        scaleSpinner.addChangeListener(e -> {
            if(imagePane.getOriginalImage() != null)
            {

            }
            else
            {
                JOptionPane.showMessageDialog(this, "Image has not been selected!", "Error", JOptionPane.QUESTION_MESSAGE);
            }
        });

        fit.addActionListener(e -> {
            FitToScreen fitToScreen = (FitToScreen) instruments.get("FitToScreenInstrument");
            imagePane.fitImage();
        });

        full.addActionListener(e -> {
            imagePane.backToFullSize();
        });
    }
}
