package com.laptevn.advertising.initialization;

import com.laptevn.advertising.entity.SiteMonthlyData;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Reads site monthly data from all files in a directory specified.
 */
@ThreadSafe
@Component
public class DataDirectoryReader implements DataReader {
    private static final Logger logger = LoggerFactory.getLogger(DataDirectoryReader.class);

    private final Path dbDirectory;
    private final DataParser parser;
    private final MetricsCalculator metricsCalculator;

    @Autowired
    public DataDirectoryReader(@Value("${dbDirectory}") Path dbDirectory, DataParser parser, MetricsCalculator metricsCalculator) {
        this.dbDirectory = dbDirectory;
        this.parser = parser;
        this.metricsCalculator = metricsCalculator;
    }

    @Override
    public List<SiteMonthlyData> read() {
        File[] dbFiles = dbDirectory.toFile().listFiles();
        if (dbFiles == null) {
            logger.error("Couldn't get db files list from {}", dbDirectory);

            return Collections.emptyList();
        }

        List<SiteMonthlyData> monthlyData = Arrays.stream(dbFiles)
                .map(File::toPath)
                .map(parser::parse)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        metricsCalculator.calculate(monthlyData);
        return monthlyData;
    }
}