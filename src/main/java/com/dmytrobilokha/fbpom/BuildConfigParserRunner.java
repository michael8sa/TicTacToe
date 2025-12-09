package com.dmytrobilokha.fbpom;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BuildConfigParserRunner {

    private static final Path OPTIONS_FILE = Paths.get("options");

    private final Path makeFilePath;
    private final Path optionsDirectoryPath;
    private final FsService fsService;

    public BuildConfigParserRunner(Path makeFilePath,
                                   Path optionsDirectoryPath,
                                   FsService fsService) {
        this.makeFilePath = makeFilePath;
        this.optionsDirectoryPath = optionsDirectoryPath;
        this.fsService = fsService;
    }

    public String runParser() {
        var buildConfig = new PortsBuildConfig();
        var parser = new BuildConfigParser(buildConfig);
        parseMakeFile(parser);
        parseOptionsFiles(parser);
        buildConfig.deduplicateOptionsState();
        return buildConfig.getAsMakefileString();
    }

    private void parseMakeFile(BuildConfigParser parser) {
        if (!fsService.isReadableRegularFile(makeFilePath)) {
            return;
        }
        var makeFileLinesIterator = fsService.getFileLinesIterator(makeFilePath);
        parser.parseMakefile(makeFileLinesIterator);
    }

    private void parseOptionsFiles(BuildConfigParser parser) {
        if (!fsService.isReadableExecutableDirectory(optionsDirectoryPath)) {
            return;
        }
        fsService.getDirectoryListing(optionsDirectoryPath)
                .filter(fsService::isReadableExecutableDirectory)
                .forEach(dir -> parseOptionsDirectory(dir, parser));
    }

    private void parseOptionsDirectory(Path optionsDirectory, BuildConfigParser parser) {
        Path optionsFilePath = optionsDirectory.resolve(OPTIONS_FILE);
        if (!(Files.isReadable(optionsFilePath) && Files.isRegularFile(optionsFilePath))) {
            return;
        }
        String portName = optionsDirectory.getFileName().toString();
        parser.parseOptionsFile(portName, fsService.getFileLinesIterator(optionsFilePath));
    }

}
