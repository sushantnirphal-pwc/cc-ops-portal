package com.company.ccops.common.util;

public final class Masking {
    private Masking() {}

    public static String maskEmail(String email) {
        if (email == null || email.isBlank()) return email;
        int at = email.indexOf('@');
        if (at <= 1) return "***" + email.substring(at);
        return email.charAt(0) + "***" + email.substring(at - 1);
    }

    public static String maskPhone(String phone) {
        if (phone == null || phone.length() < 4) return "****";
        String last4 = phone.substring(phone.length() - 4);
        return "******" + last4;
    }
}
