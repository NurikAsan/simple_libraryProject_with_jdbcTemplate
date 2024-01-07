package com.nurikov.spring.controllers;

import com.nurikov.spring.Entity.Person;
import com.nurikov.spring.services.PeopleService;
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
    private final PeopleService peopleService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }


    @GetMapping
    public String showPeople(Model model){
        model.addAttribute("people",peopleService.getAllPerson());
        return "people/show";
    }

    @GetMapping("/{id}")
    public String showByIndex(@PathVariable("id") int id, Model model){
        model.addAttribute("person",peopleService.getPerson(id));
        model.addAttribute("books",peopleService.getBookByPersonId(id));
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
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/edit/{id}")
    public String updatePerson(@PathVariable("id") int id,Model model){
        model.addAttribute("person",peopleService.getPerson(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String saveUpdatedPerson(@Valid @ModelAttribute("person") Person person,
                                    BindingResult bindingResult,
                                    @PathVariable("id") int id){
        if(bindingResult.hasErrors())
            return "people/edit";
        peopleService.update(id,person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        peopleService.remove(id);
        return "redirect:/people";
    }
}