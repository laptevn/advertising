package com.laptevn.advertising;

import com.laptevn.advertising.entity.DataPrimaryKey;
import com.laptevn.advertising.entity.SiteMonthlyData;
import org.springframework.data.repository.CrudRepository;

public interface DataRepository extends CrudRepository<SiteMonthlyData, DataPrimaryKey> {
}