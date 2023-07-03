package gui.impl;

import gui.Renderer;
import util.Point;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SVGRendererImpl implements Renderer {
    private final List<String> lines = new ArrayList<>();
    private final String fileName;

    public SVGRendererImpl(String fileName) {
        this.fileName = fileName;

        lines.add("<svg  xmlns=\"http://www.w3.org/2000/svg\"");
        lines.add("      xmlns:xlink=\"http://www.w3.org/1999/xlink\">");
    }

    public void close() throws IOException {
        lines.add("</svg>");
        Files.write(Path.of(fileName), lines);
    }

    @Override
    public void drawLine(Point s, Point e) {
        // Dodaj u lines redak koji definira linijski segment:
        // <line ... />
        int x1 = s.getX();
        int y1 = s.getY();

        int x2 = e.getX();
        int y2 = e.getY();

        String lineTag = String.format("<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"stroke:#0000ff;\"/>",
                                                    x1,      y1,       x2,       y2);

        lines.add(lineTag);
    }

    @Override
    public void fillPolygon(Point[] points) {
        StringBuilder sb = new StringBuilder();
        int numOfPoints = points.length;

        for (int i = 0; i < numOfPoints; i++) {
            Point point = points[i];

            sb.append(point.getX()).append(",").append(point.getY());

            if (i + 1 < numOfPoints) {
                sb.append(" ");
            }
        }

        String pointsAttr = sb.toString();
        String polygonTag = String.format("<polygon points=\"%s\" style=\"stroke:#ff0000; fill: #0000ff\"/>", pointsAttr);

        lines.add(polygonTag);
    }
}
