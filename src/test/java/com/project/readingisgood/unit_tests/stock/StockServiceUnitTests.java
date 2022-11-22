package com.project.readingisgood.unit_tests.stock;

import com.project.readingisgood.entity.Book;
import com.project.readingisgood.entity.Stock;
import com.project.readingisgood.service.stock.StockService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@AutoConfigureMockMvc
public class StockServiceUnitTests {

    @MockBean
    StockService stockService;

    @Test
    void findStockByBookId_test_should_find_stock_by_book_id(){
        var bookId = 1L;
        var stock = getStock();
        Mockito.when(stockService.findStockByBookId(bookId)).thenReturn(stock);
        var existStock = stockService.findStockByBookId(bookId);

        assertEquals(bookId, existStock.getId());
    }

    @Test
    void decreaseStockByBookId_test_should_decrease_stock_by_book_id(){
        var bookId = 1L;
        var stock = getStock();
        var expectedQuantity = stock.getQuantity() - 1;
        stock.setQuantity(expectedQuantity);
        Mockito.when(stockService.decreaseStockByBookId(bookId)).thenReturn(stock);

        var existStock = stockService.decreaseStockByBookId(bookId);

        assertEquals(expectedQuantity, existStock.getQuantity());
    }

    @Test
    void findNoStockByBookIds_test_should_find_no_stock_by_book_ids(){
        var bookIds = Arrays.asList(1L);
        var stocks = Arrays.asList(getStock());
        Mockito.when(stockService.findNoStockByBookIds(bookIds)).thenReturn(stocks);

        var existStocks = stockService.findNoStockByBookIds(bookIds);

        assertNotNull(existStocks);
    }

    Stock getStock(){
        var book = new Book(1,"learn java",null,15d,null);
        return new Stock(1,23,book);
    }
}
