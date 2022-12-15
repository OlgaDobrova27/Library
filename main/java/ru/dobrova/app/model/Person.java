package ru.dobrova.app.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Person {

    private int person_id;
    @Size(min = 10, max = 100,
            message = "Name is not valid! Right format: Ivanov Ivan Ivanovich")
    @Pattern(regexp = "[A-Z][a-z]+ [A-Z][a-z]+ [A-Z][a-z]+",
            message = "Name is not valid! Right format: Ivanov Ivan Ivanovich")
    private String name;
    @Min(value = 1950, message = "Year of birth is incorrect!")
    private int birthYear;

    public Person() {}

    public Person(int person_id, String name, int birthYear) {
        this.person_id = person_id;
        this.name = name;
        this.birthYear = birthYear;
    }


    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}
