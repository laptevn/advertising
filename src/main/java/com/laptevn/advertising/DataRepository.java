package com.laptevn.advertising;

import com.laptevn.advertising.entity.DataPrimaryKey;
import com.laptevn.advertising.entity.SiteMonthlyData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DataRepository extends CrudRepository<SiteMonthlyData, DataPrimaryKey> {
    @Query("SELECT new com.laptevn.advertising.entity.SiteMonthlyData(" +
            "month," +
            "'' AS site," +
            "SUM(requestsCount) AS requestsCount," +
            "SUM(impressionsCount) AS impressionsCount," +
            "SUM(clicksCount) AS clicksCount," +
            "SUM(conversionsCount) AS conversionsCount," +
            "SUM(revenue) AS revenue," +
            "AVG(ctr) AS ctr," +
            "AVG(cr) AS cr," +
            "AVG(fillRate) AS fillRate," +
            "AVG(ecpm) AS ecpm) FROM SiteMonthlyData WHERE month=:month GROUP BY month")
    Optional<SiteMonthlyData> findDataByMonth(@Param("month") String month);

    @Query("SELECT new com.laptevn.advertising.entity.SiteMonthlyData(" +
            "'' AS month," +
            "site," +
            "SUM(requestsCount) AS requestsCount," +
            "SUM(impressionsCount) AS impressionsCount," +
            "SUM(clicksCount) AS clicksCount," +
            "SUM(conversionsCount) AS conversionsCount," +
            "SUM(revenue) AS revenue," +
            "AVG(ctr) AS ctr," +
            "AVG(cr) AS cr," +
            "AVG(fillRate) AS fillRate," +
            "AVG(ecpm) AS ecpm) FROM SiteMonthlyData WHERE site=:site GROUP BY site")
    Optional<SiteMonthlyData> findDataBySite(@Param("site") String site);
}