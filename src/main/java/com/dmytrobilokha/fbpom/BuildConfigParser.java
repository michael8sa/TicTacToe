package com.dmytrobilokha.fbpom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BuildConfigParser {

    private static final Pattern SET_PATTERN = Pattern.compile("_SET\\+=");
    private static final Pattern UNSET_PATTERN = Pattern.compile("_UNSET\\+=");
    private static final Pattern VERSION_PATTERN = Pattern.compile("[.\\p{Alnum}_-]+=[.\\p{Alnum}_-]+");
    private static final Pattern OPTION_PATTERN = Pattern.compile("[\\p{Alnum}]+[\\p{Alnum}_-]*[\\p{Alnum}]+");
    private static final String OPTIONS_FILE_SET = "OPTIONS_FILE_SET+=";
    private static final String OPTIONS_FILE_UNSET = "OPTIONS_FILE_UNSET+=";
    private static final Pattern SEPARATION_PATTERN = Pattern.compile("\\s");

    private final PortsBuildConfig buildConfig;

    public BuildConfigParser(PortsBuildConfig buildConfig) {
        this.buildConfig = buildConfig;
    }

    public void parseMakefile(Iterator<String> makefileLinesIterator) {
        while (makefileLinesIterator.hasNext()) {
            String makefileLine = makefileLinesIterator.next().strip();
            if (!makefileLine.startsWith(PortsBuildConfig.COMMENT_SYMBOL)) {
                parseMakefileChunk(makefileLine, makefileLinesIterator);
            }
        }
    }

    private void parseMakefileChunk(String makefileLine, Iterator<String> makefileLinesIterator) {
        if (makefileLine.startsWith(PortsBuildConfig.DEFAULT_VERSIONS_TOKEN)) {
            parseMakefileDefaultVersions(
                    makefileLine.substring(PortsBuildConfig.DEFAULT_VERSIONS_TOKEN.length()), makefileLinesIterator);
            return;
        }
        var setMatcher = SET_PATTERN.matcher(makefileLine);
        var unsetMatcher = UNSET_PATTERN.matcher(makefileLine);
        OptionStatus optionsStatus;
        String portId;
        String optionsLine;
        if (setMatcher.find()) {
            optionsStatus = OptionStatus.SET;
            portId = makefileLine.substring(0, setMatcher.start());
            optionsLine = makefileLine.substring(setMatcher.end());
        } else if (unsetMatcher.find()) {
            optionsStatus = OptionStatus.UNSET;
            portId = makefileLine.substring(0, unsetMatcher.start());
            optionsLine = makefileLine.substring(unsetMatcher.end());
        } else {
            return;
        }
        if (PortsBuildConfig.GLOBAL_PORT_OPTIONS.equals(portId)) {
            parsePortOptions(optionsStatus, buildConfig.getGlobalOptionsState(), optionsLine, makefileLinesIterator);
            return;
        }
        parsePortOptions(
                optionsStatus,
                buildConfig.createIfAbsentPortOptionsState(portId),
                optionsLine,
                makefileLinesIterator);
    }

    private void parseMakefileDefaultVersions(String line, Iterator<String> makefileLinesIterator) {
        buildConfig.getDefaultVersions().addAll(
                        readConfigItems(line, makefileLinesIterator)
                                .stream()
                                .filter(v -> VERSION_PATTERN.matcher(v).matches())
                                .collect(Collectors.toSet()));
    }

    private void parsePortOptions(
            OptionStatus optionsStatus,
            OptionsState optionsState,
            String optionsLine,
            Iterator<String> makefileLinesIterator
    ) {
        var options = readConfigItems(optionsLine, makefileLinesIterator)
                .stream()
                .filter(o -> OPTION_PATTERN.matcher(o).matches())
                .collect(Collectors.toSet());
        if (optionsStatus == OptionStatus.SET) {
            optionsState.addEnabledOptions(options);
            return;
        }
        if (optionsStatus == OptionStatus.UNSET) {
            optionsState.addDisabledOptions(options);
        }
    }

    private List<String> readConfigItems(String line, Iterator<String> makefileLinesIterator) {
        String[] configItems = splitTokens(line);
        var result = new ArrayList<>(Arrays.asList(configItems));
        while (isMoreTokens(configItems, makefileLinesIterator)) {
            var currentLine = makefileLinesIterator.next().trim();
            configItems = splitTokens(currentLine);
            result.addAll(Arrays.asList(configItems));
        }
        return result;
    }

    public String[] splitTokens(String text) {
        return SEPARATION_PATTERN.split(text);
    }

    public boolean isMoreTokens(String[] tokens, Iterator<String> lineIterator) {
        return (tokens.length == 0 || PortsBuildConfig.NEXT_LINE.equals(tokens[tokens.length - 1]))
                && lineIterator.hasNext();
    }

    public void parseOptionsFile(String portName, Iterator<String> lineIterator) {
        while (lineIterator.hasNext()) {
            String line = lineIterator.next().strip();
            if (line.startsWith(OPTIONS_FILE_SET)) {
                parsePortOptions(
                        OptionStatus.SET,
                        buildConfig.createIfAbsentPortOptionsState(portName),
                        line.substring(OPTIONS_FILE_SET.length()),
                        lineIterator
                );
            } else if (line.startsWith(OPTIONS_FILE_UNSET)) {
                parsePortOptions(
                        OptionStatus.UNSET,
                        buildConfig.createIfAbsentPortOptionsState(portName),
                        line.substring(OPTIONS_FILE_UNSET.length()),
                        lineIterator
                );
            }
        }
    }

    public enum OptionStatus {
        SET, UNSET;
    }

}
