package dao;

import dto.Sighting;

import java.util.List;

public class SightingDao implements Dao<Sighting> {

    private String dataFolder;

    public SightingDao(String dataFolder) {
        this.dataFolder = dataFolder;
    }

    public Sighting persist(Sighting entity) {
        return null;
    }

    public List<Sighting> getAll() {
        return null;
    }

    public void remove(String name) {

    }
}
