package com.olegB.Bus.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.text.ParseException;

@MappedSuperclass
public abstract class AbstractBus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    String startTime;
    String endTime;
    public abstract String getStartTime();
    public abstract String getEndTime();
    public abstract void setStartTime(String date) throws ParseException;
    public abstract void setEndTime(String date) throws ParseException;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
