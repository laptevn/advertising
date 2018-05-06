package com.laptevn.advertising.entity;

import java.io.Serializable;

/**
 * Composite primary key for data entity.
 */
public class DataPrimaryKey implements Serializable {
    private static final long serialVersionUID = -6166600325979726864L;

    private String month;
    private String site;

    public DataPrimaryKey(String month, String site) {
        this.month = month;
        this.site = site;
    }

    public DataPrimaryKey() {
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DataPrimaryKey that = (DataPrimaryKey) o;
        if (!month.equals(that.month)) {
            return false;
        }

        return site.equals(that.site);
    }

    @Override
    public int hashCode() {
        int result = month.hashCode();
        result = 31 * result + site.hashCode();
        return result;
    }
}