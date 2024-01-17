package com.movCast.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Filmography {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String flickName;
    private String FlickDirector;
    private Integer FlickRating;
    private Integer TotalFlickRating;

    public Integer getTotalFlickRating() {
        return TotalFlickRating;
    }

    public void setTotalFlickRating(Integer totalFlickRating) {
        TotalFlickRating = totalFlickRating;
    }

    public Integer getFlickRating() { return FlickRating; }

    public void setFlickRating(Integer flickRating) { FlickRating = flickRating; }

    public void setID(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return this.id;
    }

    public void setFlickName(String film)
    {
        this.flickName = film;
    }

    public String getFlickName()
    {
        return this.flickName;
    }

    public void setFlickDirector(String director)
    {
        this.FlickDirector = director;
    }

    public String getFlickDirector()
    {
        return this.FlickDirector;
    }



}
