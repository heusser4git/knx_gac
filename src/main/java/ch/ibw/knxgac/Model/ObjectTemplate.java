package ch.ibw.knxgac.Model;

import java.util.ArrayList;
import java.util.stream.IntStream;

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
        StringBuilder result = new StringBuilder();
        result.append("ObjectTemplate {");
        result.append("id: ").append(this.getId()).append(", ");
        result.append("name: ").append(this.getName()).append(", ");
        result.append("Attributes: \n");
        for(Attribute a : this.getAttributes()) {
            result.append(a.toString()).append("\n");
        }
        result.append("}");
        return result.toString();
    }

    public int[] availableStartadresses(int[] usedStartadresses, int[]maxStartadresses) {
        // berechne den unterschied von maxStartadresse und usedStartadresse
        int[] uniqueAdresses = IntStream.concat(IntStream.of(maxStartadresses), IntStream.of(usedStartadresses))
                .filter(x -> !IntStream.of(maxStartadresses).anyMatch(y -> y == x) || !IntStream.of(usedStartadresses).anyMatch(z -> z == x))
                .toArray();

        Integer anzAdr = this.getAttributes().size();
        for (int i = 0; i < anzAdr; i++) {
            // pruefe ob die aktelle und x-weitere verfuegbar sind

        }
        return uniqueAdresses;
    }
//    public  int[] intersectionSimple(int[] a, int[] b){
//        IntStream.of(a).filter(Arrays.asList(IntStream.of(b)):con)
//        return IntStream.of(a).filter(Arrays.asList(b)::contains);
//    }



}
