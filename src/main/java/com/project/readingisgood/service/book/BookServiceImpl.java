package com.project.readingisgood.service.book;

import com.project.readingisgood.entity.Book;
import com.project.readingisgood.entity.Stock;
import com.project.readingisgood.exception.exceptions.BookNotFoundException;
import com.project.readingisgood.model.request.BookSaveRequestModel;
import com.project.readingisgood.repository.BookRepository;
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
    private final ModelMapper modelMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
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
        return bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book not found"));
    }

    @Override
    public List<Book> findBooksByIdIn(List<Long> bookIds) {
        return bookRepository.findBooksByIdIn(bookIds);
    }


}
