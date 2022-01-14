package ch.ibw.knxgac.Model;

public class Address extends Data {
    private int idMiddlegroup;
    private int startAddress;
    private ObjectTemplate objectTemplate;
    

    public Address() {
    }

    public Address(int idAddress) {
        this.setId(idAddress);
    }

    public int getIdMiddlegroup() {
        return idMiddlegroup;
    }

    public void setIdMiddlegroup(int idMiddlegroup) {
        this.idMiddlegroup = idMiddlegroup;
    }

    public int getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(int startAddress) {
        this.startAddress = startAddress;
    }

    
    public ObjectTemplate getObjectTemplate() {
        return objectTemplate;
    }

    public void setObjectTemplate(ObjectTemplate objectTemplate) {
        this.objectTemplate = objectTemplate;
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
            if(this.startAddress>0) {
                where += " AND startaddress = " + this.startAddress;
            }
            if(this.idMiddlegroup>0) {
                where += " AND idMiddlegroup = " + this.idMiddlegroup;
            }
        }
        where += " AND deleted <> 1";
        return  where;
    }

    @Override
    public String getUpdateClause() {
        String update = super.getUpdateClause();
        if(this.getStartAddress() >= 0) {
            if(update.length()>0) {
                update += ", ";
            }
            update += "startaddress = " + this.getStartAddress();
        }
        if(this.idMiddlegroup > 0) {
            if(update.length()>0) {
                update += ", ";
            }
            update += "idMiddlegroup = " + this.getIdMiddlegroup();
        }
        return update;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Address {");
        result.append("id: ").append(this.getId()).append(", ");
        result.append("name: ").append(this.getName()).append(", ");
        result.append("startaddress: ").append(this.getStartAddress()).append(", ");
        result.append("idMiddlegroup: ").append(this.getIdMiddlegroup()).append(", ");
        result.append("ObjectTemplate: {\n");
        if(this.objectTemplate != null) {
            result.append(this.objectTemplate).append("\n");
        }
        result.append("}");
        return result.toString();
    }

    @Override
    public int compareTo(Object o) {
        return this.startAddress-((Address) o).getStartAddress();
    }

}