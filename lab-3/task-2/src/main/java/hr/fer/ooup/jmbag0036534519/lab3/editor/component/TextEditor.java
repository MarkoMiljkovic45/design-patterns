package hr.fer.ooup.jmbag0036534519.lab3.editor.component;

import hr.fer.ooup.jmbag0036534519.lab3.editor.model.ClipboardStack;
import hr.fer.ooup.jmbag0036534519.lab3.editor.model.Location;
import hr.fer.ooup.jmbag0036534519.lab3.editor.model.LocationRange;
import hr.fer.ooup.jmbag0036534519.lab3.editor.model.TextEditorModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Iterator;

public class TextEditor extends JComponent {

    private final TextEditorModel model;
    private final ClipboardStack clipboardStack;

    public TextEditor(String text) {
        model = new TextEditorModel(text);
        clipboardStack = new ClipboardStack();
        model.addCursorObserver(loc -> repaint());
        model.addTextObserver(this::repaint);

        setFocusable(true);
        addKeyListener(new TextEditorKeyListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.BLACK);
        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 18);
        g.setFont(font);

        FontMetrics fm = getFontMetrics(font);

        paintLines(g, fm);
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

    private void paintLines(Graphics g, FontMetrics fontMetrics) {
        int fontAscent = fontMetrics.getAscent();
        int fontHeight = fontMetrics.getHeight();
        int fontWidth  = fontMetrics.stringWidth("a");

        LocationRange selection = model.getSelectionRange();
        Location selectionStart;
        Location selectionEnd;

        if (selection.getStart().compareTo(selection.getEnd()) < 0) {
            selectionStart = selection.getStart();
            selectionEnd   = selection.getEnd();
        } else {
            selectionStart = selection.getEnd();
            selectionEnd   = selection.getStart();
        }

        Iterator<String> iterator = model.allLines();
        int line = 0;

        while (iterator.hasNext()) {
            char[] lineString = iterator.next().toCharArray();
            int lineStringLen = lineString.length;

            for (int col = 0; col < lineStringLen; col++) {
                Location charLoc = new Location(line + 1, col);

                boolean inSelection = charLoc.compareTo(selectionStart) >= 0 &&
                                      charLoc.compareTo(selectionEnd) < 0;

                if (inSelection) {
                    g.setColor(Color.BLUE);
                    g.fillRect(col * fontWidth, fontHeight * line, fontWidth, fontHeight);

                    g.setColor(Color.WHITE);
                    g.drawChars(lineString, col, 1, col * fontWidth, fontAscent + fontHeight * line);

                    g.setColor(Color.BLACK);
                } else {
                    g.drawChars(lineString, col, 1, col * fontWidth, fontAscent + fontHeight * line);
                }
            }

            line++;
        }
    }

    private class TextEditorKeyListener extends KeyAdapter {

        private volatile boolean isShiftPressed;
        private volatile boolean isCtrlPressed;

        public TextEditorKeyListener(boolean isShiftPressed, boolean isCtrlPressed) {
            this.isShiftPressed = isShiftPressed;
            this.isCtrlPressed = isCtrlPressed;
        }

        public TextEditorKeyListener() {
            this(false, false);
        }

        public void setShiftPressed(boolean shiftPressed) {
            isShiftPressed = shiftPressed;
        }

        public void setCtrlPressed(boolean ctrlPressed) {
            isCtrlPressed = ctrlPressed;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_SHIFT -> setShiftPressed(true);
                case KeyEvent.VK_CONTROL -> setCtrlPressed(true);
                case KeyEvent.VK_KP_LEFT, KeyEvent.VK_LEFT -> left();
                case KeyEvent.VK_KP_RIGHT, KeyEvent.VK_RIGHT -> right();
                case KeyEvent.VK_KP_UP, KeyEvent.VK_UP -> up();
                case KeyEvent.VK_KP_DOWN, KeyEvent.VK_DOWN -> down();
                case KeyEvent.VK_DELETE -> del();
                case KeyEvent.VK_BACK_SPACE -> backspace();
                default -> key(e);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_SHIFT -> setShiftPressed(false);
                case KeyEvent.VK_CONTROL -> setCtrlPressed(false);
            }
        }

        private void left() {
            model.moveCursorLeft();
            updateSelection();
        }

        private void right() {
            model.moveCursorRight();
            updateSelection();
        }

        private void up() {
            model.moveCursorUp();
            updateSelection();
        }

        private void down() {
            model.moveCursorDown();
            updateSelection();
        }

        private void updateSelection() {
            LocationRange selection = model.getSelectionRange();
            Location cursorLocation = model.getCursorLocation();

            if (!isShiftPressed) {
                selection.setStart(cursorLocation);
                selection.setEnd(cursorLocation);
                return;
            }

            selection.setEnd(cursorLocation);
        }

        private void del() {
            LocationRange selection = model.getSelectionRange();

            if (selection.getStart().equals(selection.getEnd())) {
                model.deleteAfter();
            } else {
                deleteRange();
            }
        }

        private void backspace() {
            LocationRange selection = model.getSelectionRange();

            if (selection.getStart().equals(selection.getEnd())) {
                model.deleteBefore();
            } else {
                deleteRange();
            }
        }

        private void deleteRange() {
            LocationRange selection = model.getSelectionRange();

            model.deleteRange(selection);

            if (selection.getStart().compareTo(selection.getEnd()) < 0) {
                selection.setEnd(selection.getStart());
                model.moveCursor(selection.getStart());
            } else {
                selection.setStart(selection.getEnd());
                model.moveCursor(selection.getEnd());
            }
        }

        private void key(KeyEvent e) {
            if (isCtrlPressed) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_C -> copy();
                    case KeyEvent.VK_X -> cut();
                    case KeyEvent.VK_V -> paste();
                    default -> insertChar(e.getKeyChar());
                }
            } else {
                insertChar(e.getKeyChar());
            }
        }

        private void insertChar(char c) {
            LocationRange selection = model.getSelectionRange();

            if (!selection.getStart().equals(selection.getEnd())) {
                deleteRange();
            }

            model.insert(c);
        }

        private void copy() {
            LocationRange selection = model.getSelectionRange();

            if (!selection.getStart().equals(selection.getEnd())) {
                clipboardStack.push(model.getTextFromRange(selection));
            }
        }

        private void cut() {
            LocationRange selection = model.getSelectionRange();

            if (!selection.getStart().equals(selection.getEnd())) {
                clipboardStack.push(model.getTextFromRange(selection));
                deleteRange();
            }
        }

        private void paste() {
            if (clipboardStack.isEmpty()) {
                return;
            }

            String text = clipboardStack.peek();
            LocationRange selection = model.getSelectionRange();

            if (!selection.getStart().equals(selection.getEnd())) {
                deleteRange();
            }

            model.insert(text);

            if (isShiftPressed) {
                clipboardStack.pop();
            }
        }
    }
}
