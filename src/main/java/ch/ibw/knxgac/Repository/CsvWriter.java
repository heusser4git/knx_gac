package ch.ibw.knxgac.Repository;


import ch.ibw.knxgac.Model.Project;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class CsvWriter {
    private String filePath;
    private Path file;

    public CsvWriter(String filePath) {
        this.filePath = filePath;
        this.file = Path.of(this.filePath);
    }

    public boolean writeProjectToCsv(Project project) throws IOException {
        return this.writeToFile(project.getName());
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
