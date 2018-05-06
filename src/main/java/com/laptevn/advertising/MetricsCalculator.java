package com.laptevn.advertising;

import net.jcip.annotations.ThreadSafe;

import java.util.List;

/**
 * Calculates metrics for site monthly data.
 */
@ThreadSafe
public class MetricsCalculator {
    public void calculate(List<SiteMonthlyData> monthlyData) {
        monthlyData.forEach(data -> data
                .setCtr(data.getImpressionsCount() <= 0 ? 0 : data.getClicksCount() * 100F / data.getImpressionsCount())
                .setCr(data.getImpressionsCount() <= 0 ? 0 : data.getConversionsCount() * 100F / data.getImpressionsCount())
                .setFillRate(data.getRequestsCount() <= 0 ? 0 : data.getImpressionsCount() * 100F / data.getRequestsCount())
                .setEcpm(data.getImpressionsCount() <= 0 ? 0 : data.getRevenue() * 1000.0 / data.getImpressionsCount()));
    }
}