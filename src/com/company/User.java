package com.company;

import java.io.Serializable;
import java.util.ArrayList;
//Ska ha en lista på lånade böcker som användaren har lånat
public class User implements Serializable {
    private String name;
    private ArrayList<Book>books = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }
    public void showBorrowedBooks () {
    }

}
