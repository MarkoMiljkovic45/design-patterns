package strategy.impl;

import strategy.NumberSource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class FileNumberSource implements NumberSource {

    private Scanner scanner;

    public FileNumberSource(Path sourceFilePath) {
        try {
            scanner = new Scanner(sourceFilePath);
        }
        catch (IOException io) {
            scanner = null;
        }
    }

    @Override
    public int poll() {
        try {
            return scanner.nextInt();
        }
        catch (Exception e) {
            return -1;
        }
    }
}
