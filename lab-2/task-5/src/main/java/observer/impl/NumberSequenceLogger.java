package observer.impl;

import context.NumberSequence;
import observer.NumberSequenceObserver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NumberSequenceLogger implements NumberSequenceObserver {

    @Override
    public void update(NumberSequence sequence) {
        String timestamp = new SimpleDateFormat("d. M. yyyy. HH:mm:ss").format(new Date());
        List<String> numbers = sequence.getNumbers().stream().map(Object::toString).toList();

        List<String> lines = new ArrayList<>();
        lines.add(timestamp);
        lines.addAll(numbers);

        try {
            Files.write(Path.of("number_sequence_log.txt"),
                    lines,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.SYNC);
        }
        catch (IOException io) {
            System.out.printf("(%s) Failed to log data.\n", timestamp);
        }
    }
}
