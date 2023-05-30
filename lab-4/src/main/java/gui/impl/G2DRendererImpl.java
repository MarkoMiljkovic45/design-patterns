package gui.impl;

import gui.Renderer;
import model.impl.Point;

import java.awt.*;


public class G2DRendererImpl implements Renderer {
    private final Graphics2D g2d;

    public G2DRendererImpl(Graphics2D g2d) {
        this.g2d = g2d;
    }

    @Override
    public void drawLine(Point s, Point e) {
        g2d.setColor(Color.BLUE);
        g2d.drawLine(s.getX(), s.getY(), e.getX(), e.getY());
    }

    @Override
    public void fillPolygon(Point[] points) {
        int nPoints = points.length;
        int[] xPoints = new int[nPoints];
        int[] yPoints = new int[nPoints];

        for (int i = 0; i < nPoints; i++) {
            Point point = points[i];

            xPoints[i] = point.getX();
            yPoints[i] = point.getY();
        }

        g2d.setColor(Color.BLUE);
        g2d.fillPolygon(xPoints, yPoints, nPoints);

        g2d.setColor(Color.RED);
        g2d.drawPolygon(xPoints, yPoints, nPoints);
    }
}
