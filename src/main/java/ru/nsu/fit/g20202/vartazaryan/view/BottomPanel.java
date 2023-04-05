package ru.nsu.fit.g20202.vartazaryan.view;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
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

        scaleSpinner.addChangeListener(e -> {
            if(imagePane.getOriginalImage() != null)
            {
                //imagePane.zoomImage((Integer) scaleSpinner.getValue());
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Image has not been selected!", "Error", JOptionPane.QUESTION_MESSAGE);
            }
        });

        fit.addActionListener(e -> {
            FitToScreen fitToScreen = (FitToScreen) instruments.get("FitToScreenInstrument");
            fitToScreen.setWidth(imagePane.getWidth());
            fitToScreen.setHeight(imagePane.getHeight());
            imagePane.fitImage();
        });
    }
}
