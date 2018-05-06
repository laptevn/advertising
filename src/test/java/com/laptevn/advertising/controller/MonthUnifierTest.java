package com.laptevn.advertising.controller;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class MonthUnifierTest {
    @Test
    public void fullMonthWrongCase() {
        assertThat(new MonthUnifier().unify("sEptEmber").get(), is(equalTo("September")));
    }

    @Test
    public void fullMonth() {
        assertThat(new MonthUnifier().unify("September").get(), is(equalTo("September")));
    }

    @Test
    public void shortMonth() {
        assertThat(new MonthUnifier().unify("Sep").get(), is(equalTo("September")));
    }

    @Test
    public void shortMonthWrongCase() {
        assertFalse(new MonthUnifier().unify("SeP").isPresent());
    }

    @Test
    public void wrongTextMonth() {
        assertFalse(new MonthUnifier().unify("Junee").isPresent());
    }

    @Test
    public void numericMonth() {
        assertThat(new MonthUnifier().unify("1").get(), is(equalTo("January")));
    }

    @Test
    public void wrongNumericMonth() {
        assertFalse(new MonthUnifier().unify("13").isPresent());
    }
}