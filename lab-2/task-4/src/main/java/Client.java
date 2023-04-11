import context.DistributionTester;
import strategy.gen.IntegerGenerator;
import strategy.gen.impl.FibonacciIntegerGenerator;
import strategy.gen.impl.IntegerSequenceGenerator;
import strategy.gen.impl.NormalDistributionIntegerGenerator;
import strategy.pcalc.PercentileCalculator;
import strategy.pcalc.impl.LinearInterpolationPercentileCalculator;
import strategy.pcalc.impl.NearestRankPercentileCalculator;

import java.util.prefs.PreferenceChangeEvent;

public class Client {

    public static void main(String[] args) {
        IntegerGenerator seqGen    = new IntegerSequenceGenerator(1, 11, 1);
        IntegerGenerator normalGen = new NormalDistributionIntegerGenerator(5, 1, 10);
        IntegerGenerator fibGen    = new FibonacciIntegerGenerator(10);

        PercentileCalculator nRank  = new NearestRankPercentileCalculator();
        PercentileCalculator linear = new LinearInterpolationPercentileCalculator();

        System.out.println("Sequential Integer generator and Nearest-rank Percentile Calculator");
        System.out.println("--------------------------------------------------------------------");
        new DistributionTester(seqGen, nRank).test();
        System.out.println();

        System.out.println("Sequential Integer generator and Linear Interpolation Percentile Calculator");
        System.out.println("----------------------------------------------------------------------------");
        new DistributionTester(seqGen, linear).test();
        System.out.println();

        System.out.println("Normal Distribution Integer generator and Nearest-rank Percentile Calculator");
        System.out.println("-----------------------------------------------------------------------------");
        new DistributionTester(normalGen, nRank).test();
        System.out.println();

        System.out.println("Normal Distribution Integer generator and Linear Interpolation Percentile Calculator");
        System.out.println("-------------------------------------------------------------------------------------");
        new DistributionTester(normalGen, linear).test();
        System.out.println();

        System.out.println("Fibonacci Integer generator and Nearest-rank Percentile Calculator");
        System.out.println("--------------------------------------------------------------------");
        new DistributionTester(fibGen, nRank).test();
        System.out.println();

        System.out.println("Fibonacci Integer generator and Linear Interpolation Percentile Calculator");
        System.out.println("---------------------------------------------------------------------------");
        new DistributionTester(fibGen, linear).test();
        System.out.println();
    }
}
