package com.laptevn.advertising;

import com.laptevn.advertising.entity.DataPrimaryKey;
import com.laptevn.advertising.entity.SiteMonthlyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReportsService {
    private final DataRepository dataRepository;

    @Autowired
    public ReportsService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public Optional<SiteMonthlyData> findDataByMonthAndSite(String month, String site) {
        return dataRepository.findById(new DataPrimaryKey(month, site));
    }

    public Optional<SiteMonthlyData> findDataByMonth(String month) {
        return dataRepository.findDataByMonth(month);
    }

    public Optional<SiteMonthlyData> findDataBySite(String site) {
        return dataRepository.findDataBySite(site);
    }
}