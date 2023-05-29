package hr.fer.ooup.jmbag0036534519.lab3.editor.model.observers;

public interface UndoManagerObserver {
    void undoStackUpdated(boolean isEmpty);
    void redoStackUpdated(boolean isEmpty);
}
