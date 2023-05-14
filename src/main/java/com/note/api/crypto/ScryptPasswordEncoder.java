package com.note.api.crypto;

import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ScryptPasswordEncoder implements PasswordEncoder {

    private static final SCryptPasswordEncoder ENCODER = new SCryptPasswordEncoder(16, 8, 1, 32, 64);

    @Override
    public String encrypt(String password) {
        return ENCODER.encode(password);
    }

    @Override
    public Boolean matches(String rawPassword, String encryptedPassword) {
        return ENCODER.matches(rawPassword, encryptedPassword);
    }
}
