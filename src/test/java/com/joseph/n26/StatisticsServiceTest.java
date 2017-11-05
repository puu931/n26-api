package com.joseph.n26;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.joseph.n26.service.IStatisticsService;
import com.joseph.n26.vo.Statistics;
import com.joseph.n26.vo.Transaction;


@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticsServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsServiceTest.class);

    @Autowired
    private IStatisticsService statisticsService;

    @Test
    public void testOrderThan60s(){
        Statistics summaryBefore = statisticsService.getStatistics();
        statisticsService.addTransaction(new Transaction(10.5, System.currentTimeMillis() - 60001));
        assertEquals(summaryBefore.getCount(), statisticsService.getStatistics().getCount());
        assertEquals(summaryBefore.getSum(), statisticsService.getStatistics().getSum(), 0.0);
        assertEquals(summaryBefore.getAvg(), statisticsService.getStatistics().getAvg(), 0.0);
        assertEquals(summaryBefore.getMax(), statisticsService.getStatistics().getMax(), 0.0);
        assertEquals(summaryBefore.getMin(), statisticsService.getStatistics().getMin(), 0.0);
    }

    @Test
    public void testGetStatistics(){
        logger.debug("====testGetStatistics====");
        long cur = System.currentTimeMillis();
        statisticsService.addTransaction(new Transaction(1.9, cur - 90000));
        statisticsService.addTransaction(new Transaction(2.8, cur - 80000));
        statisticsService.addTransaction(new Transaction(3.7, cur - 70000));
        statisticsService.addTransaction(new Transaction(4.6, cur - 60001));
        statisticsService.addTransaction(new Transaction(5.5, cur - 50000));
        statisticsService.addTransaction(new Transaction(6.4, cur - 40000));
        statisticsService.addTransaction(new Transaction(7.3, cur - 30000));
        statisticsService.addTransaction(new Transaction(8.2, cur - 20000));
        statisticsService.addTransaction(new Transaction(9.1, cur - 10000));
        statisticsService.addTransaction(new Transaction(10.0,  cur ));
        statisticsService.addTransaction(new Transaction(11.1, cur + 10000));
        statisticsService.addTransaction(new Transaction(22.2, cur + 20000));

        Statistics summary = statisticsService.getStatistics();
        logger.debug("summary:");
        logger.debug("count="+summary.getCount());
        logger.debug("sum="+summary.getSum());
        logger.debug("avg="+summary.getAvg());
        logger.debug("max="+summary.getMax());
        logger.debug("min="+summary.getMin());
        assertEquals(summary.getCount(), 6);
        assertEquals(summary.getSum(), 46.5, 0.0);
        assertEquals(summary.getAvg(), 7.75, 0.0);
        assertEquals(summary.getMax(), 10.0, 0.0);
        assertEquals(summary.getMin(), 5.5, 0.0);
    }

}
