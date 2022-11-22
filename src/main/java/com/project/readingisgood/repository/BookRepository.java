package com.project.readingisgood.repository;

import com.project.readingisgood.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    List<Book> findBooksByIdIn(List<Long> ids);
}
