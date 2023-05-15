package hr.fer.ooup.jmbag0036534519.lab3.editor.model;

import hr.fer.ooup.jmbag0036534519.lab3.editor.model.observers.UndoManagerObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class UndoManager {
    private final Stack<EditAction> undoStack;
    private final Stack<EditAction> redoStack;
    private final List<UndoManagerObserver> undoManagerObservers;

    public UndoManager() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        undoManagerObservers = new ArrayList<>();
    }

    public void addUndoManagerObserver(UndoManagerObserver observer) {
        undoManagerObservers.add(observer);
    }

    public void notifyUndoStackObservers() {
        undoManagerObservers.forEach(o -> {
            o.undoStackUpdated(undoStack.isEmpty());
            o.redoStackUpdated(redoStack.isEmpty());
        });
    }

    public void undo() {
        EditAction action = undoStack.pop();
        action.executeUndo();
        redoStack.push(action);

        notifyUndoStackObservers();
    }

    public void redo() {
        EditAction action = redoStack.pop();
        action.executeDo();
        undoStack.push(action);

        notifyUndoStackObservers();
    }

    public void push(EditAction action) {
        redoStack.clear();
        undoStack.push(action);

        notifyUndoStackObservers();
    }
}
