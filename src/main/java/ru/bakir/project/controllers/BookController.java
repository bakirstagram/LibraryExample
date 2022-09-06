package ru.bakir.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bakir.project.models.Book;
import ru.bakir.project.services.BookService;
import ru.bakir.project.services.PeopleService;

import java.util.stream.IntStream;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }


    @GetMapping
    public String index(Model model,
//                        @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                        @RequestParam(value = "page", required = false, defaultValue = "0") int page) {

        Page<Book> books = bookService.findAll(page, 5, Sort.by("year"));
        model.addAttribute("books", books);
        model.addAttribute("numbers", IntStream.range(0, books.getTotalPages()).toArray());

        return "/books/all";
    }

    @GetMapping("/{id}")
    public String book(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findOne(id));
        model.addAttribute("people", peopleService.findAll());
        return "/books/info";
    }

    @GetMapping("/new")
    public String createForm(@ModelAttribute("book") Book book) {
        return "/books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") Book book) {
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        return "/books/edit";
    }

    @PostMapping("/{id}/set")
    public String set(@RequestParam("user_id") int user_id, @PathVariable("id") int id) {

        bookService.setPerson(id, user_id);

        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}/setfree")
    public String setfree(@PathVariable("id") int id) {
        bookService.setFree(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String getSearchForm(){
        return "/books/search";
    }
    @PostMapping("/search")
    public String getBooks(@RequestParam(value = "text", required = false, defaultValue = "") String text, Model model){
        model.addAttribute("books", bookService.searchStartsWith(text));
        return "/books/search";
    }


}
