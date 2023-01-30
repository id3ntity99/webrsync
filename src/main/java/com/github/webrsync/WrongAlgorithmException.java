package com.github.webrsync;

public class WrongAlgorithmException extends RuntimeException {
    public WrongAlgorithmException(String s, Throwable t) {
        super(s, t);
    }
}
