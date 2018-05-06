package com.laptevn.advertising.initialization;

import com.laptevn.advertising.entity.SiteMonthlyData;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Calculates metrics for site monthly data.
 */
@ThreadSafe
@Component
public class MetricsCalculator {
    void calculate(List<SiteMonthlyData> monthlyData) {
        monthlyData.forEach(data -> data
                .setCtr(data.getImpressionsCount() <= 0 ? 0 : data.getClicksCount() * 100F / data.getImpressionsCount())
                .setCr(data.getImpressionsCount() <= 0 ? 0 : data.getConversionsCount() * 100F / data.getImpressionsCount())
                .setFillRate(data.getRequestsCount() <= 0 ? 0 : data.getImpressionsCount() * 100F / data.getRequestsCount())
                .setEcpm(data.getImpressionsCount() <= 0 ? 0 : data.getRevenue() * 1000.0 / data.getImpressionsCount()));
    }
}