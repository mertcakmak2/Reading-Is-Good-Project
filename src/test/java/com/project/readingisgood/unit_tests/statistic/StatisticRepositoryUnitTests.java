package com.project.readingisgood.unit_tests.statistic;

import com.project.readingisgood.entity.Statistic;
import com.project.readingisgood.repository.StatisticRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureMockMvc
class StatisticRepositoryUnitTests {

    @MockBean
    StatisticRepository statisticRepository;

    @Test
    void findStatisticByMonthName_test_should_find_statistic_bY_month_name() {
        Statistic statistic = getStatistic();

        Mockito.when(statisticRepository.findStatisticByMonthName(statistic.getMonthName())).thenReturn(statistic);
        Statistic existStatistic = this.statisticRepository.findStatisticByMonthName(statistic.getMonthName());

        assertEquals(statistic.getMonthName(), existStatistic.getMonthName());
    }

    Statistic getStatistic() {
        return new Statistic(1L,"May",10,23,1461.50);
    }

}
