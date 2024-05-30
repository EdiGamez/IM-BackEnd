package com.edig.imbackend.exceptions;

public class CategoryNotExistsException extends RuntimeException{
    public CategoryNotExistsException(String message) {
        super(message);
    }
}
