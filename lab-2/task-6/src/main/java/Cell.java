import java.util.LinkedList;
import java.util.List;

public class Cell implements CellObserver {

    private String exp;
    private double value;
    private final Sheet sheet;
    private final List<CellObserver> observers;

    public Cell(Sheet sheet) {
        this.sheet = sheet;
        this.observers = new LinkedList<>();
    }

    public double getValue() {
        return value;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
        sheet.evaluate(this);
    }

    public void setValue(double value) {
        this.value = value;
        notifyObservers();
    }

    public void attach(CellObserver observer) {
        observers.add(observer);
    }

    public void detach(CellObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        observers.forEach(o -> o.valueChanged(Cell.this));
    }

    @Override
    public void valueChanged(Cell cell) {
        sheet.evaluate(this);
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
