package ru.nsu.fit.g20202.vartazaryan.view;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import ru.nsu.fit.g20202.vartazaryan.ImagePane;

import javax.swing.*;
import java.awt.*;

public class BottomPanel extends JPanel
{
    ImagePane imagePane;

    public BottomPanel(ImagePane image)
    {
        imagePane = image;

        setLayout(new FlowLayout(FlowLayout.LEFT));

        SpinnerNumberModel scaleModel = new SpinnerNumberModel(100, 0, 200, 1);
        JSpinner scaleSpinner = new JSpinner(scaleModel);
        add(scaleSpinner);

        scaleSpinner.addChangeListener(e -> {
            if(imagePane.getOriginalImage() != null)
            {
                imagePane.zoomImage((Integer) scaleSpinner.getValue());
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Image has not been selected!", "Error", JOptionPane.QUESTION_MESSAGE);
            }
        });
    }
}
