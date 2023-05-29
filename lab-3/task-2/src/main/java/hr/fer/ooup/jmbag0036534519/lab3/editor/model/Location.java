package hr.fer.ooup.jmbag0036534519.lab3.editor.model;

import java.util.Objects;

public class Location implements Comparable<Location> {

    private int row;
    private int column;

    public Location(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Location() {
        this(1, 0);
    }

    public Location(Location loc) {
        this(loc.getRow(), loc.getColumn());
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return row == location.row && column == location.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public int compareTo(Location o) {
        if (equals(o)) {
            return 0;
        }

        if (getRow() == o.getRow()) {
            return getColumn() - o.getColumn();
        }

        return getRow() - o.getRow();
    }
}
