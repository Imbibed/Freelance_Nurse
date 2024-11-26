package org.jules.repository;

import org.jules.Model.Nurse;
import org.jules.Model.PlanningRow;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeRepositoryImpli implements HomeRepository{
    private static final Nurse NURSE1 = new Nurse(1, "toto.toto@chu.com", new ArrayList<>());

    private static final Nurse NURSE2 = new Nurse(1, "toto.toto@chu.com", new ArrayList<>());

    static {
        NURSE1.addPlanning(new PlanningRow(1, LocalDate.of(2024, 11, 5), true));
        NURSE1.addPlanning(new PlanningRow(2, LocalDate.of(2024, 11, 16), true));
        NURSE1.addPlanning(new PlanningRow(3, LocalDate.of(2024, 11, 18), false));
        NURSE2.addPlanning(new PlanningRow(4, LocalDate.of(2024, 11, 2), true));
        NURSE2.addPlanning(new PlanningRow(5, LocalDate.of(2024, 11, 12), true));
        NURSE2.addPlanning(new PlanningRow(6, LocalDate.of(2024, 11, 21), false));
    }

    @Override
    public List<Nurse> getNurseList() {
        List<Nurse> nurses = new ArrayList<>();
        nurses.add(NURSE1);
        nurses.add(NURSE2);
        return nurses;
    }

    @Override
    public List<PlanningRow> getPlanningByNurseIdForTheMonth(Month month, int nurseId) throws Exception {
        List<Nurse> l = getNurseList().stream().filter(n -> n.getId() == nurseId).collect(Collectors.toList());
        if (l.isEmpty()) {
            throw new Exception("Infirmière non trouvée");
        }
        return l.getFirst().getPlanning().stream().filter(plr -> plr.getDate().getMonth() == month).collect(Collectors.toList());
    }

    @Override
    public void updatePlanningRow(PlanningRow row, int nurseId) {
        if(nurseId == NURSE1.getId()) {

        } else {

        }
    }

    @Override
    public void createPlanningRow(PlanningRow row, int nurseId) {

    }
}
