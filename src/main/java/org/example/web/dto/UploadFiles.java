package org.example.web.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UploadFiles {

    @NotNull
    @NotEmpty
    @Size(max = 250)
    private String filename;

    public UploadFiles() {
    }

    public UploadFiles(String fileName) {
        this.filename = fileName;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
