package com.ajay.messenger.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {
    private String title;
    private String author;
    private double price;
 
    public Book() {
    }
 
    public Book(String title, String author, double price) {
    	this.title = title;
    	this.author = author;
    	this.price = price;
    
    }
    
    public String toString() {
        return String.format("%s - %s - %f", title, author, price);
    }
 
    // getters and setters
}