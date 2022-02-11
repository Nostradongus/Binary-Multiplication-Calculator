package com.csarch2.group11.binarymultiplication.service;

import java.nio.file.Path;

public interface FileExporter {
    public Path export(String fileContent, String fileName);
}
