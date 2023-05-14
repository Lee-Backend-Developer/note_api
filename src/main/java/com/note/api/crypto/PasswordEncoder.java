package com.note.api.crypto;

public interface PasswordEncoder {

    String encrypt(String password);

    Boolean matches(String rawPassword, String encryptedPassword);
}
