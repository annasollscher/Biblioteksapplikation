package com.company;

import java.io.Serializable;
import java.util.ArrayList;
//implementerar Serializable för att kunna läsa om till fil
public class User implements Serializable {
    //Privat attribut name
    //Har en lista av böcker
    private String name;
    //Lista av böcker som den användare har lånat
    private ArrayList<Book> borrowedBooks = new ArrayList<>();

    //konstruktor för att skapa User
    public User(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    //metod som visar lånade böcker
    //Tar emot boken som ska lånas
    //Anropar setBorroed (true) för att visa att boken är lånad
    //Lägger till den lånade boken i listan
    public void borrowedBook(Book book) {
        book.setBorrowed(true);
        borrowedBooks.add(book);
    }
}
