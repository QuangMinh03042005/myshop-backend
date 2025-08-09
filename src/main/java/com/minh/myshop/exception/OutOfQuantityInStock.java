package com.minh.myshop.exception;

public class OutOfQuantityInStock extends Exception{
    public OutOfQuantityInStock(String message) {
        super(message);
    }
}
