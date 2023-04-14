package ru.nsu.fit.g20202.vartazaryan;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import ru.nsu.fit.g20202.vartazaryan.filters.*;
import ru.nsu.fit.g20202.vartazaryan.instruments.FitToScreen;
import ru.nsu.fit.g20202.vartazaryan.instruments.Instrument;
import ru.nsu.fit.g20202.vartazaryan.instruments.RotateInstrument;
import ru.nsu.fit.g20202.vartazaryan.options.*;
import ru.nsu.fit.g20202.vartazaryan.view.*;
import ru.nsu.fit.g20202.vartazaryan.view.MenuBar;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame
{
    private ImagePane imagePane;
    private JScrollPane imageScrollPane;
    private RotateOption rotateOptions;
    private BottomPanel bottomOptions;

    private ToolBar toolBar;
    private MenuBar menu;

    private Map<String, IFilter> filters = new HashMap<>();
    private Map<String, Instrument> instruments = new HashMap<>();
    private Map<String, Option> options = new HashMap<>();

    public MainFrame() throws IOException
    {
        super("PhotoEditor");
        FlatLightLaf.setup();
        SwingUtilities.updateComponentTreeUI(this);

        ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/recources/AppIcon.png")));
        setIconImage(icon.getImage());

        getContentPane().setBackground(Color.DARK_GRAY);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setBounds(400, 100, 640, 480);
        setMinimumSize(new Dimension(640, 480));

        filters.put("BWFilter", new BlackAndWhiteFilter());
        filters.put("NegativeFilter", new NegativeFilter());
        filters.put("GammaCorrectionFilter", new GammaCorrection());
        filters.put("ContouringFilter", new ContouringFilter());
        filters.put("EmbossingFilter", new EmbossingFilter());
        filters.put("SepiaFilter", new SepiaFilter());
        filters.put("FloydSteinbergDitheringFilter", new FloydSteinbergDithering());
        filters.put("OrderedDitheringFilter", new OrderedDithering());
        filters.put("SharpnessFilter", new SharpnessFilter());
        filters.put("WaterColorFilter", new WaterColorFilter());
        filters.put("GlitchFilter", new GlitchFilter());
        filters.put("GaussBlurFilter", new GaussBlur());

        instruments.put("FitToScreenInstrument", new FitToScreen());
        instruments.put("RotateInstrument", new RotateInstrument());

        imageScrollPane = new JScrollPane();
        imagePane = new ImagePane(imageScrollPane, filters, instruments);
        imagePane.setVisible(true);
        add(imageScrollPane);

        LoadImage loadImage = new LoadImage(imagePane);
        SaveImage saveImage = new SaveImage(imagePane);
        GammaOptions gammaOptions = new GammaOptions((GammaCorrection) filters.get("GammaCorrectionFilter"));
        ContouringOptions contouringOptions = new ContouringOptions((ContouringFilter) filters.get("ContouringFilter"));
        FloydDitheringOptions floydDitheringOption = new FloydDitheringOptions((FloydSteinbergDithering) filters.get("FloydSteinbergDitheringFilter"));
        GaussBlurOptions gaussBlurOptions = new GaussBlurOptions((GaussBlur) filters.get("GaussBlurFilter"));
        OrderedDitheringOptions orderedDitheringOptions = new OrderedDitheringOptions((OrderedDithering) filters.get("OrderedDitheringFilter"));

        options.put("GammaOptions", gammaOptions);
        options.put("ContouringOptions", contouringOptions);
        options.put("FloydDitheringOptions", floydDitheringOption);
        options.put("GaussBlurOptions", gaussBlurOptions);
        options.put("OrderedDitheringOptions", orderedDitheringOptions);

        toolBar = new ToolBar(imagePane, loadImage, saveImage, options);
        add(toolBar, BorderLayout.NORTH);

        menu = new MenuBar(imagePane, loadImage, saveImage, filters, gammaOptions, (FitToScreen) instruments.get("FitToScreenInstrument"));
        setJMenuBar(menu);

        rotateOptions = new RotateOption(imagePane);

        bottomOptions = new BottomPanel(imagePane, instruments);
        add(bottomOptions, BorderLayout.SOUTH);

        /*THEMES*/
        ButtonGroup themesGroup = new ButtonGroup();

        JMenu view = new JMenu("View");
        menu.add(view);

        JMenu themes = new JMenu("Theme");
        view.add(themes);

        JRadioButtonMenuItem whiteTheme = new JRadioButtonMenuItem("White");
        JRadioButtonMenuItem darculaTheme = new JRadioButtonMenuItem("Darcula");

        themesGroup.add(whiteTheme);
        themesGroup.add(darculaTheme);

        themes.add(whiteTheme);
        themes.add(darculaTheme);

        whiteTheme.addActionListener(e -> {
            FlatLightLaf.setup();
            SwingUtilities.updateComponentTreeUI(this);
        });

        darculaTheme.addActionListener(e -> {
            FlatDarculaLaf.setup();
            SwingUtilities.updateComponentTreeUI(this);
        });
        /*------*/

        /*ACTION LISTENERS*/
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                super.windowClosed(e);
                if(JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Close", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                {
                    System.exit(0);
                }
            }
        });

        pack();
        setVisible(true);
    }


    public static void main(String[] args) throws IOException
    {
        MainFrame mainFrame = new MainFrame();
    }
}
