package ru.nsu.fit.g20202.vartazaryan;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.fit.g20202.vartazaryan.filters.*;
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
    private int originalImageWidth;
    private int originalImageHeight;

    @Getter
    private BufferedImage filteredImage;
    @Setter
    @Getter
    private String imageName;

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
    private ContouringFilter contouringFilter = new ContouringFilter();
    private SepiaFilter sepiaFilter = new SepiaFilter();
    private EmbossingFilter embossingFilter = new EmbossingFilter();
    private FloydSteinbergDithering floydSteinbergDithering = new FloydSteinbergDithering();

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


    /**
     * This method applies current filter to the original image.
     * <br>To set up a new filter must be called setCurrentFilter()
     */
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

                case CONTOURING_FILTER -> {
                    filteredImage = contouringFilter.applyFilter(originalImage);
                    showFiltered = 1;
                }

                case SEPIA_FILTER -> {
                    filteredImage = sepiaFilter.applyFilter(originalImage);
                    showFiltered = 1;
                }

                case EMBOSSING_FILTER -> {
                    filteredImage = embossingFilter.applyFilter(originalImage);
                    showFiltered = 1;
                }

                case FLOYD_DITHERING -> {
                    filteredImage = floydSteinbergDithering.applyFilter(originalImage);
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

    /**
     * This method sets new image into image pane
     *
     * @param newImage
     */
    public void setOriginalImage(BufferedImage newImage)
    {
        label.setVisible(false);

        originalImage = newImage;
        g2d = newImage.createGraphics();

        width = newImage.getWidth();
        height = newImage.getHeight();
        setPreferredSize(new Dimension(width, height));

        originalImageWidth = width;
        originalImageHeight = height;

        showFiltered = 0;
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
        // bugged, needs fixes
        zoom.setZoomCoef(zoomCoef);
        if(showFiltered == 0)
        {
            filteredImage = zoom.apply(originalImage, originalImageWidth, originalImageHeight);
            showFiltered = 1;
            repaint();
        }
        else
        {
            filteredImage = zoom.apply(filteredImage, originalImageWidth, originalImageHeight);
            repaint();
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
    public void mouseReleased(MouseEvent e) {}

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
        GAMMA_CORRECTOR,
        CONTOURING_FILTER,
        SEPIA_FILTER,
        EMBOSSING_FILTER,
        FLOYD_DITHERING
    }
}
