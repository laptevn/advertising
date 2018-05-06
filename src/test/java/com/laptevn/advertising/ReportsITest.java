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
}