package com.company.ccops.common.util;

public final class Correlation {
    private static final ThreadLocal<String> ID = new ThreadLocal<>();
    private Correlation() {}

    public static String getId() {
        return ID.get();
    }
    public static void setId(String id) {
        ID.set(id);
    }
    public static void clear() {
        ID.remove();
    }
}
