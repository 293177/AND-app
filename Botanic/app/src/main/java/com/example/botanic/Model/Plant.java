package com.example.botanic.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Plant {

    @PrimaryKey(autoGenerate = true)
    private int plantID;
    private String plantName;
    private String season;
    private String watering;
    private String sunlight;
    private String temperature;
    private int weeksToHarvest;
    private String lifeSpan;
    private String pollination;
    private String catFriendly;
    private String origin;
    private String notes;
    private String photoPath;

    public Plant(String plantName, String season, String watering, String sunlight, String temperature, int weeksToHarvest, String lifeSpan, String pollination, String catFriendly, String origin, String notes, String photoPath) {
        this.plantName = plantName;
        this.season = season;
        this.watering = watering;
        this.sunlight = sunlight;
        this.temperature = temperature;
        this.weeksToHarvest = weeksToHarvest;
        this.lifeSpan = lifeSpan;
        this.pollination = pollination;
        this.catFriendly = catFriendly;
        this.origin = origin;
        this.notes = notes;
        this.photoPath = photoPath;
    }

    public int getPlantID() {
        return plantID;
    }

    public void setPlantID(int plantID) {
        this.plantID = plantID;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getWatering() {
        return watering;
    }

    public void setWatering(String watering) {
        this.watering = watering;
    }

    public String getSunlight() {
        return sunlight;
    }

    public void setSunlight(String sunlight) {
        this.sunlight = sunlight;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public int getWeeksToHarvest() {
        return weeksToHarvest;
    }

    public void setWeeksToHarvest(int weeksToHarvest) {
        this.weeksToHarvest = weeksToHarvest;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(String lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public String getPollination() {
        return pollination;
    }

    public void setPollination(String pollination) {
        this.pollination = pollination;
    }

    public String getCatFriendly() {
        return catFriendly;
    }

    public void setCatFriendly(String catFriendly) {
        this.catFriendly = catFriendly;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

}