package server.service;

import dao.BirdDao;
import dao.SightingDao;
import request.Request;
import response.Response;

public class ServerExecutor {

    private BirdDao birdDao;
    private SightingDao sightingDao;

    private ServerExecutor(String dataFolder) {
        this.birdDao = new BirdDao(dataFolder);
        this.sightingDao = new SightingDao(dataFolder);
    }

    public static ServerExecutor configure(String dataFolder) {
        return new ServerExecutor(dataFolder);
    }

    public Response execute(Request request) {
        return null;
    }
}
