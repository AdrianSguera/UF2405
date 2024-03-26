package com.ceica.firstspringcomplete.model;

import jakarta.persistence.*;

@Entity
@Table(name = "timeband")
public class TimeBand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    public TimeBand() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTimeBand() {
        return name;
    }

    public void setTimeBand(String timeBand) {
        this.name = timeBand;
    }
}
