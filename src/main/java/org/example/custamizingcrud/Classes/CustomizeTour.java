// Java class: CustomizeTour.java
package org.example.custamizingcrud.Classes;

import java.util.List;

public class CustomizeTour {
    private String name;
    private String email;
    private String phone;
    private String title;
    private String country;
    private String pickup;
    private String arrival_date;
    private String departure_date;
    private int duration;
    private String vehicle;
    private int adults;
    private int children;
    private String accommodation;
    private double budget;
    private List<String> age_group;
    private List<String> destinations;

    public CustomizeTour() {}

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getPickup() { return pickup; }
    public void setPickup(String pickup) { this.pickup = pickup; }

    public String getArrival_date() { return arrival_date; }
    public void setArrival_date(String arrival_date) { this.arrival_date = arrival_date; }

    public String getDeparture_date() { return departure_date; }
    public void setDeparture_date(String departure_date) { this.departure_date = departure_date; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getVehicle() { return vehicle; }
    public void setVehicle(String vehicle) { this.vehicle = vehicle; }

    public int getAdults() { return adults; }
    public void setAdults(int adults) { this.adults = adults; }

    public int getChildren() { return children; }
    public void setChildren(int children) { this.children = children; }

    public String getAccommodation() { return accommodation; }
    public void setAccommodation(String accommodation) { this.accommodation = accommodation; }

    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }

    public List<String> getAge_group() { return age_group; }
    public void setAge_group(List<String> age_group) { this.age_group = age_group; }

    public List<String> getDestinations() { return destinations; }
    public void setDestinations(List<String> destinations) { this.destinations = destinations; }

    @Override
    public String toString() {
        return name + " (" + email + ") - " + country + ", " + destinations;
    }
}
