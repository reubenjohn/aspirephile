package com.aspirephile.shared.timming;

import com.aspirephile.shared.debug.Logger;
import com.aspirephile.shared.debug.NullPointerAsserter;

import java.util.Calendar;
import java.util.Date;

public class ETA extends Time {
    private Logger l = new Logger(ETA.class);
    private NullPointerAsserter asserter = new NullPointerAsserter(l);
    
    private final Calendar c;
    Date startDate, endDate;

    public ETA(Date date) {
        super(0);
        this.startDate = date;
        c = Calendar.getInstance();
        endDate = c.getTime();
        recalculateEta();
    }

    public void setOffset(Date startDate) {
        if (asserter.assertPointer(startDate))
            this.startDate = startDate;
    }

    private void recalculateEta() {
        setTime(startDate.getTime() - endDate.getTime());
    }
}