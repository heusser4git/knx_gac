package ch.ibw.knxgac.Model;

import java.util.ArrayList;

public class ObjectTemplate extends Data {
    private int idAddress;
    private ArrayList<Attribute> attributes = new ArrayList<>();

    public ObjectTemplate() {
    }

    public ObjectTemplate(int idObjectTemplate) {
        this.setId(idObjectTemplate);
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<Attribute> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(Attribute attribute) {
        this.attributes.add(attribute);
    }

    public void removeAttribute(Attribute attribute) {
        this.attributes.remove(attribute);
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
            if(this.idAddress>0) {
                where += " AND idAddress = " + this.idAddress;
            }
        }
        where += " AND deleted <> 1";
        return  where;
    }

    @Override
    public String getUpdateClause() {
        String update = super.getUpdateClause();
        update += " , idAddress = " + this.getIdAddress();
        return update;
    }

    @Override
    public String toString() {
        String result = "ObjectTemplate{";
        result += "id: " + this.getId() + ", ";
        result += "name: " + this.getName() + ", ";
        result += "idAddress: " + this.getIdAddress() + ", ";
        result += "Attributes: \n";
        for(Attribute a : this.getAttributes()) {
            result.concat(a.toString() + "\n");
        }
        result += "}";
        return result;
    }
}
