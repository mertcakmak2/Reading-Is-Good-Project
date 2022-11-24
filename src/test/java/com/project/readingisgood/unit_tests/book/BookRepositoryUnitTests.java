package com.project.readingisgood.unit_tests.book;

import com.project.readingisgood.entity.Book;
import com.project.readingisgood.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class BookRepositoryUnitTests {

    @MockBean
    BookRepository bookRepository;

    @Test
    void findBooksByIdIn_test_should_find_books_by_id_in() {
        var books = getBooks();
        List<Long> ids = Arrays.asList(1L);
        Mockito.when(bookRepository.findBooksByIdIn(ids)).thenReturn(books);
        List<Book> existBooks = this.bookRepository.findBooksByIdIn(ids);

        assertEquals(existBooks.size(), books.size());
    }

    List<Book> getBooks() {
        List<Book> books = Arrays.asList(new Book(1, "learn java", null, 25d, null));
        return books;
    }

}