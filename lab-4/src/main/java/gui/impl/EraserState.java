package gui.impl;

import gui.Renderer;
import model.GraphicalObject;
import model.impl.DocumentModel;
import util.Point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EraserState extends SimpleState {
    private final DocumentModel model;
    private final Set<GraphicalObject> objectsToErase;
    private final List<Point> strokePoints;

    public EraserState(DocumentModel model) {
        this.model = model;
        this.objectsToErase = new HashSet<>();
        this.strokePoints = new ArrayList<>();
    }

    @Override
    public void mouseDragged(Point mousePoint) {
        strokePoints.add(mousePoint);

        GraphicalObject nearestObject = model.findSelectedGraphicalObject(mousePoint);
        if (nearestObject != null) {
            objectsToErase.add(nearestObject);
        }

        model.notifyListeners();
    }

    @Override
    public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        objectsToErase.forEach(model::removeGraphicalObject);

        strokePoints.clear();
        objectsToErase.clear();

        model.notifyListeners();
    }

    @Override
    public void afterDraw(Renderer r) {
        int numOfStrokes = strokePoints.size();

        for (int i = 1; i < numOfStrokes; i++) {
            r.drawLine(strokePoints.get(i-1), strokePoints.get(i));
        }
    }
}
