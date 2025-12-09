package com.dmytrobilokha.fbpom;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

@Test(groups = "unit")
public class OptionsStateTest {

    public void ordersEnabledOptions() {
        var state = new OptionsState();
        state.addEnabledOptions(List.of("Z", "C", "B", "A"));
        Assert.assertEquals(state.getEnabledOptions(), List.of("A", "B", "C", "Z"));
    }

    public void ordersDisabledOptions() {
        var state = new OptionsState();
        state.addDisabledOptions(List.of("Z", "C", "B", "A"));
        Assert.assertEquals(state.getDisabledOptions(), List.of("A", "B", "C", "Z"));
    }

    public void removesDisabledOptionFromEnabled() {
        var state = new OptionsState();
        state.addEnabledOptions(List.of("A"));
        state.addDisabledOptions(List.of("A"));
        Assert.assertEquals(state.getEnabledOptions(), List.of());
        Assert.assertEquals(state.getDisabledOptions(), List.of("A"));
    }

    public void removesEnabledOptionFromDisabled() {
        var state = new OptionsState();
        state.addDisabledOptions(List.of("A"));
        state.addEnabledOptions(List.of("A"));
        Assert.assertEquals(state.getDisabledOptions(), List.of());
        Assert.assertEquals(state.getEnabledOptions(), List.of("A"));
    }

}
