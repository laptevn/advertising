package com.laptevn.advertising.initialization;

import com.laptevn.advertising.entity.SiteMonthlyData;

import java.util.List;

public interface DataReader {
    List<SiteMonthlyData> read();
}