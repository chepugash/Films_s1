package org.example.entity;

import java.util.Objects;

public class Comment {
    private Integer id;
    private int filmId;
    private String userNickname;
    private String text;

    public Comment(Integer id, int filmId, String userNickname, String text) {
        this.id = id;
        this.filmId = filmId;
        this.userNickname = userNickname;
        this.text = text;
    }

    public Comment(int filmId, String userNickname, String text) {
        this.id = null;
        this.filmId = filmId;
        this.userNickname = userNickname;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id.equals(comment.id) && filmId == comment.filmId && userNickname.equals(comment.userNickname) && text.equals(comment.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filmId, userNickname, text);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
