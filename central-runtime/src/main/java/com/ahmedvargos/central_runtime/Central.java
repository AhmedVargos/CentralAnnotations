package com.ahmedvargos.central_runtime;


public class Central {
    private static int type = 0;

    public static int getType() {
        return type;
    }

    public static void setType(int type) {
        Central.type = type;
    }
}