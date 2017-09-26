package common.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Sighting {

    private String birdName;

    private String sighting;

    private Date date;

    public Sighting(String[] input) {
        this.birdName = input[0];
        this.sighting = input[1];
        this.date = new Date(input[2]);
    }

    public Sighting() {
    }

    public String getBirdName() {
        return birdName;
    }

    public void setBirdName(String birdName) {
        this.birdName = birdName;
    }

    public String getSighting() {
        return sighting;
    }

    public void setSighting(String sighting) {
        this.sighting = sighting;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return birdName + " " + sighting + " " + date.toString();
    }
}
