package ch.ibw.knxgac.Model;

import java.util.ArrayList;

public class Address extends Data {
    private int idMiddlegroup;
    private int startAddress;
    private ArrayList<ObjectTemplate> objectTemplates = new ArrayList<>();

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
        return  where;
    }
}