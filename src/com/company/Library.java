package com.company;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Class Library 
public class Library {
    //Sparar böckerna i en fil, listofbook
    //Sparar användarna i en fil, listofuser
    private static final String LISTOFBOOK_FILE = "books.ser";
    private static final String LISTOFUSER_FILE = "users.ser";
    //Eventuellt ta med inlogg innan menyn?

    //en startmetod som startar upp library
    public void start () {
        showMenu();
    }

    //Scanner som läser in menyvalen
    //Metod: visar menyn
    //Så länge som continueToRun är sant, körs valen
    Scanner scanner = new Scanner(System.in);
    public void showMenu () {
        boolean continueToRun = true;
        System.out.println("------Welcome to the library--------");
        System.out.println("------------------------------------");
        while(continueToRun) {
            System.out.println("-----------Make a choice-----------:");
            System.out.println("1. Show all books");
            System.out.println("2. Show information about a book");
            System.out.println("3. Borrow book in my name");
            System.out.println("4. Search by book, either book title or author");
            System.out.println("5.Show list with my borrowed books");
            System.out.println("6. Return book");
            System.out.println("9. Exit libraryprogram");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter menu choice:");
            int choice = Integer.parseInt(scanner.nextLine());

            //switch case meny där man kan välja
            switch (choice) {
                case 1:
                    showBooks();
                    break;
                case 2:
                    showInformation();
                    break;
                case 3:
                    borrowBook();
                    break;
                case 4:
                    searchBook();
                    break;
                case 5:
                    showBorrowedBook();
                    break;
                case 6:
                    returnBook();
                    break;
                case 9:
                    continueToRun = false;
                    break;
            }
        }
    }
    //Private List av books
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
    //En metod som visar alla böcker via for-each-loop
    private void showBooks () {
        for (Book book : books) {
            System.out.println(book);
        }
    }
    //Metod som visar informationen om boken
    private void showInformation () {
    }
    private void borrowBook () {
    }
    private void searchBook () {
    }
    private void showBorrowedBook () {
    }
    private void returnBook () {
    }
    private void exit () {
    }

    //metod som lägger till bok, som sparas i en fil
    //Lagt till en lista av böcker med title, author, description
    private void createBook() {
        List<Book> bookList = new ArrayList();
        bookList.add(new Book("Harry Potter", "J.K Rowling", "En saga om trollkarlen Harry Potter"));
        bookList.add(new Book("En avlägsen kust", "Jenny Colgan", "Handlar om en ö som heter Mure och livet på ön"));
        bookList.add(new Book("Java, A beginner`s guide", "Herbert Schildt", "Studiebok om Java"));
        bookList.add(new Book("Nalle Puh", "A.A Milnes", "Om Nalle Puh och hans vänner i Sjumilaskogen"));
    }
}
