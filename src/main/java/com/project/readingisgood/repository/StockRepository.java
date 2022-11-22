package com.project.readingisgood.repository;

import com.project.readingisgood.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findStockByBook_Id(long bookId);

    @Query("from Stock where quantity = 0 and book.id in :bookIds")
    List<Stock> findNoStockByBookIds(List<Long> bookIds);

    @Modifying
    @Transactional
    @Query("update Stock o set o.quantity = ?1 where o.book.id = ?2")
    void updateStockQuantityByBookId(long quantity, long bookId);
}
