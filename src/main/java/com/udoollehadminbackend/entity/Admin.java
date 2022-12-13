package com.udoollehadminbackend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "admin")
@Entity
@Getter
@NoArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "salt")
    private String salt;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Builder
    public Admin(String email, String password, String salt) {
        this.email = email;
        this.password = password;
        this.salt = salt;
    }
    public void changeRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
