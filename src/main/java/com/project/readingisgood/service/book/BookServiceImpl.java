package com.project.readingisgood.service.book;

import com.project.readingisgood.entity.Book;
import com.project.readingisgood.entity.Stock;
import com.project.readingisgood.exception.exceptions.BookNotFoundException;
import com.project.readingisgood.exception.exceptions.QuantityException;
import com.project.readingisgood.model.request.BookSaveRequestModel;
import com.project.readingisgood.repository.BookRepository;
import com.project.readingisgood.service.stock.StockService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;
    private final StockService stockService;
    private final ModelMapper modelMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, StockService stockService, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.stockService = stockService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Book saveBook(BookSaveRequestModel bookSaveRequestModel) {
        Book book = modelMapper.map(bookSaveRequestModel, Book.class);
        Stock stock = new Stock(bookSaveRequestModel.getQuantity(),book);
        book.setStock(stock);
        bookRepository.save(book);
        logger.info("Book saved. {} ", book.getBookName());
        return book;
    }

    @Override
    public Book findBookById(long bookId) throws BookNotFoundException {
        logger.info("Find book by id. {} ", bookId);
        return bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book not found"));
    }

    @Override
    public List<Book> findBooksByIdIn(List<Long> bookIds) {
        return bookRepository.findBooksByIdIn(bookIds);
    }

    @Override
    public String updateStockOfBook(long bookId, long quantity) throws BookNotFoundException, QuantityException {
        if( quantity < 0) throw new QuantityException("Quantity cannot be less than 0");
        findBookById(bookId);
        stockService.updateStockQuantityByBookId(quantity,bookId);
        logger.info("Updated book stock by id. {} ", bookId);
        return "Successfully updated";
    }

}
