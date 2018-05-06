package com.laptevn.advertising.initialization;

import com.laptevn.advertising.entity.SiteMonthlyData;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class DataDirectoryReaderTest {
    private static final String DB_DIRECTORY = "db";

    @Test
    public void emptyDirectory() throws IOException {
        Path directoryPath = Files.createTempDirectory(DB_DIRECTORY);
        List<SiteMonthlyData> monthlyData;
        try {
            monthlyData = new DataDirectoryReader(directoryPath, new DataParser(), new MetricsCalculator()).read();
        } finally {
            directoryPath.toFile().delete();
        }

        assertTrue(monthlyData.isEmpty());
    }

    @Test
    public void nonEmptyDirectory() throws IOException {
        Path directoryPath = Files.createTempDirectory(DB_DIRECTORY);
        List<SiteMonthlyData> monthlyData;
        try {
            prepareFiles(directoryPath);
            monthlyData = new DataDirectoryReader(directoryPath, new DataParser(), new MetricsCalculator()).read();
        } finally {
            directoryPath.toFile().delete();
        }

        assertThat(monthlyData.size(), is(equalTo(8)));

        SiteMonthlyData januaryDesktopData = monthlyData.stream()
                .filter(data -> data.getMonth().equals("January") && data.getSite().equals("desktop web"))
                .findFirst().get();
        assertThat(String.format("%.2f", januaryDesktopData.getCtr()), is(equalTo("0.26")));
    }

    private static void prepareFiles(Path directoryPath) throws IOException {
        String februaryContent = "site, requests, impressions, clicks, conversions, revenue (USD)\n" +
                "desktop web, 11243875, 10366355, 40456, 1456, 15745.32\n" +
                "mobile web, 8804521, 8101153, 15365, 5267, 18053.34\n" +
                "android, 8921215, 8342439, 22934, 5347, 17210.12\n" +
                "iOS, 5000221, 4512765, 18987, 6001, 11931.37\n";
        FileUtils.createTempFile(
                DataParserTest.FEBRUARY_FILE_PREFIX,
                DataParserTest.FILE_SUFFIX,
                Optional.of(februaryContent),
                Optional.of(directoryPath.toFile()));

        String januaryContent = "site, requests, impressions, clicks, conversions, revenue (USD)\n" +
                "desktop web, 12483775, 11866157, 30965, 7608, 23555.46\n" +
                "mobile web, 9905942, 9401153, 25291, 6216, 19053.61\n" +
                "android, 9914106, 9412958, 24395,6018, 18110.41\n" +
                "iOS, 2550165, 2419733, 6331, 1564, 4692.28\n";
        FileUtils.createTempFile(
                DataParserTest.JANUARY_FILE_PREFIX,
                DataParserTest.FILE_SUFFIX,
                Optional.of(januaryContent),
                Optional.of(directoryPath.toFile()));
    }
}