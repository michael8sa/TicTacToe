package com.dmytrobilokha.fbpom;

import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class PortsBuildConfig {

    public static final String DEFAULT_VERSIONS_TOKEN = "DEFAULT_VERSIONS+=";
    public static final String GLOBAL_PORT_OPTIONS = "OPTIONS";
    public static final String COMMENT_SYMBOL = "#";
    public static final String NEXT_LINE = "\\";
    public static final String NEW_LINE = System.lineSeparator();
    private static final String NEW_OPTION_LINE = NEXT_LINE + NEW_LINE + "\t\t\t";
    private static final String SET = "_SET+=";
    private static final String UNSET = "_UNSET+=";
    private static final int NEW_LINE_TRESHOLD = 50;

    private final SortedSet<String> defaultVersions = new TreeSet<>();
    private final OptionsState globalOptionsState = new OptionsState();
    private final SortedMap<String, OptionsState> portOptionsStates = new TreeMap<>();

    public SortedSet<String> getDefaultVersions() {
        return defaultVersions;
    }

    public OptionsState getGlobalOptionsState() {
        return globalOptionsState;
    }

    public OptionsState createIfAbsentPortOptionsState(String portname) {
        return portOptionsStates.computeIfAbsent(portname, x -> new OptionsState());
    }

    public void deduplicateOptionsState() {
        for (var portOptionsState : portOptionsStates.values()) {
            portOptionsState.removeDuplications(globalOptionsState);
        }
    }

    public String getAsMakefileString() {
        var sb = new StringBuilder();
        formatDefaultVersions(sb);
        formatGlobalOptionsState(sb);
        for (var portEntry : portOptionsStates.entrySet()) {
            formatPortOptionsState(sb, portEntry.getKey(), portEntry.getValue());
        }
        return sb.toString();
    }

    private void formatDefaultVersions(StringBuilder sb) {
        if (defaultVersions.isEmpty()) {
            sb.append(COMMENT_SYMBOL)
                    .append("No default versions specified")
                    .append(NEW_LINE)
                    .append(NEW_LINE);
        } else {
            sb.append(COMMENT_SYMBOL)
                    .append("Default versions")
                    .append(NEW_LINE)
                    .append(DEFAULT_VERSIONS_TOKEN)
                    .append(formatTokens(defaultVersions))
                    .append(NEW_LINE);
        }
    }

    private void formatGlobalOptionsState(StringBuilder sb) {
        if (getGlobalOptionsState().isEmpty()) {
            sb.append(COMMENT_SYMBOL)
                    .append("No global options specified")
                    .append(NEW_LINE)
                    .append(NEW_LINE);
        } else {
            sb.append(COMMENT_SYMBOL)
                    .append("Global options")
                    .append(NEW_LINE);
            formatPortOptionsState(sb, GLOBAL_PORT_OPTIONS, globalOptionsState);
        }
    }

    private void formatPortOptionsState(StringBuilder sb, String portId, OptionsState optionsState) {
        if (optionsState.isEmpty()) {
            return;
        }
        var enabledOptions = optionsState.getEnabledOptions();
        if (!enabledOptions.isEmpty()) {
            sb.append(portId)
                    .append(SET)
                    .append(formatTokens(enabledOptions));
        }
        var disabledOptions = optionsState.getDisabledOptions();
        if (!disabledOptions.isEmpty()) {
            sb.append(portId)
                    .append(UNSET)
                    .append(formatTokens(disabledOptions));
        }
        sb.append(NEW_LINE);
    }

    private String formatTokens(Iterable<String> tokens) {
        if (!tokens.iterator().hasNext()) {
            return "";
        }
        StringBuilder outputBuilder = new StringBuilder("\t");
        int lineLength = 0;
        for (String token : tokens) {
            if (lineLength + token.length() > NEW_LINE_TRESHOLD && token.length() < NEW_LINE_TRESHOLD) {
                outputBuilder.append(NEW_OPTION_LINE);
                lineLength = 0;
            }
            outputBuilder.append(token).append(' ');
            lineLength += token.length() + 1;
        }
        outputBuilder.append(NEW_LINE);
        return outputBuilder.toString();
    }

}
