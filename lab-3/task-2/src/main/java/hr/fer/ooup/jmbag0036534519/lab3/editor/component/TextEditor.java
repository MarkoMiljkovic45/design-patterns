package hr.fer.ooup.jmbag0036534519.lab3.editor.component;

import hr.fer.ooup.jmbag0036534519.lab3.editor.model.TextEditorModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Iterator;

public class TextEditor extends JComponent {

    private final TextEditorModel model;

    public TextEditor(String text) {
        model = new TextEditorModel(text);
        model.addCursorObserver(loc -> repaint());

        setFocusable(true);
        initKeyListeners();
    }

    private void initKeyListeners() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_KP_LEFT, KeyEvent.VK_LEFT -> model.moveCursorLeft();
                    case KeyEvent.VK_KP_RIGHT, KeyEvent.VK_RIGHT -> model.moveCursorRight();
                    case KeyEvent.VK_KP_UP, KeyEvent.VK_UP -> model.moveCursorUp();
                    case KeyEvent.VK_KP_DOWN, KeyEvent.VK_DOWN -> model.moveCursorDown();
                    default -> System.out.println("Non arrow key pressed: " + e.getKeyCode());
                }
            }
        });
    }

    public TextEditorModel getModel() {
        return model;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.BLACK);
        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 18);
        g.setFont(font);

        FontMetrics fm = getFontMetrics(font);
        int fontAscent = fm.getAscent();
        int fontHeight = fm.getHeight();

        Iterator<String> iter = model.allLines();
        int i = 0;

         while (iter.hasNext()) {
            g.drawString(iter.next(), 0, fontAscent + fontHeight * i);
            i++;
        }

        paintCursor(g, fm);
    }

    private void paintCursor(Graphics g, FontMetrics fontMetrics) {
        g.setColor(Color.BLACK);

        int height = fontMetrics.getHeight();
        int width  = fontMetrics.stringWidth("a");

        int x = model.getCursorLocation().getColumn();
        int y = model.getCursorLocation().getRow();

        g.drawLine(x * width, (y - 1) * height, x * width, y * height);
    }
}
