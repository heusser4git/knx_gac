package ch.ibw.knxgac.Model;

import java.util.ArrayList;

public class Project extends Data {
    private String name;
    private ArrayList<MainGroup> maingroups = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList getMaingroups() {
        return maingroups;
    }

    public void setMaingroups(ArrayList maingroups) {
        this.maingroups = maingroups;
    }

    public void addFloor(MainGroup floor) {
        this.maingroups.add(floor);
    }

    public void removeFloor(MainGroup floor) {
        this.maingroups.remove(floor);
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
        return  where;
    }
}
