package com.dmytrobilokha.fbpom;

import org.testng.Assert;

public final class TestUtil {

    private TestUtil() {
        //No instance
    }

    public static void assertNoEnabledOptions(OptionsState state) {
        assertEnabledOptions(state);
    }

    public static void assertEnabledOptions(OptionsState state, String... expected) {
        var enabledOptions = state.getEnabledOptions();
        Assert.assertEquals(enabledOptions.size(), expected.length, "Enabled options count");
        for (var expectedOption : expected) {
            Assert.assertTrue(enabledOptions.contains(expectedOption),
                    "The '" + expectedOption + "' should be enabled, but it is not: " + enabledOptions.toString());
        }
    }

    public static void assertNoDisabledOptions(OptionsState state) {
        assertDisabledOptions(state);
    }

    public static void assertDisabledOptions(OptionsState state, String... expected) {
        var disabledOptions = state.getDisabledOptions();
        Assert.assertEquals(disabledOptions.size(), expected.length, "Disabled options count");
        for (var expectedOption : expected) {
            Assert.assertTrue(disabledOptions.contains(expectedOption),
                    "The '" + expectedOption + "' should be disabled, but it is not: " + disabledOptions.toString());
        }
    }

}
