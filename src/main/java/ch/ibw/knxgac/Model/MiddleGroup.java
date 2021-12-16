package ch.ibw.knxgac.Model;

import java.util.ArrayList;

public class MiddleGroup extends Data {
    private int number;
    private int idMaingroup;
    private ArrayList<Address> addresses = new ArrayList<>();

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
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
            if(this.idMaingroup>0) {
                where += " AND idMaingroup = " + this.idMaingroup;
            }
        }
        return  where;
    }
}
