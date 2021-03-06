package com.joseph.n26.service.impl;

import java.util.DoubleSummaryStatistics;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.joseph.n26.service.IStatisticsService;
import com.joseph.n26.util.TimeUtil;
import com.joseph.n26.vo.Statistics;
import com.joseph.n26.vo.Transaction;

@Service
public class StatisticsServiceImpl implements IStatisticsService {

    private Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);

    private Map<Long, Transaction> transactionMap = new ConcurrentHashMap<Long, Transaction>();

    public Map<Long, Transaction> getTransactionMap() {
        return transactionMap;
    }

    public void setTransactionMap(Map<Long, Transaction> transactionMap) {
        this.transactionMap = transactionMap;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        if (transaction != null) {
            if (transaction.getAmount() != null && transaction.getAmount() != null) {
                if (!TimeUtil.isOlderThan60s(transaction.getTimestamp())) {
                    transactionMap.put(transaction.getTimestamp(), transaction);
                }
            }
        }
    }

    @Override
    public Statistics getStatistics() {
        Statistics statistics = new Statistics();

        if (transactionMap.size() == 0) {
            return statistics;
        }

        DoubleSummaryStatistics summary = transactionMap.values().stream()
            .filter(transaction -> !TimeUtil.isOlderThan60s(transaction.getTimestamp()))
            .filter(transaction -> !TimeUtil.isFuture(transaction.getTimestamp()))
            .mapToDouble(transaction -> transaction.getAmount()).summaryStatistics();

        if (summary.getCount() > 0) {
            statistics.setCount(summary.getCount());
            statistics.setMin(summary.getMin());
            statistics.setMax(summary.getMax());
            statistics.setSum(summary.getSum());
            statistics.setAvg(summary.getAverage());
        }

        return statistics;
    }

}
