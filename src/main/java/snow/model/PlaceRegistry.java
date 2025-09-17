package snow.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Registry for managing places assigned to tasks.
 * Maintains a centralized collection of all places and provides
 * methods for creating and retrieving places.
 */
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
     * Find existing place by ID.
     */
    public static Place findById(int id) {
        for (Place p : places) {
            if (p.getId() == id) {
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
     * Add a place with specific ID (used during loading from storage).
     */
    public static void addPlace(Place place) {
        places.add(place);
        // Update nextId to ensure no conflicts
        if (place.getId() >= nextId) {
            nextId = place.getId() + 1;
        }
    }

    /**
     * Clear all places (used for loading from storage).
     */
    public static void clearPlaces() {
        places.clear();
        nextId = 1;
    }

    /**
     * Return all known places.
     */
    public static List<Place> getPlaces() {
        return places;
    }
}
