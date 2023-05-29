package hr.fer.ooup.jmbag0036534519.lab3.demo;

import javax.swing.*;
import java.awt.*;

public class MyComponent extends JComponent {

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.RED);
        g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());

        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 20);
        g.setFont(font);

        FontMetrics fm = getFontMetrics(font);

        g.drawString("First line", 0, fm.getAscent());
        g.drawString("Second line", 0, fm.getAscent() + fm.getHeight());
    }
}
