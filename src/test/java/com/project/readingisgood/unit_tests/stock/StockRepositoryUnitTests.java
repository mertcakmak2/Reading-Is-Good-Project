package com.project.readingisgood.unit_tests.stock;


import com.project.readingisgood.entity.Book;
import com.project.readingisgood.entity.Stock;
import com.project.readingisgood.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureMockMvc
public class StockRepositoryUnitTests {

    @MockBean
    StockRepository stockRepository;

    @Test
    void findStockByBook_Id_test_should_find_stock_by_book_id() {
        var bookId = 1L;
        var quantity = 20L;
        Stock stock = getStock(bookId, quantity);

        Mockito.when(stockRepository.findStockByBook_Id(bookId)).thenReturn(stock);
        Stock existStock = this.stockRepository.findStockByBook_Id(bookId);

        assertEquals(bookId, existStock.getBook().getId());
    }

    @Test
    void findNoStockByBookIds_test_should_find_no_stock_by_book_ids() {
        var bookId = 1L;
        var quantity = 20L;
        var stocks = new ArrayList<Stock>(Arrays.asList(getStock(bookId, quantity)));
        var ids = new ArrayList<Long>(Arrays.asList(bookId));

        Mockito.when(stockRepository.findNoStockByBookIds(ids)).thenReturn(stocks);
        List<Stock> existStocks = this.stockRepository.findNoStockByBookIds(ids);

        assertEquals(bookId, existStocks.get(0).getBook().getId());
    }


    Stock getStock(long bookId, long quantity) {
        var book = new Book(1, "learn java", null, 50d, null);
        var stock = new Stock(1, 10, book);
        return stock;
    }

}