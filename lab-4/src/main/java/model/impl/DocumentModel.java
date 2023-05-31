package model.impl;

import model.GraphicalObject;
import model.listeners.DocumentModelListener;
import model.listeners.GraphicalObjectListener;
import util.GeometryUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DocumentModel {
    public final static double SELECTION_PROXIMITY = 10;

    /**
     * Kolekcija svih grafičkih objekata
     */
    private final List<GraphicalObject> objects = new ArrayList<>();

    /**
     * Read-Only proxy oko kolekcije grafičkih objekata
     */
    private final List<GraphicalObject> roObjects = Collections.unmodifiableList(objects);

    /**
     * Kolekcija prijavljenih promatrača
     */
    private final List<DocumentModelListener> listeners = new ArrayList<>();

    /**
     * Kolekcija selektiranih objekata
     */
    private final List<GraphicalObject> selectedObjects = new ArrayList<>();

    /**
     * Read-Only proxy oko kolekcije selektiranih objekata
     */
    private final List<GraphicalObject> roSelectedObjects = Collections.unmodifiableList(selectedObjects);


    /**
     * Promatrač koji će biti registriran nad svim objektima crteža
     */
    private final GraphicalObjectListener goListener = new GraphicalObjectListener() {
        @Override
        public void graphicalObjectChanged(GraphicalObject go) {
            notifyListeners();
        }

        @Override
        public void graphicalObjectSelectionChanged(GraphicalObject go) {
            if (go.isSelected()) {
                selectedObjects.add(go);
            } else {
                selectedObjects.remove(go);
            }
            notifyListeners();
        }
    };


    public DocumentModel() {
        //TODO
    }

    /**
     * Brisanje svih objekata iz modela (pazite da se sve potrebno odregistrira)
     * i potom obavijeste svi promatrači modela
     */
    public void clear() {
        objects.forEach(o -> o.removeGraphicalObjectListener(goListener));

        objects.clear();
        selectedObjects.clear();
        notifyListeners();
    }

    /**
     * Dodavanje objekta u dokument (pazite je li već selektiran; registrirajte model kao promatrača)
     *
     * @param obj koji treba dodati
     */
    public void addGraphicalObject(GraphicalObject obj) {
        objects.add(obj);

        if (obj.isSelected()) {
            selectedObjects.add(obj);
        }

        obj.addGraphicalObjectListener(goListener);
        notifyListeners();
    }

    /**
     * Uklanjanje objekta iz dokumenta (pazite je li već selektiran; odregistrirajte model kao promatrača)
     *
     * @param obj koji treba ukloniti
     */
    public void removeGraphicalObject(GraphicalObject obj) {
        objects.remove(obj);

        if (obj.isSelected()) {
            selectedObjects.remove(obj);
        }

        obj.removeGraphicalObjectListener(goListener);
        notifyListeners();
    }

    /**
     * @return nepromjenjivu listu postojećih objekata (izmjene smiju ići samo kroz metode modela)
     */
    public List<GraphicalObject> list() {
        return roObjects;
    }

    public void addDocumentModelListener(DocumentModelListener l) {
        listeners.add(l);
    }

    public void removeDocumentModelListener(DocumentModelListener l) {
        listeners.remove(l);
    }

    public void notifyListeners() {
        listeners.forEach(DocumentModelListener::documentChange);
    }

    /**
     * @return nepromjenjivu listu selektiranih objekata
     */
    public List<GraphicalObject> getSelectedObjects() {
        return roSelectedObjects;
    }

    /**
     * Pomakni predani objekt u listi objekata na jedno mjesto kasnije.
     * Time će se on iscrtati kasnije (pa će time možda veći dio biti vidljiv)
     *
     * @param go objekt koji treba pomaknuti dolje
     */
    public void increaseZ(GraphicalObject go) {
        if (go == null) return;

        int newZ = objects.indexOf(go) + 1;

        if (newZ < objects.size()) {
            objects.remove(go);
            objects.add(newZ, go);
            notifyListeners();
        }
    }

    /**
     * Pomakni predani objekt u listi objekata na jedno mjesto ranije
     *
     * @param go objekt koji treba pomaknuti gore
     */
    public void decreaseZ(GraphicalObject go) {
        if (go == null) return;

        int newZ = objects.indexOf(go) - 1;

        if (newZ >= 0) {
            objects.remove(go);
            objects.add(newZ, go);
            notifyListeners();
        }
    }

    /**
     * Pronađi postoji li u modelu neki objekt koji klik na točku koja je
     * predana kao argument selektira i vrati ga ili vrati null. Točka selektira
     * objekt kojemu je najbliža uz uvjet da ta udaljenost nije veća od
     * SELECTION_PROXIMITY. Status selektiranosti objekta ova metoda NE dira.
     *
     * @param mousePoint točka gdje je miš kliknuo
     * @return najbliži objekt kliku ili null ako nema objekata u blizini
     */
    public GraphicalObject findSelectedGraphicalObject(Point mousePoint) {
        GraphicalObject nearestObject = null;
        double minDistance = -1;

        for (GraphicalObject go: objects) {
            double distance = go.selectionDistance(mousePoint);

            if (nearestObject == null || distance < minDistance) {
                nearestObject = go;
                minDistance = distance;
            }
        }

        if (nearestObject != null && minDistance < SELECTION_PROXIMITY) {
            return nearestObject;
        } else {
            return null;
        }
    }

    /**
     * Pronađi da li u predanom objektu predana točka miša selektira neki hot-point.
     * Točka miša selektira onaj hot-point objekta kojemu je najbliža uz uvjet da ta
     * udaljenost nije veća od SELECTION_PROXIMITY. Vraća se indeks hot-pointa
     * kojeg bi predana točka selektirala ili -1 ako takve nema. Status selekcije
     * se pri tome NE dira.
     *
     * @param object selektirani objekt
     * @param mousePoint mjesto gdje je miš kliknuo
     * @return index najbližeg hot-pointa ili -1 ako ih nema u blizini
     */
    public int findSelectedHotPoint(GraphicalObject object, Point mousePoint) {
        int hotPointLen = object.getNumberOfHotPoints();
        int closestHotPointIndex = -1;
        double minDistance = -1;

        for (int i = 0; i < hotPointLen; i++) {
            Point hotPoint = object.getHotPoint(i);
            double distance = GeometryUtil.distanceFromPoint(hotPoint, mousePoint);

            if (distance < minDistance || minDistance == -1) {
                closestHotPointIndex = i;
                minDistance = distance;
            }
        }

        if (minDistance < SELECTION_PROXIMITY) {
            return closestHotPointIndex;
        } else {
            return -1;
        }
    }

    public void deselectAll() {
        while (!selectedObjects.isEmpty()) {
            selectedObjects.get(0).setSelected(false);
        }
    }
}
