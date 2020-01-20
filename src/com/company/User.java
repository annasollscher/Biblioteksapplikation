package com.company;

import java.io.Serializable;
import java.util.ArrayList;
//implementerar Serializable för att kunna läsa om till fil
public class User implements Serializable {
    //Privat attribut name
    //Har en lista av böcker
    private String name;
    private ArrayList<Book>books = new ArrayList<>();

    //konstruktor med namn
    public User(String name) {
        this.name = name;
    }
    //metod som visar lånade böcker
    public void showBorrowedBooks () {
    }

}
