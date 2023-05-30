package model.impl;

import model.GraphicalObject;
import gui.Renderer;
import util.GeometryUtil;

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
        //TODO
        return null;
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {
        //TODO
    }

    @Override
    public void save(List<String> rows) {
        //TODO
    }
}
