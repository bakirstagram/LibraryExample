package ru.bakir.project.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bakir.project.models.Book;
import ru.bakir.project.models.Person;
import ru.bakir.project.repositories.BookRepository;
import ru.bakir.project.repositories.PeopleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;

    public BookService(BookRepository bookRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
    }

    public Page<Book> findAll(int page, int itemsPerPage, Sort sort) {

        return bookRepository.findAll(PageRequest.of(page, itemsPerPage, sort));
    }

    public Optional<Book> findOne(int id) {
        Optional<Book> book = bookRepository.findById(id);
        return book;
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void setPerson(int id, int user_id) {
        Person p = peopleRepository.findById(user_id).get();
        Book b = bookRepository.findById(id).get();
        b.setOwner(p);
        bookRepository.save(b);
    }

    @Transactional
    public void setFree(int id) {
        Optional<Book> book = bookRepository.findById(id);
        book.get().setOwner(null);
        Book setNullOwner = book.get();
        bookRepository.save(setNullOwner);
    }

    public List<Book> searchStartsWith(String start){
        if(start.isEmpty())
            return new ArrayList<>();
        return bookRepository.findByNameStartingWith(start);
    }

}
