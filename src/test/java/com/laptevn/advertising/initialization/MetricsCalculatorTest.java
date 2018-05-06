package com.laptevn.advertising.initialization;

import com.laptevn.advertising.entity.SiteMonthlyData;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class MetricsCalculatorTest {
    @Test
    public void noData() {
        List<SiteMonthlyData> monthlyData = Collections.emptyList();
        new MetricsCalculator().calculate(monthlyData);
        assertTrue(monthlyData.isEmpty());
    }

    @Test
    public void zeroBases() {
        List<SiteMonthlyData> monthlyData = Collections.singletonList(
                new SiteMonthlyData()
        .setMonth("April")
        .setSite("Some site")
        .setRequestsCount(0)
        .setImpressionsCount(0)
        .setClicksCount(30965)
        .setConversionsCount(7608)
        .setRevenue(23555.46));

        new MetricsCalculator().calculate(monthlyData);

        assertThat(monthlyData.size(), is(equalTo(1)));
        assertThat(monthlyData.get(0).getCtr(), is(equalTo(0.0F)));
        assertThat(monthlyData.get(0).getCr(), is(equalTo(0.0F)));
        assertThat(monthlyData.get(0).getFillRate(), is(equalTo(0.0F)));
        assertThat(monthlyData.get(0).getEcpm(), is(equalTo(0.0)));
    }

    @Test
    public void severalEntries() {
        List<SiteMonthlyData> monthlyData = Arrays.asList(
                new SiteMonthlyData()
                        .setMonth("April")
                        .setSite("desktop web")
                        .setRequestsCount(12483775)
                        .setImpressionsCount(11866157)
                        .setClicksCount(30965)
                        .setConversionsCount(7608)
                        .setRevenue(23555.46),
                new SiteMonthlyData()
                        .setMonth("June")
                        .setSite("mobile web")
                        .setRequestsCount(9905942)
                        .setImpressionsCount(9401153)
                        .setClicksCount(25291)
                        .setConversionsCount(6216)
                        .setRevenue(19053.61));

        new MetricsCalculator().calculate(monthlyData);

        SiteMonthlyData desktopData = monthlyData.get(0);
        assertThat(String.format("%.2f", desktopData.getCtr()), is(equalTo("0.26")));
        assertThat(String.format("%.2f", desktopData.getCr()), is(equalTo("0.06")));
        assertThat(String.format("%.2f", desktopData.getFillRate()), is(equalTo("95.05")));
        assertThat(String.format("%.2f", desktopData.getEcpm()), is(equalTo("1.99")));

        SiteMonthlyData mobileData = monthlyData.get(1);
        assertThat(String.format("%.2f", mobileData.getCtr()), is(equalTo("0.27")));
        assertThat(String.format("%.2f", mobileData.getCr()), is(equalTo("0.07")));
        assertThat(String.format("%.2f", mobileData.getFillRate()), is(equalTo("94.90")));
        assertThat(String.format("%.2f", mobileData.getEcpm()), is(equalTo("2.03")));
    }
}