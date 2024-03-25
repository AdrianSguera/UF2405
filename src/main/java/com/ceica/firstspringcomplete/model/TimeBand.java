package com.ceica.firstspringcomplete.model;

import jakarta.persistence.*;

@Entity
@Table
public class TimeBand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String timeBand;

    public TimeBand() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTimeBand() {
        return timeBand;
    }

    public void setTimeBand(String timeBand) {
        this.timeBand = timeBand;
    }
}
