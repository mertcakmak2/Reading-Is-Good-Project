package com.project.readingisgood.service.stock;

import com.project.readingisgood.entity.Stock;

import java.util.List;

public interface StockService {
    Stock findStockByBookId(long bookId);
    void updateStockQuantityByBookId(long quantity, long bookId);
    Stock decreaseStockByBookId(long bookId);
    List<Stock> findNoStockByBookIds(List<Long> bookIds);
}
