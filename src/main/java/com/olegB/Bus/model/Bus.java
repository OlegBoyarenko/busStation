package com.olegB.Bus.model;


import com.olegB.Bus.util.Type;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Bus extends AbstractBus {
    @Enumerated(EnumType.STRING)
    Type type;
    String startTime;
    String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String date) {
        startTime = date;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String date) {
        endTime = date;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Bus(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Bus() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bus bus = (Bus) o;
        return Objects.equals(startTime, bus.startTime) &&
                Objects.equals(endTime, bus.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime);
    }

    @Override
    public String toString() {
        return "Bus{" +
                "type=" + type +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
