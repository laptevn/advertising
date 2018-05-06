package com.laptevn.advertising;

import com.laptevn.advertising.entity.SiteMonthlyData;
import com.laptevn.advertising.initialization.DataReader;
import com.laptevn.advertising.initialization.ParseException;
import org.easymock.EasyMock;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class DbInitializerTest {
    @Test
    public void entriesStored() {
        List<SiteMonthlyData> monthlyData = Arrays.asList(new SiteMonthlyData());
        DataRepository dataRepository = createRepository(monthlyData);
        new DbInitializer(createReader(monthlyData), dataRepository).initialize();

        EasyMock.verify(dataRepository);
    }

    @Test
    public void exceptionGenerated() {
        DataRepository dataRepository = EasyMock.createMock(DataRepository.class);
        EasyMock.replay(dataRepository);

        new DbInitializer(createBrokenReader(), dataRepository).initialize();
    }

    private static DataReader createReader(List<SiteMonthlyData> monthlyData) {
        DataReader reader = EasyMock.createMock(DataReader.class);
        EasyMock.expect(reader.read()).andReturn(monthlyData);
        EasyMock.replay(reader);
        return reader;
    }

    private static DataReader createBrokenReader() {
        DataReader reader = EasyMock.createMock(DataReader.class);
        EasyMock.expect(reader.read()).andThrow(new ParseException(""));
        EasyMock.replay(reader);
        return reader;
    }

    private static DataRepository createRepository(List<SiteMonthlyData> monthlyData) {
        DataRepository dataRepository = EasyMock.createMock(DataRepository.class);
        EasyMock.expect(dataRepository.saveAll(monthlyData)).andReturn(null).once();
        EasyMock.replay(dataRepository);
        return dataRepository;
    }
}