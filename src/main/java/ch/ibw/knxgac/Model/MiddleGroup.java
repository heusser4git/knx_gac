package ch.ibw.knxgac.Model;

import java.util.ArrayList;

public class MiddleGroup extends Data {
    private int number;
    private int idMaingroup;
    private final ArrayList<Address> addresses = new ArrayList<>();

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
        if(this.getNumber()>=0) {
            if (update.length() > 0) {
                update += ", ";
            }
            update += "number = " + this.getNumber();
        }
        if(this.getIdMaingroup()>0) {
            if(update.length()>0) {
                update += ", ";
            }
            update += "idMaingroup = " + this.getIdMaingroup();
        }
        return update;
    }

    @Override
    public String getOrderByClause() {
        return "number ASC";
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("MiddleGroup{");
        result.append("id: ").append(this.getId()).append(", ");
        result.append("name: ").append(this.getName()).append(", ");
        result.append("number: ").append(this.getNumber()).append(", ");
        result.append("idMaingroup: ").append(this.getIdMaingroup()).append(", ");
        result.append("Adresses: \n");
        for(Address a : this.getAddresses()) {
            result.append(a.toString()).append("\n");
        }
        result.append("}");
        return result.toString();
    }
}
