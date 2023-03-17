package ru.nsu.fit.g20202.vartazaryan;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import ru.nsu.fit.g20202.vartazaryan.options.GammaOptions;
import ru.nsu.fit.g20202.vartazaryan.options.RotateOption;
import ru.nsu.fit.g20202.vartazaryan.view.*;
import ru.nsu.fit.g20202.vartazaryan.view.MenuBar;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame
{
    private ImagePane imagePane;
    private JScrollPane imageScrollPane;
    private GammaOptions gammaOptions;
    private RotateOption rotateOptions;
    private BottomPanel bottomOptions;

    private ToolBar toolBar;
    private MenuBar menu;

    private JPopupMenu popupMenu;

    public MainFrame() throws IOException
    {
        setTitle();
        FlatLightLaf.setup();
        SwingUtilities.updateComponentTreeUI(this);

        ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/recources/AppIcon.png")));
        setIconImage(icon.getImage());

        getContentPane().setBackground(Color.DARK_GRAY);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setBounds(400, 100, 640, 480);
        setMinimumSize(new Dimension(640, 480));

        imageScrollPane = new JScrollPane();
        imagePane = new ImagePane(imageScrollPane);
        imagePane.setVisible(true);
        add(imageScrollPane);

        LoadImage loadImage = new LoadImage(imagePane);
        SaveImage saveImage = new SaveImage(imagePane);

        toolBar = new ToolBar(imagePane, loadImage, saveImage);
        add(toolBar, BorderLayout.NORTH);

        menu = new MenuBar(imagePane, loadImage, saveImage);
        setJMenuBar(menu);

        rotateOptions = new RotateOption(imagePane);

        bottomOptions = new BottomPanel(imagePane);
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

        MouseAdapter click = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e)
            {
                if (e.isPopupTrigger())
                {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        };

        imagePane.addMouseListener(click);

        pack();
        setVisible(true);
    }

    private void setTitle()
    {
        Font font = this.getFont();

        String currentTitle = "PhotoEditor";

        this.setTitle(currentTitle);

    }

    public static void main(String[] args) throws IOException
    {
        MainFrame mainFrame = new MainFrame();
    }
}
