package com.project.readingisgood.service.stock;

import com.project.readingisgood.entity.Stock;
import com.project.readingisgood.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    @Autowired
    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public Stock findStockByBookId(long bookId) {
        return stockRepository.findStockByBook_Id(bookId);
    }

    @Override
    public void updateStockQuantityByBookId(long quantity, long bookId) {
        stockRepository.updateStockQuantityByBookId(quantity, bookId);
    }

    @Override
    public Stock decreaseStockByBookId(long bookId) {
        Stock stock = findStockByBookId(bookId);
        stock.setQuantity(stock.getQuantity() - 1 );
        return stockRepository.save(stock);
    }

    @Override
    public List<Stock> findNoStockByBookIds(List<Long> bookIds) {
        return stockRepository.findNoStockByBookIds(bookIds);
    }
}
