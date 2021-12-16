package ch.ibw.knxgac.Model;

import java.util.ArrayList;

public class MainGroup extends Data {
    private int number;
    private int idProject;
    private ArrayList<MiddleGroup> middlegroups = new ArrayList<>();

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
        return  where;
    }
}
