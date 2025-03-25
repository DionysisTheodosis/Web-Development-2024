package com.icsd.healthcare.slot;

import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;

public class SlotNotFoundException extends  RuntimeException{
    public SlotNotFoundException(String message) {
        super(message);
    }
    public SlotNotFoundException() {
        super(ErrorCode.ERROR_SLOT_NOT_FOUND);
    }
}
