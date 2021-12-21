package ch.ibw.knxgac.Model;

public class Attribute extends Data {
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
        String result = "Attribute{";
        result += "id: " + this.getId() + ", ";
        result += "name: " + this.getName() + ", ";
        result += "idObjectTemplate: " + this.getIdObjectTemplate();
        result += "}";
        return result;
    }
}
