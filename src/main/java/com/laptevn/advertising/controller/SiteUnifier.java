package com.laptevn.advertising.controller;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
class SiteUnifier {
    private final Map<String, String> siteNames = new HashMap<>();

    public SiteUnifier() {
        siteNames.put("desktop_web", "desktop web");
        siteNames.put("mobile_web", "mobile web");
    }

    public String unify(String site) {
        return siteNames.containsKey(site) ? siteNames.get(site) : site;
    }
}