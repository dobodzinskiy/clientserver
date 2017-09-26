package common.request;

import common.dto.Sighting;

public class AddSightingRequest extends Request {

    private Sighting sighting;

    public Sighting getSighting() {
        return sighting;
    }

    public void setSighting(Sighting sighting) {
        this.sighting = sighting;
    }
}
