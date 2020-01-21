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

    //Lista av böcker och användare
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();

    //Inloggade användaren
    //Scanner som läser in
    private User user;
    private Scanner scanner = new Scanner(System.in);

    //Konstruktor för library-klassen
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
    //En metod som läser in ett heltal från användaren, om det inte stämmer, kommer felmeddelande ut och användaren får
    //Texten man skickar in skrivs ut först, innan man läser in tal från användaren
    //Try-catch metoden används för att se om det användaren matar in kan omvandlas till ett tal
    private int readIntegerFromUser (String message) {
        while (true) {
            try {
                System.out.println(message);
                //Omvandling, det som användaren läser in
                //NumberFormatException = om det som användaren matar in ej kan omvandlas till ett tal
                return Integer.parseInt(scanner.nextLine());
              } catch (NumberFormatException number) {
                System.out.println("----Could not convert to number, try again!----");
            }
        }

    }

    //Metod som visar olika alternativ-val
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
            System.out.println("7.Show available books");
            System.out.println("8. Show all users (librarian only)");
            System.out.println("9. Exit libraryprogram");
            Scanner scanner = new Scanner(System.in);
            //Anropar metoden för att läsa in heltal från användaren
            int choice = readIntegerFromUser("Enter menu choice");

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
                    searchChoice();
                    break;
                case 5:
                    showBorrowedBooks();
                    break;
                case 6:
                    returnBook();
                    break;
                case 7:
                    showAvailableBooks();
                    break;
                case 8:
                    showAllUsers();
                    break;
                case 9:
                    continueToRun = false;
                    exit();
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
        //Anropar metod för att läsa in heltal från användaren
        int choice = readIntegerFromUser("Enter index of book");
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

        //Anropar metoden för att läsa in ett heltal
        int choice = readIntegerFromUser("Enter index book to borrow");
        user.borrowBook(availableBooks.get(choice -1));
    }
    //Metod där man kan söka på bok, genom olika val, titel, författare
    //En while loop med alternativ där man kan söka bok på titel eller författare, scanner för att läsa in
    //If sats: Om valet är 1 och boken har rätt titel, omvandlar boktiteln till små bokstäver, omvandlar till det
    //som användaren söker efter (contains- kollar om en textsträng innehåller en annan textsträng)
        private void searchChoice() {
            boolean continueToRun = true;
            System.out.println("-----------Make a choice-----------:");
            System.out.println("------------------------------------");
            while (continueToRun) {

                System.out.println("0. Return to main menu");
                System.out.println("1. Search by title");
                System.out.println("2. Search by author");
                //Anropar metod för att mata in till ett heltal
                int choice = readIntegerFromUser("----Enter choice-----");
                if(choice == 0 ) {
                    return;
                }
                System.out.println("----Enter search string-----");
                String search = scanner.nextLine();
                for (Book book : books) {
                    if (choice == 1 && book.getTitle().toLowerCase().contains(search.toLowerCase())) {
                        System.out.println(book);
                    }
                    if (choice == 2 && book.getAuthor().toLowerCase().contains(search.toLowerCase())) {
                        System.out.println(book);
                    }
                }
            }
        }
             //Metod som visar de lånade böckerna, Foor - each loop som lopar igenom användarens lånade böcker, via get metoden
            //Går igenom böckerna som en användare har lånat
            private void showBorrowedBooks () {
                for (Book book : user.getBorrowedBooks()) {
                    System.out.println(book);
                }
            }
            //Metod som lämnar tillbaka bok
            //Skriver ut alla lånade böcker som användaren har lånat, via for-loop
            private void returnBook () {
                for (int i = 0; i < user.getBorrowedBooks().size(); i++) {
                    System.out.printf("%d. %s \n", i + 1, user.getBorrowedBooks().get(i).getTitle());
                }
                //Användaren kan mata in ett index på den bok som ska lämnas in, som scannas in
                // -1 för att listan börjar på 0
                //Hämtar böckerna
                //Anropar returnBook från user, skickar med boken som användaren har valt, .get hämtar ut en specifik bok, -1
                //för att listan börjar på 0, -1 är första på listan

                //Anropar metoden för att se om det användaren matar in var ett heltal
                int choice = readIntegerFromUser("Enter index of book to return");
                user.returnBook(user.getBorrowedBooks().get(choice - 1));
            }
            //Metod som visar tillgängliga böcker, om boken inte är utlånad - visas boken
            private void showAvailableBooks () {
                for (Book book : books) {
                    if (!book.isBorrowed()) {
                        System.out.println(book);
                    }
                }
            }
            //om användaren inte är bibliotikarie(! user instanceof Librarian) skrivs felmeddelande ut och metoden avslutas
           //Går igenom alla användare och skriver ut namnen (skriver även ut librarians, då dessa är en typ av användare)
            private void showAllUsers () {
                if (!(user instanceof Librarian)) {
                    System.out.println("Only available for librarians");
                    return;
                }
                for (User user : users) {
                    System.out.println(user.getName());
                }
            }
            //Metod för att avsluta programmet
            //Sparar ner användare och böcker till fil när programmet avslutas
            private void exit () {
            FileUtility.saveObject(LISTOFUSER_FILE, users);
            FileUtility.saveObject(LISTOFBOOK_FILE,books);
            }

            //metod som lägger till bok, som sparas i en fil
            //Lagt till en lista av böcker med title, author, description
            private void createBook () {
                List<Book> bookList = new ArrayList();
                bookList.add(new Book("Harry Potter", "J.K Rowling", "En saga om trollkarlen Harry Potter"));
                bookList.add(new Book("En avlägsen kust", "Jenny Colgan", "Handlar om en ö som heter Mure och livet på ön"));
                bookList.add(new Book("Java, A beginner`s guide", "Herbert Schildt", "Studiebok om Java"));
                bookList.add(new Book("Nalle Puh", "A.A Milnes", "Om Nalle Puh och hans vänner i Sjumilaskogen"));

                //FileUtility : sparar ner boklistan till fil
                FileUtility.saveObject(LISTOFBOOK_FILE, bookList);
            }
            //Metod som skapar användare och lägger till 3 st användar, skapar och lägger till 2 st bibliotikarier
            private void createUser () {
                List<User> defaultUser = new ArrayList<>();
                defaultUser.add(new User("Kalle"));
                defaultUser.add(new User("Sven"));
                defaultUser.add(new User("Stina"));
                defaultUser.add(new Librarian("Kajsa"));
                defaultUser.add(new Librarian("Pelle"));
                //Skriver ner user och librarian till fil
                FileUtility.saveObject(LISTOFUSER_FILE, defaultUser);
            }
}
