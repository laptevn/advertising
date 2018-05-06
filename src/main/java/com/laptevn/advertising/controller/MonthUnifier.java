package com.laptevn.advertising.controller;

import com.laptevn.advertising.MonthUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
import java.util.Optional;

@Component
class MonthUnifier {
    private static final String FULL_MONTH_PATTERN = "MMMM";
    private static final String SHORT_MONTH_PATTERN = "MMM";

    public Optional<String> unify(String month) {
        Optional<String> fullMonth = getFullMonthIgnoreCase(month);
        if (fullMonth.isPresent()) {
            return fullMonth;
        }

        Optional<String> shortMonth = getShortMonth(month);
        if (shortMonth.isPresent()) {
            return shortMonth;
        }

        return getNumericMonth(month);
    }

    private static Optional<String> getFullMonthIgnoreCase(String month) {
        String monthWithCase = StringUtils.capitalize(month.toLowerCase());
        return getMonth(monthWithCase, FULL_MONTH_PATTERN);
    }

    private static Optional<String> getShortMonth(String month) {
        return getMonth(month, SHORT_MONTH_PATTERN);
    }

    private static Optional<String> getMonth(String month, String pattern) {
        TemporalAccessor temporalAccessor;
        try {
            temporalAccessor = DateTimeFormatter.ofPattern(pattern).withLocale(Locale.ENGLISH).parse(month);
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }

        temporalAccessor.get(ChronoField.MONTH_OF_YEAR);
        return Optional.of(MonthUtils.monthToString(Month.from(temporalAccessor)));
    }

    private static Optional<String> getNumericMonth(String month) {
        try {
            int monthCode = Integer.parseInt(month);
            return Optional.of(MonthUtils.monthToString(Month.of(monthCode)));
        } catch (NumberFormatException | DateTimeException e) {
            return Optional.empty();
        }
    }
}