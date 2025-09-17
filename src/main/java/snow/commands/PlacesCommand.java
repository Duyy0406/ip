package snow.commands;

import java.util.List;

import snow.exception.SnowException;
import snow.io.Storage;
import snow.io.Ui;
import snow.model.Place;
import snow.model.PlaceRegistry;
import snow.model.TaskList;

/**
 * Represents the Places command to show all saved places.
 */
public class PlacesCommand extends Command {

    private static final String PLACES_MESSAGE = "Here are all the saved places:";

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SnowException {
        resetString();
        List<Place> places = PlaceRegistry.getPlaces();

        command.append(PLACES_MESSAGE);
        if (places.isEmpty()) {
            command.append("\n").append("No places have been saved yet.");
        } else {
            for (int i = 0; i < places.size(); i++) {
                Place place = places.get(i);
                command.append("\n").append(i + 1).append(". ").append(place.getName());
            }
        }

        ui.printPlaces(places);
    }
}
