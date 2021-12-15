package ch.ibw.knxgac.Model;

import ch.ibw.knxgac.Repository.Database.DBField;

import java.util.ArrayList;

public class Address implements DataInterface {
    @DBField(name = "id", isFilter = true, isPrimaryKey = true, datatype = "int", datatypesize = "(11)", isAutoincrement = true, type = Integer.class)
    private int id;
    @DBField(name = "name", useQuotes = true, datatype = "varchar", datatypesize = "(255)", type = String.class)
    private String name;
    @DBField(name = "idMiddlegroup", isFilter = true, datatype = "int", datatypesize = "(11)", isForeignKey = true, foreignTable = "MiddleGroup", foreignTableColumn = "id", type = Integer.class)
    private int idMiddlegroup;
    @DBField(name = "startaddress", datatype = "int", datatypesize = "(11)")
    private int startAddress;
    @DBField(isNotInDb = true)
    private ArrayList<ObjectTemplate> objectTemplates = new ArrayList<>();

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

    public int getIdMiddlegroup() {
        return idMiddlegroup;
    }

    public void setIdMiddlegroup(int idMiddlegroup) {
        this.idMiddlegroup = idMiddlegroup;
    }
}