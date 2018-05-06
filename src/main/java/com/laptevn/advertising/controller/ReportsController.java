package com.laptevn.advertising.controller;

import com.laptevn.advertising.ReportsService;
import com.laptevn.advertising.entity.SiteMonthlyData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Provides reports per specific criteria.
 */
@RestController
public class ReportsController {
    private final static String ERROR_INVALID_MONTH = "Invalid month provided. Month can be one of the following:\n" +
            "* numeric (ranging from 1-12) that map to the corresponding months (`1` for `January`, `2` for `February` etc)\n" +
            "* first 3 letters of the month (`Jan` for `January`, `Feb` for `February` etc)\n" +
            "* full name of the month (case insensitive)";

    private static final Logger logger = LoggerFactory.getLogger(ReportsController.class);

    private final ReportsService reportsService;
    private final MonthUnifier monthUnifier;
    private final SiteUnifier siteUnifier;

    @Autowired
    public ReportsController(ReportsService reportsService, MonthUnifier monthUnifier, SiteUnifier siteUnifier) {
        this.reportsService = reportsService;
        this.monthUnifier = monthUnifier;
        this.siteUnifier = siteUnifier;
    }

    @RequestMapping(value = "/reports/months/{month}/sites/{site}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getReport(@PathVariable("month") String month, @PathVariable("site") String site) {
        logger.info("Getting report for {} month and {} site", month, site);

        Optional<String> unifiedMonth = monthUnifier.unify(month);
        if (!unifiedMonth.isPresent()) {
            logger.info("Invalid months provided");
            return new ResponseEntity<>(ERROR_INVALID_MONTH, HttpStatus.BAD_REQUEST);
        }

        String unifiedSite = siteUnifier.unify(site);
        Optional<SiteMonthlyData> monthlyData = reportsService.findDataByMonthAndSite(unifiedMonth.get(), unifiedSite);
        logger.info("Report is found - {}", monthlyData.isPresent());

        return monthlyData.isPresent() ? new ResponseEntity<>(monthlyData.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/reports/months/{month}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getReportForMonth(@PathVariable("month") String month) {
        logger.info("Getting report for {} month ", month);

        Optional<String> unifiedMonth = monthUnifier.unify(month);
        if (!unifiedMonth.isPresent()) {
            logger.info("Invalid months provided");
            return new ResponseEntity<>(ERROR_INVALID_MONTH, HttpStatus.BAD_REQUEST);
        }

        Optional<SiteMonthlyData> monthlyData = reportsService.findDataByMonth(unifiedMonth.get());
        logger.info("Report is found - {}", monthlyData.isPresent());

        return monthlyData.isPresent() ? new ResponseEntity<>(monthlyData.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/reports/sites/{site}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getReportForSite(@PathVariable("site") String site) {
        logger.info("Getting report for {} site ", site);

        Optional<SiteMonthlyData> monthlyData = reportsService.findDataBySite(siteUnifier.unify(site));
        logger.info("Report is found - {}", monthlyData.isPresent());

        return monthlyData.isPresent() ? new ResponseEntity<>(monthlyData.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}