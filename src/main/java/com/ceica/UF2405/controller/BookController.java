package com.ceica.UF2405.controller;

import com.ceica.UF2405.model.Book;
import com.ceica.UF2405.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public String getBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

    @GetMapping("/book")
    public String getBook(Model model, @RequestParam Integer book_id){
        model.addAttribute("book", bookService.getBookById(book_id));
        model.addAttribute("authors", bookService.getAllAuthors());
        return "book";
    }

    @PostMapping("/book")
    public String postBook(@RequestParam Integer id, @RequestParam String title, @RequestParam String author, @RequestParam String description, RedirectAttributes redirectAttributes){
        bookService.editBookById(id, title, description, author);
        return "redirect:/books";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam Integer book_id){
        bookService.deleteBookById(book_id);
        return "redirect:/books";
    }

    @GetMapping("/newBook")
    public String getNewBook(Model model) {
        model.addAttribute("authors", bookService.getAllAuthors());
        return "new";
    }

    @PostMapping("/newBook")
    public String addNewBook(@RequestParam("title") String title,
                             @RequestParam("author") String author,
                             @RequestParam("description") String description,
                             @RequestParam("image") MultipartFile imageFile) {
        bookService.newBook(title, author, description, imageFile);
        return "redirect:/books";
    }

    @GetMapping("/authors")
    public String getAuthors(Model model){
        model.addAttribute("authors", bookService.getAllAuthors());
        return "authorManager";
    }

    @GetMapping("/newAuthor")
    public String getNewAuthor(){
        return "newAuthor";
    }

    @PostMapping("/newAuthor")
    public String postNewAuthor(@RequestParam String name){
        bookService.newAuthor(name);
        return "redirect:/authors";
    }

    @GetMapping("/editAuthor")
    public String getEditAuthor(Model model, @RequestParam String name){
        model.addAttribute("author", bookService.getAuthorByName(name));
        return "editAuthor";
    }

    @PostMapping("/editAuthor")
    public String postEditAuthor(@RequestParam Integer id, @RequestParam String name){
        bookService.editAuthorById(id, name);
        return "redirect:authors";
    }

    @GetMapping("/deleteAuthor")
    public String deleteAuthor(@RequestParam String name){
        bookService.deleteAuthorByName(name);
        return "redirect:authors";
    }
}
