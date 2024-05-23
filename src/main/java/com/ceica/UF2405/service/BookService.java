package com.ceica.UF2405.service;

import com.ceica.UF2405.model.Author;
import com.ceica.UF2405.model.Book;
import com.ceica.UF2405.repository.AuthorRepository;
import com.ceica.UF2405.repository.BookRepository;
import com.ceica.UF2405.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class BookService {

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    @Value("${app.upload.dir:${user.home}}/Desktop/Programas/IdeaProjects/UF2405/src/main/resources/static/img")
    private String uploadDir1;

    @Value("${app.upload.dir:${user.home}}/Desktop/Programas/IdeaProjects/UF2405/target/classes/static/img")
    private String uploadDir2;

    @Autowired
    public BookService(AuthorRepository authorRepository, BookRepository bookRepository,
                              UserRepository userRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Integer id){
        return bookRepository.findBookById(id);
    }

    public void editBookById(Integer id, String title, String description, String author) {
        Book book = bookRepository.findBookById(id);
        book.setAuthor(authorRepository.findAuthorByName(author));
        book.setTitle(title);
        book.setDescription(description);
        bookRepository.save(book);
    }

    public void deleteBookById(Integer bookId) {
        bookRepository.delete(bookRepository.findBookById(bookId));
    }

    public void newBook(String title, String author, String description, MultipartFile imageFile) {
        String fileName = imageFile.getOriginalFilename();
        Path path1 = Paths.get(uploadDir1 + File.separator + fileName);
        try {
            Files.write(path1, imageFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Path path2 = Paths.get(uploadDir2 + File.separator + fileName);
        try {
            Files.write(path2, imageFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(authorRepository.findAuthorByName(author));
        book.setDescription(description);
        book.setImage(imageFile.getOriginalFilename());
        bookRepository.save(book);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public void newAuthor(String name) {
        Author author = new Author();
        author.setName(name);
        authorRepository.save(author);
    }

    public Author getAuthorByName(String name) {
        return authorRepository.findAuthorByName(name);
    }

    public void editAuthorById(Integer id, String name) {
        Author author = authorRepository.findAuthorById(id);
        author.setName(name);
        authorRepository.save(author);
    }

    public void deleteAuthorByName(String name) {
        authorRepository.delete(authorRepository.findAuthorByName(name));
    }
}
