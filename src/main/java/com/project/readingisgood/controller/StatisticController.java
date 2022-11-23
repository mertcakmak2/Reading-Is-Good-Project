package com.project.readingisgood.controller;

import com.project.readingisgood.entity.Statistic;
import com.project.readingisgood.result.DataResult;
import com.project.readingisgood.result.SuccessDataResult;
import com.project.readingisgood.service.statistic.StatisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/statistics")
@SecurityRequirement(name = "bearerAuth")
public class StatisticController {

    Logger logger = LoggerFactory.getLogger(StatisticController.class);
    private final StatisticService statisticService;

    @Autowired
    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping(path = "")
    @Operation(summary = "Find all statistics.")
    public ResponseEntity<DataResult> findAllStatistics() {
        logger.info("Incoming request at for request /v1/statistics ");
        var dataResult = new SuccessDataResult<List<Statistic>>
                ("Fetched all statistic records.", statisticService.findAllStatistics());
        return new ResponseEntity<DataResult>(dataResult, HttpStatus.OK);
    }
}