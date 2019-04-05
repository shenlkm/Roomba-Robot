package com.marin.mauricio.repository.data;

import java.util.List;

public class Instruction {

    private List<String[]> map;
    private List<String> commands;
    private int battery;
    private Position start;

    public Position getStart() {
        return start;
    }

    public void setStart(Position start) {
        this.start = start;
    }

    public List<String[]> getMap() {
        return map;
    }

    public void setMap(List<String[]> map) {
        this.map = map;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }
}
