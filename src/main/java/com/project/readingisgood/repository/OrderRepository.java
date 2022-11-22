package com.project.readingisgood.repository;

import com.project.readingisgood.entity.Order;
import com.project.readingisgood.model.enums.OrderStatesEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findOrdersByCustomer_Id(long customerId, Pageable pageable);

    @Query("from Order o where o.created_at between :beginDate and :endDate order by o.id")
    List<Order> findOrdersByDateInterval(Date beginDate, Date endDate);

    @Modifying
    @Transactional
    @Query("update Order o set o.state = ?1 where o.id = ?2")
    void updateOrderState(OrderStatesEnum state, long id);
}
