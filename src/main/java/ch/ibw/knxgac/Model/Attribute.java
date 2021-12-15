package ch.ibw.knxgac.Model;

import ch.ibw.knxgac.Repository.Database.DBField;

public class Attribute {
    @DBField(name="id", isFilter = true, isPrimaryKey = true, datatype = "int(11)", isAutoincrement = true, type = Integer.class)
    private int id;
    @DBField(name = "name", isFilter = true, useQuotes = true, datatype = "varchar(255)", type = String.class)
    private String name;
    @DBField(name = "idObjectTemplate", isFilter = true, isForeignKey=true, foreignTable = "ObjectTemplate", foreignTableColumn = "id", datatype = "int(11)", type = Integer.class)
    private int idObjectTemplate;

    public Attribute(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Attribute() {
    }

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

    public int getIdObjectTemplate() {
        return idObjectTemplate;
    }

    public void setIdObjectTemplate(int idObjectTemplate) {
        this.idObjectTemplate = idObjectTemplate;
    }
}
