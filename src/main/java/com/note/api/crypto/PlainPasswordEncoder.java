package com.note.api.crypto;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("test")
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
