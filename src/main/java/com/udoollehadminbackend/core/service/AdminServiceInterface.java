package com.udoollehadminbackend.core.service;

import com.udoollehadminbackend.web.dto.RequestAdmin;
import com.udoollehadminbackend.web.dto.ResponseAdmin;

import java.util.Optional;

public interface AdminServiceInterface {
    void register(RequestAdmin.adminInfo registerDto);
    Boolean isRootAdmin(String email);
    Optional<ResponseAdmin.token> login(RequestAdmin.adminInfo requestDto);
    String createAccessToken(String userid);
    String createRefreshToken(String userid);
}
