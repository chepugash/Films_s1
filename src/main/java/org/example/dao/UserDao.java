package org.example.dao;

import org.example.entity.User;
import org.example.util.ConnectionProvider;
import org.example.util.DbException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private ConnectionProvider connectionProvider;

    public UserDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public void createTable() throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon()
                    .prepareStatement("create table if not exists public.user (\n" +
                            "    id bigint primary key generated always as identity,\n" +
                            "    email varchar(255) not null unique,\n" +
                            "    password varchar(32) not null,\n" +
                            "    role varchar(20) not null default 'simpleuser'\n" +
                            ");");
            st.execute();
        } catch (SQLException e) {
            throw new DbException("Can't create table.", e);
        }
    }

    public Boolean isEmailExists(String username) throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon()
                    .prepareStatement("SELECT * FROM public.user WHERE email = ?");
            st.setString(1, username);
            ResultSet result = st.executeQuery();
            Boolean hasOne = result.next();
            return hasOne;
        } catch (SQLException e) {
            throw new DbException("Can't search for email in DB.");
        }
    }

    public User getUserByUsernameAndPassword(String username, String password) throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon()
                    .prepareStatement("SELECT * FROM public.user WHERE email = ? and password = MD5(?)");
            st.setString(1, username);
            st.setString(2, password);
            ResultSet result = st.executeQuery();
            Boolean hasOne = result.next();
            if (hasOne) {
                return new User(
                        result.getInt("id"),
                        result.getString("email"),
                        null,
                        result.getString("role")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DbException("Can't get film from DB.", e);
        }
    }
    
    public void setUser(String username, String password) throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon()
                    .prepareStatement("INSERT INTO public.user (email, password) VALUES (?, md5(?))");
            st.setString(1, username);
            st.setString(2, password);
            st.execute();
        } catch (SQLException e) {
            throw new DbException("Can't insert user to DB.", e);
        }
    }
}
