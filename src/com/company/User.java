package com.company;

import java.io.Serializable;
import java.util.ArrayList;

//implementerar Serializable för att kunna läsa om till fil
public class User implements Serializable {
    //Privat attribut med name, lista av böcker som den användare har lånat (borrowedBooks)
    private String name;
    private ArrayList<Book> borrowedBooks = new ArrayList<>();

    //konstruktor för att skapa User med namn
    public User(String name) {
        this.name = name;
    }
    //Getter för att hämta ut name
    public String getName() {
        return name;
    }

    /**metod som visar lånade böcker, borrowBook
    *Tar emot boken som ska lånas
    *Anropar book.setBorroed (true) för att visa att boken är lånad
    *Lägger till den lånade boken i listan borrowedBooks(borrowedBooks.add)
     */
    public void borrowBook(Book book) {
        book.setBorrowed(true);
        borrowedBooks.add(book);
    }
    //Metod, med en arraylista av book som Returnerar en lista  med alla lånade böcker, (en kopia av listan)
    public ArrayList<Book> getBorrowedBooks() {
        return new ArrayList<>(borrowedBooks);
    }
    /** metod som tar emot en bok som ska lämnas tillbaka, via index (returnBook)
     * Returnerar boken som användaren lämnar tillbaka, return borrowedBooks
    *Tar bort boken från listan av lånade böcker via index,(borrowedBooks.remove(index))
     */
    public Book returnBook (int index) {
        return borrowedBooks.remove(index);
    }
}
