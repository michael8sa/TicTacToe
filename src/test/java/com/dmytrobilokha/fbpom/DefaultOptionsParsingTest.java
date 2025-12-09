package com.dmytrobilokha.fbpom;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

@Test(groups = "unit")
public class DefaultOptionsParsingTest {

    private PortsBuildConfig config;
    private BuildConfigParser parser;

    @BeforeMethod
    public void initParser() {
        config = new PortsBuildConfig();
        parser = new BuildConfigParser(config);
    }

    public void parsesMakefileLongDefaultOptions() {
        parser.parseMakefile(
                List.of(
                        "OPTIONS_SET+=\tGSSAPI_NONE HTTP2 ICONV IDN MANPAGES NLS OPENSSL \\",
                        "\t\t\tREADLINE SIMD SSE SSE2 SSE3 SSE41 SSE42 SSSE3 \\",
                        "\t\t\tTHREADS UTF8 ",
                        "# Options unset",
                        "\t\t\tOPTIONS_UNSET+=\tALSA AVAHI BUNDLED_CAIRO CAPIDOCS CUPS DEBUG \\",
                        "\t\t\tDOCBOOK DOCS DOXYGEN DTRACE ESOUND EXAMPLES GCONF \\",
                        "\t\t\tGNUTLS GVFS HAL HTMLDOCS IPV6 JACK JAPANESE \\",
                        "\t\t\tKERBEROS LDAP MDNS MDNSRESPONDER MHASH NAS \\",
                        "\t\t\tPULSEAUDIO RDOC SAMBA SMB TEST TESTS WAYLAND "
                ).iterator());
        var optionsState = config.getGlobalOptionsState();
        TestUtil.assertEnabledOptions(optionsState,
                "GSSAPI_NONE", "HTTP2", "ICONV", "IDN", "MANPAGES", "NLS",
                "OPENSSL", "READLINE", "SIMD", "SSE", "SSE2", "SSE3",
                "SSE41", "SSE42", "SSSE3", "THREADS", "UTF8");
        TestUtil.assertDisabledOptions(optionsState,
                "ALSA", "AVAHI", "BUNDLED_CAIRO", "CAPIDOCS", "CUPS", "DEBUG",
                "DOCBOOK", "DOCS", "DOXYGEN", "DTRACE", "ESOUND", "EXAMPLES",
                "GCONF", "GNUTLS", "GVFS", "HAL", "HTMLDOCS", "IPV6", "JACK",
                "JAPANESE", "KERBEROS", "LDAP", "MDNS", "MDNSRESPONDER",
                "MHASH", "NAS", "PULSEAUDIO", "RDOC", "SAMBA", "SMB", "TEST",
                "TESTS", "WAYLAND");
    }

}
