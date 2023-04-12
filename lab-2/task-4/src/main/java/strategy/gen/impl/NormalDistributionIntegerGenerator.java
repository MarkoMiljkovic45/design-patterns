package strategy.gen.impl;

import strategy.gen.IntegerGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NormalDistributionIntegerGenerator implements IntegerGenerator {

    private final double mean;
    private final double stdDev;
    private final int sampleSize;

    public NormalDistributionIntegerGenerator(double mean, double stdDev, int sampleSize) {
        this.mean = mean;
        this.stdDev = stdDev;
        this.sampleSize = sampleSize;
    }

    @Override
    public List<Integer> gen() {
        Random random = new Random();
        List<Integer> integers = new ArrayList<>(sampleSize);

        for (int i = 0; i < sampleSize; i++) {
            integers.add((int) (random.nextGaussian() * stdDev * stdDev + mean));
        }

        return integers;
    }
}