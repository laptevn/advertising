package com.laptevn.advertising.initialization;

import com.laptevn.advertising.entity.SiteMonthlyData;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DateTimeException;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Parses CSV files to get the list of site monthly data.
 */
@ThreadSafe
@Component
public class DataParser {
    private final static int MONTH_GROUP_INDEX = 1;
    private final static String ENTRIES_SEPARATOR = ",";
    private final static int ENTRIES_COUNT = 6;

    private final static String ERROR_CANNOT_PARSE_MONTH = "Cannot parse month from file name. " +
            "Please use '2018_XY*' format of file name where XY are 2 digits representing a month value. Month value should be within 1-12 range.";
    private final static String ERROR_FILE_DOESNT_EXIST = "File doesn't exist";
    private final static String ERROR_GENERAL = "Cannot parse data";
    private final static String ERROR_DUPLICATE_ENTRIES = "File contains many entries for the same site";
    private final static String ERROR_WRONG_ENTRIES_COUNT = "Wrong entries count, " + ENTRIES_COUNT + " entries are expected";
    private final static String ERROR_WRONG_DATA_FORMAT = "Wrong format of site data";

    private final static int SITE_INDEX = 0;
    private final static int REQUESTS_INDEX = 1;
    private final static int IMPRESSIONS_INDEX = 2;
    private final static int CLICKS_INDEX = 3;
    private final static int CONVERSIONS_INDEX = 4;
    private final static int REVENUE_INDEX = 5;

    private final static Pattern monthPattern = Pattern.compile("2018_(\\d{2}).*");

    /**
     * Parses the content of a file
     * @param sourcePath Path of a file to parse
     * @return a list of site monthly data
     * @throws ParseException in case of parsing problems
     */
    List<SiteMonthlyData> parse(Path sourcePath) {
        if (!Files.exists(sourcePath)) {
            throw new ParseException(ERROR_FILE_DOESNT_EXIST);
        }

        Month month = parseMonth(sourcePath.getFileName().toString());
        List<SiteMonthlyData> monthlyData = parseMonthlyData(sourcePath, month);
        if (hasDuplicateEntries(monthlyData)) {
            throw new ParseException(ERROR_DUPLICATE_ENTRIES);
        }

        return monthlyData;
    }

    private List<SiteMonthlyData> parseMonthlyData(Path sourcePath, Month month) {
        try (Stream<String> linesStream = Files.lines(sourcePath)) {
            return linesStream
                    .map(line -> line.split(ENTRIES_SEPARATOR))
                    .filter(lineEntries -> !isHeader(lineEntries))
                    .map(lineEntries -> parseSiteData(lineEntries, month))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new ParseException(ERROR_GENERAL, e);
        }
    }

    private static boolean hasDuplicateEntries(List<SiteMonthlyData> monthlyData) {
        int uniqueEntriesCount = monthlyData
                .stream()
                .map(SiteMonthlyData::getSite)
                .collect(Collectors.toSet())
                .size();
        return monthlyData.size() != uniqueEntriesCount;
    }

    private boolean isHeader(String[] entries) {
        return entries.length == ENTRIES_COUNT && entries[ENTRIES_COUNT - 1].trim().equals("revenue (USD)");
    }

    private static SiteMonthlyData parseSiteData(String[] entries, Month month) throws ParseException {
        if (entries.length != ENTRIES_COUNT) {
            throw new ParseException(ERROR_WRONG_ENTRIES_COUNT);
        }

        try {
            return new SiteMonthlyData()
                    .setMonth(month.getDisplayName(TextStyle.FULL, Locale.ENGLISH))
                    .setSite(entries[SITE_INDEX].trim())
                    .setRequestsCount(Long.parseLong(entries[REQUESTS_INDEX].trim()))
                    .setImpressionsCount(Long.parseLong(entries[IMPRESSIONS_INDEX].trim()))
                    .setClicksCount(Long.parseLong(entries[CLICKS_INDEX].trim()))
                    .setConversionsCount(Long.parseLong(entries[CONVERSIONS_INDEX].trim()))
                    .setRevenue(Double.parseDouble(entries[REVENUE_INDEX].trim()));
        } catch (NumberFormatException e) {
            throw new ParseException(ERROR_WRONG_DATA_FORMAT, e);
        }
    }

    private Month parseMonth(String fileName) throws ParseException {
        Matcher monthMatcher = monthPattern.matcher(fileName);
        if (!monthMatcher.find() || monthMatcher.groupCount() != 1) {
            throw new ParseException(ERROR_CANNOT_PARSE_MONTH);
        }

        try {
            return Month.of(parseMonthCode(monthMatcher));
        } catch (DateTimeException e) {
            throw new ParseException(ERROR_CANNOT_PARSE_MONTH);
        }
    }

    private int parseMonthCode(Matcher monthMatcher) {
        try {
            return Integer.valueOf(monthMatcher.group(MONTH_GROUP_INDEX));
        } catch (NumberFormatException e) {
            throw new ParseException(ERROR_CANNOT_PARSE_MONTH);
        }
    }
}