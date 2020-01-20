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

    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    //Scanner som läser in menyvalenprivate
    //Inloggade användaren
    private User user;
    private Scanner scanner = new Scanner(System.in);

    //Om filen (if) inte existerar, skapas den, för book, user
    public Library() {
        if (!Files.exists(Path.of(LISTOFBOOK_FILE))) {
            createBook();
        }
        if (!Files.exists(Path.of(LISTOFUSER_FILE))) {
            createUser();
        }
        //Del av konstruktorn, laddar in books och users från filen ovanför
        books = (ArrayList<Book>) FileUtility.loadObject(LISTOFBOOK_FILE);
        users = (ArrayList<User>) FileUtility.loadObject(LISTOFUSER_FILE);
    }
    //logIn metod
    //Om användarens namn stämmer överrens med det användaren matar in, loggar man in
    //Läser in via scanner, går igenom alla User via en for - loop
    //Om användarens namn stämmer överrens med det användaren matar in
    //Loggar in med den användaren (this.user = user)
    private void logIn () {
        while (true) {
            System.out.println("Write your username");
            String userName = scanner.nextLine();
            for (User user : users) {
                if (user.getName().equals(userName)) {
                    this.user = user;
                    return;
                }
            }
            //Annars, skrivs denna text ut:
            System.out.println("Wrong username, try again!");
        }
    }
    //en startmetod som startar upp library
    //Inlog visas
    //När man loggar in, visas menyn
    public void start() {
        logIn();
        showMenu();
    }
    //Metod som visar menyn
    //Scanner som läser in
    //Så länge som continueToRun är sant, körs valen
    public void showMenu() {
        boolean continueToRun = true;
        System.out.println("------Welcome to the library--------");
        System.out.println("------------------------------------");
        while (continueToRun) {
            System.out.println("-----------Make a choice-----------:");
            System.out.println("1. Show all books");
            System.out.println("2. Show information about a book");
            System.out.println("3. Borrow book");
            System.out.println("4. Search book");
            System.out.println("5.Show list with my borrowed books");
            System.out.println("6. Return book");
            System.out.println("9. Exit libraryprogram");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter menu choice:");
            int choice = Integer.parseInt(scanner.nextLine());

            //switch case meny där man kan välja olika alternativ, genom att trycka på valen visas ex,
            //böcker, info, lånade böcker osv...
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
                    showBorrowedBooks();
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
    //metod som lägger till användare, som sparas till en fil
    //En metod som visar alla böcker via for-each-loop
    private void showBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }
    //Metod som visar informationen om boken, genom en for-loop
    //Listan börjar från 1, enklare för användaren
    private void showInformation() {
        for (int i = 0; i < books.size(); i++) {
            System.out.printf("%d. %s \n", i + 1, books.get(i).getTitle());
        }
        //Användaren kan mata in ett index som scannas in
        // -1 för att listan börjar på 0
        //Hämtar böckerna
        System.out.println("Enter index of book");
        int choice = Integer.parseInt(scanner.nextLine());
        System.out.println(books.get(choice -1));
    }
    //metod som visar lånade böcker
    //Lista av tillgängliga böcker
    //Går igenom alla böcker
    //om boken inte är utlånad (! book.isBorrowed)
    //lägger man till boken i listan
    private void borrowBook() {
        ArrayList<Book> availableBooks = new ArrayList<>();
        for(Book book : books) {
            if(!book.isBorrowed()){
                availableBooks.add(book);
            }
        }
        //Går igenom de tillgängliga böckerna och skriver ut de, (i++ = lägger till)
        // %d = i + 1, %s = availableBooks + title
        for (int i = 0; i < availableBooks.size(); i++) {
            System.out.printf("%d. %s \n", i + 1, availableBooks.get(i).getTitle());
        }
        //Användaren kan mata in ett index som scannas in
        // -1 för att listan börjar på 0
        //Hämtar tillgängliga böcker som man kan låna
        System.out.println("Enter index book to borrow");
        int choice = Integer.parseInt(scanner.nextLine());
        user.borrowBook(availableBooks.get(choice -1));
    }
    //Metod där man kan söka på bok, på titel eller författare
    private void searchBook() {

    }

    //Metod som visar de lånade böckerna, Foor - each loop som lopar igenom användarens lånade böcker, via get metoden
    //Går igenom böckerna som en användare har lånat
    private void showBorrowedBooks() {
        for(Book book: user.getBorrowedBooks()) {
            System.out.println(book);
        }
    }
    //Metod som lämnar tillbaka bok
    //Skriver ut alla lånade böcker som användaren har lånat, via for-loop
    private void returnBook() {
        for (int i = 0; i < user.getBorrowedBooks().size(); i++) {
            System.out.printf("%d. %s \n", i + 1, user.getBorrowedBooks().get(i).getTitle());
        }
        //Användaren kan mata in ett index som scannas in
        // -1 för att listan börjar på 0
        //Hämtar böckerna
        //Anropar returnBook från user, skickar med boken som användaren har valt, .get hämtar ut en specifik bok, -1
        //för att listan börjar på 0, -1 är första på listan
        System.out.println("Enter index of book to return");
        int choice = Integer.parseInt(scanner.nextLine());
        user.returnBook(user.getBorrowedBooks().get(choice-1));
    }
    //Metod för att avsluta
    private void exit() {
    }

    //metod som lägger till bok, som sparas i en fil
    //Lagt till en lista av böcker med title, author, description
    private void createBook() {
        List<Book> bookList = new ArrayList();
        bookList.add(new Book("Harry Potter", "J.K Rowling", "En saga om trollkarlen Harry Potter"));
        bookList.add(new Book("En avlägsen kust", "Jenny Colgan", "Handlar om en ö som heter Mure och livet på ön"));
        bookList.add(new Book("Java, A beginner`s guide", "Herbert Schildt", "Studiebok om Java"));
        bookList.add(new Book("Nalle Puh", "A.A Milnes", "Om Nalle Puh och hans vänner i Sjumilaskogen"));

        //FileUtility : sparar ner boklistan till fil
        FileUtility.saveObject(LISTOFBOOK_FILE, bookList);
    }
    //Metod som skapar användare och lägger till 3 st användare
    //Skapar och lägger till 2 st bibliotikarier
    //Skriver om de till en fil
    private void createUser () {
        List<User>defaultUser = new ArrayList<>();
        defaultUser.add(new User("Kalle"));
        defaultUser.add(new User("Sven"));
        defaultUser.add(new User("Stina"));
        defaultUser.add(new Librarian("Kajsa"));
        defaultUser.add(new Librarian("Pelle"));

        //Skriver ner user och librarian till fil
        FileUtility.saveObject(LISTOFUSER_FILE,defaultUser);
    }
}
