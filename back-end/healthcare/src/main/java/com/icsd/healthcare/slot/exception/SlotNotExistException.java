package com.icsd.healthcare.slot.exception;

public class SlotNotExistException extends  RuntimeException{
    public SlotNotExistException (String message) {
        super(message);
    }
}
