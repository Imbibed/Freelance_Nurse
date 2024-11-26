package org.jules.service;

import org.jules.Model.Nurse;
import org.jules.Model.PlanningRow;
import org.jules.repository.HomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeServiceImpl implements HomeService {

    private final HomeRepository homeRepository;

    @Autowired
    public HomeServiceImpl(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }

    @Override
    public boolean isKnownNurse(int id) {
        List<Integer> ids = homeRepository.getNurseList().stream().map(Nurse::getId).collect(Collectors.toList());
        return ids.contains(id);
    }

    @Override
    public List<PlanningRow> declarePlanning(List<PlanningRow> planningRows, int nurseId) throws Exception {
        if(planningRows.isEmpty()) {
            throw new Exception("La liste est vide");
        }
        Month month = planningRows.getFirst().getDate().getMonth();
        List<PlanningRow> dbRows = homeRepository.getPlanningByNurseIdForTheMonth(month, nurseId);
        for(PlanningRow row: planningRows){
            if (dbRows.stream().map(PlanningRow::getDate).collect(Collectors.toList()).contains(row.getDate())) {
                homeRepository.updatePlanningRow(row, nurseId);
            } else {
                homeRepository.createPlanningRow(row, nurseId);
            }
        }
        return homeRepository.getPlanningByNurseIdForTheMonth(month, nurseId);
    }
}
