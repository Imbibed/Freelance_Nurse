package org.jules.Model;

import java.time.LocalDate;

public class PlanningRow {

    public PlanningRow(int _id, LocalDate _date, boolean _isAvailable) {
        id = _id;
        date = _date;
        isAvailable = _isAvailable;
    }
    int id;
    LocalDate date;
    boolean isAvailable;

    public LocalDate getDate() {
        return date;
    }
}
