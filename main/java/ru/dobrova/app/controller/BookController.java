package ru.dobrova.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.dobrova.app.dao.BookDAO;
import ru.dobrova.app.dao.PersonDAO;
import ru.dobrova.app.model.Book;
import ru.dobrova.app.model.Person;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private BookDAO bookDAO;
    private PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "/books/index";
    }

    @GetMapping("/new")
    public String addPage(@ModelAttribute("book") Book book) {
        return "/books/new";
    }

    @PostMapping()
    public String addBook(@Valid @ModelAttribute("book") Book book,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/books/new";
        }
        bookDAO.add(book);
        return "redirect:/books";
    }

    @GetMapping("/{book_id}")
    public String bookPage(@PathVariable("book_id") int book_id, Model model,
                           @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.getBookById(book_id));

        Optional<Person> owner = bookDAO.getBookOwner(book_id);
        if (owner.isPresent()) {
            model.addAttribute("owner", owner.get());
        } else {
            model.addAttribute("people", personDAO.index());
        }

        return "/books/book";
    }

    @PatchMapping("/{book_id}/assign")
    public String assignBook(@PathVariable("book_id") int book_id, @ModelAttribute("person") Person person) {
        bookDAO.assign(book_id, person);
        return "redirect:/books/" + book_id;
    }

    @PatchMapping("/{book_id}/release")
    public String releaseBook(@PathVariable("book_id") int book_id) {
        bookDAO.release(book_id);
        return "redirect:/books/" + book_id;
    }

    @GetMapping("/{book_id}/edit")
    public String editPage(@PathVariable("book_id") int book_id, Model model) {
        model.addAttribute("book", bookDAO.getBookById(book_id));
        return "/books/edit";
    }

    @PatchMapping("/{book_id}")
    public String editBook(@PathVariable("book_id") int book_id,
                           @Valid @ModelAttribute("book") Book book,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookDAO.edit(book_id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{book_id}")
    public String deleteBook(@PathVariable("book_id") int book_id) {
        bookDAO.delete(book_id);
        return "redirect:/books";
    }
}
