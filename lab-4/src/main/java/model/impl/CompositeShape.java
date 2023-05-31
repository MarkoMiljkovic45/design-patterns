package model.impl;

import gui.Renderer;
import model.GraphicalObject;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Stack;

public class CompositeShape extends AbstractGraphicalObject {
    private final List<GraphicalObject> objects;

    public CompositeShape(List<GraphicalObject> objects, boolean selected) {
        super(new Point[0]);
        this.objects = objects;
        setSelected(selected);
    }

    public CompositeShape(List<GraphicalObject> objects) {
        this(objects, false);
    }

    public List<GraphicalObject> getObjects() {
        return objects;
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
