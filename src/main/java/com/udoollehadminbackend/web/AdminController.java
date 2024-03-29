package com.udoollehadminbackend.web;

import com.udoollehadminbackend.exception.errors.CustomJwtRuntimeException;
import com.udoollehadminbackend.exception.errors.LoginFailedException;
import com.udoollehadminbackend.exception.errors.NotRootAdminException;
import com.udoollehadminbackend.provider.security.JwtAuthToken;
import com.udoollehadminbackend.provider.security.JwtAuthTokenProvider;
import com.udoollehadminbackend.provider.service.AdminService;
import com.udoollehadminbackend.web.dto.RequestAdmin;
import com.udoollehadminbackend.web.dto.ResponseAdmin;
import com.udoollehadminbackend.web.dto.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    @PostMapping("/admin/register")
    public ResponseEntity<ResponseMessage> requestRegister(@Valid @RequestBody RequestAdmin.AdminInfoDto registerDto, HttpServletRequest request){
        Optional<String> token = jwtAuthTokenProvider.getAuthToken(request);
        String email = null;
        if(token.isPresent()){
            JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(token.get());
            email = jwtAuthToken.getClaims().getSubject();
        }
        //root 계정만 관리자 등록 가능
        if(adminService.isRootAdmin(email)){
            adminService.register(registerDto);
        }else throw new NotRootAdminException();

        return new ResponseEntity<>(ResponseMessage.builder()
                .message("관리자 등록 성공")
                .build(), HttpStatus.OK);
    }
    @PostMapping("/admin/login")
    public ResponseEntity<ResponseMessage> login(@Valid @RequestBody RequestAdmin.AdminInfoDto requestDto){
        ResponseAdmin.TokenDto token = adminService.login(requestDto).orElseThrow(()-> new LoginFailedException());

        return new ResponseEntity<>(ResponseMessage.builder()
                .message("로그인 성공")
                .list(token)
                .build(), HttpStatus.OK);
    }

    @PostMapping("/admin/update/accessToken")
    public ResponseEntity<ResponseMessage> updateAccessToken(@RequestBody Map<String, String> refreshToken){
        ResponseAdmin.TokenDto token = adminService.updateAccessToken(refreshToken.get("refreshToken")).orElseThrow(()-> new CustomJwtRuntimeException());

        return new ResponseEntity<>(ResponseMessage.builder()
                .message("accessToken 갱신 성공")
                .list(token)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/admin/valid/accessToken")
    public ResponseEntity<ResponseMessage> validAccessToken(HttpServletRequest request) {
        Optional<String> token = jwtAuthTokenProvider.getAuthToken(request);
        if(!adminService.validAdminAccessToken(token.orElseThrow(() -> new CustomJwtRuntimeException()))){
            throw new CustomJwtRuntimeException();
        }
        return new ResponseEntity<>(ResponseMessage.builder()
                .message("유효한 토큰입니다.")
                .build(), HttpStatus.OK);
    }
}
