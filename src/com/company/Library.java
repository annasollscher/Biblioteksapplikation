package com.company;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Class Library 
public class Library {
    //Sparar böckerna i en fil 
    //Sparar användarna i en fil 
    private static final String LISTOFBOOK_FILE = "books.ser";
    private static final String LISTOFUSER_FILE = "users.ser";
    //Eventuellt ta med inlogg innan menyn?

    Scanner scanner = new Scanner(System.in);
    public void showMenu (String showChoice) {
        System.out.println();

    }

    //startar upp programmet
    public void start () {
    }
    //Lista av böcker 
    private ArrayList<Book>books = new ArrayList<>();
    
    //Om filen inte existerar, skapar en ny fil med böcker
    //Om filen inte existerar, skapas en ny fil med användare
    public Library() {
        if(!Files.exists(Path.of(LISTOFBOOK_FILE))) {
            createBook ();
        }
        if(!Files.exists(Path.of(LISTOFUSER_FILE))) {
            createUser ();
        }
    }

    //metod som lägger till användare, som sparas till en fil
    private void createUser() {

    }
    //metod som lägger till bok, som sparas i en fil
    private void createBook() {
        List<Book> bookList = new ArrayList();
        bookList.add(new Book("Harry Potter", "J.K Rowling", "En saga om trollkarlen Harry Potter"));
        bookList.add(new Book("En avlägsen kust", "Jenny Colgan", "Handlar om en ö som heter Mure och livet på ön"));
        bookList.add(new Book("Java, A beginner`s guide", "Herbert Schildt", "Studiebok om Java"));
        bookList.add(new Book("Nalle Puh", "A.A Milnes", "Om Nalle Puh och hans vänner i Sjumilaskogen"));
    }
}
