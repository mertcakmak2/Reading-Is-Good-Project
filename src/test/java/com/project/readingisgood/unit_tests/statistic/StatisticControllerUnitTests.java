package com.project.readingisgood.unit_tests.statistic;

import com.project.readingisgood.controller.StatisticController;
import com.project.readingisgood.entity.Statistic;
import com.project.readingisgood.result.DataResult;
import com.project.readingisgood.result.SuccessDataResult;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class StatisticControllerUnitTests {

    @MockBean
    StatisticController statisticController;

    @Test
    void findAllStatistics_test_method_should_find_all_statistics(){
        Statistic statistic = new Statistic(1L,"May",10,23,1461.50);
        List<Statistic> statistics = Arrays.asList(statistic);

        var dataResult = new SuccessDataResult<List<Statistic>>
                ("Fetched all statistic records.", statistics);
        var expectedResponse = new ResponseEntity<DataResult>(dataResult, HttpStatus.OK);

        Mockito.when(statisticController.findAllStatistics()).thenReturn(expectedResponse);
        var response = statisticController.findAllStatistics();

        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }
}
