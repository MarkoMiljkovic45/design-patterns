package model.listeners;

import model.GraphicalObject;

public interface GraphicalObjectListener {
    /**
     * Poziva se kad se nad objektom promjeni bio što...
     *
     * @param go object that changed
     */
    void graphicalObjectChanged(GraphicalObject go);

    /**
     * Poziva se isključivo ako je nad objektom promjenjen status selektiranosti
     * (baš objekta, ne njegovih hot-point-a).
     *
     * @param go object that got selected
     */
    void graphicalObjectSelectionChanged(GraphicalObject go);
}
