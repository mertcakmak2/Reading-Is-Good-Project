package com.project.readingisgood.controller;

import com.project.readingisgood.entity.Book;
import com.project.readingisgood.exception.exceptions.BookNotFoundException;
import com.project.readingisgood.exception.exceptions.QuantityException;
import com.project.readingisgood.model.request.BookSaveRequestModel;
import com.project.readingisgood.result.DataResult;
import com.project.readingisgood.result.SuccessDataResult;
import com.project.readingisgood.service.book.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/books")
@SecurityRequirement(name = "bearerAuth")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(path = "")
    @Operation(summary = "Save book.")
    public ResponseEntity<DataResult<Book>> saveBook(@RequestBody @Valid BookSaveRequestModel bookSaveRequestModel) {
        var dataResult = new SuccessDataResult<Book>("Book saved.", bookService.saveBook(bookSaveRequestModel));
        return new ResponseEntity<>(dataResult, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{bookId}/quantity/{quantity}")
    @Operation(summary = "Update stock of book.")
    public ResponseEntity<DataResult<String>> updateStockOfBook(@PathVariable long bookId, @PathVariable long quantity) throws BookNotFoundException, QuantityException {
        var dataResult = new SuccessDataResult<String>("Updated book stock.", bookService.updateStockOfBook(bookId, quantity));
        return new ResponseEntity<>(dataResult, HttpStatus.OK);
    }
}