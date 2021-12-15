package ch.ibw.knxgac.Model;

import ch.ibw.knxgac.Repository.Database.DBField;

import java.util.ArrayList;

/**
 * Model.Floor aka Hauptgruppe or Etage (expl. 1. Stock)
 */
public class MainGroup implements DataInterface  {
    @DBField(name="id", isFilter = true, isPrimaryKey = true, datatype = "int", datatypesize = "(11)", isAutoincrement = true, type = Integer.class)
    private int id;
    @DBField(name = "name", isFilter = true, useQuotes = true, datatype = "varchar", datatypesize = "(255)", type = String.class)
    private String name;
    @DBField(name = "number", isFilter = true, datatype = "int", datatypesize = "(11)", type = Integer.class)
    private String number;
    @DBField(name = "idProject", isFilter = true, isForeignKey=true, foreignTable = "project", foreignTableColumn = "id", datatype = "int(11)", type = Integer.class)
    private int idProject;
    @DBField(isNotInDb = true)
    private ArrayList<MiddleGroup> middlegroups = new ArrayList<>();

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
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
}
