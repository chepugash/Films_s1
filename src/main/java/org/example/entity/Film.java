package org.example.entity;

import java.util.Objects;

public class Film {
    private Integer id;
    private String title;
    private int publishedYear;
    private String director;
    private String genres;
    private String info;

    public Film(String title, int publishedYear, String director, String genres, String info) {
        this.id = null;
        this.title = title;
        this.publishedYear = publishedYear;
        this.director = director;
        this.genres = genres;
        this.info = info;
    }

    public Film(Integer id, String title, int publishedYear, String director, String genres, String info) {
        this.id = id;
        this.title = title;
        this.publishedYear = publishedYear;
        this.director = director;
        this.genres = genres;
        this.info = info;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return publishedYear == film.publishedYear && id.equals(film.id) && title.equals(film.title) && director.equals(film.director) && genres.equals(film.genres) && info.equals(film.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, publishedYear, director, genres, info);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
