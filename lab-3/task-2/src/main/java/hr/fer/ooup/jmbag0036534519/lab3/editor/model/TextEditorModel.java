package hr.fer.ooup.jmbag0036534519.lab3.editor.model;

import hr.fer.ooup.jmbag0036534519.lab3.editor.model.observers.CursorObserver;
import hr.fer.ooup.jmbag0036534519.lab3.editor.model.observers.TextObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class TextEditorModel {

    /**
     * List of lines of text
     */
    private final List<String> lines;
    /**
     * Coordinates (row, column) of the beggining and end of the selection
     */
    private LocationRange selectionRange;
    /**
     * Coordinates (row, column) of the current cursor location, equal to the location of the character in front of the cursor
     */
    private final Location cursorLocation;

    /**
     * List of cursor observers
     */
    private final List<CursorObserver> cursorObservers;

    /**
     * List of text observers
     */
    private final List<TextObserver> textObservers;

    /**
     * The input text will be broken up into a list of lines on the newline character \n
     * @param text to be displayed on the document
     */
    public TextEditorModel(String text) {
        this.lines = new ArrayList<>();
        this.cursorLocation = new Location();
        this.selectionRange = new LocationRange();
        this.cursorObservers = new ArrayList<>();
        this.textObservers = new ArrayList<>();

        Collections.addAll(lines, text.split("\\n"));
    }

    public List<String> getLines() {
        return lines;
    }

    public Location getCursorLocation() {
        return cursorLocation;
    }

    public Iterator<String> allLines() {
        return lines.iterator();
    }

    /**
     * @param start of range
     * @param end of range
     * @return iterator over the range of lines [start, end>
     */
    public Iterator<String> linesRange(int start, int end) {
        return lines.subList(start, end).iterator();
    }

    public void addCursorObserver(CursorObserver observer) {
        cursorObservers.add(observer);
    }

    public void removeCursorObserver(CursorObserver observer) {
        cursorObservers.remove(observer);
    }

    private void notifyCursorObservers() {
        cursorObservers.forEach(o -> o.updateCursorLocation(cursorLocation));
    }

    private boolean moveCursor(int row, int col) {
        if (row > 0 && col >= 0) {
            cursorLocation.setRow(row);
            cursorLocation.setColumn(col);

            return true;
        }

        return false;
    }

    public void moveCursorLeft() {
        if (moveCursor(cursorLocation.getRow(), cursorLocation.getColumn() - 1)) {
            notifyCursorObservers();
        }
    }

    public void moveCursorRight() {
        if (moveCursor(cursorLocation.getRow(), cursorLocation.getColumn() + 1)) {
            notifyCursorObservers();
        }
    }

    public void moveCursorUp() {
        if (moveCursor(cursorLocation.getRow() - 1, cursorLocation.getColumn())) {
            notifyCursorObservers();
        }
    }

    public void moveCursorDown() {
        if (moveCursor(cursorLocation.getRow() + 1, cursorLocation.getColumn())) {
            notifyCursorObservers();
        }
    }

    public void addTextObserver(TextObserver observer) {
        textObservers.add(observer);
    }

    public void removeTextObserver(TextObserver observer) {
        textObservers.remove(observer);
    }

    private void notifyAllTextObservers() {
        textObservers.forEach(TextObserver::updateText);
    }

    private boolean deleteCharAt(int row, int col) {
        try {
            String line = lines.get(row - 1);

            String editedLine;

            if (line.length() == col) {
                editedLine = line.substring(0, col - 1);
            } else {
                editedLine = line.substring(0, col - 1) + line.substring(col + 1);
            }

            lines.set(row - 1, editedLine);
            return true;
        }
        catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * Deletes a character that is before the cursor and moves the cursor left
     */
    public void deleteBefore() {
        if (deleteCharAt(cursorLocation.getRow(), cursorLocation.getColumn())) {
            notifyAllTextObservers();
            moveCursorLeft();
        }
    }

    /**
     * Deletes a character that is after the cursor
     */
    public void deleteAfter() {
        if (deleteCharAt(cursorLocation.getRow(), cursorLocation.getColumn() + 1)) {
            notifyAllTextObservers();
        }
    }

    /**
     * Deletes the text in the given range
     * @param r range of text to be deleted
     */
    public void deleteRange(LocationRange r) {
        //TODO
        notifyAllTextObservers();
    }

    /**
     * @return the current selected range
     */
    public LocationRange getSelectionRange() {
        return selectionRange;
    }

    /**
     * Sets the current selection range
     * @param range to be selected
     */
    public void setSelectionRange(LocationRange range) {
        selectionRange = range;
    }
}
