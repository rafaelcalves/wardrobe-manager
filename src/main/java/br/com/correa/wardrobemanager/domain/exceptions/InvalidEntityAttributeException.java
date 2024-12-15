package br.com.correa.wardrobemanager.domain.exceptions;

public class InvalidEntityAttributeException extends RuntimeException {
    public InvalidEntityAttributeException(String message) {
        super(message);
    }
}
