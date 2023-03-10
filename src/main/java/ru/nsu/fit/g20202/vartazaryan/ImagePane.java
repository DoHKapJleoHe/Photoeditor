package ru.nsu.fit.g20202.vartazaryan;

import ru.nsu.fit.g20202.vartazaryan.filters.BlackAndWhiteFilter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class ImagePane extends JPanel implements MouseListener, MouseMotionListener
{
    private static final int INDENT = 4;

    private BufferedImage originalImage;
    private BufferedImage filteredImage;

    private Graphics2D g2d;

    private int width = 640;
    private int height = 480;

    private JScrollPane scrollPane;
    private Point origin;
    private Filter curFilter;

    /*SHOW PARAMETERS*/
    private int showFiltered = 0;

    private BlackAndWhiteFilter bwfFilter = new BlackAndWhiteFilter();

    public ImagePane(JScrollPane sp)
    {
        scrollPane = sp;
        scrollPane.setViewportView(this);
        scrollPane.setViewportBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(INDENT, INDENT, INDENT, INDENT), // this creates indent between frame and image area
                BorderFactory.createDashedBorder(Color.BLACK, 5, 2)));

    }

    public void applyBlackAndWhite()
    {
        if(originalImage != null)
        {
            curFilter = Filter.BLACK_WHITE_FILTER;
            filteredImage = bwfFilter.applyFilter(originalImage);
            showFiltered = 1;

            repaint();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image has not been selected!", "Error", JOptionPane.QUESTION_MESSAGE);
        }
    }

    public void setImage(BufferedImage newImage)
    {
        originalImage = newImage;
        g2d = newImage.createGraphics();

        width = newImage.getWidth();
        height = newImage.getHeight();
        setPreferredSize(new Dimension(width, height));

        repaint();
    }

    public void showOriginalImage()
    {
        if(filteredImage != null)
        {
            showFiltered = 0;
            repaint();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image has not been filtered yet!", "Error", JOptionPane.QUESTION_MESSAGE);

        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if(showFiltered == 0)
        {
            g.drawImage(originalImage, 0, 0, this);
        }
        else
        {
            g.drawImage(filteredImage, 0, 0, this);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        origin = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e)
    {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    enum Filter{
        BLACK_WHITE_FILTER,
        NEGATIVE_FILTER
    }
}
