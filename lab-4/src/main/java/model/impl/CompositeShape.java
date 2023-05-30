package model.impl;

import gui.Renderer;
import model.GraphicalObject;
import model.listeners.GraphicalObjectListener;

import java.util.*;

public class CompositeShape implements GraphicalObject {
    private final List<GraphicalObject> objects;
    private final List<GraphicalObjectListener> listeners;
    private boolean selected;

    public CompositeShape(List<GraphicalObject> objects, List<GraphicalObjectListener> listeners, boolean selected) {
        this.objects = objects;
        this.listeners = listeners;
        this.selected = selected;
    }

    public CompositeShape(List<GraphicalObject> objects, boolean selected) {
        this(objects, new ArrayList<>(), selected);
    }

    public CompositeShape(List<GraphicalObject> objects) {
        this(objects, new ArrayList<>(), false);
    }

    public List<GraphicalObject> getObjects() {
        return objects;
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
    public int getNumberOfHotPoints() {
        throw new UnsupportedOperationException("Composite shape doesn't support hot-points");
    }

    @Override
    public Point getHotPoint(int index) {
        throw new UnsupportedOperationException("Composite shape doesn't support hot-points");
    }

    @Override
    public void setHotPoint(int index, Point point) {
        throw new UnsupportedOperationException("Composite shape doesn't support hot-points");
    }

    @Override
    public boolean isHotPointSelected(int index) {
        throw new UnsupportedOperationException("Composite shape doesn't support hot-points");
    }

    @Override
    public void setHotPointSelected(int index, boolean selected) {
        throw new UnsupportedOperationException("Composite shape doesn't support hot-points");
    }

    @Override
    public double getHotPointDistance(int index, Point mousePoint) {
        throw new UnsupportedOperationException("Composite shape doesn't support hot-points");
    }

    @Override
    public void translate(Point delta) {
        objects.forEach(obj -> obj.translate(delta));
        notifyListeners();
    }

    @Override
    public Rectangle getBoundingBox() {
        int x = -1;
        int y = -1;
        int bottomRightX = -1;
        int bottomRightY = -1;

        for (GraphicalObject go: objects) {
            Rectangle goBoundingBox = go.getBoundingBox();

            int bbX = goBoundingBox.getX();
            int bbY = goBoundingBox.getY();

            int bbBottomRightX = bbX + goBoundingBox.getWidth();
            int bbBottomRightY = bbY + goBoundingBox.getHeight();

            if (bbX < x || x == -1) {
                x = bbX;
            }

            if (bbY < y || y == -1) {
                y = bbY;
            }

            if (bbBottomRightX > bottomRightX || bottomRightX == -1) {
                bottomRightX = bbBottomRightX;
            }

            if (bbBottomRightY > bottomRightY || bottomRightY == -1) {
                bottomRightY = bbBottomRightY;
            }
        }

        int width = bottomRightX - x;
        int height = bottomRightY - y;

        return new Rectangle(x, y ,width, height);
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        OptionalDouble distance = objects.stream()
                .mapToDouble(obj -> obj.selectionDistance(mousePoint))
                .min();

        if (distance.isPresent()) {
            return distance.getAsDouble();
        } else {
            return -1;
        }
    }

    @Override
    public void render(Renderer r) {
        objects.forEach(obj -> obj.render(r));
    }

    @Override
    public void addGraphicalObjectListener(GraphicalObjectListener l) {
        listeners.add(l);
    }

    @Override
    public void removeGraphicalObjectListener(GraphicalObjectListener l) {
        listeners.remove(l);
    }

    protected void notifyListeners() {
        listeners.forEach(l -> l.graphicalObjectChanged(this));
    }

    protected void notifySelectionListeners() {
        listeners.forEach(l -> l.graphicalObjectSelectionChanged(this));
    }

    @Override
    public String getShapeName() {
        return "Composite Shape";
    }

    @Override
    public GraphicalObject duplicate() {
        List<GraphicalObject> duplicates = new ArrayList<>();

        for (GraphicalObject go: objects) {
            duplicates.add(go.duplicate());
        }

        return new CompositeShape(duplicates);
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
