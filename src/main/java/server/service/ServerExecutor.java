package server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.dto.Bird;
import common.dto.CommandType;
import common.dto.Sighting;
import common.request.AddBirdRequest;
import common.request.AddSightingRequest;
import common.request.Request;
import common.response.BirdsResponse;
import common.response.EntityResponse;
import common.response.Response;
import common.response.SightingsResponse;
import server.dao.BirdDao;
import server.dao.SightingDao;
import server.state.ServerState;

import java.io.IOException;

public class ServerExecutor {

    private BirdDao birdDao;
    private SightingDao sightingDao;

    private ObjectMapper mapper;

    private ServerExecutor(String dataFolder) throws IOException {
        this.birdDao = new BirdDao(dataFolder);
        this.sightingDao = new SightingDao(dataFolder);
        this.mapper = new ObjectMapper();
    }

    public static ServerExecutor configure(String dataFolder) throws IOException {
        return new ServerExecutor(dataFolder);
    }

    Response execute(String json) {
        Response response = null;
        try {
            Request request = mapper.readValue(json, Request.class);

            switch (request.getMethod()) {
                case ADDBIRD:
                    AddBirdRequest addBirdRequest = mapper.readValue(json, AddBirdRequest.class);
                    if (ServerState.addBird(addBirdRequest.getBird())) {
                        response = new EntityResponse<>(addBirdRequest.getBird());
                        response.setId(addBirdRequest.getId());
                        response.setStatus("200");
                        response.setCommandType(CommandType.ADDBIRD);
                    } else {
                        response = new Response();
                        response.setId(addBirdRequest.getId());
                        response.setStatus("500");
                        response.setCommandType(CommandType.ADDBIRD);
                    }
                    break;
                case ADDSIGHTING:
                    AddSightingRequest addSightingRequest = mapper.readValue(json, AddSightingRequest.class);
                    ServerState.addSighting(addSightingRequest.getSighting());
                    response = new EntityResponse<>(addSightingRequest.getSighting());
                    response.setId(addSightingRequest.getId());
                    response.setStatus("200");
                    response.setCommandType(CommandType.ADDSIGHTING);
                    break;
                case REMOVE:
                    response = new Response();
                    response.setId(request.getId());
                    response.setCommandType(CommandType.REMOVE);
                    if (ServerState.removeBird(request.getParams())) {
                        response.setStatus("200");
                    } else {
                        response.setStatus("500");
                    }
                    break;
                case LISTBIRDS:
                    response = new BirdsResponse(ServerState.getBirds());
                    response.setId(request.getId());
                    response.setStatus("200");
                    response.setCommandType(CommandType.LISTBIRDS);
                    break;
                case LISTSIGHTINGS:
                    response = new SightingsResponse(ServerState.getSightings(request.getParams()));
                    response.setId(request.getId());
                    response.setStatus("200");
                    response.setCommandType(CommandType.LISTSIGHTINGS);
                    break;
            }
        } catch (IOException e) {
            System.err.print("Server received wrong message format. Reason: " + e.getMessage());
            response = new Response();
            response.setStatus("500");
        }
        return response;
    }

    public void loadDataFromFile() {
        for (Bird bird : birdDao.getAll()) {
            ServerState.addBird(bird);
        }

        for (Sighting sighting : sightingDao.getAll()) {
            ServerState.addSighting(sighting);
        }
    }

    public void saveDataToFile() {
        for (Bird bird : ServerState.getBirds()) {
            birdDao.persist(bird);
        }
        for (Sighting sighting : ServerState.getSightings()) {
            sightingDao.persist(sighting);
        }
    }

}
