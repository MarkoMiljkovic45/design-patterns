package model.impl;

import model.GraphicalObject;
import gui.Renderer;
import util.GeometryUtil;
import util.Point;
import util.Rectangle;

import java.util.List;
import java.util.Stack;

public class LineSegment extends AbstractGraphicalObject {

    public LineSegment(Point start, Point end) {
        super(new Point[] {start, end});
    }

    public LineSegment() {
        this(new Point(0, 0), new Point(0, 10));
    }

    private Point start() {
        return getHotPoint(0);
    }

    private Point end() {
        return getHotPoint(1);
    }

    @Override
    public Rectangle getBoundingBox() {
        int x = Math.min(start().getX(), end().getX());
        int y = Math.min(start().getY(), end().getY());
        
        int width = Math.max(start().getX() - x, end().getX() - x);
        int height = Math.max(start().getY() - y, end().getY() - y);
        
        return new Rectangle(x, y, width, height);
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        return GeometryUtil.distanceFromLineSegment(start(), end(), mousePoint);
    }

    @Override
    public void render(Renderer r) {
        r.drawLine(start(), end());
    }

    @Override
    public String getShapeName() {
        return "Line";
    }

    @Override
    public GraphicalObject duplicate() {
        return new LineSegment(
                new Point(start().getX(), start().getY()),
                new Point(end().getX(), end().getY())
        );
    }

    @Override
    public String getShapeID() {
        return "@LINE";
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {
        String[] args = data.split(" ");

        int startX = Integer.parseInt(args[0]);
        int startY = Integer.parseInt(args[1]);

        int endX = Integer.parseInt(args[2]);
        int endY = Integer.parseInt(args[3]);

        LineSegment lineSegment = new LineSegment(new Point(startX, startY), new Point(endX, endY));
        stack.push(lineSegment);
    }

    @Override
    public void save(List<String> rows) {
        Point start = start();
        Point end = end();

        String line = String.format("%s %d %d %d %d",
                getShapeID(), start.getX(), start.getY(), end.getX(), end.getY());

        rows.add(line);
    }
}
