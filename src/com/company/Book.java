package com.company;

import java.io.Serializable;
//privata attribut om title, författare, beskrivning om boken
//huvudklassen implementerar Serializable
//privata attribut, title, author, description, isBorrowed
public class Book implements Serializable {
    private String title;
    private String author;
    private String description;
    private boolean isBorrowed;

    //Konstruktor för att skapa boken med title, author, description
    public Book(String title, String author, String description) {
        this.title = title;
        this.author = author;
        this.description = description;
    }
    //tilldelar själv ett värde, om den är utlånad eller ej
    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }
    //Metod som returnerar true/false om boken är lånad
    public boolean isBorrowed() {
        return isBorrowed;
    }
    //To String metod för att skriva ut title, author, description
    @Override
    public String toString() {
        return "Book " +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'';
    }
    //Getter till Title, Author, Description för att kunna hämta ut dessa i andra klasser
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }
}
