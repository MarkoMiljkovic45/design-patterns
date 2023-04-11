package strategy.gen.impl;

import strategy.gen.IntegerGenerator;

import java.util.ArrayList;
import java.util.List;

public class FibonacciIntegerGenerator implements IntegerGenerator {

    private final int sampleSize;

    public FibonacciIntegerGenerator(int sampleSize) {
        this.sampleSize = sampleSize;
    }

    @Override
    public List<Integer> gen() {
        List<Integer> integers = new ArrayList<>(sampleSize);

        int first = 0;
        int second = 1;

        for (int i = 0; i < sampleSize; i++) {
            integers.add(first);

            int next = first + second;

            first = second;
            second = next;
        }

        return integers;
    }


}