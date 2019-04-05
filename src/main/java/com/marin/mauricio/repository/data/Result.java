package com.marin.mauricio.repository.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Result {

    private List<Point> visited;
    private List<Point> cleaned;
    @SerializedName("final")
    private Position end;
    private int battery;

    public Result() {
        visited = new ArrayList<>();
        cleaned = new ArrayList<>();
    }

    public List<Point> getVisited() {
        return visited;
    }

    public void setVisited(List<Point> visited) {
        this.visited = visited;
    }

    public List<Point> getCleaned() {
        return cleaned;
    }

    public void setCleaned(List<Point> cleaned) {
        this.cleaned = cleaned;
    }

    public Position getEnd() {
        return end;
    }

    public void setEnd(Position end) {
        this.end = end;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }
}
