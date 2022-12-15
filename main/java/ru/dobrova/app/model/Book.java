package ru.dobrova.app.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class Book {

    private int book_id;

    @Size(min = 3, max = 100, message = "Title is not valid!")
    private String title;
    @Size(min = 5, max = 100, message = "Author name is not valid!")
    private String author;
    @Min(value = 0, message = "Publishing year must be greater than 0!")
    private int publishingYear;

    public Book() {}

    public Book(int book_id, String title, String author, int publishingYear) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.publishingYear = publishingYear;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(int publishingYear) {
        this.publishingYear = publishingYear;
    }
}
