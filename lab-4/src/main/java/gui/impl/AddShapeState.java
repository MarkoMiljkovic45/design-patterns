package gui.impl;

import model.GraphicalObject;
import model.impl.DocumentModel;
import model.impl.Point;

public class AddShapeState extends SimpleState {
    private final GraphicalObject prototype;
    private final DocumentModel model;

    public AddShapeState(DocumentModel model, GraphicalObject prototype) {
        this.model = model;
        this.prototype = prototype;
    }

    @Override
    public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        GraphicalObject object = prototype.duplicate();
        object.translate(mousePoint);
        model.addGraphicalObject(object);
    }
}
