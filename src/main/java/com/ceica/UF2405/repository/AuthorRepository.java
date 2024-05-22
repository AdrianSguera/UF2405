package com.ceica.UF2405.repository;

import com.ceica.UF2405.model.Author;
import com.ceica.UF2405.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Author findAuthorByName(String name);

    Author findAuthorById(Integer id);
}
