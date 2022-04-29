package fr.plrpower.exolia.launcher.auth.mineweb.utils;

public class Core {
    public static String Username;
    public static String Password;
    public static String v = "1.3.0";
    public static String accessToken;
    public static boolean isConnected;
    public static String URLRoot;
    public static int tpConnect;
    public static boolean debug;

    public Core() {
    }

    public static void log(String message) {
        if (debug) {
            System.out.println("[AUTH-MINEWEB] " + message);
        }

    }

    public static void clearData() {
        Username = null;
        Password = null;
        accessToken = null;
        isConnected = false;
        URLRoot = null;
        tpConnect = 0;
    }
}