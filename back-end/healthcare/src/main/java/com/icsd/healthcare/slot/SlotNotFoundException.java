package com.icsd.healthcare.slot;

public class SlotNotFoundException extends  RuntimeException{
    public SlotNotFoundException(String message) {
        super(message);
    }
}
