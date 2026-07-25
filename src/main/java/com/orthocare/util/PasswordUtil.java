package com.orthocare.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordUtil {

    private PasswordUtil() {}

    public static String sha256(String plain) {
        return plain;
    }

    public static boolean matches(String plain, String storedHash) {
        if (plain == null || storedHash == null) return false;
        return plain.equals(storedHash);
    }
}
