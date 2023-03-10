package ru.nsu.fit.g20202.vartazaryan;

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

    private JToolBar toolBar;
    private JButton loadButton;
    private JButton blackWhite;
    private JButton negative;
    private JButton returnButton;


    public MainFrame() throws IOException
    {
        super("Eleonora");

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setBounds(400, 100, 640, 480);
        setMinimumSize(new Dimension(640, 480));
        setVisible(true);

        /*ICONS*/
        ImageIcon blackWhiteIcon = new ImageIcon(ImageIO.read(new File("src/main/recources/black&white.png")));
        ImageIcon loadIcon = new ImageIcon(ImageIO.read(new File("src/main/recources/load.png")));
        ImageIcon backIcon = new ImageIcon(ImageIO.read(new File("src/main/recources/back.png")));
        ImageIcon negativeIcon = new ImageIcon(ImageIO.read(new File("src/main/recources/negative.png")));
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

        returnButton = createButton("Return", backIcon);
        toolBar.add(returnButton);
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
    }

    private JButton createButton(String buttonName, ImageIcon icon)
    {
        JButton newButton = new JButton(icon);
        newButton.setFocusPainted(false);

        return newButton;
    }

    public static void main(String[] args) throws IOException
    {
        MainFrame mainFrame = new MainFrame();
    }
}
