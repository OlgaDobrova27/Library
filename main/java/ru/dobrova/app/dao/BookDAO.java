package ru.dobrova.app.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.dobrova.app.model.Book;
import ru.dobrova.app.model.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public void add(Book book) {
        jdbcTemplate.update(
                "INSERT INTO book(title, author, publishingYear) VALUES (?, ?, ?)",
                book.getTitle(),
                book.getAuthor(),
                book.getPublishingYear());
    }

    public List<Book> getBooksByPersonId(int person_id) {
        return jdbcTemplate.query(
                "SELECT book.title, book.author, book.publishingYear " +
                        "FROM person JOIN book ON person.person_id = ? AND book.person_id=?",
                new Object[]{person_id, person_id},
                new BeanPropertyRowMapper<>(Book.class));
    }

    public Book getBookById(int book_id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE book_id=?",
                new BeanPropertyRowMapper<>(Book.class), book_id).stream().findAny().orElse(null);
    }

    public Optional<Person> getBookOwner(int book_id) {
        return jdbcTemplate.query("SELECT person.* FROM person JOIN book ON book.person_id = person.person_id" +
                        " WHERE book_id=?", new BeanPropertyRowMapper<>(Person.class), book_id).stream().findAny();
    }

    public void assign(int book_id, Person person) {
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE book_id=?", person.getPerson_id(), book_id);
    }

    public void release(int book_id) {
        jdbcTemplate.update("UPDATE book SET person_id=NULL WHERE book_id=?", book_id);
    }

    public void edit(int book_id, Book book) {
        jdbcTemplate.update("UPDATE book SET title=?, author=?, publishingYear=? WHERE book_id=?",
                book.getTitle(), book.getAuthor(), book.getPublishingYear(), book.getBook_id());
    }

    public void delete(int book_id) {
        jdbcTemplate.update("DELETE FROM book WHERE book_id=?", book_id);
    }
}
