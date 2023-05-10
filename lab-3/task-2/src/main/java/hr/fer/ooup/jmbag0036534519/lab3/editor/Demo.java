package hr.fer.ooup.jmbag0036534519.lab3.editor;

import hr.fer.ooup.jmbag0036534519.lab3.editor.component.TextEditor;

import javax.swing.*;
import java.awt.*;

public class Demo extends JFrame {

    public Demo(String text) {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(0, 0);
        setSize(800, 800);
        setTitle("Text Editor Demo");

        initGui(text);
    }

    private void initGui(String text) {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        TextEditor textEditor = new TextEditor(text);

        cp.add(textEditor, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        String text = """
                First line
                Second line
                Third line""";

        SwingUtilities.invokeLater(() -> new Demo(text).setVisible(true));
    }
}
