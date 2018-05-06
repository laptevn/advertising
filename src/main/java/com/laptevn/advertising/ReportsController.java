package com.laptevn.advertising;

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
    private final DataRepository dataRepository;

    @Autowired
    public ReportsController(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @RequestMapping(value = "/reports/months/{month}/sites/{site}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getReports(@PathVariable("month") String month, @PathVariable("site") String site) {
        Optional<SiteMonthlyData> monthlyData = dataRepository.findById(new DataPrimaryKey(month, site));
        return monthlyData.isPresent() ? new ResponseEntity<>(monthlyData.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}