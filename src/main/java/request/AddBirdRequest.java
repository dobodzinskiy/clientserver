package request;

import dto.Bird;

public class AddBirdRequest extends Request {

    private Bird bird;

    public Bird getBird() {
        return bird;
    }

    public void setBird(Bird bird) {
        this.bird = bird;
    }
}
