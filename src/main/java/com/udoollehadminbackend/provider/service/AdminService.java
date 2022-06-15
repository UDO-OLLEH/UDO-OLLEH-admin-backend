package com.udoollehadminbackend.provider.service;

import com.udoollehadminbackend.core.security.RootAuth;
import com.udoollehadminbackend.core.security.role.Role;
import com.udoollehadminbackend.core.service.AdminServiceInterface;
import com.udoollehadminbackend.entity.Admin;
import com.udoollehadminbackend.exception.errors.CustomJwtRuntimeException;
import com.udoollehadminbackend.exception.errors.LoginFailedException;
import com.udoollehadminbackend.exception.errors.RegisterFailedException;
import com.udoollehadminbackend.provider.security.JwtAuthToken;
import com.udoollehadminbackend.provider.security.JwtAuthTokenProvider;
import com.udoollehadminbackend.repository.AdminRepository;
import com.udoollehadminbackend.util.SHA256Util;
import com.udoollehadminbackend.web.dto.RequestAdmin;
import com.udoollehadminbackend.web.dto.ResponseAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

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

    @Override
    @Transactional
    public Optional<ResponseAdmin.token> login(RequestAdmin.adminInfo requestDto){
        Admin admin = adminRepository.findByEmail(requestDto.getEmail());
        if(admin == null){
            throw new LoginFailedException();
        }
        String salt = admin.getSalt();
        admin = adminRepository.findByEmailAndPassword(requestDto.getEmail(), SHA256Util.getEncrypt(requestDto.getPassword(), salt));
        if(admin == null){
            throw new LoginFailedException();
        }
        //로그인 성공
        String refreshToken = createRefreshToken(requestDto.getEmail());
        ResponseAdmin.token response = ResponseAdmin.token.builder()
                .accessToken(createAccessToken(admin.getEmail()))
                .refreshToken(refreshToken)
                .build();
        admin.changeRefreshToken(refreshToken);

        return Optional.ofNullable(response);
    }

    @Override
    public String createAccessToken(String userid) {
        //유효기간 설정-2분
        Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(10).atZone(ZoneId.systemDefault()).toInstant());
        //토큰 발급
        JwtAuthToken accessToken = jwtAuthTokenProvider.createAuthToken(userid, Role.ADMIN.getCode(),expiredDate);
        return accessToken.getToken();
    }
    @Override
    public String createRefreshToken(String userid) {
        //유효기간 설정-1년
        Date expiredDate = Date.from(LocalDateTime.now().plusYears(1) .atZone(ZoneId.systemDefault()).toInstant());
        //토큰 발급
        JwtAuthToken refreshToken = jwtAuthTokenProvider.createAuthToken(userid, Role.ADMIN.getCode(),expiredDate);
        return refreshToken.getToken();
    }

    @Transactional
    @Override
    public Optional<ResponseAdmin.token> updateAccessToken(String refreshToken){
        if(refreshToken == null || refreshToken.equals("null")){
            throw new CustomJwtRuntimeException();
        }
        Admin admin = adminRepository.findByRefreshToken(refreshToken);
        if(admin == null){
            throw new CustomJwtRuntimeException();
        }
        if(!admin.getRefreshToken().equals(refreshToken)){
            throw new CustomJwtRuntimeException();
        }
        //토큰 유효 검증
        JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(refreshToken);
        if(!jwtAuthToken.validate() || !jwtAuthToken.getClaims().get("role").equals(Role.ADMIN.getCode())){
            return Optional.empty(); //유효하지 않으면
        }

        String id = String.valueOf(jwtAuthToken.getClaims().getSubject());
        String accessToken = createAccessToken(id); //accessToken 재발급

        ResponseAdmin.token newToken = ResponseAdmin.token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        return Optional.ofNullable(newToken);
    }
}
