package ru.nsu.fit.g20202.vartazaryan.view;

import ru.nsu.fit.g20202.vartazaryan.ImagePane;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SaveImage extends JFrame
{
    private ImagePane imagePane;

    public SaveImage(ImagePane image)
    {
        imagePane = image;
    }

    public void saveImage()
    {
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
}
