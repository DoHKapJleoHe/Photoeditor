package ru.nsu.fit.g20202.vartazaryan.view;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import ru.nsu.fit.g20202.vartazaryan.ImagePane;
import ru.nsu.fit.g20202.vartazaryan.options.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ToolBar extends JToolBar
{
    private ImagePane imagePane;
    private LoadImage imageLoader;
    private SaveImage imageSaver;
    private GammaOptions gammaOptions;
    private ContouringOptions contouringOptions;
    private FloydDitheringOptions floydDitheringOptions;
    private GaussBlurOptions gaussBlurOptions;

    private JButton loadButton;
    private JButton saveButton;
    private JButton blackWhite;
    private JButton negative;
    private JButton gammaCorrection;
    private JButton sharpness;
    private JButton contouring;
    private JButton sepia;
    private JButton embossing;
    private JButton dithering;
    private JButton watercolor;
    private JButton glitch;
    private JButton gaussBlur;
    private JButton rotateButton;
    private JButton returnButton;

    public ToolBar(ImagePane image, LoadImage loadImage, SaveImage saveImage, Map<String, Option> options) throws IOException
    {
        imagePane = image;
        imageLoader = loadImage;
        imageSaver = saveImage;
        this.gammaOptions = (GammaOptions) options.get("GammaOptions");
        this.contouringOptions = (ContouringOptions) options.get("ContouringOptions");
        this.floydDitheringOptions = (FloydDitheringOptions) options.get("FloydDitheringOptions");
        this.gaussBlurOptions = (GaussBlurOptions) options.get("GaussBlurOptions");

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
        ImageIcon sharpnessIcon = new ImageIcon(ImageIO.read(new File("src/main/recources/sharpness.png")));
        ImageIcon brushIcon = new ImageIcon(ImageIO.read(new File("src/main/recources/paintbrush.png")));
        ImageIcon glitchIcon = new ImageIcon(ImageIO.read(new File("src/main/recources/glitch.png")));
        ImageIcon gaussIcon = new ImageIcon(ImageIO.read(new File("src/main/recources/gauss.png")));


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

        sharpness = createButton("Sharpness Filter", sharpnessIcon);
        add(sharpness);

        contouring = createButton("Contouring Filter", contourIcon);
        add(contouring);

        sepia = createButton("Sepia Filter", sepiaIcon);
        add(sepia);

        embossing = createButton("Embossing Filter", embossingIcon);
        add(embossing);

        dithering = createButton("Dithering", ditheringIcon);
        add(dithering);

        watercolor = createButton("WatercolorFilter", brushIcon);
        add(watercolor);

        glitch = createButton("Glitch", glitchIcon);
        add(glitch);

        gaussBlur = createButton("Gauss blur", gaussIcon);
        add(gaussBlur);

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
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    gammaOptions,
                    "Gamma options",
                    JOptionPane.OK_CANCEL_OPTION
                    );
            if(confirm == JOptionPane.OK_OPTION)
            {
                System.out.println("Gamma applied");
                imagePane.applyFilter("GammaCorrectionFilter");
            }
        });

        sharpness.addActionListener(e -> {
            imagePane.applyFilter("SharpnessFilter");
        });

        contouring.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    contouringOptions,
                    "Contouring options",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if(confirm == JOptionPane.OK_OPTION)
            {
                imagePane.applyFilter("ContouringFilter");
            }
        });

        sepia.addActionListener(e -> {
            imagePane.applyFilter("SepiaFilter");
        });

        embossing.addActionListener(e -> {
            imagePane.applyFilter("EmbossingFilter");
        });

        dithering.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    floydDitheringOptions,
                    "Dithering options",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if(confirm == JOptionPane.OK_OPTION)
            {
                imagePane.applyFilter("FloydSteinbergDitheringFilter");
            }
        });

        watercolor.addActionListener(e -> {
            imagePane.applyFilter("WaterColorFilter");
        });

        glitch.addActionListener(e -> {
            imagePane.applyFilter("GlitchFilter");
        });

        gaussBlur.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    gaussBlurOptions,
                    "Blur options",
                    JOptionPane.OK_CANCEL_OPTION);

            if(confirm == JOptionPane.OK_OPTION)
            {
                imagePane.applyFilter("GaussBlurFilter");
            }
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
