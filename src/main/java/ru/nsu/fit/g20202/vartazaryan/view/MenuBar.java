package ru.nsu.fit.g20202.vartazaryan.view;

import ru.nsu.fit.g20202.vartazaryan.ImagePane;
import ru.nsu.fit.g20202.vartazaryan.filters.IFilter;
import ru.nsu.fit.g20202.vartazaryan.instruments.FitToScreen;
import ru.nsu.fit.g20202.vartazaryan.instruments.Instrument;
import ru.nsu.fit.g20202.vartazaryan.options.GammaOptions;

import javax.swing.*;
import java.io.IOException;
import java.util.Map;

public class MenuBar extends JMenuBar
{
    private ImagePane imagePane;
    private JMenu file;
    private JMenu edit;
    private LoadImage imageLoader;
    private SaveImage imageSaver;
    private GammaOptions gammaOptions;
    private FitToScreen fitToScreen;
    private Map<String, IFilter> filters;

    public MenuBar(ImagePane image, LoadImage loadImage, SaveImage saveImage, Map<String, IFilter> filters, GammaOptions gammaOptions, FitToScreen fitToScreenInstrument)
    {
        this.filters = filters;
        fitToScreen = fitToScreenInstrument;
        imagePane = image;
        imageLoader = loadImage;
        imageSaver= saveImage;
        this.gammaOptions = gammaOptions;

        file = new JMenu("File");
        add(file);

        JMenuItem saveItem = new JMenuItem("Save");
        file.add(saveItem);

        JMenuItem loadItem = new JMenuItem("Load");
        file.add(loadItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        file.add(exitItem);

        edit = new JMenu("Edit");
        add(edit);

        JMenuItem gamma = new JMenuItem("Gamma");
        edit.add(gamma);

        ButtonGroup operatorsGroup = new ButtonGroup();
        JMenu contouring = new JMenu("Contouring");
        edit.add(contouring);
        JRadioButtonMenuItem sobel = new JRadioButtonMenuItem("Sobel");
        sobel.setSelected(true);
        JRadioButtonMenuItem robert = new JRadioButtonMenuItem("Robert");
        contouring.add(sobel);
        contouring.add(robert);
        operatorsGroup.add(sobel);
        operatorsGroup.add(robert);

        JMenu interpolationType = new JMenu("Interpolation");
        edit.add(interpolationType);
        ButtonGroup type = new ButtonGroup();
        JRadioButtonMenuItem bilinear = new JRadioButtonMenuItem("Bilinear");
        bilinear.setSelected(true);
        type.add(bilinear);
        interpolationType.add(bilinear);

        JRadioButtonMenuItem bicubic = new JRadioButtonMenuItem("Bicubic");
        type.add(bicubic);
        interpolationType.add(bicubic);

        JMenu about = new JMenu("About");
        add(about);

        JMenuItem aboutItem = new JMenuItem("About");
        about.add(aboutItem);

        aboutItem.addActionListener(e -> {
            JOptionPane.showConfirmDialog(
                    null,
                    "Author: Vartazaryan Eduard Araevich, FIT NSU",
                    "About",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
        });

        loadItem.addActionListener(e -> {
            try
            {
                imageLoader.loadImage();
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        saveItem.addActionListener(e -> {
            imageSaver.saveImage();
        });

        exitItem.addActionListener(e -> {System.exit(0);});

        gamma.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    gammaOptions,
                    "Gamma options",
                    JOptionPane.OK_CANCEL_OPTION
            );
        });

        bicubic.addActionListener(e -> {
            fitToScreen.setType(3);
        });

        bilinear.addActionListener(e -> {
            fitToScreen.setType(2);
        });
    }
}
