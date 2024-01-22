package com.movCast.demo.dto;

import com.movCast.demo.Films;

import java.util.List;

public class PaginatedFilmListingDTO {
    private int page;
    private List<FilmListingDTO> results;

    public PaginatedFilmListingDTO() {
    }

    public List<FilmListingDTO> getResults() {
        return results;
    }

    public void setResults(List<FilmListingDTO> results) {
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
