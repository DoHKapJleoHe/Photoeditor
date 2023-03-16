package ru.nsu.fit.g20202.vartazaryan.view;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import ru.nsu.fit.g20202.vartazaryan.ImagePane;
import ru.nsu.fit.g20202.vartazaryan.options.GammaOptions;

import javax.swing.*;
import java.io.IOException;

public class MenuBar extends JMenuBar
{
    private ImagePane imagePane;
    private JMenu file;
    private JMenu edit;
    private LoadImage imageLoader;
    private SaveImage imageSaver;
    private GammaOptions gammaOptions;

    public MenuBar(ImagePane image, LoadImage loadImage, SaveImage saveImage)
    {
        imagePane = image;
        imageLoader = loadImage;
        imageSaver= saveImage;
        gammaOptions = new GammaOptions(imagePane);

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
    }
}
