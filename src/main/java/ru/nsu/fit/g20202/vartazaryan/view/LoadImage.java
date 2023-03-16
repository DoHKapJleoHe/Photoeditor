package ru.nsu.fit.g20202.vartazaryan.view;

import ru.nsu.fit.g20202.vartazaryan.ImagePane;
import ru.nsu.fit.g20202.vartazaryan.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.UnaryOperator;

public class LoadImage extends JFrame
{
    private ImagePane imagePane;
    //private UnaryOperator<MainFrame> titleFunc = (e) -> e;

    public LoadImage(ImagePane image)
    {
        setVisible(false);
        imagePane = image;
    }

    public void loadImage() throws IOException
    {
        FileDialog loadFile = new FileDialog((Frame) null,"Load Image", FileDialog.LOAD);
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
                imagePane.setOriginalImage(newImage);
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
}
