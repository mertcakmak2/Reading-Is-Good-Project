package com.project.readingisgood.service.statistic;

import com.project.readingisgood.entity.Statistic;
import com.project.readingisgood.model.OrderStatistic;

import java.util.List;

public interface StatisticService {

    List<Statistic> findAllStatistics();
    Statistic saveMonthlyStatistic(OrderStatistic orderStatistic);
    Statistic updateMonthlyStatistic(Statistic statistic, OrderStatistic orderStatistic);
    Statistic findStatisticByMonthName(String monthName);
}
