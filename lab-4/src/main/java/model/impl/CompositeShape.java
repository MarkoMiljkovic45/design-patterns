package model.impl;

import gui.Renderer;
import model.GraphicalObject;
import model.listeners.GraphicalObjectListener;

import java.util.List;
import java.util.Stack;

//TODO Entire class, maybe extend abstract graphical object?
public class CompositeShape implements GraphicalObject {
    private final List<GraphicalObject> objects;

    public CompositeShape(List<GraphicalObject> objects, boolean b) {
        this.objects = objects;
    }

    public List<GraphicalObject> getObjects() {
        return objects;
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public void setSelected(boolean selected) {

    }

    @Override
    public int getNumberOfHotPoints() {
        return 0;
    }

    @Override
    public Point getHotPoint(int index) {
        return null;
    }

    @Override
    public void setHotPoint(int index, Point point) {

    }

    @Override
    public boolean isHotPointSelected(int index) {
        return false;
    }

    @Override
    public void setHotPointSelected(int index, boolean selected) {

    }

    @Override
    public double getHotPointDistance(int index, Point mousePoint) {
        return 0;
    }

    @Override
    public void translate(Point delta) {

    }

    @Override
    public Rectangle getBoundingBox() {
        return null;
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        return 0;
    }

    @Override
    public void render(Renderer r) {

    }

    @Override
    public void addGraphicalObjectListener(GraphicalObjectListener l) {

    }

    @Override
    public void removeGraphicalObjectListener(GraphicalObjectListener l) {

    }

    @Override
    public String getShapeName() {
        return null;
    }

    @Override
    public GraphicalObject duplicate() {
        return null;
    }

    @Override
    public String getShapeID() {
        return null;
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {

    }

    @Override
    public void save(List<String> rows) {

    }
}
