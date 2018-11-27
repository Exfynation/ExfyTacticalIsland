package de.exfy.tacticalisland.maps;

import org.bukkit.Location;

public class Map {

    private String name;
    private String[] teams;
    private Location tower;
    private float towerRange;

    public Map(String name, String[] teams, Location tower, float towerRange) {
        this.name = name;
        this.teams = teams;
        this.tower = tower;
        this.towerRange = towerRange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getTeams() {
        return teams;
    }

    public void setTeams(String[] teams) {
        this.teams = teams;
    }

    public Location getTower() {
        return tower;
    }

    public void setTower(Location tower) {
        this.tower = tower;
    }

    public float getTowerRange() {
        return towerRange;
    }

    public void setTowerRange(float towerRange) {
        this.towerRange = towerRange;
    }

    @Override
    public String toString() {
        return "Map{name=" + this.name + ",tower=" + this.tower + ",towerrange=" + this.towerRange + '}';
    }
}
