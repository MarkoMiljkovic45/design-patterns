package hr.fer.ooup.jmbag0036534519.lab3.editor.model;

public class LocationRange {

    /**
     * Start of selection, inclusive in range
     */
    private Location start;
    /**
     * End of selection, exclusive from range
     */
    private Location end;

    /**
     * Selection range of text [start, end>
     *
     * @param start of selection inclusive
     * @param end of selection exclusive
     */
    public LocationRange(Location start, Location end) {
        this.start = start;
        this.end = end;
    }

    public LocationRange() {
        this(new Location(), new Location());
    }

    public Location getStart() {
        return start;
    }

    public Location getEnd() {
        return end;
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public void setEnd(Location end) {
        this.end = end;
    }
}
