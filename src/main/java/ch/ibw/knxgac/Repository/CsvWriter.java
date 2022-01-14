package ch.ibw.knxgac.Repository;


import ch.ibw.knxgac.Model.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class CsvWriter {
    private String filePath;
    private Path file;

    public CsvWriter(String filePath) {
        this.filePath = filePath;
        this.file = Path.of(this.filePath);
    }

    public boolean writeProjectToCsv(Project project) throws IOException {
        String templatemag1 = " ; ;";
        String templatemag2 = "\"\";\"\";\"\";\"\";\"AUTO\"";
        String templatemig = "\"\";\"\";\"\";\"AUTO\"";
        String templateadr = "\"\";\"\";\"AUTO\"";
        ArrayList<String> export = new ArrayList<>();
        for(MainGroup mainGroup : project.getMaingroups()) {
             export.add("\"" + mainGroup.getName() + "\";" + templatemag1 + "\"" + mainGroup.getNumber() + "\";" + templatemag2);
            for(MiddleGroup middleGroup : mainGroup.getMiddlegroups()) {
                export.add(" ;\"" + middleGroup.getName() + "\";" + " ;" + "\"" + mainGroup.getNumber() + "\";" + "\"" + middleGroup.getNumber() + "\";" + templatemig);
                for (Address address: middleGroup.getAddresses()) {
                    for (Attribute attribute : address.getObjectTemplate().getAttributes()) {
                        export.add(" ; ;\"" + address.getName() + " " + attribute.getName() + "\";\"" + mainGroup.getNumber() + "\";\"" + middleGroup.getNumber() + "\";\"" + (address.getStartAddress() + attribute.getNumber()) + "\";" + templateadr);
                    }
                }
            }
        }
        String csv = "";
        for (String s : export) {
            csv = csv + s + "\n";
        }
        return this.writeToFile(csv);
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
