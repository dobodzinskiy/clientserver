package dao;

import dto.Bird;

import java.util.List;

public class BirdDao implements Dao<Bird> {

    private String dataFolder;

    public BirdDao(String dataFolder) {
        this.dataFolder = dataFolder;
    }

    public Bird persist(Bird entity) {
        return null;
    }

    public List<Bird> getAll() {
        return null;
    }

    public void remove(String name) {

    }
}
