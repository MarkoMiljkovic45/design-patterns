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

    private void notifyCursorObservers() {
        cursorObservers.forEach(o -> o.updateCursorLocation(cursorLocation));
    }

    public void moveCursor(int row, int col) {
        int index = row - 1;

        if (row > 0 && index < lines.size()) {
            String line = lines.get(index);
            if (col >= 0 && col <= line.length()) {
                cursorLocation.setRow(row);
                cursorLocation.setColumn(col);
                notifyCursorObservers();
            } else if (col < 0){
                if (index != 0) {
                    moveCursor(row - 1, lines.get(index-1).length());
                }
            } else if (cursorLocation.getRow() == row) {
                moveCursor(row + 1, 0);
            } else {
                moveCursor(row, lines.get(index).length());
            }
        }
    }

    public void moveCursor(Location loc) {
        moveCursor(loc.getRow(), loc.getColumn());
    }

    public void moveCursorLeft() {
        moveCursor(cursorLocation.getRow(), cursorLocation.getColumn() - 1);
    }

    public void moveCursorRight() {
        moveCursor(cursorLocation.getRow(), cursorLocation.getColumn() + 1);
    }

    public void moveCursorUp() {
        moveCursor(cursorLocation.getRow() - 1, cursorLocation.getColumn());
    }

    public void moveCursorDown() {
        moveCursor(cursorLocation.getRow() + 1, cursorLocation.getColumn());
    }

    public void addTextObserver(TextObserver observer) {
        textObservers.add(observer);
    }

    private void notifyAllTextObservers() {
        textObservers.forEach(TextObserver::updateText);
    }

    private void deleteCharAt(int row, int col) {
        int index = row - 1;

        if (index < 0 || index > lines.size()) {
            return;
        }

        String line = lines.get(index);

        if (col > 0 && col <= line.length()) {
            String editedLine;

            if (line.length() == col) {
                editedLine = line.substring(0, col - 1);
            } else {
                editedLine = line.substring(0, col - 1) + line.substring(col);
            }

            lines.set(row - 1, editedLine);
            notifyAllTextObservers();
        }
        if (col == 0 && index > 0) {
            lines.remove(index);
            lines.set(index-1, lines.get(index-1) + line);
            notifyAllTextObservers();
        }
        if (col > line.length() && index + 1 < lines.size()) {
            lines.set(index, line + lines.get(index+1));
            lines.remove(index+1);
            notifyAllTextObservers();
        }
    }

    /**
     * Deletes a character that is before the cursor and moves the cursor left
     */
    public void deleteBefore() {
        int row = cursorLocation.getRow();
        int col = cursorLocation.getColumn();

        moveCursorLeft();
        deleteCharAt(row, col);
    }

    /**
     * Deletes a character that is after the cursor
     */
    public void deleteAfter() {
        deleteCharAt(cursorLocation.getRow(), cursorLocation.getColumn() + 1);
    }

    /**
     * Deletes the text in the given range
     * @param r range of text to be deleted
     */
    public void deleteRange(LocationRange r) {
        int startRow;
        int startCol;
        int endRow;
        int endCol;

        if (r.getStart().compareTo(r.getEnd()) < 0) {
            startRow = r.getStart().getRow();
            startCol = r.getStart().getColumn();
            endRow = r.getEnd().getRow();
            endCol = r.getEnd().getColumn();
        } else {
            startRow = r.getEnd().getRow();
            startCol = r.getEnd().getColumn();
            endRow = r.getStart().getRow();
            endCol = r.getStart().getColumn();
        }

        int index = startRow - 1;

        if (index > lines.size()) {
            return;
        }

        if (startRow == endRow) {
            String line = lines.get(index);
            String editedLine = "";

            if (startCol < line.length()) {
                editedLine += line.substring(0, startCol);
            }

            if (endCol < line.length()) {
                editedLine += line.substring(endCol);
            }

            lines.set(index, editedLine);
        } else {
            //First line
            String firstLine = lines.get(index);
            String editedLine = "";

            if (startCol < firstLine.length()) {
                editedLine += firstLine.substring(0, startCol);
            }

            lines.set(index++, editedLine);

            //Middle lines
            while (index < --endRow) {
                lines.remove(index);
            }

            //Last Line
            String lastLine = lines.get(index);
            editedLine = "";

            if (endCol < lastLine.length()) {
                editedLine += lastLine.substring(endCol);
            }

            lines.remove(index--);
            lines.set(index, lines.get(index) + editedLine);
        }

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

    /**
     * Inserts the character at the cursor location
     * @param c character to be inserted
     */
    public void insert(char c) {
        int row = cursorLocation.getRow();
        int col = cursorLocation.getColumn();

        int index = row - 1;

        if (index < 0) {
            return;
        }

        if (index > lines.size()) {
            lines.add(index, Character.toString(c));
        }

        String line = lines.get(index);

        if (col > 0 && col < line.length()) {
            String firstPart = line.substring(0, col);
            String secondPart = line.substring(col);

            if (c == '\n') {
                lines.set(index, firstPart);
                lines.add(index + 1, secondPart);
            } else {
                lines.set(index, firstPart + c + secondPart);
            }
        } else if (col == 0) {
            if (c == '\n') {
                lines.add(index, "");
            } else {
                lines.set(index, c + line);
            }
        } else if (col == line.length()) {
            if (c == '\n') {
                lines.add(index+1, "");
            } else {
                lines.set(index, line + c);
            }
        } else {
            return;
        }

        notifyAllTextObservers();
        moveCursorRight();
    }

    /**
     * Inserts text at cursor location (potentially multiple lines) at the cursor location
     * @param text to be inserted
     */
    public void insert(String text) {
        char[] chars = text.toCharArray();

        for (char c: chars) {
            insert(c);
        }
    }

    public String getTextFromRange(LocationRange r) {
        int startRow;
        int startCol;
        int endRow;
        int endCol;

        if (r.getStart().compareTo(r.getEnd()) < 0) {
            startRow = r.getStart().getRow();
            startCol = r.getStart().getColumn();
            endRow = r.getEnd().getRow();
            endCol = r.getEnd().getColumn();
        } else {
            startRow = r.getEnd().getRow();
            startCol = r.getEnd().getColumn();
            endRow = r.getStart().getRow();
            endCol = r.getStart().getColumn();
        }

        int index = startRow - 1;

        if (index > lines.size()) {
            return "";
        }

        if (startRow == endRow) {
            String line = lines.get(index);
            return line.substring(startCol, endCol);
        } else {
            StringBuilder selection = new StringBuilder();

            //First line
            String firstLine = lines.get(index++);
            selection.append(firstLine.substring(startCol));

            //Middle lines
            while (index < endRow - 1) {
                selection.append(lines.get(index++));
            }

            //Last Line
            String lastLine = lines.get(index);
            selection.append(lastLine, 0, endCol);

            return selection.toString();
        }
    }
}
