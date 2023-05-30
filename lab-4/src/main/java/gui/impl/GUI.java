package gui.impl;

import model.GraphicalObject;
import model.impl.DocumentModel;
import model.impl.LineSegment;
import model.impl.Oval;
import model.impl.Point;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUI extends JFrame {

    private final List<GraphicalObject> objectPrototypes;
    private final DocumentModel documentModel;
    private final JCanvas canvas;

    public GUI(List<GraphicalObject> objectPrototypes) {
        this.objectPrototypes = objectPrototypes;
        this.documentModel = new DocumentModel();
        this.canvas = new JCanvas(documentModel);

        initGUI();
    }

    private void initGUI() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(0, 0);
        setSize(800, 800);
        setTitle("Vector Graphics");

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(canvas, BorderLayout.CENTER);

        initToolBar();
    }

    private void initToolBar() {
        JToolBar toolBar = new JToolBar("tools");
        toolBar.setFloatable(true);

        for (GraphicalObject go: objectPrototypes) {
            toolBar.add(new JButton(go.getShapeName()));
        }

        toolBar.addSeparator();

        getContentPane().add(toolBar, BorderLayout.PAGE_START);
    }
}
