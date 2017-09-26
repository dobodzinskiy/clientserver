package server.dao;

import common.dto.Bird;
import common.dto.Sighting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SightingDao implements Dao<Sighting> {

    private File sightingFile;

    public SightingDao(String dataFolder) throws IOException {
        Path path = Paths.get(dataFolder);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
        this.sightingFile = new File(dataFolder + "/sightings.txt");
        if (!sightingFile.exists()) {
            sightingFile.createNewFile();
        }
    }

    public Sighting persist(Sighting entity) {
        try {
            PrintStream printStream = new PrintStream(this.sightingFile);

            printStream.print(entity.toString());
            printStream.flush();
            printStream.close();
        } catch (FileNotFoundException e) {
            System.err.print("Error while saving sighting info. Reason: " + e.getMessage());
        }
        return entity;
    }

    public List<Sighting> getAll() {
        List<Sighting> sightings = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(this.sightingFile);
            while (scanner.hasNext()) {
                sightings.add(new Sighting(scanner.nextLine().split(" ")));
            }
        } catch (FileNotFoundException e) {
            System.err.print("Error while getting entities. Reason: " + e.getMessage());
        }

        return sightings;
    }
}
