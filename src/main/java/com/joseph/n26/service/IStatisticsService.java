package com.joseph.n26.service;

import com.joseph.n26.vo.Statistics;
import com.joseph.n26.vo.Transaction;

public interface IStatisticsService {

    void addTransaction(Transaction transaction);

    Statistics getStatistics();

}
