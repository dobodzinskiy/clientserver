package server.state;

import common.dto.Bird;
import common.dto.Sighting;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class ServerState {

    private static List<Bird> birds;
    private static List<Sighting> sightings;

    static {
        birds = new ArrayList<>();
        sightings = new ArrayList<>();
    }

    public static List<Bird> getBirds() {
        return birds;
    }

    public static List<Sighting> getSightings() {
        return sightings;
    }

    public static List<Sighting> getSightings(String birdName) {
        return sightings
                .stream()
                .filter(sighting -> Objects.equals(sighting.getBirdName(), birdName))
                .collect(Collectors.toList());
    }

    public static boolean addBird(Bird bird) {
        if (!birds.contains(bird)) {
            birds.add(bird);
            return true;
        }
        return false;
    }

    public static void addSighting(Sighting sighting) {
        sightings.add(sighting);
    }

    public static boolean removeBird(String birdName) {
        for (Bird bird : birds) {
            if (bird.getName().equals(birdName)) {
                birds.remove(bird);
                return true;
            }
        }

        return false;
    }

}
