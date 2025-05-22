package com.tourismplatform.model;

public class TravelPackage {
    private int id;
    private String name;
    private String description;
    private double cost;
    private String imagePath;

    public TravelPackage(int id, String name, String description, double cost, String imagePath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.imagePath = imagePath;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getCost() { return cost; }
    public String getImagePath() { return imagePath; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setCost(double cost) { this.cost = cost; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    @Override
    public String toString() {
        return id + "," + name + "," + description + "," + cost + "," + imagePath;
    }
}