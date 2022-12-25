package org.example.dao;

import lombok.Cleanup;
import org.example.entity.Comment;
import org.example.entity.Film;
import org.example.util.ConnectionProvider;
import org.example.util.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDao {
    private ConnectionProvider connectionProvider;

    public CommentDao (ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public void createTable() throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon()
                    .prepareStatement("create table if not exists comment (\n" +
                            "    id bigint primary key generated always as identity,\n" +
                            "    film_id bigint references film(id) on delete cascade not null,\n" +
                            "    user_email varchar(255) not null,\n" +
                            "    \"text\" text not null\n" +
                            ");");
            st.execute();
        } catch (SQLException e) {
            throw new DbException("Can't create table.", e);
        }
    }

    public void setComment(int filmId, String userNickname, String text) throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon()
                    .prepareStatement("INSERT INTO comment (film_id, user_email, text) VALUES (?, ?, ?)");
            st.setInt(1, filmId);
            st.setString(2, userNickname);
            st.setString(3, text);
            st.execute();
        } catch (SQLException e) {
            throw new DbException("Can't insert comment to DB.", e);
        }
    }

    public void delComment(Integer id) throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon()
                    .prepareStatement("DELETE FROM comment WHERE id = ?");
            st.setInt(1, id);
            st.executeQuery();
        } catch (SQLException e) {
            throw new DbException("Can't delete comment from DB.", e);
        }
    }

    public List<Comment> getCommentByFilmId(int filmId) throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon()
                    .prepareStatement("SELECT * FROM comment WHERE film_id = ?");
            st.setInt(1, filmId);
            ResultSet result = st.executeQuery();
            List<Comment> comments = new ArrayList<>();
            while(result.next()) {
                Comment comment = new Comment(
                        result.getInt("id"),
                        result.getInt("film_id"),
                        result.getString("user_email"),
                        result.getString("text")
                );
                comments.add(comment);
            }
            return comments;
        } catch (SQLException e) {
            throw new DbException("Can't get comments from DB.", e);
        }
    }
}
