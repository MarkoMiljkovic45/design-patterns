package strategy.impl;

import strategy.NumberSource;

import java.util.Scanner;

public class KeyboardNumberSource implements NumberSource {

    @Override
    public int poll() {
        try {
            return new Scanner(System.in).nextInt();
        }
        catch (Exception e) {
            return -1;
        }
    }
}
