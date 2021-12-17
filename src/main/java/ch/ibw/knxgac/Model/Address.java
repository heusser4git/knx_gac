package ch.ibw.knxgac.Model;

import java.util.ArrayList;

public class Address extends Data {
    private int idMiddlegroup;
    private int startAddress;
    private ArrayList<ObjectTemplate> objectTemplates = new ArrayList<>();

    public Address() {
    }

    public Address(int idAddress) {
        this.setId(idAddress);
    }

    public int getIdMiddlegroup() {
        return idMiddlegroup;
    }

    public void setIdMiddlegroup(int idMiddlegroup) {
        this.idMiddlegroup = idMiddlegroup;
    }

    public int getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(int startAddress) {
        this.startAddress = startAddress;
    }

    public ArrayList<ObjectTemplate> getObjectTemplates() {
        return objectTemplates;
    }

    public void setObjectTemplates(ArrayList<ObjectTemplate> objectTemplates) {
        this.objectTemplates = objectTemplates;
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
            if(this.startAddress>0) {
                where += " AND startaddress = " + this.startAddress;
            }
            if(this.idMiddlegroup>0) {
                where += " AND idMiddlegroup = " + this.idMiddlegroup;
            }
        }
        where += " AND deleted <> 1";
        return  where;
    }

    @Override
    public String getUpdateClause() {
        String update = super.getUpdateClause();
        if(this.getStartAddress()>=0)
            update += " , startaddress = " + this.getStartAddress();
        if(this.idMiddlegroup>0)
            update += " , idMiddlegroup = " + this.getIdMiddlegroup();
        return update;
    }

    @Override
    public String toString() {
        String result = "Address{";
        result += "id: " + this.getId() + ", ";
        result += "name: " + this.getName() + ", ";
        result += "startaddress: " + this.getStartAddress() + ", ";
        result += "idMiddlegroup: " + this.getIdMiddlegroup() + ", ";
        result += "ObjectTemplates: \n";
        for(ObjectTemplate o : this.getObjectTemplates()) {
            result += o.toString() + "\n";
        }
        result += "}";
        return result;
    }
}