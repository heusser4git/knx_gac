package ch.ibw.knxgac.Model;

public class Attribute extends Data {
    private int idObjectTemplate;

    public Attribute(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Attribute() {
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
        return  where;
    }
}
