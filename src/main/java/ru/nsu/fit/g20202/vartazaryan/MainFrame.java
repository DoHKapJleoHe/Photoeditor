package ru.nsu.fit.g20202.vartazaryan;

import ru.nsu.fit.g20202.vartazaryan.options.GammaOptions;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame
{
    private ImagePane imagePane;
    private JScrollPane imageScrollPane;
    private GammaOptions gammaOptions = new GammaOptions();

    private JToolBar toolBar;
    private JButton loadButton;
    private JButton blackWhite;
    private JButton negative;
    private JButton gammaCorrection;
    private JButton returnButton;


    private JMenuBar menu;
    private JMenu file;
    private JMenu edit;



    public MainFrame() throws IOException
    {
        super("ARAEditor");

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setBounds(400, 100, 640, 480);
        setMinimumSize(new Dimension(640, 480));
        setVisible(true);

        /*ICONS*/
        ImageIcon blackWhiteIcon = new ImageIcon(ImageIO.read(new File("src/main/recources/black&white.png")));
        ImageIcon loadIcon = new ImageIcon(ImageIO.read(new File("src/main/recources/load.png")));
        ImageIcon backIcon = new ImageIcon(ImageIO.read(new File("src/main/recources/back.png")));
        ImageIcon negativeIcon = new ImageIcon(ImageIO.read(new File("src/main/recources/negative.png")));
        ImageIcon gammaIcon = new ImageIcon(ImageIO.read(new File("src/main/recources/gamma.png")));
        /*-----*/

        /*IMAGE FIELD*/
        imageScrollPane = new JScrollPane();
        imagePane = new ImagePane(imageScrollPane);
        imagePane.setVisible(true);
        add(imageScrollPane);
        /*-----------*/

        /*TOOL BAR*/
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setRollover(false);
        toolBar.setVisible(true);
        add(toolBar, BorderLayout.NORTH);

        loadButton = createButton("Load", loadIcon);
        toolBar.add(loadButton);

        blackWhite = createButton("BWF", blackWhiteIcon);
        toolBar.add(blackWhite);

        negative = createButton("Negative", negativeIcon);
        toolBar.add(negative);

        gammaCorrection = createButton("Gamma", gammaIcon);
        toolBar.add(gammaCorrection);

        returnButton = createButton("Return", backIcon);
        toolBar.add(returnButton);
        /*--------*/

        /*MENU BAR*/
        menu = new JMenuBar();
        setJMenuBar(menu);

        file = new JMenu("File");
        menu.add(file);
        JMenuItem saveItem = new JMenuItem("Save");
        file.add(saveItem);

        JMenuItem loadItem = new JMenuItem("Load");
        file.add(loadItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        file.add(exitItem);

        edit = new JMenu("Edit");
        menu.add(edit);

        JMenuItem gamma = new JMenuItem("Gamma");
        edit.add(gamma);
        /*--------*/

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



        loadButton.addActionListener(e -> {
            loadImage();
        });

        blackWhite.addActionListener(e -> {
            imagePane.setCurFilter(ImagePane.Filter.BLACK_WHITE_FILTER);
            imagePane.applyFilter();
        });

        returnButton.addActionListener(e -> {
            imagePane.showOriginalImage();
        });

        negative.addActionListener(e -> {
            imagePane.setCurFilter(ImagePane.Filter.NEGATIVE_FILTER);
            imagePane.applyFilter();
        });

        gammaCorrection.addActionListener(e -> {
            imagePane.setCurFilter(ImagePane.Filter.GAMMA_CORRECTOR);
            imagePane.applyFilter();
        });

        loadItem.addActionListener(e -> {
            loadImage();
        });

        saveItem.addActionListener(e -> {
            saveImage();
        });

        exitItem.addActionListener(e -> {System.exit(0);});

        gamma.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    gammaOptions,
                    "Gamma options",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if(JOptionPane.OK_OPTION == confirm)
            {
                imagePane.updateGammaOptions(gammaOptions.getGammaValue());
            }
        });

        pack();
    }

    private JButton createButton(String buttonName, ImageIcon icon)
    {
        JButton newButton = new JButton(icon);
        newButton.setFocusPainted(false);

        return newButton;
    }

    private void saveImage() {
        FileDialog save = new FileDialog(this, "Сохранить изображение", FileDialog.SAVE);
        save.setVisible(true);

        String image = save.getFile();
        String dir = save.getDirectory();
        String name = dir + image + ".png";

        File file = new File(name);
        try
        {
            if(image != null)
            {
                ImageIO.write(imagePane.getFilteredImage(), "png", file);
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Please, name your image!", "Error", JOptionPane.QUESTION_MESSAGE);
            }

        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    private void loadImage()
    {
        FileDialog loadFile = new FileDialog(this,"Load Image", FileDialog.LOAD);
        loadFile.setFile("*.png; *.jpg; *.jpeg;");

        loadFile.setVisible(true);
        String image = loadFile.getFile();
        String dir = loadFile.getDirectory();
        String file = dir + image;

        System.out.println("File name is "+file);

        if(image != null)
        {
            try
            {
                BufferedImage newImage = ImageIO.read(new File(file));
                imagePane.setImage(newImage);
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image has not been selected!", "Error", JOptionPane.QUESTION_MESSAGE);
        }
    }

    public static void main(String[] args) throws IOException
    {
        MainFrame mainFrame = new MainFrame();
    }
}
