package strategy.pcalc.impl;

import strategy.pcalc.PercentileCalculator;

import java.util.List;

public class LinearInterpolationPercentileCalculator implements PercentileCalculator {

    @Override
    public int calc(List<Integer> integers, int p) {
        List<Integer> sorted = integers.stream().sorted().toList();

        int N = sorted.size();

        for (int i = 0; i < N; i++) {
            double pVi = 100 * ((i + 1) - 0.5) / N;

            if (i == 0 && p < pVi) return sorted.get(0);
            if (i == N - 1 && p > pVi) return sorted.get(N - 1);

            double pVi1 = 100 * ((i + 1 + 1) - 0.5) / N;

            if (p >= pVi && p <= pVi1) {
                double inter = sorted.get(i) + N * (p - pVi) * (sorted.get(i + 1) - sorted.get(i)) / 100;
                return (int) inter;
            }
        }

        return 0;
    }
}
