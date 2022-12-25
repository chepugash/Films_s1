package org.example.dao;

import org.example.entity.Film;
import org.example.util.ConnectionProvider;
import org.example.util.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmDao {
    private ConnectionProvider connectionProvider;

    public FilmDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public void createTable() throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon()
                    .prepareStatement("create table if not exists film (\n" +
                            "    id bigint primary key generated always as identity,\n" +
                            "    title varchar(255) not null,\n" +
                            "    published_year integer not null,\n" +
                            "    director varchar(64) not null default 'director',\n" +
                            "    genres varchar(255) not null default 'genres',\n" +
                            "    info text not null default 'info'\n" +
                            ");");
            st.execute();
            st = this.connectionProvider.getCon()
                    .prepareStatement("create table if not exists images (\n" +
                            "    id bigint primary key generated always as identity,\n" +
                            "    film_id bigint references film(id) on delete cascade not null,\n" +
                            "    image varchar(255)\n" +
                            ");");
            st.execute();
        } catch (SQLException e) {
            throw new DbException("Can't create table.", e);
        }
    }

    public List<Film> getPage() throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon()
                    .prepareStatement("SELECT * FROM film");
            ResultSet result = st.executeQuery();
            List<Film> films = new ArrayList<>();
            while(result.next()) {
                Film film = new Film(
                        result.getInt("id"),
                        result.getString("title"),
                        result.getInt("published_year"),
                        result.getString("director"),
                        result.getString("genres"),
                        result.getString("info")
                );
                films.add(film);
            }
            System.out.println(films);
            return films;
        } catch (SQLException e) {
            throw new DbException("Can't get film from DB.", e);
        }
    }

    public Film getDetail(int id) throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon()
                    .prepareStatement("SELECT * FROM film WHERE id = ?");
            st.setInt(1, id);
            ResultSet result = st.executeQuery();
            Boolean hasOne = result.next();
            if (hasOne) {
                return new Film(
                        result.getInt("id"),
                        result.getString("title"),
                        result.getInt("published_year"),
                        result.getString("director"),
                        result.getString("genres"),
                        result.getString("info")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DbException("Can't get film from DB.", e);
        }
    }

    public void setFilm(String title, int publishedYear, String director, String genres, String info) throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon()
                    .prepareStatement("INSERT INTO film (title, published_year, director, genres, info) VALUES (?, ?, ?, ?, ?)");
            st.setString(1, title);
            st.setInt(2, publishedYear);
            st.setString(3, director);
            st.setString(4, genres);
            st.setString(5, info);
            st.execute();
        } catch (SQLException e) {
            throw new DbException("Can't insert film to DB.", e);
        }
    }

    public void delFilm(int id) throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon()
                    .prepareStatement("DELETE FROM film WHERE id = ?");
            st.setInt(1, id);
            st.execute();
        } catch (SQLException e) {
            throw new DbException("Can't delete film from DB.", e);
        }
    }

    public void editFilm(Integer id, String title, int publishedYear, String director, String genres, String info) throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon()
                    .prepareStatement("UPDATE film SET title = ?, published_year = ?, director = ?, genres = ?, info = ? WHERE id = ?");
            st.setString(1, title);
            st.setInt(2, publishedYear);
            st.setString(3, director);
            st.setString(4, genres);
            st.setString(5, info);
            st.setInt(6, id);
            st.execute();
        } catch (SQLException e) {
            throw new DbException("Can't delete film from DB.", e);
        }
    }

    public void setImageById(Integer id, String image) throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon()
                    .prepareStatement("DELETE FROM images WHERE film_id = ?");
            st.setInt(1, id);
            st.execute();
            st = this.connectionProvider.getCon()
                    .prepareStatement(" INSERT INTO images (film_id, image) VALUES (?, ?)");
            st.setInt(1, id);
            st.setString(2, image);
            st.execute();
        } catch (SQLException e) {
            throw new DbException("Can't insert image to DB.", e);
        }
    }

    public String getImage(int id) throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon()
                    .prepareStatement("SELECT image FROM images WHERE film_id = ?");
            st.setInt(1, id);
            ResultSet result = st.executeQuery();
            Boolean hasOne = result.next();
            if (hasOne) {
                return result.getString("image");
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DbException("Can't get image from DB.", e);
        }
    }
}
