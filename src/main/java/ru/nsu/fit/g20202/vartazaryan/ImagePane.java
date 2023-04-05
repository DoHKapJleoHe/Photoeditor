package ru.nsu.fit.g20202.vartazaryan;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.fit.g20202.vartazaryan.filters.*;
import ru.nsu.fit.g20202.vartazaryan.instruments.FitToScreen;
import ru.nsu.fit.g20202.vartazaryan.instruments.Instrument;
import ru.nsu.fit.g20202.vartazaryan.instruments.RotateInstrument;
import ru.nsu.fit.g20202.vartazaryan.instruments.Zoom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class ImagePane extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener
{
    private static final int INDENT = 4;
    private static final double zoomK = 0.05;

    private int originalImageWidth;
    private int originalImageHeight;

    private BufferedImage originalImage;
    @Getter
    private BufferedImage filteredImage;
    @Setter
    @Getter
    private String imageName;

    private Graphics2D g2d;

    private int width = 640;
    private int height = 480;

    private JScrollPane scrollPane;
    private Dimension panelSize;
    private Point origin;

    /*SHOW PARAMETERS*/
    private int showFiltered = 0;

    /*FILTERS*/
    private Map<String, IFilter> filters;

    /*INSTRUMENTS*/
    private RotateInstrument rotateInstrument = new RotateInstrument();
    //private Zoom zoom = new Zoom();

    Map<String, Instrument> instruments;

    private JLabel label;

    public ImagePane(JScrollPane sp, Map<String, IFilter> filters, Map<String, Instrument> instruments)
    {
        this.filters = filters;
        this.instruments = instruments;

        scrollPane = sp;
        scrollPane.setViewportView(this);
        scrollPane.setViewportBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(INDENT, INDENT, INDENT, INDENT), // this creates indent between frame and image area
                BorderFactory.createDashedBorder(Color.BLACK, 5, 2)));

        label = new JLabel("Place for image");
        add(label);

        panelSize = getVisibleRect().getSize();
        addMouseListener(this);
        addMouseWheelListener(this);
        scrollPane.setViewportView(this);
    }

    public void applyFilter(String filter)
    {
        if(originalImage != null)
        {
            IFilter curFilter = filters.get(filter);
            filteredImage = curFilter.applyFilter(originalImage);
            showFiltered = 1;

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

    public void fitImage()
    {
        if(originalImage != null)
        {
            //originalImage = instruments.get("FitToScreenInstrument").apply(originalImage);
            //System.out.println("Resized");
            //this.revalidate();
            double coef = Math.min(getWidth() / originalImageWidth, getHeight() / originalImageHeight);
            System.out.println(coef);
            originalImageWidth = (int) (originalImageWidth*coef);
            originalImageHeight = (int) (originalImageHeight*coef);
            showFiltered = 0;
            repaint();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image has not been selected!", "Error", JOptionPane.QUESTION_MESSAGE);
        }

    }

    public BufferedImage getOriginalImage() {
        return originalImage;
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

    /*public void zoomImage(int zoomCoef)
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
    }*/

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if(showFiltered == 0)
        {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(originalImage, 0, 0, originalImageWidth, originalImageHeight, this);
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

    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        /*if(originalImage != null)
        {
            double k = 1 - e.getWheelRotation()*zoomK;
            int newPW = (int)(panelSize.width*k);

            if(k > 1)
            {
                int newPH = (int)(panelSize.height*k);
                Dimension viewSize = getVisibleRect().getSize();
                int pixSizeX = newPW / originalImageWidth;
                int pixSizeY = newPH / originalImageHeight;

                if (pixSizeX>0 && pixSizeY>0)
                {
                    int pixNumX = viewSize.width / pixSizeX;
                    int pixNumY = viewSize.height / pixSizeY;
                    if (pixNumX<2 || pixNumY<2)
                        return;
                }
            }

            panelSize.width = newPW;
            panelSize.height = (int) ((long)panelSize.width * originalImageHeight / originalImageWidth);
            int x = (int) (e.getX() * k);
            int y = (int) (e.getY() * k);
            Point scroll = scrollPane.getViewport().getViewPosition();
            scroll.x -= e.getX();
            scroll.y -= e.getY();
            scroll.x += x;
            scroll.y += y;
            repaint();
            revalidate();
            scrollPane.validate();

            scrollPane.getHorizontalScrollBar().setValue(scroll.x);
            scrollPane.getVerticalScrollBar().setValue(scroll.y);
            scrollPane.repaint();
        }*/
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
