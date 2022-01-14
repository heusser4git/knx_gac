package ch.ibw.knxgac.model;

import java.util.ArrayList;

public class MainGroup extends Data {
    private int number;
    private int idProject;
    private ArrayList<MiddleGroup> middlegroups = new ArrayList<>();

    public MainGroup() {
    }

    public MainGroup(int idMaingroup) {
        this.setId(idMaingroup);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public ArrayList<MiddleGroup> getMiddlegroups() {
        return middlegroups;
    }

    public void setMiddlegroups(ArrayList<MiddleGroup> middlegroups) {
        this.middlegroups = middlegroups;
    }
    public void addMiddlegroup(MiddleGroup middleGroup) {
        this.middlegroups.add(middleGroup);
    }

    public void removeMiddlegroup(MiddleGroup middleGroup) {
        this.middlegroups.remove(middleGroup);
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
            if(this.idProject>0) {
                where += " AND idProject = " + this.idProject;
            }
        }
        where += " AND deleted <> 1";
        return  where;
    }

    @Override
    public String getOrderByClause() {
        return "number ASC";
    }

    @Override
    public String getUpdateClause() {
        String update = super.getUpdateClause();
        if(this.getNumber()>=0) {
            if (update.length() > 0) {
                update += ", ";
            }
            update += "number = " + this.getNumber();
        }
        if(this.idProject>0) {
            if (update.length() > 0) {
                update += ", ";
            }
            update += "idProject = " + this.getIdProject();
        }
        return update;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("MainGroup {");
        result.append("id: ").append(this.getId()).append(", ");
        result.append("name: ").append(this.getName()).append(", ");
        result.append("number: ").append(this.getNumber()).append(", ");
        result.append("idProject: ").append(this.getIdProject()).append(", ");
        result.append("Middlegroups: \n");
        for(MiddleGroup m : this.getMiddlegroups()) {
            result.append(m.toString()).append("\n");
        }
        result.append("}");
        return result.toString();
    }
}
