package ch.ibw.knxgac.Model;

import java.util.ArrayList;

public class Project extends Data {
    private int number;
    private ArrayList<MainGroup> maingroups = new ArrayList<>();

    public Project() {
    }
    public Project(int idProject) {
        this.setId(idProject);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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
            if(this.number>0) {
                where += " AND number = " + this.number;
            }
        }
        where += " AND deleted <> 1";
        return where;
    }

    @Override
    public String toString() {
        String result = "Project{";
        result += "id: " + this.getId() + ", ";
        result += "name: " + this.getName() + ", ";
        result += "number: " + this.getNumber() + ", ";
        result += "Maingroups: \n";
        for (MainGroup m : this.getMaingroups()) {
            result.concat(m.toString() + "\n");
        }
        result += "}";
        return result;
    }
}
