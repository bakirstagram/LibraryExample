package ru.bakir.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bakir.project.models.Person;
import ru.bakir.project.services.PeopleService;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "/people/index";
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable("id") int id) {
        model.addAttribute("books", peopleService.books(id));
        model.addAttribute("person", peopleService.findOne(id));
        return "/people/show";
    }

    @GetMapping("/new")
    public String creatForm(@ModelAttribute("person") Person person) {
        // тут аннотация @modelattribute создаёт объект класса Person и передаёт его в VIEW
        return "/people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") Person person) {
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.findOne(id));
        return "/people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person")Person person, @PathVariable("id") int id) {

        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }

}
