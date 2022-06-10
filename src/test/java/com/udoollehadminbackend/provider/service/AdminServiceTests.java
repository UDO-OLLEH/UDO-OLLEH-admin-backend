package com.udoollehadminbackend.provider.service;

import com.udoollehadminbackend.entity.Admin;
import com.udoollehadminbackend.repository.AdminRepository;
import com.udoollehadminbackend.web.dto.RequestAdmin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AdminServiceTests {
    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;
    @Test
    @DisplayName("회원가입 테스트")
    @Transactional
    void registerTest(){
        RequestAdmin.adminInfo requestDto = RequestAdmin.adminInfo.builder()
                .email("test")
                .password("1234")
                .build();
        adminService.register(requestDto);
        assertNotNull(adminRepository.findByEmail(requestDto.getEmail()));
    }
}
