package com.sena.crud_2899747.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.crud_2899747.model.book;

public interface Ibook extends JpaRepository<book, Integer> {

    // Custom query to find active books
    // @Query("SELECT b FROM book b WHERE b.status = true")
    // List<book> getActiveBooks();

    // Derived query methods
    List<book> findByTitleContainingIgnoreCase(String title);
    List<book> findByAuthorContainingIgnoreCase(String author);
    List<book> findByCategory(String category);
    List<book> findByPublicationYear(int year);
    List<book> findByStatus(boolean status);
    List<book> findByIsbn(String isbn);

    /*
     * C (Create), R (Read), U (Update), D (Delete)
     */
}