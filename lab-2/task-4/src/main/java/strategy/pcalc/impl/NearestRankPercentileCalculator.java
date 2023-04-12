package strategy.pcalc.impl;

import strategy.pcalc.PercentileCalculator;

import java.util.List;

public class NearestRankPercentileCalculator implements PercentileCalculator {

    @Override
    public int calc(List<Integer> integers, int p) {
        List<Integer> sorted = integers.stream().sorted().toList();
        double n_p = (double) p * sorted.size() / 100;
        return sorted.get((int) Math.ceil(n_p) - 1);
    }
}
