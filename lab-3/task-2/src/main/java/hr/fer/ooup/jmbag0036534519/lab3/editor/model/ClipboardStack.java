package hr.fer.ooup.jmbag0036534519.lab3.editor.model;

import hr.fer.ooup.jmbag0036534519.lab3.editor.model.observers.ClipboardObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ClipboardStack {

    private final Stack<String> texts;
    private final List<ClipboardObserver> clipboardObservers;

    public ClipboardStack() {
        texts = new Stack<>();
        clipboardObservers = new ArrayList<>();
    }

    public boolean isEmpty() {
        return texts.isEmpty();
    }

    public void push(String text) {
        texts.push(text);
        notifyClipboardObservers();
    }

    public String peek() {
        return texts.peek();
    }

    public String pop() {
        String str = texts.pop();
        notifyClipboardObservers();
        return str;

    }

    public void clear() {
        texts.clear();
        notifyClipboardObservers();
    }

    public void addClipboardObserver(ClipboardObserver observer) {
        clipboardObservers.add(observer);
    }

    public void notifyClipboardObservers() {
        clipboardObservers.forEach(ClipboardObserver::updateClipboard);
    }
}
