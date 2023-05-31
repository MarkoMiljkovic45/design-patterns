package gui.impl;

import gui.Renderer;
import model.GraphicalObject;
import model.impl.CompositeShape;
import model.impl.DocumentModel;
import model.impl.Point;
import model.impl.Rectangle;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SelectShapeState extends SimpleState {
    private static final int HOT_POINT_SIZE = 6;

    private final DocumentModel model;
    private GraphicalObject selectedObject;
    private int hotPointIndex;
    private Point previousPoint;

    public SelectShapeState(DocumentModel model) {
        this.model = model;
        this.selectedObject = null;
        this. hotPointIndex = -1;
        this.previousPoint = null;
    }

    @Override
    public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        if (!ctrlDown) {
            model.deselectAll();
            selectedObject = null;
        }

        selectedObject = model.findSelectedGraphicalObject(mousePoint);

        if (selectedObject != null) {
            previousPoint = mousePoint;
            selectedObject.setSelected(!selectedObject.isSelected());
            hotPointIndex = model.findSelectedHotPoint(selectedObject, mousePoint);
            if (hotPointIndex >= 0) {
                selectedObject.setHotPointSelected(hotPointIndex, true);
            }
        }
    }

    @Override
    public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        if (hotPointIndex >= 0) {
            selectedObject.setHotPointSelected(hotPointIndex, false);
            hotPointIndex = -1;
            previousPoint = null;
        }
    }

    @Override
    public void mouseDragged(Point mousePoint) {
        if (selectedObject == null) return;

        if (hotPointIndex >= 0) {
            selectedObject.setHotPoint(hotPointIndex, mousePoint);
        } else {
            if (previousPoint != null) {
                selectedObject.translate(mousePoint.difference(previousPoint));
            }
            previousPoint = mousePoint;
        }
    }

    @Override
    public void keyPressed(int keyCode) {
        List<GraphicalObject> selectedObjects = model.getSelectedObjects();

        if (selectedObjects.isEmpty()) return;

        switch (keyCode) {
            case KeyEvent.VK_UP -> selectedObjects.forEach(obj -> obj.translate(new Point(0, -1)));
            case KeyEvent.VK_DOWN -> selectedObjects.forEach(obj -> obj.translate(new Point(0, 1)));
            case KeyEvent.VK_LEFT -> selectedObjects.forEach(obj -> obj.translate(new Point(-1, 0)));
            case KeyEvent.VK_RIGHT -> selectedObjects.forEach(obj -> obj.translate(new Point(1, 0)));
            case KeyEvent.VK_PLUS, KeyEvent.VK_ADD -> model.increaseZ(selectedObject);
            case KeyEvent.VK_MINUS, KeyEvent.VK_SUBTRACT -> model.decreaseZ(selectedObject);
            case KeyEvent.VK_G -> {
                List<GraphicalObject> group = new ArrayList<>(selectedObjects);
                GraphicalObject composite = new CompositeShape(group, true);

                group.forEach(model::removeGraphicalObject);
                model.addGraphicalObject(composite);
            }
            case KeyEvent.VK_U -> {
                if (selectedObject != null) {
                    if (selectedObject instanceof CompositeShape group) {
                        List<GraphicalObject> objects = group.getObjects();
                        objects.forEach(obj -> obj.setSelected(true));
                        model.removeGraphicalObject(group);
                        objects.forEach(model::addGraphicalObject);
                    }
                }
            }
        }
    }

    @Override
    public void afterDraw(Renderer r, GraphicalObject go) {
        if (!go.isSelected()) return;

        Rectangle boundingBox = go.getBoundingBox();

        int x = boundingBox.getX();
        int y = boundingBox.getY();
        int width = boundingBox.getWidth();
        int height = boundingBox.getHeight();

        Point topLeft = new Point(x, y);
        Point topRight = new Point(x + width, y);
        Point bottomRight = new Point(x + width, y + height);
        Point bottomLeft = new Point(x, y + height);

        r.drawLine(topLeft, topRight);
        r.drawLine(topRight, bottomRight);
        r.drawLine(bottomRight, bottomLeft);
        r.drawLine(bottomLeft, topLeft);

        if (go instanceof CompositeShape) return;

        if (model.getSelectedObjects().size() == 1) {
            int delta = HOT_POINT_SIZE / 2;
            int numOfHotPoints = go.getNumberOfHotPoints();

            for (int i = 0; i < numOfHotPoints; i++) {
                Point hotPoint = go.getHotPoint(i);
                int hpX = hotPoint.getX();
                int hpY = hotPoint.getY();

                Point topLeftHP = new Point(hpX - delta, hpY - delta);
                Point topRightHP = new Point(hpX + delta, hpY - delta);
                Point bottomLeftHP = new Point(hpX - delta, hpY + delta);
                Point bottomRightHP = new Point(hpX + delta, hpY + delta);

                r.drawLine(topLeftHP, topRightHP);
                r.drawLine(topRightHP, bottomRightHP);
                r.drawLine(bottomRightHP, bottomLeftHP);
                r.drawLine(bottomLeftHP, topLeftHP);
            }
        }
    }

    @Override
    public void onLeaving() {
        model.deselectAll();
    }
}
