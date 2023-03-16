package ru.nsu.fit.g20202.vartazaryan;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.fit.g20202.vartazaryan.filters.BlackAndWhiteFilter;
import ru.nsu.fit.g20202.vartazaryan.filters.GammaCorrection;
import ru.nsu.fit.g20202.vartazaryan.filters.NegativeFilter;
import ru.nsu.fit.g20202.vartazaryan.instruments.RotateInstrument;
import ru.nsu.fit.g20202.vartazaryan.instruments.Zoom;

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
    @Getter
    private BufferedImage filteredImage;

    private Graphics2D g2d;

    private int width = 640;
    private int height = 480;

    private JScrollPane scrollPane;
    private Point origin;
    @Setter
    private Filter curFilter;

    /*SHOW PARAMETERS*/
    private int showFiltered = 0;

    /*FILTERS*/
    private BlackAndWhiteFilter bwfFilter = new BlackAndWhiteFilter();
    private NegativeFilter negFilter = new NegativeFilter();
    private GammaCorrection gammaCorrector = new GammaCorrection();

    /*INSTRUMENTS*/
    private RotateInstrument rotateInstrument = new RotateInstrument();
    private Zoom zoom = new Zoom();

    private JLabel label;

    public ImagePane(JScrollPane sp)
    {
        scrollPane = sp;
        scrollPane.setViewportView(this);
        scrollPane.setViewportBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(INDENT, INDENT, INDENT, INDENT), // this creates indent between frame and image area
                BorderFactory.createDashedBorder(Color.BLACK, 5, 2)));

        label = new JLabel("Place for image");
        add(label);
    }

    public void applyFilter()
    {
        if(originalImage != null)
        {
            switch (curFilter)
            {
                case BLACK_WHITE_FILTER -> {
                    filteredImage = bwfFilter.applyFilter(originalImage);
                    showFiltered = 1;
                }

                case NEGATIVE_FILTER -> {
                    filteredImage = negFilter.applyFilter(originalImage);
                    showFiltered = 1;
                }

                case GAMMA_CORRECTOR -> {
                    filteredImage = gammaCorrector.applyFilter(originalImage);
                    showFiltered = 1;
                }
            }

            repaint();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image has not been selected!", "Error", JOptionPane.QUESTION_MESSAGE);
        }
    }

    public void setOriginalImage(BufferedImage newImage)
    {
        label.setVisible(false);

        originalImage = newImage;
        g2d = newImage.createGraphics();

        width = newImage.getWidth();
        height = newImage.getHeight();
        setPreferredSize(new Dimension(width, height));

        repaint();
    }

    public BufferedImage getOriginalImage() {
        return originalImage;
    }

    public void updateGammaOptions(int gamma)
    {
        gammaCorrector.setGamma((double)gamma/10);

        if(originalImage != null)
        {
            filteredImage = gammaCorrector.applyFilter(originalImage);
            showFiltered = 1;
            repaint();
        }
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

    public void rotateImage(int degree)
    {
        if(originalImage != null)
        {
            rotateInstrument.setDegree(degree);
            originalImage = rotateInstrument.apply(originalImage);
            showFiltered = 0;
            repaint();
        }

    }

    public void zoomImage(int zoomCoef)
    {
        zoom.setZoomCoef(zoomCoef);
        originalImage = zoom.apply(originalImage);
        repaint();
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

    public enum Filter{
        BLACK_WHITE_FILTER,
        NEGATIVE_FILTER,
        GAMMA_CORRECTOR
    }
}
