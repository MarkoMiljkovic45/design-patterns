package gui.impl;

import gui.Renderer;
import model.GraphicalObject;
import model.impl.DocumentModel;

import java.util.List;

import javax.swing.*;
import java.awt.*;

public class JCanvas extends JComponent {

    private final DocumentModel documentModel;

    public JCanvas(DocumentModel documentMode) {
        this.documentModel = documentMode;
        documentModel.addDocumentModelListener(this::repaint);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        Graphics2D g2d = (Graphics2D)g;
        Renderer renderer = new G2DRendererImpl(g2d);

        List<GraphicalObject> objects = documentModel.list();
        for (GraphicalObject go: objects) {
            go.render(renderer);
        }
    }
}
