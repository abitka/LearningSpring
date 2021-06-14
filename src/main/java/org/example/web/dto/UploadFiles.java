package org.example.web.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UploadFiles {

    @NotNull
    @NotEmpty
    @Size(max = 250)
    private String filename;
    @NotNull
    @NotEmpty
    private String filepath;

    public UploadFiles() {
    }

    public UploadFiles(String filename, String filepath) {
        this.filename = filename;
        this.filepath = filepath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
