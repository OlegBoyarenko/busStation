package com.olegB.Bus.dto;

import com.olegB.Bus.util.Type;

public class BusDTO {

    Type type;
    String startTime;
    String endTime;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public BusDTO() {
    }

    public BusDTO(Type type, String startTime, String endTime) {
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
