package ch.ibw.knxgac.Model;

import java.util.ArrayList;

public class MiddleGroup extends Data {
    private int number;
    private int idMaingroup;
    private ArrayList<Address> addresses = new ArrayList<>();

    public MiddleGroup() {
    }

    public MiddleGroup(int idMiddlegroup) {
        this.setId(idMiddlegroup);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<Address> addresses) {
        this.addresses.addAll(addresses);
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
        where += " AND deleted <> 1";
        return  where;
    }

    @Override
    public String getUpdateClause() {
        String update = super.getUpdateClause();
        if(this.getNumber()>=0)
            update += " , number = " + this.getNumber();
        update += " , idMaingroup = " + this.getIdMaingroup();
        return update;
    }

    @Override
    public String toString() {
        String result = "MiddleGroup{";
        result += "id: " + this.getId() + ", ";
        result += "name: " + this.getName() + ", ";
        result += "idMaingroup: " + this.getIdMaingroup() + ", ";
        result += "Adresses: \n";
        for(Address a : this.getAddresses()) {
            result.concat(a.toString() + "\n");
        }
        result += "}";
        return result;
    }
}
