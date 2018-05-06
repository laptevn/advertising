package com.laptevn.advertising.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * Represents site monthly data.
 */
@IdClass(DataPrimaryKey.class)
@Entity
@Table(name = "site_monthly_data", indexes = {
        @Index(name = "month_index",  columnList = "month"),
        @Index(name = "site_index", columnList = "site")})
public class SiteMonthlyData {
    @Id
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String month;

    @Id
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String site;

    @JsonProperty("requests") private long requestsCount;
    @JsonProperty("impressions") private long impressionsCount;
    @JsonProperty("clicks") private long clicksCount;
    @JsonProperty("conversions") private long conversionsCount;

    @JsonProperty("revenue")
    @JsonSerialize(using=DoubleSerializer.class)
    private double revenue;

    @JsonProperty("CTR")
    @JsonSerialize(using=FloatSerializer.class)
    private float ctr;

    @JsonProperty("CR")
    @JsonSerialize(using=FloatSerializer.class)
    private float cr;

    @JsonProperty("fill_rate")
    @JsonSerialize(using=FloatSerializer.class)
    private float fillRate;

    @JsonProperty("eCPM")
    @JsonSerialize(using=DoubleSerializer.class)
    private double ecpm;

    public SiteMonthlyData() {
    }

    /**
     * Used by Spring Data aggregate queries.
     */
    public SiteMonthlyData(
            String month,
            String site,
            long requestsCount,
            long impressionsCount,
            long clicksCount,
            long conversionsCount,
            double revenue,
            double ctr,
            double cr,
            double fillRate,
            double ecpm) {

        this.month = month;
        this.site = site;
        this.requestsCount = requestsCount;
        this.impressionsCount = impressionsCount;
        this.clicksCount = clicksCount;
        this.conversionsCount = conversionsCount;
        this.revenue = revenue;
        this.ctr = (float) ctr;
        this.cr = (float) cr;
        this.fillRate = (float) fillRate;
        this.ecpm = ecpm;
    }

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