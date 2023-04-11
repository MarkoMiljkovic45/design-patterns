package observer.impl;

import context.NumberSequence;
import observer.NumberSequenceObserver;

public class NumberSequenceSum implements NumberSequenceObserver {

    @Override
    public void update(NumberSequence sequence) {
        int sum = sequence.getNumbers().stream().mapToInt(n -> n).sum();
        System.out.println("Sum of all elements: " + sum);
    }
}
