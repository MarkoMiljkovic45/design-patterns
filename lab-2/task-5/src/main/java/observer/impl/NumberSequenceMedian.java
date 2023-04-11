package observer.impl;

import context.NumberSequence;
import observer.NumberSequenceObserver;

import java.util.List;

public class NumberSequenceMedian implements NumberSequenceObserver {

    @Override
    public void update(NumberSequence sequence) {
        List<Integer> sorted = sequence.getNumbers().stream().sorted().toList();

        if (sorted.size() == 0) return;

        int index = sorted.size() / 2;

        System.out.println("Median of all elements: " + sorted.get(index));
    }
}
