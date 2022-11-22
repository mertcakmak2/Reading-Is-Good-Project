package com.project.readingisgood.repository;

import com.project.readingisgood.entity.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {

    Statistic findStatisticByMonthName(String monthName);

}
