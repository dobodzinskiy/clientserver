package server.dao;

import common.dto.Bird;

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

public class BirdDao implements Dao<Bird> {

    private File birdFile;

    public BirdDao(String dataFolder) throws IOException {
        Path path = Paths.get(dataFolder);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
        this.birdFile = new File(dataFolder + "/birds.txt");
        if (!birdFile.exists()) {
            birdFile.createNewFile();
        }
    }

    public Bird persist(Bird entity) {
        try {
            PrintStream printStream = new PrintStream(birdFile);

            printStream.print(entity.toString());
            printStream.flush();
            printStream.close();
        } catch (FileNotFoundException e) {
            System.err.print("Error while saving bird info. Reason: " + e.getMessage());
        }
        return entity;
    }

    public List<Bird> getAll() {
        List<Bird> birds = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(this.birdFile);
            while (scanner.hasNext()) {
                birds.add(new Bird(scanner.nextLine().split(" ")));
            }
        } catch (FileNotFoundException e) {
            System.err.print("Error while getting entities. Reason: " + e.getMessage());
        }

        return birds;
    }
}
