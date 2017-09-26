package common.dto;

public class Bird {

    private String name;

    private String color;

    private Double weight;

    private Double height;

    public Bird(String[] input) {
        this.name = input[0];
        this.color = input[1];
        this.weight = Double.parseDouble(input[2]);
        this.height = Double.parseDouble(input[3]);
    }

    public Bird() {
    }

    public Bird(String name, String color, Double weight, Double height) {
        this.name = name;
        this.color = color;
        this.weight = weight;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bird bird = (Bird) o;

        return name.equals(bird.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name + " " + color + " " + weight + " " + height;
    }
}
