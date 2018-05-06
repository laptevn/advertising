package com.laptevn.advertising.initialization;

import com.laptevn.advertising.entity.SiteMonthlyData;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class DataParserTest {
    static final String JANUARY_FILE_PREFIX = "2018_01_report";
    static final String FEBRUARY_FILE_PREFIX = "2018_02_report";
    static final String FILE_SUFFIX = ".csv";

    @Test(expected = ParseException.class)
    public void notExistingSourceFile() {
        new DataParser().parse(Paths.get(JANUARY_FILE_PREFIX + FILE_SUFFIX));
    }

    @Test
    public void emptySourceFile() throws IOException {
        List<SiteMonthlyData> data = parseData(JANUARY_FILE_PREFIX, FILE_SUFFIX);
        assertTrue(data.isEmpty());
    }

    @Test
    public void januaryData() throws IOException {
        String sourceData = "site, requests, impressions, clicks, conversions, revenue (USD)\n" +
                "desktop web, 12483775, 11866157, 30965, 7608, 23555.46\n" +
                "mobile web, 9905942, 9401153, 25291, 6216, 19053.61\n" +
                "android, 9914106, 9412958, 24395,6018, 18110.41\n" +
                "iOS, 2550165, 2419733, 6331, 1564, 4692.28\n";
        List<SiteMonthlyData> data = parseData(JANUARY_FILE_PREFIX, FILE_SUFFIX, sourceData);

        assertThat(data.size(), is(equalTo(4)));

        SiteMonthlyData desktopData = data.get(0);
        assertThat(desktopData.getSite(), is(equalTo("desktop web")));
        assertThat(desktopData.getMonth(), is(equalTo("January")));
        assertThat(desktopData.getRequestsCount(), is(equalTo(12483775L)));
        assertThat(desktopData.getImpressionsCount(), is(equalTo(11866157L)));
        assertThat(desktopData.getClicksCount(), is(equalTo(30965L)));
        assertThat(desktopData.getConversionsCount(), is(equalTo(7608L)));
        assertThat(desktopData.getRevenue(), is(equalTo(23555.46)));
        assertThat(desktopData.getCr(), is(equalTo(0.0F)));
        assertThat(desktopData.getCtr(), is(equalTo(0.0F)));
        assertThat(desktopData.getFillRate(), is(equalTo(0.0F)));
        assertThat(desktopData.getEcpm(), is(equalTo(0.0)));

        SiteMonthlyData mobileWebData = data.get(1);
        assertThat(mobileWebData.getSite(), is(equalTo("mobile web")));
        assertThat(mobileWebData.getMonth(), is(equalTo("January")));
        assertThat(mobileWebData.getRequestsCount(), is(equalTo(9905942L)));
        assertThat(mobileWebData.getImpressionsCount(), is(equalTo(9401153L)));
        assertThat(mobileWebData.getClicksCount(), is(equalTo(25291L)));
        assertThat(mobileWebData.getConversionsCount(), is(equalTo(6216L)));
        assertThat(mobileWebData.getRevenue(), is(equalTo(19053.61)));
        assertThat(mobileWebData.getCr(), is(equalTo(0.0F)));
        assertThat(mobileWebData.getCtr(), is(equalTo(0.0F)));
        assertThat(mobileWebData.getFillRate(), is(equalTo(0.0F)));
        assertThat(mobileWebData.getEcpm(), is(equalTo(0.0)));

        SiteMonthlyData androidData = data.get(2);
        assertThat(androidData.getSite(), is(equalTo("android")));
        assertThat(androidData.getMonth(), is(equalTo("January")));
        assertThat(androidData.getRequestsCount(), is(equalTo(9914106L)));
        assertThat(androidData.getImpressionsCount(), is(equalTo(9412958L)));
        assertThat(androidData.getClicksCount(), is(equalTo(24395L)));
        assertThat(androidData.getConversionsCount(), is(equalTo(6018L)));
        assertThat(androidData.getRevenue(), is(equalTo(18110.41)));
        assertThat(androidData.getCr(), is(equalTo(0.0F)));
        assertThat(androidData.getCtr(), is(equalTo(0.0F)));
        assertThat(androidData.getFillRate(), is(equalTo(0.0F)));
        assertThat(androidData.getEcpm(), is(equalTo(0.0)));

        SiteMonthlyData iosData = data.get(3);
        assertThat(iosData.getSite(), is(equalTo("iOS")));
        assertThat(iosData.getMonth(), is(equalTo("January")));
        assertThat(iosData.getRequestsCount(), is(equalTo(2550165L)));
        assertThat(iosData.getImpressionsCount(), is(equalTo(2419733L)));
        assertThat(iosData.getClicksCount(), is(equalTo(6331L)));
        assertThat(iosData.getConversionsCount(), is(equalTo(1564L)));
        assertThat(iosData.getRevenue(), is(equalTo(4692.28)));
        assertThat(iosData.getCr(), is(equalTo(0.0F)));
        assertThat(iosData.getCtr(), is(equalTo(0.0F)));
        assertThat(iosData.getFillRate(), is(equalTo(0.0F)));
        assertThat(iosData.getEcpm(), is(equalTo(0.0)));
    }

    @Test
    public void februaryDataWithoutHeader() throws IOException {
        String sourceData = "mobile web, 8804521, 8101153, 15365, 5267, 18053.34\n" +
                "123, 1, 2, 3, 4, 5.00123";
        List<SiteMonthlyData> data = parseData(FEBRUARY_FILE_PREFIX, ".xyz", sourceData);

        assertThat(data.size(), is(equalTo(2)));

        SiteMonthlyData mobileWebData = data.get(0);
        assertThat(mobileWebData.getSite(), is(equalTo("mobile web")));
        assertThat(mobileWebData.getMonth(), is(equalTo("February")));
        assertThat(mobileWebData.getRequestsCount(), is(equalTo(8804521L)));
        assertThat(mobileWebData.getImpressionsCount(), is(equalTo(8101153L)));
        assertThat(mobileWebData.getClicksCount(), is(equalTo(15365L)));
        assertThat(mobileWebData.getConversionsCount(), is(equalTo(5267L)));
        assertThat(mobileWebData.getRevenue(), is(equalTo(18053.34)));
        assertThat(mobileWebData.getCr(), is(equalTo(0.0F)));
        assertThat(mobileWebData.getCtr(), is(equalTo(0.0F)));
        assertThat(mobileWebData.getFillRate(), is(equalTo(0.0F)));
        assertThat(mobileWebData.getEcpm(), is(equalTo(0.0)));

        SiteMonthlyData otherData = data.get(1);
        assertThat(otherData.getSite(), is(equalTo("123")));
        assertThat(otherData.getMonth(), is(equalTo("February")));
        assertThat(otherData.getRequestsCount(), is(equalTo(1L)));
        assertThat(otherData.getRevenue(), is(equalTo(5.00123)));
    }

    @Test(expected = ParseException.class)
    public void duplicatedEntries() throws IOException {
        String sourceData = "site, requests, impressions, clicks, conversions, revenue (USD)\n" +
                "desktop web, 12483775, 11866157, 30965, 7608, 23555.46\n" +
                "mobile web, 9905942, 9401153, 25291, 6216, 19053.61\n" +
                "desktop web, 9914106, 9412958, 24395,6018, 18110.41\n" +
                "iOS, 2550165, 2419733, 6331, 1564, 4692.28\n";
        parseData(JANUARY_FILE_PREFIX, FILE_SUFFIX, sourceData);
    }

    @Test(expected = ParseException.class)
    public void invalidType() throws IOException {
        String sourceData = "site, requests, impressions, clicks, conversions, revenue (USD)\n" +
                "desktop web, 1248.3775, 11866157, 30965, 7608, 23555.46\n";
        parseData(JANUARY_FILE_PREFIX, FILE_SUFFIX, sourceData);
    }

    @Test(expected = ParseException.class)
    public void notEnoughCells() throws IOException {
        String sourceData = "site, requests, impressions, clicks, conversions, revenue (USD)\n" +
                "desktop web, 1248, 11866157, 7608, 23555.46\n";
        parseData(JANUARY_FILE_PREFIX, FILE_SUFFIX, sourceData);
    }

    @Test(expected = ParseException.class)
    public void invalidMonth() throws IOException {
        parseData("2018_13_report", FILE_SUFFIX);
    }

    @Test(expected = ParseException.class)
    public void notSupportedFileNameFormat() throws IOException {
        parseData("2017_01_report", FILE_SUFFIX);
    }

    private static List<SiteMonthlyData> parseData(String prefix, String suffix, Optional<String> content) throws IOException {
        File sourceFile = FileUtils.createTempFile(prefix, suffix, content, Optional.empty());
        try {
            return new DataParser().parse(sourceFile.toPath());
        } finally {
            sourceFile.delete();
        }
    }

    private static List<SiteMonthlyData> parseData(String prefix, String suffix) throws IOException {
        return parseData(prefix, suffix, Optional.empty());
    }

    private static List<SiteMonthlyData> parseData(String prefix, String suffix, String content) throws IOException {
        return parseData(prefix, suffix, Optional.of(content));
    }
}