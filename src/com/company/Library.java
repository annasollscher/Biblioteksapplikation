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
    private ArrayList<Book> availableBooks = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();

    //Nuvarande inloggade användaren, attribut i klassen, scanner som läser in
    private User user;
    private Scanner scanner = new Scanner(System.in);

    //Konstruktor för library-klassen, if sats: om filen inte existerar, skapas den, för book och user (createBook, createUser)
    public Library() {
        if (!Files.exists(Path.of(LISTOFBOOK_FILE))) {
            createBook();
        }
        if (!Files.exists(Path.of(LISTOFUSER_FILE))) {
            createUser();
        }
        //Del av konstruktorn, laddar in books och users från filen ovanför(loadObject)
        availableBooks = (ArrayList<Book>) FileUtility.loadObject(LISTOFBOOK_FILE);
        users = (ArrayList<User>) FileUtility.loadObject(LISTOFUSER_FILE);
    }
    /**logIn metod där användaren ska skriva in ett inloggningsnamn
    *Läser in via scanner, går igenom alla User via en for-each loop
    *Om användarens namn stämmer överrens med det namn som användaren matar in, går det att logga in
    *Annars, skrivs texten "Wrong username..." ut
     */
    private void logIn () {
        while (true) {
            System.out.println("--------------------------Dear user, welcome to the library--------------------------");
            System.out.println("------------------Please write your username to login and press enter----------------");
            String userName = scanner.nextLine();
            for (User user : users) {
                if (user.getName().equals(userName)) {
                    this.user = user;
                    return;
                }
            }
            System.out.println("Wrong username, try again!");
        }
    }
    /**en startmetod
    *Inlog visas, se metoden ovan, private void logIn
    *När man loggar in, visas menyn (showMenu)
     */
    public void start() {
        logIn();
        showMenu();
    }
    /**Beskriver metoden nedan:
     * En metod som läser in ett heltal från användaren, om det inte stämmer, kommer felmeddelande "Could not convert..."
    *Texten man skickar in skrivs ut först, innan man läser in tal från användaren
    *Try-catch metoden används för att se om det användaren matar in kan omvandlas till ett tal
     */
    private int readIntegerFromUser (String message) {
        System.out.println(message);
        while (true) {
            try {

                //Omvandling, det som användaren läser in
                //NumberFormatException = om det som användaren matar in ej kan omvandlas till ett tal, skrivs texten "could not convert..."
                return Integer.parseInt(scanner.nextLine());
              } catch (NumberFormatException number) {
                System.out.println("-----------Could not convert to number, please try again!------------");
            }
        }
    }
    /**Metod showMenu: visar menyalternativ som användaren kan välja genom att trycka på valt nr
    *Scanner som läser in
    *Så länge som continueToRun är sant, körs valen
    *Anropar metoden readIntegerFromUser för att läsa in heltal från användaren
     */
    public void showMenu() {
        boolean continueToRun = true;
        while (continueToRun) {
            System.out.println("------------DEAR USER, PLEASE MAKE A CHOICE FROM THE LIST-------------------");
            System.out.println("[1] Show all books");
            System.out.println("[2] Show information about a book");
            System.out.println("[3] Borrow book");
            System.out.println("[4] Search book");
            System.out.println("[5] Show list with my borrowed books");
            System.out.println("[6] Return book");
            System.out.println("[7] Show available books");
            System.out.println("[8] Show all users (librarian only)");
            System.out.println("[9] Exit libraryprogram");
            Scanner scanner = new Scanner(System.in);
            int choice = readIntegerFromUser("------CHOOSE BY TYPE IN A NUMBER FROM THE MENU AND PRESS ENTER---------");

            //switch case meny där olika metoder (se nedan) anropas
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
                    System.out.println("-------The program ends, hope see you soon!------");
                    continueToRun = false;
                    exit();
                    break;
                default:
                    System.out.println("---------Wrong menu choice, please try again-----------");
                    break;
            }
        }
    }
    //En metod som visar alla böcker via for-each-loop som går igenom alla böcker och printar ut de
    private void showBooks() {
        System.out.println("Here is a list of all the books: ");
        ArrayList<Book> allBooks = getAllBooks();

        for (Book book : allBooks) {
            System.out.println(book);
        }
    }

    private ArrayList<Book> getAllBooks() {
        ArrayList<Book> allBooks = new ArrayList<>(availableBooks);
        for (User user : users) {
            allBooks.addAll(user.getBorrowedBooks());
        }

        return allBooks;
    }
    /**Metod som visar information om böckerna
    *Går igenom varje bok (for-loop) och skriver ut dem
    *%d, tar ett index boken har = books.get(i), %s = hämtar ut strängen, i detta fall Title(book.getTitle)
    *Listan börjar från 1, alltså i + 1 istället för 0
     */
    private void showInformation() {
        ArrayList<Book> books = getAllBooks();

        while (true) {
            for (int i = 0; i < books.size(); i++) {
                System.out.printf("%d. %s \n", i + 1, books.get(i).getTitle());
            }
            /**Anropar metod för att läsa in heltal från användaren, användaren kan mata in ett index som scannas in
             *Om valet är mindre än 1 och större än arraylistans längd, kan man ej välja boken
             *Annars hämtas bokvalet ut (books.get(choice -1), -1 för att listan början på 0 och användaren räknar från 1
             *Om man ej har - 1 får man ex nr 3 när man väljer nr 4
             */
            int choice = readIntegerFromUser("Enter the number of some of the book above to get more info about that book:");
            if (choice < 1 || choice > books.size()) {
                System.out.println("----------------You typed in wrong number-------------");
                continue;
            }
            System.out.println("You choosed this book: ");
            System.out.println(books.get(choice - 1));
            break;
        }
    }
    /**metod: borrowBook, Lista av tillgängliga böcker, Går igenom alla böcker
    *om boken inte är utlånad (! book.isBorrowed), lägger man till boken i listan availableBooks
    */
    private void borrowBook() {
        /**
         *  Går igenom de tillgängliga böckerna och skriver ut de, (i++ = lägger till)
         * %d, tar ett nr = books.get(i), alltså vilket index boken har, %s = hämtar ut strängen, i detta fall Title(book.getTitle)
         * Listan börjar från 1, alltså i + 1 istället för 0
         */
        while(true) {
                //System.out.println("2");
            for (int i = 0; i < availableBooks.size(); i++) {
                //System.out.println("3");
                System.out.printf("%d. %s \n", i + 1, availableBooks.get(i).getTitle());
            }
            //Användaren kan mata in ett index som scannas in, anropar readIntegerFromUser för att se om det är ett heltal användaren matar in
            //If för att kontrollera om valet är mindre än 0 eller större än listans size
            // -1 för att listan börjar på 0, användarvänligt då första valet ska vara 1 och inte 0 (-1)
            //Hämtar tillgängliga böcker som man kan låna
            int choice = readIntegerFromUser("Please type the number of the book you wan't to borrow: ");
            if (choice < 1 || choice > availableBooks.size()) {
                //System.out.println("4");
                System.out.println("------------Wrong number-------------");
                continue;
            }
                //System.out.println("4");
            System.out.println("The book you borrowed: " + availableBooks.get(choice - 1));

            user.borrowBook(availableBooks.remove(choice - 1));
            break;
        }

    }
    /**Metod där man kan söka på bok, genom olika val, titel, författare
    *En while loop med alternativ där man kan söka bok på titel eller författare, scanner för att läsa in
    *if sats för att kontrollera om valen är tillgängliga, om det är 0, går tillbaka till huvudmenyn
    *If sats: Om valet är 1 och boken har rätt titel, omvandlar boktiteln till små bokstäver
    *som användaren söker efter (contains- kollar om en textsträng innehåller en annan textsträng)
     */
        private void searchChoice() {
            boolean continueToRun = true;
            System.out.println("-----------Dear user, please make a choice-----------");
            while (continueToRun) {
                System.out.println("0. Return to main menu");
                System.out.println("1. Press 1 and enter to search by Title");
                System.out.println("2. Press 2 and enter to search by Author");
                //Anropar metod för att mata in till ett heltal
                int choice = readIntegerFromUser("-------------Enter choice----------------");
                if (choice < 0 || choice > 2) {
                    System.out.println("----------Dear user, you entered wrong number, try again with the choices below-----------");
                    continue;
                }
                if (choice == 0) {
                    return;
                }
                /**Välj author or title, går igenom alla böcker via for-each
                *If ---> om valet är 1 (hämta titel) eller 2 (hämta författare),
                *programmet kan konvertera boktitel och författare som små bokstäver(toLowerCase) för att
                *resultat ska visas även om användare matar in stora/små bokstäver
                *Contains = innebär att datorn är nöjd så länge några bokstäver i följd matchar
                 */
                System.out.println("-----------If you choose 1. Type the book title and press enter---------------");
                System.out.println("-----------If you choose 2. Type type the author and press enter --------------");
                String search = scanner.nextLine();
                for (Book book : getAllBooks()) {
                    if (choice == 1 && book.getTitle().toLowerCase().contains(search.toLowerCase())) {
                        System.out.println("You wrote: " + search);
                        System.out.println("We found: " + book.getTitle());
                    }
                    if (choice == 2 && book.getAuthor().toLowerCase().contains(search.toLowerCase())) {
                        System.out.println("You wrote: " + search);
                        System.out.println("We found: " + book.getAuthor());
                    }
                }
                break;
            }
        }
             //Metod som visar de lånade böckerna, Foor - each loop som lopar igenom användarens lånade böcker, via get metoden
            //Går igenom böckerna som en användare har lånat
            private void showBorrowedBooks () {
                System.out.println("Here is the list with your borrowed books: ");
                if(user.getBorrowedBooks().isEmpty()) {
                    System.out.println("You have no borrowed books!");
                    return;
                }
                for (Book book : user.getBorrowedBooks()) {
                    System.out.println(book);
                }
            }
            //Metod som lämnar tillbaka bok
            //Skriver ut alla lånade böcker som användaren har lånat, via for-loop
            private void returnBook () {
                System.out.println("Here is the list of books that you can return: ");
                if(user.getBorrowedBooks().isEmpty()) {
                    System.out.println("------You have no books to return!-----------");
                    return;
                }
                for (int i = 0; i < user.getBorrowedBooks().size(); i++) {
                    System.out.printf("%d. %s \n", i + 1, user.getBorrowedBooks().get(i).getTitle());
                }
                /**Användaren kan mata in ett index på den bok som ska lämnas in, som scannas in
                *Anropar metoden readIntegerFromUser för att se om det användaren matar in var ett heltal
                *kontrollerar via en if-sats om numret är korrekt
                *Hämtar böckerna
                *Anropar returnBook från user, skickar med boken som användaren har valt, .get hämtar ut en specifik bok
                 */
                int choice = readIntegerFromUser("Please type the number of book you wan't to return: ");
                if(choice <1 || choice > user.getBorrowedBooks().size()) {
                    System.out.println("----Wrong number----");
                    return;
                }
                Book returnedBook = user.returnBook(choice - 1);
                availableBooks.add(returnedBook);
            }
            //Metod som visar tillgängliga böcker, om boken inte är utlånad (!book.isBorrowed)- visas boken
            private void showAvailableBooks () {
                System.out.println("Here are the available books: ");
                for (Book book : availableBooks) {
                    //if (!book.isBorrowed()) {
                        System.out.println(book);
                    //}
                }
            }
            //om användaren inte är bibliotikarie(! user instanceof Librarian) skrivs felmeddelande ut och metoden avslutas
           //Går igenom alla användare och skriver ut namnen (skriver även ut librarians, då dessa är en typ av användare)
            private void showAllUsers () {
                if (!(user instanceof Librarian)) {
                    System.out.println("Dear user, this choice is only available for the librarians");
                    return;
                }
                System.out.println("Dear librarian, here is the name of all users in the library: ");
                for (User user : users) {
                    System.out.println(user.getName());
                }
            }
            //Metod för att avsluta programmet, sparar ner användare och böcker till fil när programmet avslutas
            private void exit () {
            FileUtility.saveObject(LISTOFUSER_FILE, users);
            FileUtility.saveObject(LISTOFBOOK_FILE, availableBooks);
            }
            //metod createBook. skapar en Lista av böcker och lägger till de i listan med title, author, description
                private void createBook () {
                List<Book> bookList = new ArrayList();
                bookList.add(new Book("Harry Potter","J.K Rowling", "A story about the wizard Harry Potter"));
                bookList.add(new Book("A remote coust","Jenny Colgan", "Is about an island called Mure and life on the island"));
                bookList.add(new Book("Java, A beginner`s guide", "Herbert Schildt", "Study-book about Java"));
                bookList.add(new Book("Nalle Puh", "A.A Milnes", "About Nalle Puh and his friends in the forest"));

                //FileUtility : sparar ner boklistan till fil
                FileUtility.saveObject(LISTOFBOOK_FILE, bookList);
            }

            //Metod createUser som skapar användare,lista av User (List<User>defaultUser = new ArrayList<>), lägger till de i listan
            // Lägger till 3 st användar, skapar och lägger till 2 st bibliotikarier
            private void createUser () {
                List<User> defaultUser = new ArrayList<>();
                defaultUser.add(new User("Kalle"));
                defaultUser.add(new User("Sven"));
                defaultUser.add(new User("Stina"));
                defaultUser.add(new User("Johan"));
                defaultUser.add(new User("Mimmi"));
                defaultUser.add(new Librarian("Kajsa"));
                defaultUser.add(new Librarian("Pelle"));

                FileUtility.saveObject(LISTOFUSER_FILE, defaultUser);   //Skriver ner user och librarian till fil
            }
}
