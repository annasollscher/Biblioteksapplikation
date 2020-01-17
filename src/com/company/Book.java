package com.company;

import java.io.Serializable;
//privata attribut om title, författare, beskrivning om boken
//huvudklassen implementerar Serializable
public class Book implements Serializable {
    private String title;
    private String author;
    private String description;

    public Book(String title, String author, String description) {
        this.title = title;
        this.author = author;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Book " +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'';
    }
}
