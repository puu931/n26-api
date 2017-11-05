package com.joseph.n26.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joseph.n26.service.IStatisticsService;
import com.joseph.n26.vo.Statistics;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    private IStatisticsService statisticsService;

    public ResponseEntity<Statistics> getStatistics() {
        return new ResponseEntity<Statistics>(statisticsService.getStatistics(), HttpStatus.OK);
    }

}
