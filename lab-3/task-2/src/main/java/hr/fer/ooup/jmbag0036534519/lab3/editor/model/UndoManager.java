package hr.fer.ooup.jmbag0036534519.lab3.editor.model;

import java.util.Stack;

public class UndoManager {
    private final Stack<EditAction> undoStack;
    private final Stack<EditAction> redoStack;

    public UndoManager() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }
    public void undo() {
        EditAction action = undoStack.pop();
        action.executeUndo();
        redoStack.push(action);
    }

    public void redo() {
        EditAction action = redoStack.pop();
        action.executeDo();
        undoStack.push(action);
    }

    public void push(EditAction action) {
        redoStack.clear();
        undoStack.push(action);
    }
}
