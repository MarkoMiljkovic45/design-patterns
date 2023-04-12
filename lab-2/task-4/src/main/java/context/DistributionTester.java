package context;

import strategy.gen.IntegerGenerator;
import strategy.pcalc.PercentileCalculator;

import java.util.List;

public class DistributionTester {

    private final IntegerGenerator generator;
    private final PercentileCalculator percentileCalculator;

    public DistributionTester(IntegerGenerator generator, PercentileCalculator percentileCalculator) {
        this.generator = generator;
        this.percentileCalculator = percentileCalculator;
    }

    public void test() {
        List<Integer> integers = generator.gen();

        System.out.println("Generated integers: " + integers.toString());
        for (int p = 10; p < 100; p += 10) {
            int pInt = percentileCalculator.calc(integers, p);
            System.out.printf("%dth percentile: %d\n", p, pInt);
        }
    }
}
