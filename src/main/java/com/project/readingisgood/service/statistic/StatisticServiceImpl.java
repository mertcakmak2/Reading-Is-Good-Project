package com.project.readingisgood.service.statistic;

import com.project.readingisgood.entity.Statistic;
import com.project.readingisgood.model.OrderStatistic;
import com.project.readingisgood.repository.StatisticRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService {

    Logger logger = LoggerFactory.getLogger(StatisticServiceImpl.class);

    private final StatisticRepository statisticRepository;

    @Autowired
    public StatisticServiceImpl(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    @Override
    public List<Statistic> findAllStatistics() {
        return statisticRepository.findAll();
    }

    @Override
    public Statistic saveMonthlyStatistic(OrderStatistic orderStatistic) {
        Statistic existStatistic = findStatisticByMonthName(orderStatistic.getMonthName());
        if(existStatistic == null){
            Statistic statistic = new Statistic(orderStatistic.getMonthName(),1, orderStatistic.getBookSize(), orderStatistic.getSumPrice());
            logger.info("Statistic saved. {} ", statistic.getMonthName());
            return statisticRepository.save(statistic);
        }
        return updateMonthlyStatistic(existStatistic, orderStatistic);
    }

    @Override
    public Statistic updateMonthlyStatistic(Statistic existStatistic, OrderStatistic orderStatistic) {
        existStatistic.setTotalOrderCount(existStatistic.getTotalOrderCount() + 1 );
        existStatistic.setTotalBookCount(existStatistic.getTotalBookCount() + orderStatistic.getBookSize());
        existStatistic.setTotalPurchasedAmount(existStatistic.getTotalPurchasedAmount() + orderStatistic.getSumPrice());
        logger.info("Statistic updated. "+ existStatistic.getMonthName());
        return statisticRepository.save(existStatistic);
    }

    @Override
    public Statistic findStatisticByMonthName(String monthName) {
        return statisticRepository.findStatisticByMonthName(monthName);
    }

}
