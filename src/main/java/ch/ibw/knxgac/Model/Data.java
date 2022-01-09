package ch.ibw.knxgac.Model;

/**
 * Any Data-Object has to implement this DataInterface,
 * otherwise it won't be able to use the Repository
 */
public class Data {
    protected int id = 0;
    protected String name = "";

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

    /**
     * Generates the WhereClause for SQL-Queries
     * @return String SQL-WhereClause
     */
    public String getWhereClause() {
        return "ID is not NULL";
    }

    public String getOrderByClause() {
        return "name ASC";
    }

    public String getUpdateClause() {
        if(this.getName() != null)
            return "name = '" + this.getName() + "'";
        return "";
    }
}
