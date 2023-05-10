package hr.fer.ooup.jmbag0036534519.lab3.editor.model;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TextEditorModelTest {

    @Test
    public void testTextParserRegular() {
        String text = """
                First line
                Second line
                Third line""";

        String[] expectedLines = {
                "First line",
                "Second line",
                "Third line"
        };

        TextEditorModel model = new TextEditorModel(text);

        assertArrayEquals(expectedLines, model.getLines().toArray());
    }

    @Test
    public void testTextParserEmpty() {
        String text = "";

        String[] expectedLines = {""};

        TextEditorModel model = new TextEditorModel(text);

        assertArrayEquals(expectedLines, model.getLines().toArray());
    }

    @Test
    public void testAllLinesIterator() {
        String text = """
                First line
                Second line
                Third line""";

        String[] expectedLines = {
                "First line",
                "Second line",
                "Third line"
        };

        TextEditorModel model = new TextEditorModel(text);

        Iterator<String> iter = model.allLines();
        int i = 0;

        while(iter.hasNext()) {
            assertEquals(expectedLines[i++], iter.next());
        }
    }

    @Test
    public void testLinesRangeIterator() {
        String text = """
                First line
                Second line
                Third line""";

        String[] expectedLines = {
                "First line",
                "Second line",
                "Third line"
        };

        TextEditorModel model = new TextEditorModel(text);

        Iterator<String> iter = model.linesRange(1, 2);
        int i = 1;

        assertEquals(expectedLines[i], iter.next());
        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, iter::next);
    }
}
