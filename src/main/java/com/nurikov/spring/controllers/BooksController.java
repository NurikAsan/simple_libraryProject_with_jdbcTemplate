package com.nurikov.spring.controllers;

import com.nurikov.spring.DAO.BookDAO;
import com.nurikov.spring.DAO.PersonDAO;
import com.nurikov.spring.Entity.Book;
import com.nurikov.spring.Entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping
    public String showBooks(Model model){
        model.addAttribute("books",bookDAO.getAllBooks());
        return "books/show";
    }

    @GetMapping("/{id}")
    public String showByIndex(@PathVariable("id") int id, Model model, @ModelAttribute("person")Person person){
        model.addAttribute("book",bookDAO.getOneBook(id));

        Optional<Person> bookOwner = bookDAO.getBookOwner(id);

        if(bookOwner.isPresent())
            model.addAttribute("owner",bookOwner.get());
        else
            model.addAttribute("people",personDAO.getAllPerson());
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
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String updateBook(Model model,@PathVariable("id") int id){
        model.addAttribute("book",bookDAO.getOneBook(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String saveUpdatedBook(@PathVariable("id") int id,@Valid @ModelAttribute("book") Book book,
                                  BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "books/edit";
        bookDAO.update(id,book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id")int id){
        bookDAO.remove(id);
        return "redirect:/people";
    }

    @PatchMapping("/assign/{id}")
    public String giveBookPerson(@PathVariable("id")int id,@ModelAttribute("person")Person person){
        bookDAO.assign(id,person);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/release/{id}")
    public String freeBook(@PathVariable("id")int id){
        bookDAO.release(id);
        return "redirect:/books/" + id;
    }
}