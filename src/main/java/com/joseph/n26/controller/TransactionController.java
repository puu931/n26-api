package com.joseph.n26.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.joseph.n26.service.IStatisticsService;
import com.joseph.n26.util.TimeUtil;
import com.joseph.n26.vo.Transaction;


@RestController
public class TransactionController {

    @Autowired
    private IStatisticsService statisticsService;

    @RequestMapping(value = "/transactions", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addTransaction(
            @RequestBody Transaction transaction) {
        if (TimeUtil.isOlderThan60s(transaction.getTimestamp())) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } else {
            statisticsService.addTransaction(transaction);
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        }
    }
}
