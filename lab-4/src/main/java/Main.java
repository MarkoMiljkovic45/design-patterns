import gui.impl.GUI;
import model.GraphicalObject;
import model.impl.LineSegment;
import model.impl.Oval;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<GraphicalObject> objects = new ArrayList<>();

        objects.add(new LineSegment());
        objects.add(new Oval());

        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI(objects);
            gui.setVisible(true);
        });
    }
}
