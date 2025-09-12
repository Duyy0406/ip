package snow.model;

import java.util.ArrayList;
import java.util.List;

public class PlaceRegistry {
    private static final List<Place> places = new ArrayList<>();
    private static int nextId = 1;

    /**
     * Find existing place by name (case-insensitive).
     */
    public static Place findByName(String name) {
        if (name == null) {
            return null;
        }
        String key = name.trim().toLowerCase();
        for (Place p : places) {
            if (p.getName().toLowerCase().equals(key)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Gets if exists, else creates a new one.
     */
    public static Place getPlace(String name) {
        Place p = findByName(name);
        if (p != null) {
            return p;
        }

        Place newPlace = new Place(nextId++, name);
        places.add(newPlace);
        return newPlace;
    }

    /**
     * Return all known places.
     */
    public static List<Place> getPlaces() {
        return places;
    }
}
