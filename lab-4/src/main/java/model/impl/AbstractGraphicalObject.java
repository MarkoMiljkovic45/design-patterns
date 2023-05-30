package model.impl;

import model.GraphicalObject;
import model.listeners.GraphicalObjectListener;
import util.GeometryUtil;

import java.util.List;

public abstract class AbstractGraphicalObject implements GraphicalObject {
    private final Point[] hotPoints;
    private final boolean[] hotPointSelected;
    private boolean selected;
    List<GraphicalObjectListener> listeners;

    protected AbstractGraphicalObject(Point[] hotPoints) {
        this.hotPoints = hotPoints;
        this.hotPointSelected = new boolean[hotPoints.length];
    }

    @Override
    public Point getHotPoint(int index) {
        return hotPoints[index];
    }

    @Override
    public void setHotPoint(int index, Point hotPoint) {
        hotPoints[index] = hotPoint;
    }

    @Override
    public int getNumberOfHotPoints() {
        return hotPoints.length;
    }

    @Override
    public double getHotPointDistance(int index, Point point) {
        return GeometryUtil.distanceFromPoint(hotPoints[index], point);
    }

    @Override
    public boolean isHotPointSelected(int index) {
        return hotPointSelected[index];
    }

    @Override
    public void setHotPointSelected(int index, boolean selected) {
        hotPointSelected[index] = selected;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
        notifySelectionListeners();
    }

    @Override
    public void translate(Point diff) {
        for (int i = 0; i < hotPoints.length; i++) {
            hotPoints[i] = hotPoints[i].translate(diff);
        }
        notifyListeners();
    }

    @Override
    public void addGraphicalObjectListener(GraphicalObjectListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeGraphicalObjectListener(GraphicalObjectListener listener) {
        listeners.remove(listener);
    }

    protected void notifyListeners() {
        listeners.forEach(l -> l.graphicalObjectChanged(this));
    }

    protected void notifySelectionListeners() {
        listeners.forEach(l -> l.graphicalObjectSelectionChanged(this));
    }
}
