package com.example.cyronemon.nightclubbkk2;

/**
 * Created by Cyronemon on 6/20/2017.
 */
public class Nightclub {

    private String name;
    private String description;
    private String descriptionShort;
    private String image;
    private double lat;
    private double longtitude;

    public Nightclub (){

    }

    // Generate constructor
    public Nightclub(String name, String description, String image, double lat, double longtitude, String descriptionShort) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.lat = lat;
        this.longtitude = longtitude;
        this.descriptionShort = descriptionShort;

    }
// Generate getter & setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public String getDescriptionShort() {
        return descriptionShort;
    }

    public void setDescriptionShort(String descriptionShort) {
        this.descriptionShort = descriptionShort;
    }

    @Override
    public String toString() {
        return "Nightclub{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", lat=" + lat +
                ", longtitude=" + longtitude +
                "descriptionShort='" + descriptionShort + '\'' +
                '}';
    }
}
