import context.NumberSequence;
import observer.NumberSequenceObserver;
import observer.impl.NumberSequenceAverage;
import observer.impl.NumberSequenceLogger;
import observer.impl.NumberSequenceMedian;
import observer.impl.NumberSequenceSum;
import strategy.NumberSource;
import strategy.impl.FileNumberSource;
import strategy.impl.KeyboardNumberSource;

import java.nio.file.Path;

public class Client {

    public static void main(String[] args) {
        NumberSource keyboardSource = new KeyboardNumberSource();
        NumberSource fileSource     = new FileNumberSource(Path.of("source.txt"));

        NumberSequence nsFromKeyboard = new NumberSequence(keyboardSource);
        NumberSequence nsFromFile     = new NumberSequence(fileSource);

        NumberSequenceObserver logger = new NumberSequenceLogger();
        NumberSequenceObserver sum    = new NumberSequenceSum();
        NumberSequenceObserver avg    = new NumberSequenceAverage();
        NumberSequenceObserver median = new NumberSequenceMedian();

        //Number Sequence from keyboard
        nsFromKeyboard.attach(logger);
        nsFromKeyboard.attach(sum);
        nsFromKeyboard.attach(avg);
        nsFromKeyboard.attach(median);

        System.out.println("NumberSource from keyboard:");
        System.out.println("---------------------------");
        nsFromKeyboard.start();

        nsFromKeyboard.detach(logger);
        nsFromKeyboard.detach(sum);
        nsFromKeyboard.detach(avg);
        nsFromKeyboard.detach(median);

        //Number sequence from file
        nsFromFile.attach(logger);
        nsFromFile.attach(sum);
        nsFromFile.attach(avg);
        nsFromFile.attach(median);

        System.out.println("NumberSource from file:");
        System.out.println("-----------------------");
        nsFromFile.start();

        nsFromFile.detach(logger);
        nsFromFile.detach(sum);
        nsFromFile.detach(avg);
        nsFromFile.detach(median);
    }
}
