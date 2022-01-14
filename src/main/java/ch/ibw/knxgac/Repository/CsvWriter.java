package ch.ibw.knxgac.Repository;


import ch.ibw.knxgac.Model.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

public class CsvWriter {
    private String filePath;
    private Path file;

    public CsvWriter(String filePath) {
        this.filePath = filePath;
        this.file = Path.of(this.filePath);
    }

    public boolean writeProjectToCsv(Project project) throws IOException {
        // Templates for repeating strings
        String templatemag1 = " ; ;";
        String templatemag2 = "\"\";\"\";\"\";\"\";\"\";\"\";\"AUTO\"";
        String templatemig = "\"\";\"\";\"\";\"\";\"\";\"AUTO\"";
        String templateadr = "\"\";\"\";\"\";\"\";\"AUTO\"";

        // create a string-array with the csv-export lines
        StringBuilder csvString = new StringBuilder();
        for(MainGroup mainGroup : project.getMaingroups()) {
             csvString.append("\"" + mainGroup.getName() + "\";" + templatemag1 + "\"" + mainGroup.getNumber() + "\";" + templatemag2);
             csvString.append("\n");
            for(MiddleGroup middleGroup : mainGroup.getMiddlegroups()) {
                csvString.append(" ;\"" + middleGroup.getName() + "\";" + " ;" + "\"" + mainGroup.getNumber() + "\";" + "\"" + middleGroup.getNumber() + "\";" + templatemig);
                csvString.append("\n");
                Collections.sort(middleGroup.getAddresses());
                for (Address address: middleGroup.getAddresses()) {
                    Collections.sort(address.getObjectTemplate().getAttributes());
                    for (Attribute attribute : address.getObjectTemplate().getAttributes()) {
                        csvString.append(" ; ;\"" + address.getName() + " " + attribute.getName() + "\";\"" + mainGroup.getNumber() + "\";\"" + middleGroup.getNumber() + "\";\"" + (address.getStartAddress() + attribute.getNumber()) + "\";" + templateadr);
                        csvString.append("\n");
                    }
                }
            }
        }
        return this.writeToFile(csvString.toString());
    }

    public boolean writeToFile(String data) throws IOException {
        BufferedWriter bufferedWriter = Files.newBufferedWriter(this.file, Charset.forName("UTF-8"));
        bufferedWriter.write(data);
        bufferedWriter.close();
        if(Files.exists(this.file)) {
            return true;
        }
        return false;
    }
}
