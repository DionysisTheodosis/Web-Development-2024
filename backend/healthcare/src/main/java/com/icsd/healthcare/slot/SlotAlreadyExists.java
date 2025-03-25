package com.icsd.healthcare.slot;

public class SlotAlreadyExists extends  RuntimeException{
    public  SlotAlreadyExists(String message) {
        super(message);
    }
}

