package com.orthocare.util;

import java.util.regex.Pattern;

public final class ValidationUtil {

    private static final Pattern EMAIL =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE = Pattern.compile("^[0-9+\\-\\s]{7,20}$");

    private ValidationUtil() {}

    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static boolean isEmail(String s) {
        return s != null && EMAIL.matcher(s.trim()).matches();
    }

    public static boolean isPhone(String s) {
        return s != null && PHONE.matcher(s.trim()).matches();
    }

    public static Integer parseInt(String s) {
        try { return (s == null || s.isBlank()) ? null : Integer.parseInt(s.trim()); }
        catch (NumberFormatException e) { return null; }
    }

    public static Double parseDouble(String s) {
        try { return (s == null || s.isBlank()) ? null : Double.parseDouble(s.trim()); }
        catch (NumberFormatException e) { return null; }
    }

    public static String safe(String s) { return s == null ? "" : s.trim(); }
}
