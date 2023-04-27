package com.nttstory.story.exception;

import java.util.NoSuchElementException;

public class ItemNotFoundException extends NoSuchElementException {
    public ItemNotFoundException(String message) {
        super(message);
    }
}
