package com.laptevn.advertising.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(value = Parameterized.class)
public class SiteUnifierTest {
    private final String inputValue;
    private final String outputValue;

    public SiteUnifierTest(String inputValue, String outputValue) {
        this.inputValue = inputValue;
        this.outputValue = outputValue;
    }

    @Parameterized.Parameters
    public static Collection<String[]> data() {
        return Arrays.asList(new String[][]{
                {"desktop_web", "desktop web"},
                {"mobile_web", "mobile web"},
                {"android", "android"},
                {"iOS", "iOS"},
                {"other_site", "other_site"},
                {"", ""},
                {null, null}
        });
    }

    @Test
    public void unifySite() {
        assertThat(new SiteUnifier().unify(inputValue), is(equalTo(outputValue)));
    }
}