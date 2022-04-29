package fr.plrpower.exolia.launcher.auth.exception;

public class ServerNotFoundException extends Exception {
    public ServerNotFoundException() {
    }

    public ServerNotFoundException(String message) {super(message);}

    public ServerNotFoundException(String message, Throwable cause) {super(message, cause);}

    public ServerNotFoundException(Throwable cause) {super(cause);}
}