package com.dmytrobilokha.fbpom;

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class OptionsState {

    private final SortedSet<String> enabledOptions = new TreeSet<>();
    private final SortedSet<String> disabledOptions = new TreeSet<>();

    public void addEnabledOptions(Collection<String> options) {
        disabledOptions.removeAll(options);
        enabledOptions.addAll(options);
    }

    public void addDisabledOptions(Collection<String> options) {
        enabledOptions.removeAll(options);
        disabledOptions.addAll(options);
    }

    public void removeDuplications(OptionsState globalState) {
        enabledOptions.removeAll(globalState.enabledOptions);
        disabledOptions.removeAll(globalState.disabledOptions);
    }

    public List<String> getEnabledOptions() {
        return List.copyOf(enabledOptions);
    }

    public List<String> getDisabledOptions() {
        return List.copyOf(disabledOptions);
    }

    public boolean isEmpty() {
        return enabledOptions.isEmpty() && disabledOptions.isEmpty();
    }

    @Override
    public String toString() {
        return "OptionsState{"
                + "enabledOptions=" + enabledOptions
                + ", disabledOptions=" + disabledOptions
                + '}';
    }

}
