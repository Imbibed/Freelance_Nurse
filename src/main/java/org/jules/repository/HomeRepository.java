package org.jules.repository;

import org.jules.Model.Nurse;
import org.jules.Model.PlanningRow;

import java.time.Month;
import java.util.List;

public interface HomeRepository {
    List<Nurse> getNurseList();

    List<PlanningRow> getPlanningByNurseIdForTheMonth(Month month, int nurseId) throws Exception;

    void updatePlanningRow(PlanningRow row, int nurseId);

    void createPlanningRow(PlanningRow row, int nurseId);
}
