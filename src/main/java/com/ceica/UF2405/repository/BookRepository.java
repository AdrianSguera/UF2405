package com.ceica.UF2405.repository;

import com.ceica.UF2405.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Book findBookById(Integer id);

}
