package com.laptevn.advertising;

import com.laptevn.advertising.entity.SiteMonthlyData;
import com.laptevn.advertising.initialization.DataReader;
import com.laptevn.advertising.initialization.ParseException;
import net.jcip.annotations.NotThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Initializes DB with data when the application is started.
 */
@NotThreadSafe
@Component
public class DbInitializer {
    private static final Logger logger = LoggerFactory.getLogger(DbInitializer.class);

    private final DataReader dataReader;
    private final DataRepository dataRepository;

    @Autowired
    public DbInitializer(DataReader dataReader, DataRepository dataRepository) {
        this.dataReader = dataReader;
        this.dataRepository = dataRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        List<SiteMonthlyData> monthlyData;
        try {
            monthlyData = dataReader.read();
        } catch (ParseException e) {
            logger.error("Couldn't initialize DB", e);
            return;
        }

        dataRepository.saveAll(monthlyData);
        logger.info("Stored {} entries in DB", monthlyData.size());
    }
}