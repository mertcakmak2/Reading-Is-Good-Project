package com.project.readingisgood.service.book;

import com.project.readingisgood.entity.Book;
import com.project.readingisgood.exception.exceptions.BookNotFoundException;
import com.project.readingisgood.exception.exceptions.QuantityException;
import com.project.readingisgood.model.request.BookSaveRequestModel;

import java.util.List;

public interface BookService {
    Book saveBook(BookSaveRequestModel bookSaveRequestModel);
    Book findBookById(long bookId) throws BookNotFoundException;
    List<Book> findBooksByIdIn(List<Long> bookIds);
    String updateStockOfBook(long bookId, long quantity) throws BookNotFoundException, QuantityException;

}
