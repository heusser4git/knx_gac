package ch.ibw.knxgac.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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

    /**
     * determined the available startadresses
     * out of a set of maxStartadresses less usedStartadresses
     */
    public ArrayList<Integer> availableStartadresses(ArrayList<Integer> maxStartadresses, ArrayList<Integer> usedStartadresses) {
        // calculate the difference between maxStartaddress und used Startaddress
        int[] uniqueAddresses = diff(this.arrayListToArray(maxStartadresses), this.arrayListToArray(usedStartadresses));

        ArrayList<Integer> digitsToCheck = new ArrayList<>();
        ArrayList<Integer> toDelete = new ArrayList<>();
        for(int checkAdr : uniqueAddresses) {
            int anzAdr = this.getAttributes().size();
            int[] toCheck = new int[anzAdr];
            for (int i = 0; i < anzAdr; i++) {
                // pruefe ob die aktelle und x-weitere verfuegbar sind
                digitsToCheck.add(checkAdr+i);
                toCheck[i] = checkAdr+i;
            }
            // are the toCheck in uniqueAddresses? if, then ok
            int[] uebereinstimmung = this.intersection(uniqueAddresses, toCheck);
            if(uebereinstimmung.length!=anzAdr) {
                toDelete.add(checkAdr);
            }
        }
        // from arraylist to array
        int[] toDeleteArray = new int[toDelete.size()];
        for (int i = 0; i < toDelete.size(); i++) {
            toDeleteArray[i] = toDelete.get(i);
        }
        int[] availableArray = this.diff(uniqueAddresses, toDeleteArray);
        ArrayList<Integer> available = new ArrayList<>();
        for (int x : availableArray) {
            available.add(x);
        }
        return available;
    }

    /**
     * moves a int-arraylist to a array
     */
    private int[] arrayListToArray(ArrayList<Integer> arrayList) {
        int[] array = new int[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            array[i] = arrayList.get(i);
        }
        return array;
    }
    /**
     * gets the difference of two int-arrays
     */
    private int[] diff(int[] a, int[] b){
        // Quelle https://stackoverflow.com/questions/17863319/java-find-intersection-of-two-arrays
        return IntStream.concat(IntStream.of(a), IntStream.of(b))
                .filter(x -> IntStream.of(a).noneMatch(y -> y == x) || IntStream.of(b).noneMatch(z -> z == x))
                .toArray();
    }

    /**
     * fetches the matching values from two int arrays
     */
    private int[] intersection(int[] a, int[] b){
        // Quelle https://stackoverflow.com/questions/17863319/java-find-intersection-of-two-arrays
        return Arrays.stream(a)
                .distinct()
                .filter(x -> Arrays.stream(b).anyMatch(y -> y == x))
                .toArray();
    }
}
