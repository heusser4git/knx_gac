package ch.ibw.knxgac.Model;

import ch.ibw.knxgac.Repository.Database.DBField;

import java.util.ArrayList;

public class ObjectTemplate implements DataInterface  {
    @DBField(name="id", isFilter = true, isPrimaryKey = true, datatype = "int(11)", isAutoincrement = true, type = Integer.class)
    private int id = 0;
    @DBField(name = "name", isFilter = true, useQuotes = true, datatype = "varchar(255)", type = String.class)
    private String name = null;
    @DBField(name = "idaddress", isFilter = true, isForeignKey=true, foreignTable = "Address", foreignTableColumn = "id", datatype = "int(11)", type = Integer.class)
    private int idAddress;
    @DBField(isNotInDb = true)
    private ArrayList<Attribute> attributes = new ArrayList<>();


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
}
