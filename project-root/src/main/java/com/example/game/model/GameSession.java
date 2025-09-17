package com.example.game.model;

import jakarta.persistence.*;

@Entity
@Table(name = "game_session")
public class GameSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_lat", nullable = false)
    private Double userLat;

    @Column(name = "user_lng", nullable = false)
    private Double userLng;

    @Column(name = "correct_lat", nullable = false)
    private Double correctLat;

    @Column(name = "correct_lng", nullable = false)
    private Double correctLng;

    @Column(name = "earned_score", nullable = false)
    private Integer earnedScore;

    public GameSession() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getUserLat() {
        return userLat;
    }

    public void setUserLat(Double userLat) {
        this.userLat = userLat;
    }

    public Double getUserLng() {
        return userLng;
    }

    public void setUserLng(Double userLng) {
        this.userLng = userLng;
    }

    public Double getCorrectLat() {
        return correctLat;
    }

    public void setCorrectLat(Double correctLat) {
        this.correctLat = correctLat;
    }

    public Double getCorrectLng() {
        return correctLng;
    }

    public void setCorrectLng(Double correctLng) {
        this.correctLng = correctLng;
    }

    public Integer getEarnedScore() {
        return earnedScore;
    }

    public void setEarnedScore(Integer earnedScore) {
        this.earnedScore = earnedScore;
    }
}
