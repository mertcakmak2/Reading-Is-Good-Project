package com.project.readingisgood.unit_tests.book;


import com.project.readingisgood.controller.BookController;
import com.project.readingisgood.entity.Book;
import com.project.readingisgood.exception.exceptions.BookNotFoundException;
import com.project.readingisgood.exception.exceptions.QuantityException;
import com.project.readingisgood.model.request.BookSaveRequestModel;
import com.project.readingisgood.result.DataResult;
import com.project.readingisgood.result.SuccessDataResult;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerUnitTests {

    @MockBean
    BookController bookController;

    @Test
    void saveBook_test_method_should_save_book(){
        var bookSaveRequestModel = new BookSaveRequestModel("learn java", 15L, 45);
        var savedBook = new Book(1,"learn java",null,45d,null);

        var dataResult = new SuccessDataResult<Book>("Book saved.", savedBook);
        var expectedResponse = new ResponseEntity<DataResult>(dataResult, HttpStatus.CREATED);

        Mockito.when(bookController.saveBook(bookSaveRequestModel))
                .thenReturn(expectedResponse);

        var response = bookController.saveBook(bookSaveRequestModel);

        Book book = (Book) response.getBody().getData();
        assertEquals(book.getBookName(), bookSaveRequestModel.getBookName());
        assertEquals(expectedResponse.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    void updateStockOfBook_test_method_should_update_book_stock() throws BookNotFoundException, QuantityException {
        var dataResult = new SuccessDataResult<String>("Updated book stock.", "Successfully updated.");
        var expectedResponse =  new ResponseEntity<DataResult>(dataResult, HttpStatus.OK);

        var bookId = 1L;
        var quantity = 15L;
        Mockito.when(bookController.updateStockOfBook(bookId,quantity))
                .thenReturn(expectedResponse);

        var response = bookController.updateStockOfBook(bookId,quantity);

        String message = (String) expectedResponse.getBody().getData();
        assertEquals("Successfully updated.",message);
    }
}
