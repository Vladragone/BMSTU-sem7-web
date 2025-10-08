package com.example.game.model;

import jakarta.persistence.*;

@Entity
@Table(name = "game_locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double lat;

    @Column(nullable = false)
    private Double lng;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private LocationGroup group;

    public Location() {}

    public Long getId() {
        return id;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public LocationGroup getGroup() {
        return group;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public void setGroup(LocationGroup group) {
        this.group = group;
    }
}
