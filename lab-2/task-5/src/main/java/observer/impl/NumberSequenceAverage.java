package observer.impl;

import context.NumberSequence;
import observer.NumberSequenceObserver;

import java.util.OptionalDouble;

public class NumberSequenceAverage implements NumberSequenceObserver {

    @Override
    public void update(NumberSequence sequence) {
        OptionalDouble avg = sequence.getNumbers().stream().mapToInt(n -> n).average();
        System.out.println("Average of all elements: " + (avg.isEmpty() ? 0 : avg.getAsDouble()));
    }
}
