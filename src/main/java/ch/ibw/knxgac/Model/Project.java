package ch.ibw.knxgac.Model;

import java.util.ArrayList;

public class Project extends Data {
    private ArrayList<MainGroup> maingroups = new ArrayList<>();

    public Project() {
    }
    public Project(int idProject) {
        this.setId(idProject);
    }

    public ArrayList<MainGroup> getMaingroups() {
        return maingroups;
    }

    public void setMaingroups(ArrayList<MainGroup> maingroups) {
        this.maingroups.addAll(maingroups);
    }

    public void addMaingroup(MainGroup mainGroup) {
        this.maingroups.add(mainGroup);
    }

    public void removeMaingroup(MainGroup mainGroup) {
        this.maingroups.remove(mainGroup);
    }

    @Override
    public String getWhereClause() {
        String where = "ID is not NULL";
        if(this.id>0) {
            where = "ID = " + this.id;
        } else {
            if(this.name.length()>0) {
                where += " AND name LIKE '" + this.name + "%'";
            }
        }
        return where;
    }
}
