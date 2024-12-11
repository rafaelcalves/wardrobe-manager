package br.com.correa.wardrobemanager.domain.exceptions;

public class ElementAttributeInvalidException extends RuntimeException {
    public ElementAttributeInvalidException(String message) {
        super(message);
    }
}
