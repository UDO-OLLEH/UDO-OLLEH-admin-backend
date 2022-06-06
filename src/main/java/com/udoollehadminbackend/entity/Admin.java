package com.udoollehadminbackend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "admin")
@Entity
@Getter
@NoArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "adminId")
    private String adminId;

    @Column(name = "password")
    private String password;

}
