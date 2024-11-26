package org.jules.service;

import org.jules.Model.PlanningRow;

import java.util.List;

public interface HomeService {
    boolean isKnownNurse(int id);

    List<PlanningRow> declarePlanning(List<PlanningRow> planningRows, int nurseId) throws Exception;
}
