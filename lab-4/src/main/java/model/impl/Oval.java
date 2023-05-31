package model.impl;

import model.GraphicalObject;
import gui.Renderer;
import util.GeometryUtil;
import util.Point;
import util.Rectangle;

import java.util.List;
import java.util.Stack;

public class Oval extends AbstractGraphicalObject {
    private static final int NUM_OF_SAMPLES = 500;

    public Oval(Point rightHotPoint, Point downHotPoint) {
        super(new Point[] {rightHotPoint, downHotPoint});
    }

    public Oval() {
        this(new Point(10, 0), new Point(0, 10));
    }

    private Point rightHotPoint() {
        return getHotPoint(0);
    }

    private Point downHotPoint() {
        return getHotPoint(1);
    }

    @Override
    public Rectangle getBoundingBox() {
        int width = 2 * (rightHotPoint().getX() - downHotPoint().getX());
        int height = 2 * (downHotPoint().getY() - rightHotPoint().getY());

        int x = rightHotPoint().getX() - width;
        int y = downHotPoint().getY() - height;

        return new Rectangle(x, y, width, height);
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        int a = rightHotPoint().getX() - downHotPoint().getX();
        int b = downHotPoint().getY() - rightHotPoint().getY();

        int centerX = downHotPoint().getX();
        int centerY = rightHotPoint().getY();

        double a2 = a * a;
        double b2 = b * b;
        double x2 = (mousePoint.getX() - centerX) * (mousePoint.getX() - centerX);
        double y2 = (mousePoint.getY() - centerY) * (mousePoint.getY() - centerY);

        double ellipseEquation = x2 / a2 + y2 / b2;

        if (ellipseEquation <= 1.0) {
            return 0.0;
        }

        double minDistance = -1;

        for (Point sample : samplePoints()) {
            double distance = GeometryUtil.distanceFromPoint(sample, mousePoint);

            if (distance < minDistance || minDistance == -1) {
                minDistance = distance;
            }
        }

        return minDistance;
    }

    private Point[] samplePoints() {
        int a = rightHotPoint().getX() - downHotPoint().getX();
        int b = downHotPoint().getY() - rightHotPoint().getY();

        int centerX = downHotPoint().getX();
        int centerY = rightHotPoint().getY();

        Point[] samplePoints = new Point[NUM_OF_SAMPLES];

        double delta = 2 * Math.PI / NUM_OF_SAMPLES;
        double fi = 0;

        for (int i = 0; i < NUM_OF_SAMPLES; i++) {
            int x = (int) (centerX + a * Math.cos(fi));
            int y = (int) (centerY + b * Math.sin(fi));

            samplePoints[i] = new Point(x, y);

            fi += delta;
        }

        return samplePoints;
    }

    @Override
    public void render(Renderer r) {
        r.fillPolygon(samplePoints());
    }

    @Override
    public String getShapeName() {
        return "Oval";
    }

    @Override
    public GraphicalObject duplicate() {
        return new Oval(
                new Point(rightHotPoint().getX(), rightHotPoint().getY()),
                new Point(downHotPoint().getX(), downHotPoint().getY())
        );
    }

    @Override
    public String getShapeID() {
        return "@OVAL";
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {
        String[] args = data.split(" ");

        int rightX = Integer.parseInt(args[0]);
        int rightY = Integer.parseInt(args[1]);

        int downX = Integer.parseInt(args[2]);
        int downY = Integer.parseInt(args[3]);

        Oval oval = new Oval(new Point(rightX, rightY), new Point(downX, downY));
        stack.push(oval);
    }

    @Override
    public void save(List<String> rows) {
        Point rightHotPoint = rightHotPoint();
        Point downHotPoint = downHotPoint();

        String line = String.format("%s %d %d %d %d",
                getShapeID(), rightHotPoint.getX(), rightHotPoint.getY(), downHotPoint.getX(), downHotPoint.getY());

        rows.add(line);
    }
}
