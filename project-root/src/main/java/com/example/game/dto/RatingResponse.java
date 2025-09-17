package com.example.game.dto;

import com.example.game.model.Profile;
import java.util.List;

public class RatingResponse {
    private List<Profile> top;
    private int yourRank;
    private String sortBy;
    
    public RatingResponse(List<Profile> top, int yourRank, String sortBy) {
        this.top = top;
        this.yourRank = yourRank;
        this.sortBy = sortBy;
    }
    
    public List<Profile> getTop() { return top; }
    public int getYourRank() { return yourRank; }
    public String getSortBy() { return sortBy; }
}