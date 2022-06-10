package com.udoollehadminbackend.core.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
@PropertySource("classpath:/secret/admin-root.properties")
public class RootAuth {
    @Value("${root.id}")
    private String id;
    @Value("${root.password}")
    private String password;

}
