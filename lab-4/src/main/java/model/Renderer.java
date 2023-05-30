package model;

import model.impl.Point;

public interface Renderer {
    void drawLine(Point s, Point e);
    void fillPolygon(Point[] points);
}
