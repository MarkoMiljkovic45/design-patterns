package hr.fer.ooup.jmbag0036534519.lab3.editor;

import hr.fer.ooup.jmbag0036534519.lab3.editor.component.MyTextArea;
import hr.fer.ooup.jmbag0036534519.lab3.editor.model.*;
import hr.fer.ooup.jmbag0036534519.lab3.editor.model.observers.UndoManagerObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextEditor extends JFrame {

    private final MyTextArea myTextArea;
    private final TextEditorModel model;
    private final UndoManager undoManager;
    private final ClipboardStack clipboardStack;

    public TextEditor(String text) {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(0, 0);
        setSize(800, 800);
        setTitle("My Notepad");

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        myTextArea = new MyTextArea(text);
        model = myTextArea.getModel();
        undoManager = myTextArea.getUndoManager();
        clipboardStack = new ClipboardStack();

        undoManager.addUndoManagerObserver(new UndoManagerObserver() {
            @Override
            public void undoStackUpdated(boolean isEmpty) {
                undoAction.setEnabled(!isEmpty);
            }

            @Override
            public void redoStackUpdated(boolean isEmpty) {
                redoAction.setEnabled(!isEmpty);
            }
        });

        clipboardStack.addClipboardObserver(() -> {
            pasteAction.setEnabled(!clipboardStack.isEmpty());
            pasteAndTakeAction.setEnabled(!clipboardStack.isEmpty());
            deleteSelectionAction.setEnabled(!clipboardStack.isEmpty());
        });

        model.addCursorObserver((loc) -> {
            LocationRange selection = model.getSelectionRange();
            cutAction.setEnabled(!selection.getStart().equals(selection.getEnd()));
            copyAction.setEnabled(!selection.getStart().equals(selection.getEnd()));
        });

        cp.add(myTextArea, BorderLayout.CENTER);

        initActions();
        initMenus();
    }

    public TextEditor() {
        this("");
    }

    private final Action openDocumentAction = new AbstractAction("Open") {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Open file");

            if(fileChooser.showOpenDialog(TextEditor.this) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File fileName = fileChooser.getSelectedFile();
            Path filePath = fileName.toPath();

            if(!Files.isReadable(filePath)) {
                errorMessage("The file " + fileName.getAbsolutePath() + " doesn't exist!");
                return;
            }

            try {
                model.loadDocument(filePath);
                repaint();
            } catch(Exception ex) {
                errorMessage("Error while reading file: " + fileName.getAbsolutePath());
            }
        }
    };

    private final Action saveDocumentAction = new AbstractAction("Save") {
        @Override
        public void actionPerformed(ActionEvent e) {
            Path filePath;

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save file");

            if(fileChooser.showOpenDialog(TextEditor.this) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            filePath = fileChooser.getSelectedFile().toPath();

            try {
                model.saveDocument(filePath);
            } catch (IllegalArgumentException | IOException exception) {
                errorMessage(exception.getMessage());
            }
        }
    };

    private final Action exitApplicationAction = new AbstractAction("Exit") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            dispose();
        }
    };

    private final Action undoAction = new AbstractAction("Undo") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            undoManager.undo();
        }
    };

    private final Action redoAction = new AbstractAction("Redo") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            undoManager.redo();
        }
    };

    private final Action cutAction = new AbstractAction("Cut") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            LocationRange selection = model.getSelectionRange();

            String text = model.getTextFromRange(selection);
            clipboardStack.push(text);
            deleteRange(selection);
        }
    };

    private final Action copyAction = new AbstractAction("Copy") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            clipboardStack.push(model.getTextFromRange(model.getSelectionRange()));
        }
    };

    private final Action pasteAction = new AbstractAction("Paste") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String text = clipboardStack.peek();
            LocationRange selection = model.getSelectionRange();

            deleteRange(selection);

            model.insert(text);
        }
    };

    private final Action pasteAndTakeAction = new AbstractAction("Paste and take") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String text = clipboardStack.pop();
            LocationRange selection = model.getSelectionRange();

            deleteRange(selection);

            model.insert(text);
        }
    };

    private final Action deleteSelectionAction = new AbstractAction("Delete Selection") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            myTextArea.getKeyListener().del();
        }
    };

    private final Action clearDoctumentAction = new AbstractAction("Clear document") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            model.clear();
            repaint();
        }
    };

    private final Action moveToDocumentStartAction = new AbstractAction("Move to start of document") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            model.moveCursorToStart();
        }
    };

    private final Action moveToDocumentEndAction = new AbstractAction("Move to end of document") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            model.moveCursorToEnd();
        }
    };

    private void deleteRange(LocationRange range) {
        if (!range.getStart().equals(range.getEnd())) {
            myTextArea.getKeyListener().addDeleteRangeAction();
            model.deleteRange(range);
        }
    }

    private void errorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void initActions() {
        openDocumentAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control O"));

        saveDocumentAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control S"));

        exitApplicationAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("alt F4"));

        undoAction.setEnabled(false);
        undoAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control Z"));

        redoAction.setEnabled(false);
        redoAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control Y"));

        cutAction.setEnabled(false);
        cutAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control X"));

        copyAction.setEnabled(false);
        copyAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control C"));

        pasteAction.setEnabled(false);
        pasteAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control V"));

        pasteAndTakeAction.setEnabled(false);
        pasteAndTakeAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control shift V"));

        deleteSelectionAction.setEnabled(false);

        moveToDocumentStartAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("home"));

        moveToDocumentEndAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("end"));
    }

    private void initMenus() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        fileMenu.add(openDocumentAction);
        fileMenu.add(saveDocumentAction);
        fileMenu.add(exitApplicationAction);

        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);

        editMenu.add(undoAction);
        editMenu.add(redoAction);
        editMenu.add(cutAction);
        editMenu.add(copyAction);
        editMenu.add(pasteAction);
        editMenu.add(pasteAndTakeAction);
        editMenu.add(deleteSelectionAction);
        editMenu.add(clearDoctumentAction);

        JMenu moveMenu = new JMenu("Move");
        menuBar.add(moveMenu);

        moveMenu.add(moveToDocumentStartAction);
        moveMenu.add(moveToDocumentEndAction);

        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TextEditor().setVisible(true));
    }
}
