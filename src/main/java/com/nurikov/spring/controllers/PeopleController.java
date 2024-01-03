package com.nurikov.spring.controllers;

import com.nurikov.spring.DAO.PersonDAO;
import com.nurikov.spring.Entity.Person;
import com.nurikov.spring.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String showPeople(Model model){
        model.addAttribute("people",personDAO.getAllPerson());
        return "people/show";
    }

    @GetMapping("/{id}")
    public String showByIndex(@PathVariable("id") int id, Model model){
        model.addAttribute("person",personDAO.getPerson(id));
        model.addAttribute("books",personDAO.getBookByPersonId(id));
        return "people/index";
    }

    @GetMapping("/new")
    public String addPerson(@ModelAttribute("person") Person person){
        return "people/add";
    }

    @PostMapping
    public String savePerson(@Valid @ModelAttribute("person") Person person, BindingResult bindingResult){
        personValidator.validate(person,bindingResult);
        if(bindingResult.hasErrors())
            return "people/add";
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/edit/{id}")
    public String updatePerson(@PathVariable("id") int id,Model model){
        model.addAttribute("person",personDAO.getPerson(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String saveUpdatedPerson(@Valid @ModelAttribute("person") Person person,
                                    BindingResult bindingResult,
                                    @PathVariable("id") int id){
        if(bindingResult.hasErrors())
            return "people/edit";
        personDAO.update(id,person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        personDAO.remove(id);
        return "redirect:/people";
    }
}
