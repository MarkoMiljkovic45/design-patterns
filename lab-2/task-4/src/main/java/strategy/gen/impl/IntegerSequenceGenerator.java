package strategy.gen.impl;

import strategy.gen.IntegerGenerator;

import java.util.ArrayList;
import java.util.List;

public class IntegerSequenceGenerator implements IntegerGenerator {

    private final int start;
    private final int end;
    private final int step;

    /**
     * Defines bounds and step of integer sequence [start, end> generation
     *
     * @param start inclusive start of sequence
     * @param end exclusive end of sequence
     * @param step of generation
     */
    public IntegerSequenceGenerator(int start, int end, int step) {
        this.start = start;
        this.end = end;
        this.step = step;
    }

    @Override
    public List<Integer> gen() {
        List<Integer> integers = new ArrayList<>();

        for (int i = start; i < end; i += step) {
            integers.add(i);
        }

        return integers;
    }
}
