package context;

import observer.NumberSequenceObserver;
import strategy.NumberSource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NumberSequence {

    private final List<Integer> numbers;
    private final NumberSource source;
    private final List<NumberSequenceObserver> observers;

    private NumberSequence(List<Integer> numbers, NumberSource source, List<NumberSequenceObserver> observers) {
        this.numbers = numbers;
        this.source = source;
        this.observers = observers;
    }

    public NumberSequence(NumberSource source) {
        this(new ArrayList<>(), source, new LinkedList<>());
    }

    public void attach(NumberSequenceObserver observer) {
        observers.add(observer);
    }

    public void detach(NumberSequenceObserver observer) {
        observers.remove(observer);
    }

    public void start() {
        while (true) {
            int next = source.poll();

            if (next == -1) break;

            numbers.add(next);
            notifyObservers();
            System.out.println();

            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException ignore) {}
        }
    }

    public void notifyObservers() {
        observers.forEach(observer -> observer.update(NumberSequence.this));
    }

    public List<Integer> getNumbers() {
        return numbers;
    }
}
