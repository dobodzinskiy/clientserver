package client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.dto.Bird;
import common.dto.Sighting;
import common.response.BirdsResponse;
import common.response.EntityResponse;
import common.response.Response;
import common.response.SightingsResponse;

import java.io.IOException;

public class ResponseExecutor {

    private ObjectMapper mapper;

    public ResponseExecutor() {
        this.mapper = new ObjectMapper();
    }

    public void execute(String responseJson) {
        try {
            Response response = mapper.readValue(responseJson, Response.class);

            switch (response.getCommandType()) {
                case ADDBIRD:
                    if ("200".equals(response.getStatus())) {
                        EntityResponse entityResponse = mapper.readValue(responseJson, EntityResponse.class);
                        Bird bird = (Bird) entityResponse.getResult();
                        System.out.printf("Bird " + bird.getName() + " successfully added to the database");
                    } else {
                        System.out.printf("Bird has already exists");
                    }
                    break;
                case ADDSIGHTING:
                    if ("200".equals(response.getStatus())) {
                        EntityResponse entityResponse = mapper.readValue(responseJson, EntityResponse.class);
                        Sighting sighting = (Sighting) entityResponse.getResult();
                        System.out.printf("Sighting \"" + sighting.getSighting() + "\" successfully added to the database");
                    } else {
                        System.out.printf("Server error while adding sighting");
                    }
                    break;
                case REMOVE:
                    if ("200".equals(response.getStatus())) {
                        System.out.printf("Bird was successfully removed from database.");
                    } else {
                        System.out.printf("Server error while removing the bird.");
                    }
                    break;
                case LISTBIRDS:
                    BirdsResponse birdsResponse = mapper.readValue(responseJson, BirdsResponse.class);
                    if (birdsResponse.getBirdList().size() == 0) {
                        System.out.print("No birds in database");
                    }
                    for (Bird bird : birdsResponse.getBirdList()) {
                        System.out.printf(bird.toString());
                    }
                    break;
                case LISTSIGHTINGS:
                    SightingsResponse sightingResponse = mapper.readValue(responseJson, SightingsResponse.class);
                    if (sightingResponse.getSightingList().size() == 0) {
                        System.out.print("No sightings in database for this bird");
                    }
                    for (Sighting sighting : sightingResponse.getSightingList()) {
                        System.out.printf(sighting.toString());
                    }
            }
        } catch (IOException e) {
            System.err.print(e.getMessage());
        }

    }
}
