package common.response;

import common.dto.Sighting;

import java.util.List;

public class SightingsResponse extends Response {

    private List<Sighting> sightingList;

    public SightingsResponse(List<Sighting> sightingList) {
        this.sightingList = sightingList;
    }

    public SightingsResponse() {
    }

    public List<Sighting> getSightingList() {
        return sightingList;
    }

    public void setSightingList(List<Sighting> sightingList) {
        this.sightingList = sightingList;
    }
}
