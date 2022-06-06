package com.udoollehadminbackend.core.security;

public interface AuthToken<T> {
    boolean validate();
    T getClaims();
}