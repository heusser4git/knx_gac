package ch.ibw.knxgac.model;

public class Attribute extends Data {
    private int number;
    private int idObjectTemplate;


    public Attribute() {
    }

    public Attribute(int idAttribute) {
        this.setId(idAttribute);
    }

    public Attribute(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getIdObjectTemplate() {
        return idObjectTemplate;
    }

    public void setIdObjectTemplate(int idObjectTemplate) {
        this.idObjectTemplate = idObjectTemplate;
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
            if(this.idObjectTemplate>0) {
                where += " AND idObjectTemplate = " + this.idObjectTemplate;
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
        if(this.idObjectTemplate>0) {
            if(update.length()>0) {
                update += ", ";
            }
            update += "idObjectTemplate = " + this.getIdObjectTemplate();
        }
        return update;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "id: " + this.getId() + ", " +
                "name: " + this.getName() + ", " +
                "number: " + this.getNumber() + ", " +
                "idObjectTemplate: " + this.getIdObjectTemplate() +
                "}";
    }

    @Override
    public int compareTo(Object o) {
        return this.number-((Attribute) o).getNumber();
    }
}
