package de.fhdw.chitter.exceptions;

public class ResortDoesNotExistException extends Exception {
    public ResortDoesNotExistException() {
        super("Ressort existiert nicht");
    }
}
