package model.impl;

import gui.Renderer;
import model.GraphicalObject;
import util.Point;
import util.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Stack;

public class CompositeShape extends AbstractGraphicalObject {
    private final List<GraphicalObject> children;

    public CompositeShape(List<GraphicalObject> children, boolean selected) {
        super(new Point[0]);
        this.children = children;
        setSelected(selected);
    }

    public CompositeShape(List<GraphicalObject> children) {
        this(children, false);
    }

    public CompositeShape() {
        this(new ArrayList<>(), false);
    }

    public List<GraphicalObject> getChildren() {
        return children;
    }

    @Override
    public void translate(Point delta) {
        children.forEach(obj -> obj.translate(delta));
        notifyListeners();
    }

    @Override
    public Rectangle getBoundingBox() {
        int x = -1;
        int y = -1;
        int bottomRightX = -1;
        int bottomRightY = -1;

        for (GraphicalObject go: children) {
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
        OptionalDouble distance = children.stream()
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
        children.forEach(obj -> obj.render(r));
    }

    @Override
    public String getShapeName() {
        return "Composite Shape";
    }

    @Override
    public GraphicalObject duplicate() {
        List<GraphicalObject> duplicates = new ArrayList<>();

        for (GraphicalObject go: children) {
            duplicates.add(go.duplicate());
        }

        return new CompositeShape(duplicates);
    }

    @Override
    public String getShapeID() {
        return "@COMP";
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {
        int numOfChildren = Integer.parseInt(data);
        CompositeShape compositeShape = new CompositeShape();

        for (int i = 0; i < numOfChildren; i++) {
            GraphicalObject go = stack.pop();
            compositeShape.children.add(go);
        }

        stack.push(compositeShape);
    }

    @Override
    public void save(List<String> rows) {
        children.forEach(o -> o.save(rows));

        String line = getShapeID() + " " + children.size();

        rows.add(line);
    }
}
