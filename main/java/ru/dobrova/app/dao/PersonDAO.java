package ru.dobrova.app.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.dobrova.app.model.Person;

import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person getPerson(int person_id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE person_id=?",
                        new Object[]{person_id},
                        new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void add(Person person) {
        jdbcTemplate.update("INSERT INTO person(name, birthYear) VALUES (?, ?)",
                person.getName(), person.getBirthYear());
    }

    public void edit(int person_id, Person person) {
        jdbcTemplate.update("UPDATE person SET name=?, birthYear=? WHERE person_id=?",
                person.getName(), person.getBirthYear(), person.getPerson_id());
    }

    public void delete(int person_id) {
        jdbcTemplate.update("DELETE FROM person WHERE person_id=?", person_id);
    }
}
