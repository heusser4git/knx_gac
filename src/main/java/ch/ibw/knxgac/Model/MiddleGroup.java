package ch.ibw.knxgac.Model;

import ch.ibw.knxgac.Repository.Database.DBField;

import java.util.ArrayList;

/**
 * Model.Floor aka Hauptgruppe or Etage (expl. 1. Stock)
 */
public class MiddleGroup implements DataInterface  {
    @DBField(name="id", isFilter = true, isPrimaryKey = true, datatype = "int(11)", isAutoincrement = true, type = Integer.class)
    private int id;
    @DBField(name = "name", isFilter = true, useQuotes = true, datatype = "varchar(255)", type = String.class)
    private String name;
    @DBField(name = "number", isFilter = true, datatype = "int", datatypesize = "(11)", type = Integer.class)
    private String number;
    @DBField(name = "idMaingroup", isFilter = true, isForeignKey=true, foreignTable = "MainGroup", foreignTableColumn = "id", datatype = "int(11)", type = Integer.class)
    private int idMaingroup;
    @DBField(isNotInDb = true)
    private ArrayList<Address> addresses = new ArrayList<>();

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

    public ArrayList getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList addresses) {
        this.addresses = addresses;
    }

    public void addAddress(Address address) {
        this.addresses.add(address);
    }

    public void removeAddress(Address address) {
        this.addresses.remove(address);
    }

    public int getIdMaingroup() {
        return idMaingroup;
    }

    public void setIdMaingroup(int idMaingroup) {
        this.idMaingroup = idMaingroup;
    }
}
