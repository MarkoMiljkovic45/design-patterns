import java.util.ArrayList;
import java.util.List;

public class Sheet {

    private final Cell[][] sheet;

    public Sheet(int rows, int columns) {
        this.sheet = new Cell[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                sheet[i][j] = new Cell(this);
            }
        }
    }

    /**
     * Returns the referenced cell. For example:
     * <p>
     * sheet.cell("A1") returns the cell at index (0,0)
     *
     * @param ref reference name of the cell
     * @return The referenced cell
     */
    public Cell cell(String ref) {
        String[] refSplit = ref.toUpperCase().split("");
        int row = Integer.parseInt(refSplit[1]) - 1;
        int column = refSplit[0].charAt(0) - 'A';
        return sheet[row][column];
    }

    /**
     * Adds content to a referenced cell
     *
     * @param ref reference name of a cell
     * @param content to be saved in the cell
     * @throws IllegalArgumentException if the content would cause a circular relation between cells
     */
    public void set(String ref, String content) {
        Cell cell = cell(ref);

        List<Cell> oldCellRefs = getRefs(cell);

        if (oldCellRefs != null) {
            oldCellRefs.forEach(refCell -> refCell.detach(cell));
        }

        if (content == null) {
            cell.setExp("0");
            return;
        }

        if (!content.matches("-?\\d+\\.?\\d*")) {
            String[] refs = content.toUpperCase().split("\\+");

            for (String refsRef: refs) {
                Cell refCell = cell(refsRef);

                List<Cell> refCellRefs = getAllRefs(refCell);
                if (refCellRefs != null && refCellRefs.contains(cell)) {
                    throw new IllegalArgumentException("Adding " + content + " would cause a circular dependency");
                }

                refCell.attach(cell);
            }
        }

        cell.setExp(content);
    }

    /**
     * Returns a list of cells that are referenced both directly or
     * indirectly by the argument cell
     * <p>
     * cell.exp = "A1 + B2"
     * getRefs(cell) returns a list of references to A1 and B2 cells
     *
     * @param cell That references other cells
     * @return List of referenced cells
     */
    public List<Cell> getRefs(Cell cell) {
        String exp = cell.getExp();
        if (exp == null || exp.matches("-?\\d+\\.?\\d*")) return null;

        List<Cell> cellRefs = new ArrayList<>();

        String[] refs = exp.split("\\+");

        for (String ref: refs) {
            cellRefs.add(cell(ref));
        }

        return cellRefs;
    }

    private List<Cell> getAllRefs(Cell cell) {
        String exp = cell.getExp();
        if (exp == null || exp.matches("-?\\d+\\.?\\d*")) return null;

        List<Cell> cellRefs = new ArrayList<>();

        String[] refs = exp.split("\\+");

        for (String ref: refs) {
            Cell cellRef = cell(ref);
            cellRefs.add(cellRef);

            List<Cell> cellRefRefs = getAllRefs(cellRef);
            if (cellRefRefs != null) {
                cellRefs.addAll(cellRefRefs);
            }
        }

        return cellRefs;
    }

    /**
     * Evaluates the expression stored in the cell
     *
     * @param cell whose content will be evaluated
     */
    public void evaluate(Cell cell) {
        String exp = cell.getExp();
        if (exp == null) {
            return;
        }

        if (exp.matches("-?\\d+\\.?\\d*")) {
            cell.setValue(Double.parseDouble(exp));
            return;
        }

        List<Cell> cellRefs = getRefs(cell);
        double value = 0;

        for (Cell cellRef: cellRefs) {
            value += cellRef.getValue();
        }

        cell.setValue(value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        int cellWidth = getMaxWidth() + 1;
        String delimiter = getDelimiter(cellWidth, sheet[0].length);

        for (Cell[] row: sheet) {
            sb.append(delimiter);
            for (Cell cell: row) {
                int indent = cellWidth - cell.toString().length();
                sb
                        .append("|")
                        .append(" ".repeat(indent))
                        .append(cell);
            }
            sb.append("|\n");
        }

        sb.append(delimiter);
        return sb.toString();
    }

    private int getMaxWidth() {
        int max = 0;

        for (Cell[] row: sheet) {
            for (Cell cell: row) {
                int width = cell.toString().length();

                if (width > max) {
                    max = width;
                }
            }
        }

        return max;
    }

    private String getDelimiter(int colWidth, int colCount) {
        String fragment = "+" + "-".repeat(colWidth);
        return fragment.repeat(colCount) + "+\n";
    }
}
