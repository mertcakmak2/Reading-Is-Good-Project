package com.project.readingisgood.unit_tests.statistic;

import com.project.readingisgood.entity.Statistic;
import com.project.readingisgood.model.OrderStatistic;
import com.project.readingisgood.service.statistic.StatisticService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class StatisticServiceTests {

    @MockBean
    StatisticService statisticService;

    @Test
    void findAllStatistics_test_should_find_all_statistics(){
        var statistics = Arrays.asList(getStatistic());
        Mockito.when(statisticService.findAllStatistics()).thenReturn(statistics);
        var existStatistics = statisticService.findAllStatistics();
        assertNotNull(existStatistics);
    }

    @Test
    void saveMonthlyStatistic_test_should_save_monthly_statistic(){
        var statistic = getStatistic();
        var orderStatistic = new OrderStatistic(getStatistic().getMonthName(),3,50d);
        Mockito.when(statisticService.saveMonthlyStatistic(orderStatistic)).thenReturn(statistic);
        var savedStatistic = statisticService.saveMonthlyStatistic(orderStatistic);
        assertNotNull(orderStatistic.getMonthName(), savedStatistic.getMonthName());
    }

    @Test
    void updateMonthlyStatistic_test_should_update_monthly_statistic(){
        var statistic = getStatistic();
        var orderStatistic = new OrderStatistic(getStatistic().getMonthName(),3,50d);
        Mockito.when(statisticService.updateMonthlyStatistic(statistic,orderStatistic))
                .thenReturn(statistic);
        var updatedStatistic = statisticService.updateMonthlyStatistic(statistic,orderStatistic);
        assertNotNull(orderStatistic.getMonthName(), updatedStatistic.getMonthName());
    }

    @Test
    void findStatisticByMonthName_test_should_find_monthly_statistic_by_month_name(){
        var monthName = "May";
        var statistic = getStatistic();
        var orderStatistic = new OrderStatistic(getStatistic().getMonthName(),3,50d);
        Mockito.when(statisticService.findStatisticByMonthName(monthName))
                .thenReturn(statistic);
        var updatedStatistic = statisticService.findStatisticByMonthName(monthName);
        assertNotNull(orderStatistic.getMonthName(), updatedStatistic.getMonthName());
    }


    Statistic getStatistic() {
        return new Statistic(1L,"May",10,23,1461.50);
    }
}
