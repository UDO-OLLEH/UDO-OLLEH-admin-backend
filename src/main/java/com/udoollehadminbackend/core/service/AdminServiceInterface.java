package com.udoollehadminbackend.core.service;

import com.udoollehadminbackend.web.dto.RequestAdmin;

public interface AdminServiceInterface {
    void register(RequestAdmin.adminInfo registerDto);
    Boolean isRootAdmin(String email);
}
