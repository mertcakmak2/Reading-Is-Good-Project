package com.project.readingisgood.controller;

import com.project.readingisgood.entity.Statistic;
import com.project.readingisgood.result.DataResult;
import com.project.readingisgood.result.SuccessDataResult;
import com.project.readingisgood.service.statistic.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/statistics")
public class StatisticController {

    private final StatisticService statisticService;

    @Autowired
    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping(path = "")
    public ResponseEntity<DataResult> findAllStatistics() {
        var dataResult = new SuccessDataResult<List<Statistic>>
                ("Fetched all statistic records.", statisticService.findAllStatistics());
        return new ResponseEntity<DataResult>(dataResult, HttpStatus.OK);
    }
}
