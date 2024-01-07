package com.nurikov.spring.controllers;

import com.nurikov.spring.Entity.Book;
import com.nurikov.spring.Entity.Person;
import com.nurikov.spring.services.BookService;
import com.nurikov.spring.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final PeopleService peopleService;
    private final BookService bookService;

    @Autowired
    public BooksController(PeopleService peopleService, BookService bookService) {
        this.peopleService = peopleService;
        this.bookService = bookService;
    }

    @GetMapping
    public String showBooks(@RequestParam(value = "page",required = false) Integer page,
            @RequestParam(value = "books_per_page",required = false) Integer booksPerPage,
            @RequestParam(value = "sort_by_year",required = false) boolean sort
            ,Model model){
        if(page != null || booksPerPage != null)
            model.addAttribute("books",bookService.withPagination(page,booksPerPage,sort));
        else
            model.addAttribute("books",bookService.getAllBooks(sort));
        return "books/show";
    }

    @GetMapping("/{id}")
    public String showByIndex(@PathVariable("id") int id, Model model, @ModelAttribute("person")Person person){
        model.addAttribute("book",bookService.getOneBook(id));

        Person bookOwner = bookService.getBookOwner(id);

        if(bookOwner != null)
            model.addAttribute("owner",bookOwner);
        else
            model.addAttribute("people",peopleService.getAllPerson());
        return "books/index";
    }

    @GetMapping("/new")
    public String addBook(@ModelAttribute("book") Book book){
        return "books/add";
    }

    @PostMapping
    public String saveBook(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "books/add";
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String updateBook(Model model,@PathVariable("id") int id){
        model.addAttribute("book",bookService.getOneBook(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String saveUpdatedBook(@PathVariable("id") int id,@Valid @ModelAttribute("book") Book book,
                                  BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "books/edit";
        bookService.update(id,book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id")int id){
        bookService.remove(id);
        return "redirect:/people";
    }

    @PatchMapping("/assign/{id}")
    public String giveBookPerson(@PathVariable("id")int id,@ModelAttribute("person")Person person){
        bookService.assign(id,person);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/release/{id}")
    public String freeBook(@PathVariable("id")int id){
        bookService.release(id);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String searchBookByName(){
        return "books/search";
    }

    @PostMapping("/search")
    public String searchParametr(Model model,@RequestParam("query") String query){
        model.addAttribute("books",bookService.getByName(query));
        return "books/search";
    }
}