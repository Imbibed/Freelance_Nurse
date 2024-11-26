package org.jules.Model;

import java.util.List;


public class Nurse {
    int id;
    String email;
    List<PlanningRow> planning;

    public Nurse(int id, String email, List<PlanningRow> planning) {
        this.id = id;
        this.email = email;
        this.planning = planning;
    }

    public int getId() {
        return this.id;
    }

    public List<PlanningRow> getPlanning() {
        return planning;
    }

    public void setPlanning(List<PlanningRow> list){
        planning = list;
    }

    public void addPlanning(PlanningRow row){
        planning.add(row);
    }

}
