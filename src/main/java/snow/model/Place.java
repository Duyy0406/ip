package snow.model;

/**
 * Represents the places.
 */
public class Place {
    private final int id;
    private String name;

    /** Sentinel */
    public static final Place NONE = new Place(-1, "");

    public Place(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Saves place so Storage.
     * @return place format in Storage.
     */
    public String toSaveString() {
        return "P | " + id + " | " + name;
    }

    /**
     * Returns this place's id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns this place's name.
     */
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this == Place.NONE ? "" : name;
    }
}
