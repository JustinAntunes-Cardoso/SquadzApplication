package com.example.squadzframes.model;

public class Event {
    String id;
    String location;
    String host;
    String time;
    String partySize;
    String complvl;

    public Event(){
        this.id = "";
        this.location = "";
        this.host = "";
        this.time = "";
        this.partySize = "";
        this.complvl = "";
    }

    public Event(String id, String location, String host, String time, String partySize, String complvl) {
        this.id = id;
        this.location = location;
        this.host = host;
        this.time = time;
        this.partySize = partySize;
        this.complvl = complvl;
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getHost() {
        return host;
    }

    public String getTime() {
        return time;
    }

    public String getPartySize() {
        return partySize;
    }

    public String getComplvl() {
        return complvl;
    }


}
