package ch.ibw.knxgac.Model;

import ch.ibw.knxgac.Repository.Database.DBField;

import java.util.ArrayList;

public class Project implements DataInterface  {
    @DBField(name="id", isFilter = true, isPrimaryKey = true, datatype = "int", datatypesize = "(11)", isAutoincrement = true)
    private int id;
    @DBField(name = "name", isFilter = true, useQuotes = true, datatype = "varchar", datatypesize = "(255)", type = String.class)
    private String name;
    @DBField(isNotInDb = true)
    private ArrayList<MainGroup> maingroups = new ArrayList<>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}
