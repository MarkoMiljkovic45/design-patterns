package gui.impl;

import gui.Renderer;
import gui.State;
import model.GraphicalObject;
import model.impl.DocumentModel;
import model.impl.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GUI extends JFrame {

    private final List<GraphicalObject> objects;
    private final DocumentModel documentModel;
    private final JCanvas canvas;
    private State currentState;

    public GUI(List<GraphicalObject> objects) {
        this.objects = objects;
        this.documentModel = new DocumentModel();
        this.canvas = new JCanvas();
        this.currentState = new IdleState();

        initGUI();
    }

    private void initGUI() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(0, 0);
        setSize(800, 800);
        setTitle("Vector Graphics");

        getContentPane().setLayout(new BorderLayout());

        canvas.setFocusable(true);
        getContentPane().add(canvas, BorderLayout.CENTER);

        initToolBar();
    }

    private void initToolBar() {
        JToolBar toolBar = new JToolBar("tools");
        toolBar.setFloatable(true);

        for (GraphicalObject go: objects) {
            Action objectAction = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String shapeName = (String) getValue(NAME);
                    objects.stream()
                            .filter(obj -> obj.getShapeName().equals(shapeName))
                            .findFirst().ifPresent(object -> {
                                currentState.onLeaving();
                                currentState = new AddShapeState(documentModel, object);
                            });
                }
            };

            objectAction.putValue(Action.NAME, go.getShapeName());
            toolBar.add(objectAction);
        }

        toolBar.addSeparator();

        selectAction.putValue(Action.NAME, "Selektiraj");
        toolBar.add(selectAction);

        getContentPane().add(toolBar, BorderLayout.PAGE_START);
    }

    private final Action selectAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            currentState.onLeaving();
            currentState = new SelectShapeState(documentModel);
        }
    };

    public class JCanvas extends JComponent {

        public JCanvas() {
            documentModel.addDocumentModelListener(this::repaint);
            registerKeyListener();
            registerMouseListener();
            registerMouseMotionListener();
            requestFocusInWindow();
        }

        private void registerKeyListener() {
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int code = e.getKeyCode();
                    if (code == KeyEvent.VK_ESCAPE) {
                        currentState.onLeaving();
                        currentState = new IdleState();
                    } else {
                        currentState.keyPressed(e.getKeyCode());
                    }
                    e.consume();
                }
            });
        }

        private void registerMouseListener() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    currentState.mouseDown(
                            new Point(e.getX(), e.getY()),
                            e.isShiftDown(),
                            e.isControlDown());
                    e.consume();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    currentState.mouseUp(
                            new Point(e.getX(), e.getY()),
                            e.isShiftDown(),
                            e.isControlDown());
                    e.consume();
                }
            });
        }

        private void registerMouseMotionListener() {
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    currentState.mouseDragged(new Point(e.getX(), e.getY()));
                    e.consume();
                }
            });
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
                currentState.afterDraw(renderer, go);
            }

            currentState.afterDraw(renderer);
            requestFocusInWindow();
        }
    }
}
