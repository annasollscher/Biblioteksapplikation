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
    /**metod som visar lånade böcker
    *Tar emot boken som ska lånas
    *Anropar setBorroed (true) för att visa att boken är lånad
    *Lägger till den lånade boken i listan borrowedBooks(borrowedBooks.add)
     */
    public void borrowBook(Book book) {
        book.setBorrowed(true);
        borrowedBooks.add(book);
    }
    //Skickar tillbaka en lista (returnerar den) med alla lånade böcker, (en kopia av listan)
    public ArrayList<Book> getBorrowedBooks() {
        return new ArrayList<>(borrowedBooks);
    }
    /** metod som tar emot en bok som ska lämnas tillbaka
     * Returnerar boken som användaren lämnar tillbaka
    *Tar emot ett index för boken som ska lämnast tillbaka
    *Tar bort boken från listan av lånade böcker,(borrowedBooks.remove)
     */
    public Book returnBook (int index) {
        return borrowedBooks.remove(index);
    }
}
