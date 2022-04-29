package fr.plrpower.exolia.launcher.auth.mineweb.utils.connection;

public class ConnectionMineweb {
    private static String URLRoot;
    private static int typeConnection;

    public ConnectionMineweb() {
    }

    public static void setUrlRoot(String URLRootSet) {
        URLRoot = URLRootSet;
    }

    public static void setTypeConnection(int typeConnectionSet) {
        typeConnection = typeConnectionSet;
    }

    public static String getUrlRoot() {
        return URLRoot;
    }

    public static int getTypeConnection() {
        return typeConnection;
    }
}