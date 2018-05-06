package com.laptevn.advertising;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ReportsITest {
    private MockMvc restClient;

    @Autowired
    public void setRestClient(MockMvc restClient) {
        this.restClient = restClient;
    }

    @Test
    public void notExistingSite() throws Exception {
        restClient.perform(get("/reports/months/January/sites/unknown")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void wrongMonth() throws Exception {
        restClient.perform(get("/reports/months/Januar/sites/desktop_web")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void fullMonthDesktop() throws Exception {
        restClient.perform(get("/reports/months/jAnuary/sites/desktop_web")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"month\":\"January\",\"site\":\"desktop web\",\"requests\":12483775,\"impressions\":11866157,\"clicks\":30965," +
                        "\"conversions\":7608,\"revenue\":23555.46,\"CTR\":0.26,\"CR\":0.06,\"fill_rate\":95.05,\"eCPM\":1.99}"));
    }

    @Test
    public void wrongMonthAggregate() throws Exception {
        restClient.perform(get("/reports/months/Januar")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void notExistingMonthAggregate() throws Exception {
        restClient.perform(get("/reports/months/December")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void notExistingSiteAggregate() throws Exception {
        restClient.perform(get("/reports/sites/unknown")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void monthAggregate() throws Exception {
        restClient.perform(get("/reports/months/jAnuary")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"month\":\"January\",\"requests\":34853988,\"impressions\":33100001,\"clicks\":86982," +
                        "\"conversions\":21406,\"revenue\":65411.76,\"CTR\":0.26,\"CR\":0.06,\"fill_rate\":94.95,\"eCPM\":1.97}"));
    }

    @Test
    public void siteAggregate() throws Exception {
        restClient.perform(get("/reports/sites/iOS")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"site\":\"iOS\",\"requests\":7550386,\"impressions\":6932498,\"clicks\":25318," +
                        "\"conversions\":7565,\"revenue\":16623.65,\"CTR\":0.34,\"CR\":0.1,\"fill_rate\":92.57,\"eCPM\":2.29}"));
    }
}