package com.udoollehadminbackend.core.service;

import com.udoollehadminbackend.web.dto.RequestAdmin;
import com.udoollehadminbackend.web.dto.ResponseAdmin;

import java.util.Optional;

public interface AdminServiceInterface {
    void register(RequestAdmin.AdminInfoDto registerDto);
    Boolean isRootAdmin(String email);
    Optional<ResponseAdmin.TokenDto> login(RequestAdmin.AdminInfoDto requestDto);
    Optional<ResponseAdmin.TokenDto> updateAccessToken(String refreshToken);
    String createAccessToken(String userid);
    String createRefreshToken(String userid);
    boolean validAdminAccessToken(String token);
}
