package com.dmytrobilokha.fbpom;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

@Test(groups = "unit")
public class DefaultVersionsParsingTest {

    private static final List<String> SIMPLE_VERSIONS = List.of("DEFAULT_VERSIONS+=\tjava=11");
    private static final List<String> DOUBLE_VERSIONS = List.of("DEFAULT_VERSIONS+=\tjava=11 python=3.8");
    private static final List<String> DOUBLE_MULTILINE_VERSIONS = List.of("DEFAULT_VERSIONS+= java=11\t\\",
            "   \\",
            "python=3.8");

    private PortsBuildConfig config;
    private BuildConfigParser parser;

    @BeforeMethod
    public void initParser() {
        config = new PortsBuildConfig();
        parser = new BuildConfigParser(config);
    }

    public void parsesSimpleVersionLine() {
        parser.parseMakefile(SIMPLE_VERSIONS.iterator());
        Assert.assertEquals(config.getDefaultVersions().size(), 1);
        Assert.assertTrue(config.getDefaultVersions().contains("java=11"));
    }

    public void parsesDoubleVersionLine() {
        parser.parseMakefile(DOUBLE_VERSIONS.iterator());
        Assert.assertEquals(config.getDefaultVersions().size(), 2);
        Assert.assertTrue(config.getDefaultVersions().contains("java=11"));
        Assert.assertTrue(config.getDefaultVersions().contains("python=3.8"));
    }

    public void parsesDoubleMultilineVersionLine() {
        parser.parseMakefile(DOUBLE_MULTILINE_VERSIONS.iterator());
        Assert.assertEquals(config.getDefaultVersions().size(), 2);
        Assert.assertTrue(config.getDefaultVersions().contains("java=11"));
        Assert.assertTrue(config.getDefaultVersions().contains("python=3.8"));
    }

}
