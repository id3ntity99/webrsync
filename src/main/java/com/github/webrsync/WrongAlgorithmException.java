package com.github.webrsync;

@Deprecated
public class WrongAlgorithmException extends RuntimeException {
    public WrongAlgorithmException(String s, Throwable t) {
        super(s, t);
    }
}
