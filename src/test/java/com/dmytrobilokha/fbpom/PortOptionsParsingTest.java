package com.dmytrobilokha.fbpom;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

@Test(groups = "unit")
public class PortOptionsParsingTest {

    private PortsBuildConfig config;
    private BuildConfigParser parser;

    @BeforeMethod
    public void initParser() {
        config = new PortsBuildConfig();
        parser = new BuildConfigParser(config);
    }

    public void parsesSimpleOption() {
        parser.parseOptionsFile("category_port", List.of(
                "OPTIONS_FILE_UNSET+=DOCS",
                "OPTIONS_FILE_SET+=EXAMPLES"
        ).iterator());
        var optionsState = config.createIfAbsentPortOptionsState("category_port");
        TestUtil.assertEnabledOptions(optionsState, "EXAMPLES");
        TestUtil.assertDisabledOptions(optionsState, "DOCS");
    }

    public void parsesSimpleMakefileOption() {
        parser.parseMakefile(
                List.of("category_port_UNSET+=DOCS",
                        "category_port_SET+=EXAMPLES"
                ).iterator());
        var optionsState = config.createIfAbsentPortOptionsState("category_port");
        TestUtil.assertEnabledOptions(optionsState, "EXAMPLES");
        TestUtil.assertDisabledOptions(optionsState, "DOCS");
    }

    public void parsesOptionWithMinus() {
        parser.parseOptionsFile("category_port", List.of("OPTIONS_FILE_UNSET+=DEP-RSA1024").iterator());
        var optionsState = config.createIfAbsentPortOptionsState("category_port");
        TestUtil.assertDisabledOptions(optionsState, "DEP-RSA1024");
        TestUtil.assertNoEnabledOptions(optionsState);
    }

    public void parsesTwoCharactersOption() {
        parser.parseOptionsFile("category_port", List.of("OPTIONS_FILE_UNSET+=A4").iterator());
        var optionsState = config.createIfAbsentPortOptionsState("category_port");
        TestUtil.assertDisabledOptions(optionsState, "A4");
        TestUtil.assertNoEnabledOptions(optionsState);
    }

    public void parsesMultilineMakefileOption() {
        parser.parseMakefile(List.of("category_port_SET+=   AOPTION BOPTION \\", "\t\t\tCOPTION").iterator());
        var optionsState = config.createIfAbsentPortOptionsState("category_port");
        TestUtil.assertEnabledOptions(optionsState, "AOPTION", "BOPTION", "COPTION");
        TestUtil.assertNoDisabledOptions(optionsState);
    }

}
