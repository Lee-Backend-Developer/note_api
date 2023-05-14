package com.note.api.crypto;

import org.springframework.stereotype.Component;

//@Component
public class PlainPasswordEncoder implements PasswordEncoder {
    @Override
    public String encrypt(String rawPassword) {
        return rawPassword;
    }

    @Override
    public Boolean matches(String rawPassword, String encryptedPassword) {
        return rawPassword.equals(encryptedPassword);
    }
}
