package com.udoollehadminbackend.provider.service;

import com.udoollehadminbackend.entity.Admin;
import com.udoollehadminbackend.repository.AdminRepository;
import com.udoollehadminbackend.web.dto.RequestAdmin;
import com.udoollehadminbackend.web.dto.ResponseAdmin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    @DisplayName("로그인 테스트")
    @Transactional
    void loginTest() {
        RequestAdmin.adminInfo requestDto = RequestAdmin.adminInfo.builder()
                .email("test")
                .password("1234")
                .build();
        adminService.register(requestDto);
        //로그인
        assertNotNull(adminService.login(requestDto).orElseGet(()->null));
    }
    @Test
    @DisplayName("accessToken 갱신 테스트")
    @Transactional
    void updateAccessTokenTest(){

        RequestAdmin.adminInfo requestDto = RequestAdmin.adminInfo.builder()
                .email("test")
                .password("1234")
                .build();
        adminService.register(requestDto);
        ResponseAdmin.token token = adminService.login(requestDto).orElseGet(()->null);
        ResponseAdmin.token newToken = adminService.updateAccessToken(token.getRefreshToken()).orElseGet(()-> null);
        assertNotNull(newToken);
    }
}
