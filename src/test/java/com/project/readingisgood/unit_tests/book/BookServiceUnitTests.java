package com.project.readingisgood.unit_tests.book;

import com.project.readingisgood.entity.Book;
import com.project.readingisgood.entity.Stock;
import com.project.readingisgood.exception.exceptions.BookNotFoundException;
import com.project.readingisgood.exception.exceptions.QuantityException;
import com.project.readingisgood.model.request.BookSaveRequestModel;
import com.project.readingisgood.service.book.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookServiceUnitTests {

    @MockBean
    BookService bookService;

    @Test
    void saveBook_test_should_save_book() {
        var bookSaveRequestModel = new BookSaveRequestModel("learn java", 15L, 45);
        var book = getBook(
                1,
                bookSaveRequestModel.getBookName(),
                bookSaveRequestModel.getQuantity(),
                bookSaveRequestModel.getPrice());

        Mockito.when(bookService.saveBook(bookSaveRequestModel)).thenReturn(book);
        var savedBook = bookService.saveBook(bookSaveRequestModel);

        assertEquals(bookSaveRequestModel.getBookName(), savedBook.getBookName());
    }

    @Test
    void findBookById_test_should_find_book_by_id() throws BookNotFoundException {
        var bookId = 1;
        var book = getBook(bookId, "learn java", 15L, 45);
        Mockito.when(bookService.findBookById(bookId)).thenReturn(book);
        var existBook = bookService.findBookById(bookId);

        assertEquals(bookId, existBook.getId());
    }

    @Test
    void findBookById_test_should_return_book_not_found_ex() {
        var bookId = 1;
        try {
            Mockito.when(bookService.findBookById(bookId)).thenThrow(new BookNotFoundException("Book not found."));
            bookService.findBookById(bookId);
        } catch (BookNotFoundException e) {
            assertEquals("Book not found.", e.getMessage());
        }
    }

    @Test
    void findBooksByIdIn_test_should_find_books_by_id_in() {
        var id = 1L;

        var bookIds = Arrays.asList(id);
        var book = getBook(id, "learn java", 15L, 45);
        List<Book> books = Arrays.asList(book);
        Mockito.when(bookService.findBooksByIdIn(bookIds)).thenReturn(books);
        var existBooks = bookService.findBooksByIdIn(bookIds);

        assertEquals(id, existBooks.get(0).getId());
    }

    @Test
    void updateStockOfBook_test_should_update_book_stock() throws BookNotFoundException, QuantityException {
        var bookId = 1L;
        var newQuantity = 40L;
        var expectedMessage = "Successfully updated.";
        Mockito.when(bookService.updateStockOfBook(bookId, newQuantity))
                .thenReturn("Successfully updated.");
        String res = bookService.updateStockOfBook(bookId, newQuantity);
        assertEquals(expectedMessage, res);
    }

    @Test
    void updateStockOfBook_test_should_return_book_not_found_ex() {
        var bookId = 1L;
        var newQuantity = 40L;
        try {
            Mockito.when(bookService.updateStockOfBook(bookId, newQuantity))
                    .thenThrow(new BookNotFoundException("Book not found."));
            bookService.updateStockOfBook(bookId, newQuantity);
        } catch (BookNotFoundException | QuantityException e) {
            assertTrue(e instanceof BookNotFoundException);
            assertEquals("Book not found.", e.getMessage());
        }
    }

    @Test
    void updateStockOfBook_test_should_return_quantity_ex() {
        var bookId = 1L;
        var newQuantity = -1L;
        try {
            Mockito.when(bookService.updateStockOfBook(bookId, newQuantity))
                    .thenThrow(new QuantityException("Quantity cannot be less than 0."));
            bookService.updateStockOfBook(bookId, newQuantity);
        } catch (QuantityException | BookNotFoundException e) {
            assertTrue(e instanceof QuantityException);
            assertEquals("Quantity cannot be less than 0.", e.getMessage());
        }
    }

    Book getBook(long bookId, String bookName, long quantity, double price) {
        Stock stock = new Stock(1, quantity, null);
        return new Book(bookId, bookName, stock, price, null);
    }

}
