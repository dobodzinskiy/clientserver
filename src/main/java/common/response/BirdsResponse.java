package common.response;

import common.dto.Bird;

import java.util.List;

public class BirdsResponse extends Response {

    private List<Bird> birdList;

    public BirdsResponse(List<Bird> birdList) {
        this.birdList = birdList;
    }

    public BirdsResponse() {
    }

    public List<Bird> getBirdList() {
        return birdList;
    }

    public void setBirdList(List<Bird> birdList) {
        this.birdList = birdList;
    }
}
