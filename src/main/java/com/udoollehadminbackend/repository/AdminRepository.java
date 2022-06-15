package com.udoollehadminbackend.repository;

import com.udoollehadminbackend.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByEmail(String email);
    Admin findByEmailAndPassword(String email, String password);
}
