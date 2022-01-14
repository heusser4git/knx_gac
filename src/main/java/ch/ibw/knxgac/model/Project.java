package ch.ibw.knxgac.model;

import java.util.ArrayList;

public class Project extends Data {
    private int number;
    private final ArrayList<MainGroup> maingroups = new ArrayList<>();

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
    public String getOrderByClause() {
        return "number ASC";
    }

    @Override
    public String getUpdateClause() {
        String update = super.getUpdateClause();
        if(this.getNumber()>=0)
            if(update.length()>0) {
                update += ", ";
            }
            update += "number = " + this.getNumber();
        return update;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Project {");
        result.append("id: ").append(this.getId()).append(", ");
        result.append("name: ").append(this.getName()).append(", ");
        result.append("number: ").append(this.getNumber()).append(", ");
        result.append("Maingroups: \n");
        for (MainGroup m : this.getMaingroups()) {
            result.append(m.toString()).append("\n");
        }
        result.append("}");
        return result.toString();
    }
}
