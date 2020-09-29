package com.olegB.Bus.util;

import com.olegB.Bus.exeption.WrongTimeFormatException;
import com.olegB.Bus.model.Bus;
import org.springframework.stereotype.Component;

/**
 * The class will check entered time.
 *  Throw TimeFormatException if not
 */
@Component
public class Validator {
    public boolean isValid(Bus bus) throws WrongTimeFormatException {
        String regex = "(([2][0-4])|([0-1][0-9])):[0-5][0-9]";

        if (!bus.getStartTime().matches(regex) || !bus.getEndTime().matches(regex)) {
            throw new WrongTimeFormatException("Time parse exception");
        }
        return true;
    }
}
