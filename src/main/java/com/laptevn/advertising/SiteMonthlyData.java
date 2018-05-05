package com.laptevn.advertising;

// TODO: Use other names for serialized form
public class SiteMonthlyData {
    private String month;
    private String site;
    private long requestsCount;
    private long impressionsCount;
    private long clicksCount;
    private long conversionsCount;
    private double revenue;
    private float ctr;
    private float cr;
    private float fillRate;
    private double ecpm;

    public String getMonth() {
        return month;
    }

    public SiteMonthlyData setMonth(String month) {
        this.month = month;
        return this;
    }

    public String getSite() {
        return site;
    }

    public SiteMonthlyData setSite(String site) {
        this.site = site;
        return this;
    }

    public long getRequestsCount() {
        return requestsCount;
    }

    public SiteMonthlyData setRequestsCount(long requestsCount) {
        this.requestsCount = requestsCount;
        return this;
    }

    public long getImpressionsCount() {
        return impressionsCount;
    }

    public SiteMonthlyData setImpressionsCount(long impressionsCount) {
        this.impressionsCount = impressionsCount;
        return this;
    }

    public long getClicksCount() {
        return clicksCount;
    }

    public SiteMonthlyData setClicksCount(long clicksCount) {
        this.clicksCount = clicksCount;
        return this;
    }

    public long getConversionsCount() {
        return conversionsCount;
    }

    public SiteMonthlyData setConversionsCount(long conversionsCount) {
        this.conversionsCount = conversionsCount;
        return this;
    }

    public double getRevenue() {
        return revenue;
    }

    public SiteMonthlyData setRevenue(double revenue) {
        this.revenue = revenue;
        return this;
    }

    public float getCtr() {
        return ctr;
    }

    public SiteMonthlyData setCtr(float ctr) {
        this.ctr = ctr;
        return this;
    }

    public float getCr() {
        return cr;
    }

    public SiteMonthlyData setCr(float cr) {
        this.cr = cr;
        return this;
    }

    public float getFillRate() {
        return fillRate;
    }

    public SiteMonthlyData setFillRate(float fillRate) {
        this.fillRate = fillRate;
        return this;
    }

    public double getEcpm() {
        return ecpm;
    }

    public SiteMonthlyData setEcpm(double ecpm) {
        this.ecpm = ecpm;
        return this;
    }

    @Override
    public String toString() {
        return "SiteMonthlyData{" +
                "month='" + month + '\'' +
                ", site='" + site + '\'' +
                ", requestsCount=" + requestsCount +
                ", impressionsCount=" + impressionsCount +
                ", clicksCount=" + clicksCount +
                ", conversionsCount=" + conversionsCount +
                ", revenue=" + revenue +
                ", ctr=" + ctr +
                ", cr=" + cr +
                ", fillRate=" + fillRate +
                ", ecpm=" + ecpm +
                '}';
    }
}