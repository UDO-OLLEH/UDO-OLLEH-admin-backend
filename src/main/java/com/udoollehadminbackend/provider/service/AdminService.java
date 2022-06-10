package com.udoollehadminbackend.provider.service;

import com.udoollehadminbackend.core.security.RootAuth;
import com.udoollehadminbackend.core.service.AdminServiceInterface;
import com.udoollehadminbackend.entity.Admin;
import com.udoollehadminbackend.exception.errors.RegisterFailedException;
import com.udoollehadminbackend.provider.security.JwtAuthTokenProvider;
import com.udoollehadminbackend.repository.AdminRepository;
import com.udoollehadminbackend.util.SHA256Util;
import com.udoollehadminbackend.web.dto.RequestAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class AdminService implements AdminServiceInterface {
    private final AdminRepository adminRepository;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;
    private final RootAuth rootAuth;

    @PostConstruct
    public void init(){
        //루트 관리자 등록
        try {
            register(RequestAdmin.adminInfo.builder()
                    .email(rootAuth.getId())
                    .password(rootAuth.getPassword())
                    .build());
        }catch (RegisterFailedException e){
            System.out.println("이미 등록된 루트");
        }
    }

    @Transactional
    @Override
    public void register(RequestAdmin.adminInfo registerDto){
        Admin admin = adminRepository.findByEmail(registerDto.getEmail());
        if(admin != null){
            throw new RegisterFailedException();
        }
        String salt = SHA256Util.generateSalt();
        String encryptedPassword = SHA256Util.getEncrypt(registerDto.getPassword(), salt);
        admin = Admin.builder()
                .email(registerDto.getEmail())
                .password(encryptedPassword)
                .salt(salt)
                .build();
        adminRepository.save(admin);
    }

    @Override
    public Boolean isRootAdmin(String email){
        return rootAuth.getId().equals(email);
    }
}
