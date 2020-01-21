package com.company;

import java.io.Serializable;
import java.util.ArrayList;
//implementerar Serializable för att kunna läsa om till fil
public class User implements Serializable {
    //Privat attribut name
    //Har en lista av böcker
    private String name;
    //Lista av böcker som den användare har lånat
    //Läggs det till den bok som den användaren har lånat
    private ArrayList<Book> borrowedBooks = new ArrayList<>();

    //konstruktor för att skapa User
    public User(String name) {
        this.name = name;
    }
    //Getter för att
    public String getName() {
        return name;
    }

    //metod som visar lånade böcker
    //Tar emot boken som ska lånas
    //Anropar setBorroed (true) för att visa att boken är lånad
    //Lägger till den lånade boken i listan
    public void borrowBook(Book book) {
        book.setBorrowed(true);
        borrowedBooks.add(book);
    }
    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }
    //Tar emot en bok som ska lämnas tillbaka
    //SKickar in en bok som argument
    //Ändrar att boken inte längre är lånad (setBorrowed = false)
    //Tar bort boken från listan av lånade böcker
    public void returnBook (Book book) {
        book.setBorrowed(false);
        borrowedBooks.remove(book);
    }
}
