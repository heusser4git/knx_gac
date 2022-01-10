package ch.ibw.knxgac.Model;

import java.util.ArrayList;

public class ObjectTemplate extends Data {
    private ArrayList<Attribute> attributes = new ArrayList<>();

    public ObjectTemplate() {
    }

    public ObjectTemplate(int idObjectTemplate) {
        this.setId(idObjectTemplate);
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
        }
        where += " AND deleted <> 1";
        return  where;
    }

    @Override
    public String toString() {
        String result = "ObjectTemplate{";
        result += "id: " + this.getId() + ", ";
        result += "name: " + this.getName() + ", ";
        result += "Attributes: \n";
        for(Attribute a : this.getAttributes()) {
            result += a.toString() + "\n";
        }
        result += "}";
        return result;
    }
}
