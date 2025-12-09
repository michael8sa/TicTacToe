package com.dmytrobilokha.fbpom;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.stream.Stream;

public class FsService {

    public boolean isReadableRegularFile(Path path) {
        return Files.isReadable(path) && Files.isRegularFile(path);
    }

    public boolean isReadableExecutableDirectory(Path path) {
        return Files.isReadable(path) && Files.isExecutable(path) && Files.isDirectory(path);
    }

    public Iterator<String> getFileLinesIterator(Path filePath) {
        try {
            return Files.lines(filePath).iterator();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read file " + filePath, e);
        }
    }

    public Stream<Path> getDirectoryListing(Path directory) {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to list directory " + directory, e);
        }
    }

}
