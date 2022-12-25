package org.example.entity;

import lombok.*;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString(of = "id")
public class User {
    private Integer id;
    private String username;
    private String password;
    private String role;

    public User(String username, String password, String role) {
        this.id = null;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
