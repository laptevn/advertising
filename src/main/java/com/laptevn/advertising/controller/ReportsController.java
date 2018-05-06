package com.laptevn.advertising.controller;

import com.laptevn.advertising.DataRepository;
import com.laptevn.advertising.entity.DataPrimaryKey;
import com.laptevn.advertising.entity.SiteMonthlyData;
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

    private final DataRepository dataRepository;
    private final MonthUnifier monthUnifier;
    private final SiteUnifier siteUnifier;

    @Autowired
    public ReportsController(DataRepository dataRepository, MonthUnifier monthUnifier, SiteUnifier siteUnifier) {
        this.dataRepository = dataRepository;
        this.monthUnifier = monthUnifier;
        this.siteUnifier = siteUnifier;
    }

    @RequestMapping(value = "/reports/months/{month}/sites/{site}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getReports(@PathVariable("month") String month, @PathVariable("site") String site) {
        Optional<String> unifiedMonth = monthUnifier.unify(month);
        if (!unifiedMonth.isPresent()) {
            return new ResponseEntity<>(ERROR_INVALID_MONTH, HttpStatus.BAD_REQUEST);
        }

        String unifiedSite = siteUnifier.unify(site);
        Optional<SiteMonthlyData> monthlyData = dataRepository.findById(new DataPrimaryKey(unifiedMonth.get(), unifiedSite));
        return monthlyData.isPresent() ? new ResponseEntity<>(monthlyData.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}