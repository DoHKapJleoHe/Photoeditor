package ru.nsu.fit.g20202.vartazaryan.view;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import ru.nsu.fit.g20202.vartazaryan.ImagePane;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ToolBar extends JToolBar
{
    private ImagePane imagePane;
    private LoadImage imageLoader;
    private SaveImage imageSaver;

    private JButton loadButton;
    private JButton saveButton;
    private JButton blackWhite;
    private JButton negative;
    private JButton gammaCorrection;
    private JButton contouring;
    private JButton sepia;
    private JButton embossing;
    private JButton dithering;
    private JButton rotateButton;
    private JButton returnButton;

    public ToolBar(ImagePane image, LoadImage loadImage, SaveImage saveImage) throws IOException
    {
        imagePane = image;
        imageLoader = loadImage;
        imageSaver = saveImage;

        setLayout(new FlowLayout(FlowLayout.LEFT));

        setFloatable(false);
        setRollover(false);
        setVisible(true);

        ImageIcon blackWhiteIcon = new ImageIcon(ImageIO.read(new File("src/main/recources/black&white.png")));
        ImageIcon loadIcon = new ImageIcon(ImageIO.read(new File("src/main/recources/load.png")));
        ImageIcon saveIcon = new ImageIcon(ImageIO.read(new File("src/main/recources/save.png")));
        ImageIcon backIcon = new ImageIcon(ImageIO.read(new File("src/main/recources/back.png")));
        ImageIcon negativeIcon = new ImageIcon(ImageIO.read(new File("src/main/recources/negative.png")));
        ImageIcon gammaIcon = new ImageIcon(ImageIO.read(new File("src/main/recources/gamma.png")));
        ImageIcon rotateIcon = new ImageIcon(ImageIO.read(new File("src/main/recources/rotate.png")));
        ImageIcon contourIcon = new ImageIcon(ImageIO.read(new File("src/main/recources/contour.png")));
        ImageIcon sepiaIcon = new ImageIcon(ImageIO.read(new File("src/main/recources/sepia.png")));
        ImageIcon embossingIcon = new ImageIcon(ImageIO.read(new File("src/main/recources/embossing.png")));
        ImageIcon ditheringIcon = new ImageIcon(ImageIO.read(new File("src/main/recources/dithering.png")));


        loadButton = createButton("Load Image", loadIcon);
        add(loadButton);

        saveButton = createButton("Save Image", saveIcon);
        add(saveButton);

        add(new JSeparator(SwingConstants.VERTICAL));

        blackWhite = createButton("Black-White Filter", blackWhiteIcon);
        add(blackWhite);

        negative = createButton("Negative Filter", negativeIcon);
        add(negative);

        gammaCorrection = createButton("Gamma Correction", gammaIcon);
        add(gammaCorrection);

        contouring = createButton("Contouring Filter", contourIcon);
        add(contouring);

        sepia = createButton("Sepia Filter", sepiaIcon);
        add(sepia);

        embossing = createButton("Embossing Filter", embossingIcon);
        add(embossing);

        dithering = createButton("Dithering", ditheringIcon);
        add(dithering);

        add(new JSeparator(SwingConstants.VERTICAL));

        rotateButton = createButton("Rotate", rotateIcon);
        add(rotateButton);

        returnButton = createButton("Return", backIcon);
        add(returnButton);

        loadButton.addActionListener(e -> {
            try
            {
                imageLoader.loadImage();
            }
            catch (IOException ex)
            {
                throw new RuntimeException(ex);
            }
        });

        saveButton.addActionListener(e -> {
            imageSaver.saveImage();
        });

        blackWhite.addActionListener(e -> {
            imagePane.applyFilter("BWFilter");
        });

        returnButton.addActionListener(e -> {
            imagePane.showOriginalImage();
        });

        rotateButton.addActionListener(e -> {
            imagePane.rotateImage(90);
        });

        negative.addActionListener(e -> {
            imagePane.applyFilter("NegativeFilter");
        });

        gammaCorrection.addActionListener(e -> {
            //JOptionPane.showMessageDialog(this, );
            imagePane.applyFilter("GammaCorrectionFilter");
        });

        contouring.addActionListener(e -> {
            imagePane.applyFilter("ContouringFilter");
        });

        sepia.addActionListener(e -> {
            imagePane.applyFilter("SepiaFilter");
        });

        embossing.addActionListener(e -> {
            imagePane.applyFilter("EmbossingFilter");
        });

        dithering.addActionListener(e -> {
            imagePane.applyFilter("FloydSteinbergDitheringFilter");
        });
    }

    private JButton createButton(String buttonName, ImageIcon icon)
    {
        JButton newButton = new JButton(icon);
        newButton.setToolTipText(buttonName);
        newButton.setFocusPainted(false);

        return newButton;
    }
}
